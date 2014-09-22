/*
 * Autores : 
 *              Igor Nelson Garrido da Cruz                           2009111924                 
 *              Saul Miguel Pereira da Costa Santos                   2009118728
 * Porto 9500 para comandos
 * Porto 9501 para ficheiros
 */

import OAuth.GoogleOAuth;
import interfacedropbox.*;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;
import java.rmi.RemoteException;
import java.util.*;

public class ServerMain {

    static UserList userList = null;
    static TaskList taskList = null;
    static ArrayList<ObjectOutputStream> outputs = null;
    static ArrayList<String> outputs_name = null;
    static ArrayList<ClientInterface> client = null;
    static String destinationip = null;
    static long time[] = new long[1];
    static boolean pleaseWait = true;
    static GoogleOAuth REST = null;
    

    public static void main(String args[]) {


        if (args.length == 0) {

            System.out.println("java MyDropBoxServer MyDropBoxServerALternativeServer_hostname");
            System.exit(0);
        }
        System.out.println("Server is Sleeping");
        time[0] = System.currentTimeMillis();


        Thread receiveUDP = new receiveUDP(args[0], time);


        System.out.println(System.currentTimeMillis() - time[0]);
        /*while (System.currentTimeMillis() - time[0] < 20000) {
            System.out.println(System.currentTimeMillis() - time[0]);
            System.out.println("Server is Sleeping");
            try {
                Thread.sleep(4000);
            } catch (Exception e) {
            }
        }*/
        System.out.println("Server woke up");
        Thread sendUDP = new sendUDP(args[0], time);
        start();
        try {
            int serverPort = 9500;

            System.out.println("A Escuta no Porto 9500");

            ServerSocket listenSocket = new ServerSocket(serverPort);

            System.out.println("LISTEN SOCKET=" + listenSocket);

            while (true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                new Connection(clientSocket, userList, taskList, outputs, client, outputs_name);
            }
        } catch (IOException e) {
            System.out.println("Listen:" + e.getMessage());
        }

    }

    static void start() {
        // Create Shared Folder in case of inexistence

        File f = new File("MyDropBoxFolder");
        if (!f.exists()) {
            f.mkdir();
        }

        // Start ArrayLists
        userList = new UserList();
        taskList = new TaskList();
        outputs = new ArrayList<ObjectOutputStream>();
        client = new ArrayList<ClientInterface>();
        outputs_name = new ArrayList<String>();
        REST = new GoogleOAuth();
        REST.setup();

        // Load UserList from file to Memory:

        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream("userlist.bin"));
            userList = (UserList) inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Users information not found.\t>Starting with blank user details.");
        } catch (ClassNotFoundException e) {
            System.out.println("userlist.bin\t>Corrupt file,Starting with blank user details.");
        } catch (Exception e) {
        }

        // Load TaskList from file to Memory:

        try {
            inputStream = new ObjectInputStream(new FileInputStream("tasklist.bin"));
            taskList = (TaskList) inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Task information not found.\t>Starting with blank task details.");
        } catch (ClassNotFoundException e) {
            System.out.println("tasklist.bin\t>Corrupt file,Starting with blank user details.");
        } catch (Exception e) {
        }

        // Create thread to accept file transfer connections 

        userList.setAllOffline();

        Thread files = new Files();



        try {
            RMIDrop ti = new RMIDrop(userList, taskList, outputs, client);
            LocateRegistry.createRegistry(1099).rebind("RMIDROP", ti);
            System.out.println("RMI ready...");

        } catch (RemoteException e) {
            System.out.println("RMI error");
        }

    }
}
//= Thread para tratar de cada canal de comunicação com um cliente
class Connection extends Thread {

    ObjectInputStream in;
    ObjectOutputStream out;
    Socket clientSocket;
    int thread_number;
    UserList userList;
    TaskList taskList;
    boolean active; //variável para saber se a ligaçao esta activa
    static Object temp;
    ArrayList<ObjectOutputStream> outputs = null;
    static ArrayList<ClientInterface> client = null;
    static ArrayList<String> outputs_names = null;

    public Connection(Socket aClientSocket, UserList userList, TaskList taskList, ArrayList<ObjectOutputStream> outputs, ArrayList<ClientInterface> client, ArrayList<String> name) {

        try {
            clientSocket = aClientSocket;
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            this.outputs = outputs;
            this.client = client;

            this.userList = userList;
            this.taskList = taskList;
            this.outputs_names = name;




            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }
    //=============================

    public void run() {

        try {
            while (true) {
                temp = new Object();
                temp = in.readObject();
                verify(temp);
                active = true; //ligaçao activa
            }
        } catch (EOFException e) {
        } catch (IOException e) {


            for (int i = 0; i < outputs.size(); i++) {
                if (outputs.get(i).equals(out)) {

                    synchronized (outputs) {
                        outputs.remove(i);
                        userList.logout(outputs_names.get(i));
                        outputs_names.remove(i);
                    }
                }

            }

        } catch (ClassNotFoundException e) {
        }

    }

    public void verify(Object temp) {


        if (temp instanceof Login) {
            login((Login) temp);
        }

        if (temp instanceof Logout) {
            logout((Logout) temp);
        }

        if (temp instanceof Register) {
            register((Register) temp);
        }

        if (temp instanceof ShowTasks) {
            showTasks((ShowTasks) temp);
        }

        if (temp instanceof AddTask) {
            addTask((AddTask) temp);
        }

        if (temp instanceof EditTask) {
            editTask((EditTask) temp);
        }

        if (temp instanceof DeleteTask) {
            deleteTask((DeleteTask) temp);
        }

        if (temp instanceof ShowOnlineUsers) {
            showOnlineUsers((ShowOnlineUsers) temp);
        }

        if (temp instanceof ShowFiles) {
            showFiles((ShowFiles) temp);
        }

        if (temp instanceof DeleteFile) {
            deleteFile((DeleteFile) temp);
        }
        if (temp instanceof EditFile) {
            editFile((EditFile) temp);
        }

    }

    void register(Register temp) {
        if (!userList.contains(temp.getLogin())) {
            userList.add(temp.getLogin(), temp.getPass());
            userList.save();

            try {
                String response = "Successfully registered!";
                out.writeObject(response);
            } catch (Exception e) {
            }
        } else {
            try {
                String response = "Username already in use.";
                out.writeObject(response);

            } catch (Exception e) {
            }
        }

    }

    void login(Login temp) {
        System.out.println("login");
        if (userList.login(temp.getLogin(), temp.getPass())) {
            try {
                // Adiciona o utilizador a lista de conecçoes
                synchronized (outputs) {
                    outputs.add(out);
                    outputs_names.add(temp.getLogin());
                }

                AnswerLogin message = new AnswerLogin(temp.getLogin(), temp.getPass(), "Successfully logged in");
                notifyAll(temp.getLogin() + " logged in.");

                out.writeObject(message);

            } catch (Exception e) {
            }
        } else {
            try {
                String msg = "Wrong username or password!";
                out.writeObject(msg);

            } catch (Exception e) {
            }
        }
        String notes = null;
        notes = userList.getNotifications(temp.getLogin());
        if (notes != null) {
            try {
                out.writeObject("\nThere was activity while you were offline:\n" + notes);
                userList.deleteNotifications(temp.getLogin());

            } catch (Exception e) {
            }
        }

    }

    void logout(Logout temp) {
        userList.logout(temp.getLogin());
        notifyAll(temp.getLogin() + " logged out.");
    }

    void showTasks(ShowTasks temp) {
        try {
            if (taskList.getid() == 0) {
                String aux = "TaskList is empty";
                out.writeObject(aux);
            } else {
                out.writeObject(taskList);
            }
            out.reset();
        } catch (Exception e) {
        }
    }

    void addTask(AddTask temp) {
        this.taskList.add(temp.getTask());
        taskList.save();
        notifyAll("A new task was added by " + temp.getLogin());
    }

    void editTask(EditTask temp) {
        try {
            EditTask temp2 = (EditTask) temp;
            if (taskList.rename(temp2.getid(), temp2.getTaskName())) {
                String aux = "Taskname successfully changed.";
                out.writeObject(aux);
                notifyAll("A task was edited by " + temp.getLogin());
            } else {
                String aux = "TaskID not found.";
                out.writeObject(aux);
            }
        } catch (Exception e) {
        }
        taskList.save();

    }

    void deleteTask(DeleteTask temp) {
        try {
            DeleteTask temp2 = (DeleteTask) temp;
            if (taskList.delete(temp2.id)) {
                String aux = "Task successfully deleted";
                out.writeObject(aux);
                notifyAll("A task was deleted by " + temp.login);
            } else {
                String aux = "TaskID not found.";
                out.writeObject(aux);
            }
        } catch (Exception e) {
        }
        taskList.save();
    }

    void showOnlineUsers(ShowOnlineUsers temp) {
        try {
            out.writeObject(userList.getOnline());
        } catch (Exception e) {
        }
    }

    void showFiles(ShowFiles temp) {
        String filelist = "Files present on server:\n";

        File dir = new File("MyDropBoxFolder");
        String[] children = dir.list();

        if (children == null) {
            filelist = "There are no files to list.";
        } else {
            for (int i = 0; i < children.length; i++) {
                filelist = filelist.concat("'" + children[i] + "'\t");
            }
        }


        try {
            out.writeObject(filelist);
        } catch (Exception e) {
        }
    }

    void downloadFile(DownloadFile temp) {
    }

    void deleteFile(DeleteFile temp) {
        try {
            String filename = "MyDropBoxFolder/" + temp.getFilename();


            File f = new File(filename);

            if (f.exists()) {
                f.delete();
                out.writeObject("File Sucessfully deleted");
            } else {
                out.writeObject("Inexistent Filename.");
            }

        } catch (Exception e) {
            try {
                out.writeObject("File open by another user.");
            } catch (Exception e2) {
            }
        }
        notifyAll("File deleted:" + temp.getFilename());

    }

    void editFile(EditFile temp) {



        File toBeRenamed = new File("MyDropBoxFolder/" + temp.getOldName());

        File newFile = new File("MyDropBoxFolder/" + temp.getNewName());

        //Rename
        toBeRenamed.renameTo(newFile);

    }

    void notifyAll(String message) {
        for (int i = 0; i < outputs.size(); i++) {
            try {
                outputs.get(i).writeObject(message);
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < client.size(); i++) {
            try {
                client.get(i).print_on_client(message);
            } catch (Exception e) {
            }
        }
        userList.notifyOffline(message);
    }
}
