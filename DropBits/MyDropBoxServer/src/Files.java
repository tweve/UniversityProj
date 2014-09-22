
import java.io.*;
import java.net.*;

public class Files extends Thread {

    public Files() {
        this.start();
    }

    public void run() {

        try {
            int serverPort = 9501;
            System.out.println("A Escuta no Porto 9501");
            ServerSocket listenSocket = new ServerSocket(serverPort);

            System.out.println("LISTEN SOCKET=" + listenSocket);

            while (true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                new FileConnection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen:" + e.getMessage());
        }
    }
}

class FileConnection extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public FileConnection(Socket aClientSocket) {


        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {

        String filename = null;
        int filesize = 0;
        int bytesRead = 0;
        int current = 0;
        boolean connectionUp = true;
        String option = null;

        while (connectionUp) {
            try {

                option = in.readUTF();
                if (option.equals("upload")) {

                    filename = (String) in.readUTF();

                    filesize = (Integer) in.readInt();

                    byte[] mybytearray = new byte[filesize];

                    FileOutputStream fos = new FileOutputStream(filename);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    bytesRead = in.read(mybytearray, 0, mybytearray.length);
                    current = bytesRead;

                    do {
                        bytesRead = in.read(mybytearray, current, (mybytearray.length - current));
                        if (bytesRead >= 0) {
                            current += bytesRead;
                        }
                    } while (bytesRead > 0);

                    bos.write(mybytearray, 0, current);
                    bos.flush();
                    bos.close();
                } else if (option.equals("download")) {
                    String file = in.readUTF();

                    filename = "MyDropBoxFolder/" + file;

                    File sendFile = new File(filename);

                    if (!sendFile.exists()) {
                        System.out.println("n existe");
                        out.writeUTF("Inexistent file");
                        break;
                    }

                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sendFile));

                    byte[] mybytearray = new byte[(int) sendFile.length()];

                    bis.read(mybytearray, 0, mybytearray.length);

                    out.writeUTF(filename);
                    out.writeInt((int) sendFile.length());
                    out.write(mybytearray, 0, mybytearray.length);

                    out.flush();
                    bis.close();
                }

            } catch (IOException e) {
                //for (int i=0;i<outputs.size();i++)
                connectionUp = false;
            }
        }
    }
}