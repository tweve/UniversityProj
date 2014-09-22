import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextArea;

import mrc_interface.BindingUpdate;
import mrc_interface.Pacote;
import mrc_interface.PedidoCancelamentoRegisto;
import mrc_interface.PedidoRegisto;

import common.Utils;

public class HomeAgentGUI extends javax.swing.JFrame {

	private static final long serialVersionUID = -3638076809404281335L;
	static ArrayList<MobilityBindingTableEntry> MBT = new ArrayList<MobilityBindingTableEntry>();
	static JFrame frame = new JFrame("MRC - Home Agent");
	public static JTextArea text=new JTextArea(); 
	public static javax.swing.JTable table = new JTable();


	public static int MBTHasMNEntry(String mn){
		int pos = -1;
		for (MobilityBindingTableEntry element : MBT){
			pos++;
			if (element.getMN().equals(mn)){
				return pos;
			}
		}
		return pos;
	}

	public static int MBTHasCoAEntry(String mn){
		int pos = -1;
		for (MobilityBindingTableEntry element : MBT){
			pos++;
			if (element.getCoA().equals(mn)){
				return pos;
			}
		}
		return pos;
	}

	public static String[][] getTable(){
		String[][] temp = new String[HomeAgentGUI.MBT.size()][4];

		for (int i = 0;i< HomeAgentGUI.MBT.size();i++ ){
			MobilityBindingTableEntry mbe = HomeAgentGUI.MBT.get(i);
			temp[i][0] = mbe.getMN();
			temp[i][1] = mbe.getHoA();
			temp[i][2] = mbe.getCoA();
			temp[i][3] = ""+mbe.getLifeTime();

		}
		return temp;
	}

	public HomeAgentGUI() {
		initComponents();
	}

	private void initComponents() {

		setTitle("Home Agent");
		jLabel1 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		jLabel2 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel1.setText("Events:");

		text.setColumns(20);
		text.setRows(5);
		jScrollPane1.setViewportView(text);

		HomeAgentGUI.table.setModel(new javax.swing.table.DefaultTableModel(
				HomeAgentGUI.getTable(),
				new String [] {
					"Node Name","Home Address", "Care of Address", "TTL"
				}
				));

		jScrollPane2.setViewportView(table);

		jLabel2.setText("Mobility Binding Table:");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane2)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
												.addGap(0, 0, Short.MAX_VALUE))
												.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
												.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel2)
						.addGap(9, 9, 9)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		pack();
	}// </editor-fold>

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(HomeAgentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(HomeAgentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(HomeAgentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(HomeAgentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new HomeAgentGUI().setVisible(true);
			}
		});

		TemporizadorTTL tp = new TemporizadorTTL();		
		tp.start();
		AnuncioRede an = new AnuncioRede();	
		an.start();

		try{
			ServerSocket listener = new ServerSocket(Utils.portaHomeAgent);
			Socket server;
			int maxConnections = 0;
			int i = 0;

			while((i++ < maxConnections) || (maxConnections == 0)){

				server = listener.accept();
				doComms conn_c= new doComms(server);
				Thread t = new Thread(conn_c);
				t.start();
			}
			listener.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}
	// Variables declaration - do not modify
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;

}

class doComms implements Runnable {
	private Socket server;

	doComms(Socket server) {
		this.server=server;
	}

	public void run () {

		try {

			ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());

			Object o =  ois.readObject();

			if (o instanceof Pacote){
				Pacote p = (Pacote) o;
				HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"HA recebe "+ p);

				int index = 0;

				if ( p.getSource().equals("CN")){
					if ((index = HomeAgentGUI.MBTHasMNEntry(p.getDestination())) != -1 ){
						// existe na MBT
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Existe na MBT");

						Pacote newP = new Pacote("HA",HomeAgentGUI.MBT.get(index).getCoA(),p);

						Socket clientSocket = new Socket(Utils.ipCoA,Utils.portaForeignAgent );
						ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
						outStream.writeObject(newP);
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"HA reencaminha "+newP);

					}
					else{
						// nao existe na MBT
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Não existe na MBT ");

						Socket clientSocket = new Socket(Utils.ipMN,Utils.portaMN );
						ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
						outStream.writeObject(p);
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"HA envia "+p);
					}
				}
				else if (p.getSource().substring(0, 3).equals("CoA")){
					if ((index = HomeAgentGUI.MBTHasCoAEntry(p.getDestination())) != -1 ){
						// CoA existe na MBT

						Pacote desencapsulado = (Pacote)p.getData();
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"HA desencapsula pacote ");

						Socket clientSocket = new Socket(Utils.ipCN, Utils.portaCN);
						ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
						outStream.writeObject(desencapsulado);
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"HA reencaminha "+desencapsulado);
						//clientSocket.close();

					}
					else{
						// nao existe na MBT
						// ignora pacote
					}

				}
				else if(p.getSource().substring(0, 4).equals("Node")){
					Socket clientSocket = new Socket(Utils.ipCN, Utils.portaCN);
					ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
					outStream.writeObject(p);
					HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"HA reencaminha "+p);

				}
			}

			else if (o instanceof PedidoRegisto){
				HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Recebe Pedido de Registo");

				PedidoRegisto pr = (PedidoRegisto) o;
				int index = -1;
				index = HomeAgentGUI.MBTHasMNEntry(pr.getNodeName());

				if (index >-1 ){
					HomeAgentGUI.MBT.get(index).setCoA(pr.getCoA());
					HomeAgentGUI.MBT.get(index).setLifeTime(10);

				}
				else{
					if (pr.getPassword().equals("mrcteste")){
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Aceita Crediciais ");
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Adiciona entrada à MBT ");
						MobilityBindingTableEntry e = new MobilityBindingTableEntry(pr.getNodeName(),"HoA","CoA",5);
						HomeAgentGUI.MBT.add(e);

						HomeAgentGUI.table.setModel(new javax.swing.table.DefaultTableModel(
								HomeAgentGUI.getTable(),
								new String [] {
									"Node Name","Home Address", "Care of Address", "TTL"
								}
								));



						//	try
						//	{
						oos.writeInt(1);
						oos.flush();

						//Socket clientSocket = new Socket(Utils.ipCoA, Utils.portaForeignAgent);
						//ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
						//outStream.writeObject(true);
						//HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Envia confirmacao true");

						//	}
						//catch(Exception gh){
						//gh.printStackTrace();
						//}//

					}
					else{
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Rejeita Crediciais ");
						//oos.flush();
						oos.writeInt(0);
						oos.flush();
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"escreve 0 ");

						//oos.flush();

						/*	try{
							Socket clientSocket = new Socket(Utils.ipCoA, Utils.portaForeignAgent);
							ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
							outStream.writeObject(false);
							HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Envia confirmacao false");
							outStream.close();
						}
						catch(Exception gh){
							gh.printStackTrace();
						}
						 */
					}
				}
			}
			else if (o instanceof PedidoCancelamentoRegisto){
				HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Recebe pedido Cancelamento Registo");

				PedidoCancelamentoRegisto pc = (PedidoCancelamentoRegisto) o;
				int index =HomeAgentGUI.MBTHasMNEntry(pc.getNodeName()); 
				if (index >-1){
					if (pc.getPassword().equals("mrcteste")){
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Pedido da Cancelamento de Registo aceite.");
						HomeAgentGUI.MBT.remove(index);
						oos.writeInt(1);
						oos.flush();
						HomeAgentGUI.table.setModel(new javax.swing.table.DefaultTableModel(
								HomeAgentGUI.getTable(),
								new String [] {
									"Node Name","Home Address", "Care of Address", "TTL"
								}
								));

						Socket clientSocket = new Socket(Utils.ipCN, Utils.portaCN);
						ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());

						outStream.writeObject(pc);
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Envia pedido de cancelamento Binding Entry para o CN");

					}
					else{
						HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Credenciais de Pedido de Cancelamento de registo rejeitadas.");
						oos.writeInt(0);
						oos.flush();
					}
				}
				else{
					HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Aceite");
					oos.writeInt(1);
					oos.flush();
				}

			}
			else if (o instanceof BindingUpdate){

				HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Reencaminha Binding Update para o CN");

				Socket clientSocket = new Socket(Utils.ipCN, Utils.portaCN);
				ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
				ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
				outStream.writeObject((BindingUpdate)o);

				if (inStream.readInt()== 1){
					HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Reencaminha Binding ack para o CoA");
					oos.writeInt(1);
					oos.flush();
				}
				else{
					oos.writeInt(0);
					oos.flush();
				}
			}


		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

}
