import java.net.DatagramPacket;
import java.net.DatagramSocket;

import common.Utils;

public class NetworkMonitoring extends Thread {
	// Thread responsável por monitorizar as redes diponíveis
	
	public void run() {
		try {
			byte[] receiveData = new byte[1024];

			while(true)
			{

				DatagramSocket serverSocket = new DatagramSocket(Utils.portaAnuncioRede);

				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String sentence = new String( receivePacket.getData());

				
				if (MNGUI.actualNetwork == 0){

					// está na home
					if (Integer.parseInt(sentence.split(" ")[0]) == 0){
						MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Recebe broadcast: " + sentence);
					}
				}
				else if (MNGUI.actualNetwork == 1){
					// esta na visitor
					if (Integer.parseInt(sentence.split(" ")[0]) == 1){
						MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Recebe broadcast: " + sentence);
					}
				}
				serverSocket.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
