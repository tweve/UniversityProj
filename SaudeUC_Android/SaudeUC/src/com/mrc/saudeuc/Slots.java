
package com.mrc.saudeuc;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Slots extends ListActivity{

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        // storing string resources into Array
        String[] specs = Data.slots;
 
        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.slots, R.id.label, specs));
    }

	 final Handler myHandler = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	        	System.out.print(((String)msg.obj));
	        	String[] aux = ((String)msg.obj).split("-");
	        	
	        	int result = Integer.parseInt(aux[0]);
	        	if (result == 0){
	        		Data.credits = Integer.parseInt(aux[1]);
	        	
	        		
	        		try {
	        			String FILENAME = Data.getUsername()+"_consultas.saude";
	        			
	        		    DataOutputStream out = 
    		            new DataOutputStream(openFileOutput(FILENAME, Context.MODE_APPEND));
    		
	        		    out.writeUTF(aux[2]);
	        		    
	        		    out.close();

	        		   
	        		} catch (IOException e) {
	        		    Log.i("Data Input Sample", "I/O Error");
	        		}
	        		
	        		
	        		showMsg("Marcado com sucesso");
	        	}
	        	else
	        		showMsg(aux[1]);
	        }
	             
		};
	    
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	    	Data.buttonTime = position;
	    	
//	    	System.out.println(v.get
	    	Object o = this.getListAdapter().getItem(position);
    		System.out.println(o.toString());
    		Data.ids = o.toString().split(",");
	    		
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
								
//								outToServer.writeUTF("request"+" " + Data.buttonTime+ " "+Data.buttonSpec+" "+Data.buttonDoc +" " +Data.getUsername()+" "+Data.getPassword()+ " "+getMyPhoneNumber()+ "\n");
								outToServer.writeUTF("request"+" " + Data.ids[0]+" " +Data.getUsername()+" "+Data.getPassword()+ " "+getMyPhoneNumber()+ "\n");
								
								String aux = inFromServer.readLine();
								
								 
						        msg.obj = aux;
						         
						        myHandler.sendMessage(msg);
						        clientSocket.close();
							}
							catch(Exception e){
								e.printStackTrace();
								msg.obj = "3-Erro de conexao";
							}
						}
					
					}.start();
					
				}
				catch(Exception e){
				
				}
				
	    	super.onListItemClick(l, v, position, id);
	    }
	    
	    public void showMsg(String msg){
			
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle("Message From Server");
		  alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		      public void onClick(DialogInterface dialog, int which) {
		 
		    	  finish();
		 
		    } });
			alertDialog.setMessage(msg);
			alertDialog.show();
		}
	    public String getMyPhoneNumber()
	    {
	        return ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
	                .getLine1Number();
	    }
	}


