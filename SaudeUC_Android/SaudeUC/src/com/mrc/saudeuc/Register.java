package com.mrc.saudeuc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends Activity{

	Button cancel;
	Button register;
	EditText username;
	EditText password;
	EditText password2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		  setContentView(R.layout.register);
		  cancel =(Button) findViewById(R.id.bCancel);
		  register = (Button) findViewById(R.id.bConfirmRegister);
		  
		  username = (EditText) findViewById(R.id.etUser);
		  password = (EditText) findViewById(R.id.etPass1);
		  password2 = (EditText) findViewById(R.id.etPass2);
		  
		  final Handler myHandler = new Handler(){
		        @Override
		        public void handleMessage(Message msg) {

		            	int aux = Integer.parseInt((String)(msg.obj));
		            	
		            	if (aux == -1){
		            		showMsg("Erro de conexao.");
		            	}
		            	else if(aux == 0){
		            		showMsg2("Registado.");

		            	}
		            	else if (aux == 1){
		            		showMsg("Username indiponivel.");
		            	}
		            	else{
		            		showMsg("Passwords nao sao iguais.");
		            	}
		        }
		             
		        };
		  
		  cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent backToLogin = new Intent();
				setResult(RESULT_OK,backToLogin);
				finish();
				
			}
		});
		  
		  register.setOnClickListener(new View.OnClickListener() {
		        
				
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
								
									
									outToServer.writeUTF("register "+ username.getText()+" "+password.getText()+" "+password2.getText()+"\n");
									
									String aux = inFromServer.readLine();
							         
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
	}
	
	public void showMsg(String msg){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Alerta");
		
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	 
	    
	 
	    } });
		alertDialog.setMessage(msg);
		alertDialog.show();
	}
	
	public void showMsg2(String msg){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Obrigado");
		
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	    	  Intent backToLogin = new Intent();
				setResult(RESULT_OK,backToLogin);
				finish();
	    } });
		alertDialog.setMessage(msg);
		alertDialog.show();
	}

}
