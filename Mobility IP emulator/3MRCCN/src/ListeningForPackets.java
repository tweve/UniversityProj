import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import mrc_interface.BindingUpdate;
import mrc_interface.Pacote;
import mrc_interface.PedidoCancelamentoRegisto;

import common.Utils;


public class ListeningForPackets extends Thread {

	public void run(){


		try{

			ServerSocket listener = new ServerSocket(Utils.portaCN);
			Socket server;
			int maxConnections = 0;
			int i = 0;


			while((i++ < maxConnections) || (maxConnections == 0)){
				doComms connection;
				server = listener.accept();
				doComms conn_c= new doComms(server);

				Thread t = new Thread(conn_c);
				t.start();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

class doComms implements Runnable {
	private Socket server;
	private String line,input;

	doComms(Socket server) {
		this.server=server;
	}

	@Override
	public void run () {

		try {


			ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
			
			Object o = ois.readObject();
			
			if(o instanceof Pacote){
				Pacote p = (Pacote) o;
				CNGUI.jTextArea1.setText(CNGUI.jTextArea1.getText()+"\n"+"CN recebe "+ p);
			}
			else if (o instanceof BindingUpdate ){
				BindingUpdate b = (BindingUpdate) o;
				
				CNGUI.jTextArea1.setText(CNGUI.jTextArea1.getText()+"\n"+"CN recebe Binding Update");
				CNGUI.bindingTable.add(new BindingEntry(b.getNode(),b.getCoA()));
				
				CNGUI.jTextArea1.setText(CNGUI.jTextArea1.getText()+"\n"+"Envia Binding ACK true para HA");

				oos.writeInt(1);
				oos.flush();
			}
			else if (o instanceof PedidoCancelamentoRegisto){
				PedidoCancelamentoRegisto p = (PedidoCancelamentoRegisto) o;
				for (int i = 0;i<CNGUI.bindingTable.size();i++){
					if (CNGUI.bindingTable.get(i).getNodeName().equals(p.getNodeName())){
						CNGUI.bindingTable.remove(i);
					}
				}
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}