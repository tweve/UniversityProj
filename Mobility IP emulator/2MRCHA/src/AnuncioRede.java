import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import common.Utils;

public class AnuncioRede extends Thread{

	int sequenceNumber = 0;


	public void run() {
		while (true){
			try{
				if (sequenceNumber >65635){
					sequenceNumber = 256;
				}
				HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+ "Anuncia a Home Network...");

				String netName = "0 " + sequenceNumber + " Home Network";
				DatagramSocket socket = new DatagramSocket();
				InetAddress group = InetAddress.getByName(Utils.ipEnvioAnuncio);
				DatagramPacket packet = new DatagramPacket(netName.getBytes(), netName.getBytes().length, group, Utils.portaAnuncioRede);
				socket.send(packet);

				socket.close();
				sequenceNumber ++;

				sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
