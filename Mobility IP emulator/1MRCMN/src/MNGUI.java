import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import javax.swing.JOptionPane;

import mrc_interface.Pacote;
import mrc_interface.PedidoCancelamentoRegisto;
import mrc_interface.PedidoRegisto;

import common.Utils;

public class MNGUI extends javax.swing.JFrame {

	private static final long serialVersionUID = 4387789612972771953L;
	public static boolean optimized = false;
	public static int actualNetwork = -1; // Home Network

	public MNGUI() {

		initComponents();

	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {

		setTitle("Mobility Node");
		chooseNetworkLabel = new javax.swing.JLabel();
		networkName = new javax.swing.JComboBox();
		connect = new javax.swing.JButton();
		nodeNameLabel = new javax.swing.JLabel();
		nodeName = new javax.swing.JTextField();
		passwordLabel = new javax.swing.JLabel();
		password = new javax.swing.JTextField();
		sendPacket = new javax.swing.JButton();
		eventsLabel = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();


		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		chooseNetworkLabel.setText("Choose Network:");

		networkName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Home Network", "Foreign Network" }));

		connect.setText("Connect");

		nodeNameLabel.setText("Node Name:");

		nodeName.setText("Node1");

		passwordLabel.setText("Password:");

		password.setText("mrcteste");

		sendPacket.setText("Send Packet to CN");

		eventsLabel.setText("Events:");

		text.setColumns(20);
		text.setRows(5);
		jScrollPane1.setViewportView(text);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(nodeNameLabel)
												.addComponent(chooseNetworkLabel))
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup()
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
																.addComponent(nodeName, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGroup(layout.createSequentialGroup()
																		.addGap(10, 10, 10)
																		.addComponent(networkName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
																		.addComponent(sendPacket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(connect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addGroup(layout.createSequentialGroup()
																				.addComponent(eventsLabel)
																				.addGap(0, 0, Short.MAX_VALUE))
																				.addGroup(layout.createSequentialGroup()
																						.addComponent(passwordLabel)
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																						.addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
																						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(nodeNameLabel)
								.addComponent(nodeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(4, 4, 4)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(passwordLabel))
										.addGap(9, 9, 9)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chooseNetworkLabel)
												.addComponent(networkName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(connect)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(sendPacket)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(eventsLabel)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
												.addContainerGap())
				);

		pack();


		connect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (networkName.getSelectedIndex() == 0){

					if (actualNetwork == 1 ){
						// se o cliente pretende voltar a sua rede inicial
						PedidoCancelamentoRegisto pc = new PedidoCancelamentoRegisto();
						pc.setNodeName(nodeName.getText());
						pc.setPassword(password.getText());

						try{
							Socket clientSocket = new Socket(Utils.ipHA, Utils.portaHomeAgent);
							ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
							ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());

							outStream.writeObject(pc);
							MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Envia pedido de cancelamento de registo ao HA");

							if (inStream.readInt() == 1){
								actualNetwork = 0;
								MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Pedido Aceite, voltou ao HA");
							}
							else
								MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Pedido Rejeitado credenciais incorrectas");

						}
						catch(Exception e){
							e.printStackTrace();
						}

					}
					else{
						//Home network
						MNGUI.actualNetwork = 0;
						MNGUI.text.setText("");
						MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Conectou-se à Home Network");
					}
				}
				else{
					// Foreign Network
					MNGUI.text.setText("");
					MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Conectou-se à Foreign Network");
					PedidoRegisto p = new PedidoRegisto();
					p.setNodeName(MNGUI.nodeName.getText());
					p.setMAC(UUID.randomUUID().toString());
					p.setHoA("Home Agent 0");

					p.setPassword(MNGUI.password.getText());

					try{
						Socket clientSocket = new Socket(Utils.ipCoA, Utils.portaForeignAgent);
						ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
						ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());

						outStream.writeObject(p);
						MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Envia pedido de registo ao FA");

						if (inStream.readInt() == 1){
							MNGUI.actualNetwork = 1;
							MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Pedido Aceite, está Connectado");
						}
						else{
							MNGUI.actualNetwork = -1;
							MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Pedido Rejeitado");
						}

					}
					catch(Exception e){
						//e.printStackTrace();
						actualNetwork = -1;
						MNGUI.text.setText(MNGUI.text.getText()+"\n"+"Rede Indisponível");
					}
				}

			}
		});

		sendPacket.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (actualNetwork == -1){
					JOptionPane.showMessageDialog(null, "Não se encontra conectado a uma rede.");
				}
				else{
					Pacote p = new Pacote(nodeName.getText(),"CN","example data");
					MNGUI.text.setText(MNGUI.text.getText()+"\nEnvia pacote"+p);

					if (actualNetwork == 0){
						try{
							Socket clientSocket = new Socket(Utils.ipHA,Utils.portaHomeAgent);
							ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
							outStream.writeObject(p);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
					else{
						if (optimized == false){
							try{
								Socket clientSocket = new Socket(Utils.ipCoA,Utils.portaForeignAgent);
								ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
								outStream.writeObject(p);
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
						else{
							try{
								Socket clientSocket = new Socket(Utils.ipCN,Utils.portaCN);
								ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
								outStream.writeObject(p);
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}


					}


				}
			}
		});
	}

	public static void main(String args[]) {

		text = new javax.swing.JTextArea();

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MNGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MNGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MNGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MNGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MNGUI().setVisible(true);
			}
		});

		// Start Threads    
		NetworkMonitoring nm = new NetworkMonitoring();
		nm.start();

		ListeningForPackets lfp = new ListeningForPackets();
		lfp.start();

	}

	// Variables declaration - do not modify
	private javax.swing.JLabel chooseNetworkLabel;
	private javax.swing.JButton connect;
	private javax.swing.JLabel eventsLabel;
	private javax.swing.JScrollPane jScrollPane1;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox networkName;
	public static javax.swing.JTextField nodeName;
	private javax.swing.JLabel nodeNameLabel;
	public static javax.swing.JTextField password;
	private javax.swing.JLabel passwordLabel;
	private javax.swing.JButton sendPacket;
	public static javax.swing.JTextArea text;
	// End of variables declaration
}
