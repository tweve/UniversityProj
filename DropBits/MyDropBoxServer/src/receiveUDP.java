
import java.net.*;
import java.io.*;

public class receiveUDP extends Thread {

    DatagramSocket aSocket = null;
    String s;
    String ip;
    long time[];

    public receiveUDP(String ipdestino, long time[]) {
        this.ip = ipdestino;
        this.time = time;
        time[0] = System.currentTimeMillis();
        this.start();



    }

    public void run() {
        while (true) {
            time[0] = System.currentTimeMillis();
            try {
                aSocket = new DatagramSocket(9800);
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                aSocket.receive(receivePacket);
                String modifiedSentence = new String(receivePacket.getData());
            } catch (SocketException e) {
                System.out.println("Socket: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
                System.out.println("Error");
            } finally {
                if (aSocket != null) {
                    aSocket.close();
                }
            }
        }
    }
}
