
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import interfacedropbox.*;
import java.io.File;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RMIDrop extends UnicastRemoteObject implements TaskInterface {

    static UserList userList = null;
    static TaskList taskList = null;
    static ArrayList<ObjectOutputStream> outputs = null;
    static ArrayList<ClientInterface> client = null;
    private static final long serialVersionUID = 1L;

    protected RMIDrop(UserList userList, TaskList taskList, ArrayList<ObjectOutputStream> outputs, ArrayList<ClientInterface> clients) throws RemoteException {

        super();
        RMIDrop.userList = userList;
        RMIDrop.taskList = taskList;
        RMIDrop.outputs = outputs;
        RMIDrop.client = clients;
    }

    public void subscribe(ClientInterface c) throws RemoteException {
        System.out.println("Subscribing ");
        client.add(c);
    }

    public String register(String login, String password) {
        if (!userList.contains(login)) {
            userList.add(login, password);
            userList.save();

            return "Successfully registered!";
        } else {
            return "Username already in use.";
        }

    }

    public AnswerLogin login(String login, String password) {
        if (userList.login(login, password)) {
            AnswerLogin message = new AnswerLogin(login, password, "Successfully logged in");
            notifyAll(login + " logged in.");
            return message;
        } else {
            AnswerLogin message = new AnswerLogin(login, password, "Wrong username or password!");
            return message;
        }
    }

    public String showTasks() {
        if (taskList.getid() == 0) {
            return "TaskList is empty";
        } else {
            return (taskList.toString());
        }


    }

    public String addTask(String login, Task task) {
        String temp = new String();
        
        taskList.add(task);
        taskList.save();
        notifyAll("A new task was added by " + login);
        
        return temp;

    }

    public String editTask(String login, int id, String newTaskName) {
        String temp = new String();

        if (taskList.rename(id, newTaskName)) {
            taskList.save();
            notifyAll("A task was edited by " + login);
            temp = "Taskname successfully changed.";
            taskList.save();
        } else {
            temp = "TaskID not found.";
        }


        return temp;

    }

    public String deleteTask(String login, int id) {

        String aux = null;
        if (taskList.delete(id)) {
            
            taskList.save();
            aux = "Task successfully deleted";
            notifyAll("A task was deleted by " + login);

        } else {
            aux = "TaskID not found.";
        }
        return aux;
    }

    public String showOnline() {
        return userList.getOnline();
    }

    public String showFiles() {
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


        return filelist;
    }

    public void editFile(String filename, String newFileName) {

        File toBeRenamed = new File("MyDropBoxFolder/" + filename);

        File newFile = new File("MyDropBoxFolder/" + newFileName);

        //Rename
        toBeRenamed.renameTo(newFile);
    }

    public void deleteFile(String name) {

        File f = new File("MyDropBoxFolder/" + name);

        if (f.exists()) {
            f.delete();
        }

        notifyAll("File deleted:" + name);
    }

    public void logout(String login) {
        userList.logout(login);
        notifyAll(login + " logged out.");
    }

    public String getNotifications(String login) {
        String notes = null;
        notes = userList.getNotifications(login);

        if (notes != null) {
            userList.deleteNotifications(login);
        }
        return notes;
    }

    void notifyAll(String message) {
        for (int i = 0; i < client.size(); i++) {
            try {
                client.get(i).print_on_client(message);
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < outputs.size(); i++) {
            try {
                outputs.get(i).writeObject(message);
            } catch (Exception e) {
            }
        }
        userList.notifyOffline(message);
    }
}