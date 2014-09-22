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
import mrc_interface.PedidoRegisto;

import common.Utils;

public class FAGUI extends javax.swing.JFrame {

	private static final long serialVersionUID = -5379557990414818007L;
	static ArrayList<VisitorListEntry> VL = new ArrayList<VisitorListEntry>();
	static JFrame frame = new JFrame("MRC - Foreign Agent");
	static JTextArea textArea1=new JTextArea(); 
	public static javax.swing.JTable table = new JTable();

	public static String[][] getTable(){
		String[][] temp = new String[FAGUI.VL.size()][5];

		for (int i = 0;i< FAGUI.VL.size();i++ ){
			VisitorListEntry vle = FAGUI.VL.get(i);

			temp[i][0] = vle.getMN();
			temp[i][1] = vle.getMNHoA();
			temp[i][2] = vle.getMac();
			temp[i][3] = ""+vle.getTTL();
			temp[i][4] = ""+vle.isTemp();

		}
		return temp;
	}

	public FAGUI() {
		initComponents();
	}

	private void initComponents() {

		setTitle("Foreign Agent");
		jLabel1 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		jLabel2 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel1.setText("Events:");

		textArea1.setColumns(20);
		textArea1.setRows(5);
		jScrollPane1.setViewportView(textArea1);

		table.setModel(new javax.swing.table.DefaultTableModel(

				FAGUI.getTable(),
				new String [] {
					"Home Address", "Home Agent Adress", "MN Mac Address", "LifeTime", "Temporary"
				}
				));
		jScrollPane2.setViewportView(table);

		jLabel2.setText("Visitor List:");

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
			java.util.logging.Logger.getLogger(FAGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(FAGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(FAGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(FAGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FAGUI().setVisible(true);
			}
		});

		AnuncioDeRede ar = new AnuncioDeRede();
		ar.start();

		TemporizadorTTL tp = new TemporizadorTTL();
		tp.start();

		try{
			ServerSocket listener = new ServerSocket(Utils.portaForeignAgent);
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

			Object o = ois.readObject();

			if (o instanceof PedidoRegisto){
				FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Recebe Pedido de Registo");
				PedidoRegisto p = (PedidoRegisto)o;
				boolean existe = false;

				for(VisitorListEntry vle :FAGUI.VL){

					if (vle.getMN().equals(p.getNodeName()) && vle.isTemp() == false){
						FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"MN já existe na rede");
						vle.setTTL(5);
						existe = true;
						FAGUI.table.setModel(new javax.swing.table.DefaultTableModel(

								FAGUI.getTable(),
								new String [] {
									"Home Address", "Home Agent Adress", "MN Mac Address", "LifeTime", "Temporary"
								}
								));
					}
				}
				if (existe == false){
					FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Nova entrada adicionada à VL. ");

					VisitorListEntry v = new VisitorListEntry();
					v.setMN(p.getNodeName());
					v.setMac(p.getMAC());
					v.setMNHoA(p.getHoA());
					v.setTTL(5);
					v.setTemp(true);
					FAGUI.VL.add(v);

					FAGUI.table.setModel(new javax.swing.table.DefaultTableModel(

							FAGUI.getTable(),
							new String [] {
								"Home Address", "Home Agent Adress", "MN Mac Address", "LifeTime", "Temporary"
							}
							));

					p.setCoA("FAGUI");

					try{
						Socket clientSocket = new Socket(Utils.ipHA, Utils.portaHomeAgent);
						ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
						ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());

						outStream.writeObject(p);
						FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Envia pedido de registo ao HA");

						int accepted = inStream.readInt();

						if (accepted == 1){
							FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Resposta afirmativa do HA");

							v.setTemp(false);
							oos.writeInt(1);
							oos.flush();

						}
						else{
							FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Resposta negativa do HA");

							FAGUI.VL.remove(v);
							oos.writeInt(0);
							oos.flush();
						}
						FAGUI.table.setModel(new javax.swing.table.DefaultTableModel(

								FAGUI.getTable(),
								new String [] {
									"Home Address", "Home Agent Adress", "MN Mac Address", "LifeTime", "Temporary"
								}
								));
						//server.close();
					}
					catch(Exception e){
						e.printStackTrace();
					}

				}
			}

			if (o instanceof Pacote){

				Pacote p = (Pacote) o;
				if (p.getSource().equals("HA")){
					//PACOTE vem de Home agent
					Pacote temp = (Pacote)((Pacote) o).getData();

					for ( VisitorListEntry vle :FAGUI.VL ){
						if (vle.getMN().equals(temp.getDestination())){

							Socket clientSocket = new Socket(Utils.ipMN,Utils.portaMN );
							ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
							outStream.writeObject(temp);
							FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"ENVIA PACOTE PARA O MN:"+temp.getDestination());
							clientSocket.close();

						}
					}
				}
				else if(p.getSource().equals("CN")){

					Socket clientSocket = new Socket(Utils.ipMN,Utils.portaMN);

					Pacote temp = (Pacote) p.getData();

					ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
					outStream.writeObject(temp);
					FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Envia pacote para o Mobility Node "+temp.getDestination());
					clientSocket.close();

				}
				else{
					//Pacote vem de MN
					for ( VisitorListEntry vle :FAGUI.VL ){
						if (vle.getMN().equals(p.getSource())){

							Socket clientSocket = new Socket(Utils.ipHA,Utils.portaHomeAgent );

							Pacote temp = new Pacote("CoA","HA", p);

							ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
							outStream.writeObject(temp);
							FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Envia pacote para o Home Agent:"+temp.getDestination());

						}
					}

					
				}
				

			}
			else if (o instanceof BindingUpdate){
				FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Reencaminha Binding Update para HA");

				Socket clientSocket = new Socket(Utils.ipHA, Utils.portaHomeAgent);
				ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
				ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
				outStream.writeObject((BindingUpdate)o);

				if (inStream.readInt()==1){
					FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Reencaminha Binding ACK");
					oos.writeInt(1);
					oos.flush();
				}
				else{
					oos.writeInt(0);
					oos.flush();
				}
			}
			server.close();

		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

}


