package com.mrc.saudeuc;

import java.io.DataOutputStream;
import java.net.Socket;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Consultas extends Activity{
	
	int pos = 0;

	TextView consulta;
	Button anterior;
	Button seguinte;
	Button bemail;
	TextView email;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.consultas);
		 consulta = (TextView)findViewById(R.id.tvConsultas);
		 anterior = (Button)findViewById(R.id.bAnterior);
		 seguinte = (Button)findViewById(R.id.bSeguinte);
		 bemail = (Button)findViewById(R.id.bSendEmail);
		 email = (TextView) findViewById(R.id.email);
		 
		 
		 anterior.setEnabled(false);
		 

		//getConsultas();
		 
		 setConsulta();
		
		anterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				try{
					pos--;
					setConsulta();
					seguinte.setEnabled(true);
					if (pos == 0){
						anterior.setEnabled(false);
					}
				}
				catch(Exception e){
					anterior.setEnabled(false);
				}
				
				
			}
		});
		
		seguinte.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				try{
					pos++;
					setConsulta();
					anterior.setEnabled(true);
					if (pos == Data.cons.size()-1){
						seguinte.setEnabled(false);
					}
				}
				catch(Exception e){
					seguinte.setEnabled(false);
				}
			}
		});
		
		
		bemail.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				try{
					new Thread(){
						public void run(){
							
							try{
								
								DataOutputStream outToServer;
								Socket clientSocket = new Socket("10.0.2.2", 9999);
								
								outToServer = new DataOutputStream(clientSocket.getOutputStream());
								
								outToServer.writeUTF("email"+" " + email.getText()+" # " +Data.cons.get(pos) + "\n");
								
						        clientSocket.close();
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
					
					}.start();
					
				}
				catch(Exception e){
				
				}
			}
		});
			
	}
	
	/*void getConsultas(){
	
		//String FILE_NAME = "consultas.txt";
		// Create a new output file stream that’s private to this application.

		try {
		    // Write 20 Strings
			String FILENAME = Data.getUsername()+"_consultas.saude";
			
		    DataInputStream in = new DataInputStream(openFileInput(FILENAME));
		    
		   
		    try{
			    while (true){
			    	 Data.cons.add(in.readUTF());
			    }
		    }
		    catch (EOFException e) {
		        Log.i("Data Input Sample", "End of file reached");
		    }
		    
		    for (String s: Data.cons){
		    	System.out.println(s);
		    }
		 
		    in.close();

		   
		} catch (IOException e) {
		    Log.i("Data Input Sample", "I/O Error");
		}
		
		
		
		setConsulta();
	}*/
	
	void setConsulta(){
		if (Data.cons.size() == 1){
			anterior.setEnabled(false);
			seguinte.setEnabled(false);
		}
		if (Data.cons.size() != 1)
			consulta.setText(Data.cons.get(pos));
	}
	
	
	/*void sendEmail(String destination,String msg){
		
		final String username = "dropbits2011@gmail.com";
		final String password = "dropbits";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mrc@mrc.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(destination));
			message.setSubject("SaudeUC-Consulta");
			message.setText(msg);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    	sendEmail(email.getText().toString(),Data.cons.get(pos));
		
	}
	*/
	
}
