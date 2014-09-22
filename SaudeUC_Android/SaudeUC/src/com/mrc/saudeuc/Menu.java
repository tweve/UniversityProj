package com.mrc.saudeuc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends Activity{
	

	Button specilities;
	Button consultas;
	Button comprar;
	Button maps;
	TextView credits;
	Button exit;
	

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("Credits: " + Data.credits);
		credits.setText("Creditos: " + Data.credits);
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		specilities =(Button) findViewById(R.id.bSpecialities);
		consultas =(Button) findViewById(R.id.bConsultas);
		comprar = (Button) findViewById(R.id.bComprar);
		credits = (TextView) findViewById(R.id.tvCredits);
		maps = (Button) findViewById(R.id.bMap);
		exit = (Button) findViewById(R.id.bExit);
		
		maps.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent it = new Intent("com.mrc.saudeuc.MAPSACTIVITY");
				startActivity(it);
			}
		});
		
		credits.setText("Creditos:" + Data.credits);
		
		
		final Handler myHandler = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {

	        	if (msg.arg1 == 0){
	        		System.out.println("doctors");
	        	}
	        	else if (msg.arg1 == 1){
	        		Data.specialities = ((String)msg.obj).trim().split("-"); 
	        		Intent it = new Intent("com.mrc.saudeuc.SPECIALITIES");
	        		startActivity(it);
	        		
	        	}
	        	
	            	/*int aux = (Integer) msg.obj;
	            	
	            	if (aux == -1){
	            		showMsg("Connection error.");
	            	}
	            	*/
	        }
	             
		};
	  
		
		
		specilities.setOnClickListener( new View.OnClickListener(){

			public void onClick(View v) {
				try{
					new Thread(){
						public void run(){
							
							Message msg = myHandler.obtainMessage();
							
							try{
								DataOutputStream outToServer;
								BufferedReader inFromServer;
								
								Socket clientSocket = new Socket("10.0.2.2", 9999);
								  
							
								outToServer = new DataOutputStream(clientSocket.getOutputStream());
								inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
							
								System.out.println("specilities "+ Data.getUsername()+" "+Data.getPassword()+"\n");
								
								outToServer.writeUTF("specialities "+ Data.getUsername()+" "+Data.getPassword()+"\n");
								
								
								
								String aux = inFromServer.readLine();
								
								
								
								msg.arg1 = 1; 
						        msg.obj = aux;
						        
						         
						        myHandler.sendMessage(msg);
							}
							catch(Exception e){
								msg.obj = -1;
							}
						}
					
					}.start();
					
				}
				catch(Exception e){
				
				}
				
			}
		});
		
		
		
	consultas.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			
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
			
			
			if (Data.cons.size()==0){
				showMsg();
			}
			else{
				Intent it = new Intent("com.mrc.saudeuc.CONSULTAS");
				startActivity(it);
			}
			
			
		}
	});

	comprar.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			Intent it = new Intent("com.mrc.saudeuc.COMPRAR");
			startActivity(it);
			
		}
	});
		
	
	exit.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			System.exit(0);
		}
	});
	
	}

	void showMsg(){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Alerta");
		
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	 
	    } });
		alertDialog.setMessage("Nao exitem consultas marcadas.");
		alertDialog.show();
	}

	

	
	
}
