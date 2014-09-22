package com.mrc.saudeuc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Carregar extends Activity{

	
	Button bConfirmar;
	
	TextView nome;
	TextView numeroCartao;
	TextView validade;
	TextView csv;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comprar);
		
		
		nome = (TextView) findViewById(R.id.nome);
		numeroCartao = (TextView) findViewById(R.id.numeroCartao);
		validade = (TextView) findViewById(R.id.validade);
		csv = (TextView) findViewById(R.id.csv);
		
		
		bConfirmar = (Button) findViewById(R.id.bConfirmar);
		
		final Handler myHandler = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	        	
	        	
	        	if (msg.arg1 == 1){
	        		showMsg((String) msg.obj);
	        	}
	        	else {
	        		
	        		if (Integer.parseInt((String)msg.obj) < 0)
		        	{
		        		showMsg("Nao carregado!");
		        	}
		        	else 
		        	{
		        		showMsg("Carregamento: 75 Creditos\nDispoe de: "+(String)msg.obj+" Creditos");
		        		
		        		Data.credits = Integer.parseInt((String) msg.obj);
		        	}
	        		
	        	}
	        	
	        }
	             
		};
		
		
		
		
		bConfirmar.setOnClickListener( new View.OnClickListener(){

			public void onClick(View v) {
				try{
					new Thread(){
						public void run(){
							
							Message msg = myHandler.obtainMessage();
							
							try{
								System.out.println("passa");
								
								if (nome.getText().toString().length()!= 0){
									try{
										  new java.math.BigInteger(numeroCartao.getText().toString());
										  
										  if (validade.getText().toString().length()!=0){
											  try{
												  new java.math.BigInteger(csv.getText().toString());
												
												  
												  
												  	DataOutputStream outToServer;
													BufferedReader inFromServer;
													
													Socket clientSocket = new Socket("10.0.2.2", 9999);
													  
													outToServer = new DataOutputStream(clientSocket.getOutputStream());
													inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
												
													outToServer.writeUTF("charge "+ Data.getUsername()+" "+Data.getPassword()+"\n");
													
													String aux = inFromServer.readLine();
													
													msg.arg1 = 0; 
											        msg.obj = aux;
											        
											         
											        myHandler.sendMessage(msg);
												  
												  
												 
												}catch(NumberFormatException ex) {
													msg.arg1 = 1;
													msg.obj = "CSV incorrecto";
													myHandler.sendMessage(msg);
													return;
												}
										  }
										  else{
											  msg.arg1 = 1;
												msg.obj = "Deve Introduzir a validade.";
												myHandler.sendMessage(msg);
												return;
										  }
										
										  
										 
										}catch(NumberFormatException ex) {
											msg.arg1 = 1;
											msg.obj = "Numero de cartao incorrecto.";
											myHandler.sendMessage(msg);
											return;
										}
								}
								else{
									msg.arg1 = 1;
									msg.obj = "Introduza o seu nome";
									myHandler.sendMessage(msg);
							
								}
									
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
	}
	
	public void showMsg(String msg){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Mensagem");
		
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	 
	    } });
		alertDialog.setMessage(msg);
		alertDialog.show();
	}
}
