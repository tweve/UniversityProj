import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import mrc_interface.BindingUpdate;
import mrc_interface.Pacote;

import common.Utils;


public class ListeningForPackets extends Thread {

	public void run(){


		try{

			ServerSocket listener = new ServerSocket(Utils.portaMN);
			Socket server;
			int maxConnections = 0;
			int i = 0;

			MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Escolha uma Rede");

			while((i++ < maxConnections) || (maxConnections == 0)){
				server = listener.accept();
				doComms conn_c= new doComms(server);

				Thread t = new Thread(conn_c);
				t.start();
			}
			
			listener.close();
			
		} catch (IOException ioe) {
			MNGUI.text.setText(MNGUI.text.getText()+"\n"+"IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
	}
}

class doComms implements Runnable {
	private Socket server;
	
	doComms(Socket server) {
		this.server=server;
	}

	@Override
	public void run () {

		try {
			if (MNGUI.actualNetwork != -1){
				
				ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
				Pacote p = (Pacote) ois.readObject();

				if (p.getDestination().equals(MNGUI.nodeName.getText())){
					MNGUI.text.setText(MNGUI.text.getText()+"\n"+"MN recebe "+ p);
				}
				
				if (MNGUI.actualNetwork == 1 && !MNGUI.optimized){
					MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Enviando Binding Update");
					
					BindingUpdate bu = new BindingUpdate();
					bu.setNode(MNGUI.nodeName.getText());
					bu.setCoA("CoA");
					
					try{
						Socket clientSocket = new Socket(Utils.ipCoA, Utils.portaForeignAgent);
						ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
						ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
						outStream.writeObject(bu);
						
						if (inStream.readInt()==1){
							MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Optimizacao Efectuada");							
							MNGUI.optimized = true;
						}
						else
							MNGUI.optimized = false;
						
					}
					catch(Exception e){
						e.printStackTrace();
					}
					
					
					
//					MNGUI.optimized = true;
					
				}
				
				server.close();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}