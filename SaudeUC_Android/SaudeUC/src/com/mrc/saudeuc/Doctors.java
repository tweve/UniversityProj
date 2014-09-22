package com.mrc.saudeuc;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 
public class Doctors extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        // storing string resources into Array
        String[] specs = Data.doctors;
 
        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.doctors, R.id.label, specs));
 
    }
        final Handler myHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

            
             if (msg.arg1 == 1){
            		Data.slots = ((String)msg.obj).trim().split("-"); 
            		Intent it = new Intent("com.mrc.saudeuc.SLOTS");
            		startActivity(it);
            		finish();
            		
            	}
            }
                 
    	};
    
    	protected void onListItemClick(ListView l, View v, int position, long id) {
        	Data.buttonDoc = position;
        	
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
							
							
							System.out.println("dates"+" " + Data.buttonSpec+ " "+Data.buttonDoc+ " "+Data.getUsername()+" "+Data.getPassword()+"\n");
							outToServer.writeUTF("dates"+" " + Data.buttonSpec+ " "+Data.buttonDoc+ " "+Data.getUsername()+" "+Data.getPassword()+"\n");
							
							String aux = inFromServer.readLine();
							
							msg.arg1 = 1; 
					        msg.obj = aux;
					         
					        myHandler.sendMessage(msg);
					        clientSocket.close();
						}
						catch(Exception e){
							e.printStackTrace();
							msg.obj = -1;
						}
					}
				
				}.start();
				
			}
			catch(Exception e){
			
			}
    			
        	super.onListItemClick(l, v, position, id);
        }
    
}