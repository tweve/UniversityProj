import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import common.Utils;


public class AnuncioDeRede extends Thread{
	
		public void run() {
			int sequenceNumber = 0;
			while (true){
				try{
					if (sequenceNumber >65635){
						sequenceNumber = 256;
					}
					FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Anuncia a Rede");

					String netName = "1 " + sequenceNumber + " Foreign Network";
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
