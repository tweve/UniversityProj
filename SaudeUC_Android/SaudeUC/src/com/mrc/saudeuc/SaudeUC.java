package com.mrc.saudeuc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SaudeUC extends Activity {
	
	Button login;
	Button register;
	Button exit;
	TextView display;
	EditText username;
	EditText password;

	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState); 
        
        setContentView(R.layout.activity_saude_uc);
        
        login = (Button) findViewById(R.id.bLogin);
        register = (Button) findViewById(R.id.bRegister);
        exit = (Button) findViewById(R.id.bExit);
        display = (TextView) findViewById(R.id.tvMessage);
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        
    	
        final Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            	int aux = Integer.parseInt((String)msg.obj);
            	
            	if (aux == -1){
            		showMsg("Username ou password errados");
            	}
            	else if(aux >= 0){
            		Data.credits = aux;
            		Data.setUsername(username.getText().toString());
            		Data.setPassword(password.getText().toString());
            		Intent it = new Intent("com.mrc.saudeuc.MENU");
            		startActivity(it);
            	}
        }
             
        };
        
        
        login.setOnClickListener(new View.OnClickListener() {
        
			
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
							
								
								outToServer.writeUTF("login "+ username.getText()+" "+password.getText()+"\n");
								
								String aux = inFromServer.readLine();
								
								
						         
						        msg.obj = aux;
						         
						        myHandler.sendMessage(msg);
						        clientSocket.close();
						        
							}
							catch(Exception e){
								
								msg.obj = "-1";
							}
						}
					
					}.start();
					
				}
				catch(Exception e){
				
				}
				
			}
		});
        
        
        register.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent openRegister = new Intent("com.mrc.saudeuc.REGISTER");
				startActivity(openRegister);
				
				
			}
		});
        
        exit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				System.exit(0);
				
			}
		});
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_saude_uc, menu);
        return true;
    }
 
    public void showMsg(String msg){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Error");
	  alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	 
	    
	 
	    } });
		alertDialog.setMessage(msg);
		alertDialog.show();
	}
   
}
