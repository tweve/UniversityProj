
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;



public class ServerMain {
	
	static ArrayList<Speciality> specialities = new ArrayList<Speciality>();
	static ArrayList<User> users = new ArrayList<User>();

  private static int port=9999, maxConnections=0;
  // Listen for incoming connections and handle them

  public static void main(String[] args) {
	  
	  User test = new User();
	  test.setUsername("Username");
	  test.setPassword("Password");
	  test.setCredits(500);
	  
	  
	  
	  test.setConnected(false);
	  
	  users.add(test);
	
	  Speciality spec1 = new Speciality("Alergologia");
	  spec1.docs.add(new Doctor("Dr Lino Chieira"));
	  specialities.add(spec1);
	  
	  Speciality spec2 = new Speciality("Cardiologia");
	  spec2.docs.add(new Doctor("Dr. Ferrer Correia"));
	  specialities.add(spec2);
	  
	  Speciality spec3 = new Speciality("Cirurgia Geral");
	  spec3.docs.add(new Doctor("Dr. Carlos Navarro"));
	  specialities.add(spec3);
	  
	  Speciality spec4 = new Speciality("﻿Clinica Geral");
	  spec4.docs.add(new Doctor("﻿Dr. Albano"));
	  spec4.docs.add(new Doctor("﻿Dr. Antonio Garrett"));
	  specialities.add(spec4);
	  
	  GenerateSchedule gen = new GenerateSchedule();
	 
	  
	  
	 gen.start();
	 
	 int i=0;

    try{
      ServerSocket listener = new ServerSocket(port);
      Socket server;

      while((i++ < maxConnections) || (maxConnections == 0)){
        doComms connection;

        server = listener.accept();
        doComms conn_c= new doComms(server);
        Thread t = new Thread(conn_c);
        t.start();
      }
    } catch (IOException ioe) {
      System.out.println("IOException on socket listen: " + ioe);
      ioe.printStackTrace();
    }
  }


}

class doComms implements Runnable {
    private Socket server;

    doComms(Socket server) {
      this.server=server;
    }

    public void run () {



      try {
        // Get input from the client
        DataInputStream in = new DataInputStream (server.getInputStream());
        PrintStream out = new PrintStream(server.getOutputStream());
        SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yy");
        
        User temp;
        
        String received = in.readUTF();
        
        System.out.println(received);
        
        String userinfos[] = received.trim().split(" ");
        
        if (userinfos[0].equalsIgnoreCase("login")){
        	
        	//--------------------------- LOGIN -------------------------------------
        	temp = login(userinfos[1], userinfos[2]);
        	if (temp != null){
        		out.println(temp.getCredits());
        	}
        	else{
        		out.println(-1);
        	}
        	
        	
        }
        
        else if (userinfos[0].equalsIgnoreCase("register")){
        	//--------------------------- REGISTER -------------------------------------
        	
        	if (!userinfos[2].equalsIgnoreCase(userinfos[3])){
        		//passwords dont match
        		out.println(2);
        		
        	}
        	else{
        		 temp = this.searchUser(userinfos[1]);
        		 if (temp != null){
                 		// username ja em uso
                 		out.println(1);
        		 }
        		 else{
        			 //sucesso
        			 this.addUser(userinfos[1], userinfos[2]);
        			 out.println(0);
        		 }
                 	
        	}
       	
        }
        else if (userinfos[0].equalsIgnoreCase("doctors")){
        	//--------------------------- DOCTORS -------------------------------------
        	temp = login(userinfos[2], userinfos[3]);
        	if (temp != null){
        		String docs = "";
        		
        		int pos =Integer.parseInt( userinfos[1]);
        		Speciality temp_spec= ServerMain.specialities.get(pos);
        		
        		for (int i = 0;i<temp_spec.docs.size();i++){
        			docs = docs + temp_spec.docs.get(i).Name+ "-";
        		}
        		out.print(docs);
        		
        	}
        	else {
        		out.println("Invalid username");
        	}
        	
        }
        else if (userinfos[0].equalsIgnoreCase("specialities")){
        	//--------------------------- SPECIALITIES -------------------------------------
        	temp =login(userinfos[1], userinfos[2]); 
        	if (temp != null){
        		String aux = "";
        		for (int i = 0;i<ServerMain.specialities.size();i++){
        			aux= aux+ ServerMain.specialities.get(i).getName()+ "-";
        		}
        		out.print(aux);
        	}
        	else {
        		out.println("Invalid username");
        	}
        	
        }
        else if (userinfos[0].equalsIgnoreCase("dates")){
        	//--------------------------- DATES -------------------------------------
        	// dates spec doctor user pass
        	temp = login(userinfos[3], userinfos[4]);
        	if (temp!= null){
        		
        		Speciality spec = ServerMain.specialities.get(Integer.parseInt(userinfos[1]));
        		Doctor d = spec.docs.get(Integer.parseInt(userinfos[2]));
        		out.println(d.getSlots());
        		
        		
        	}
        	else {
        		out.println("Invalid username");
        	}
        	
        }
        else if (userinfos[0].equalsIgnoreCase("request")){
        	//--------------------------- DATES -------------------------------------
        	// request id user pass cellnumb
        	temp = login(userinfos[2], userinfos[3]);
        	if (temp != null){
        	
        		
        		for (Speciality element : ServerMain.specialities) {
					for (Doctor doctor : element.docs) {
						for (Consulta consulta : doctor.consultas) {
							if (consulta.getId() == Integer.parseInt(userinfos[1]) && consulta.marcada == false && temp.getCredits() >= consulta.price)
							{
								temp.setCredits(temp.getCredits() - consulta.price);
								consulta.marcada = true;		
								
								out.println(0+ "-"+temp.getCredits()+"-"+"SaudeUC: "+  date_format.format(consulta.date.getTime())+" "+ consulta.hora+" "+"Medico: "+doctor.Name+" Especialidade: "+element.getName()+"\n");
		            			
		            			sendSMS(userinfos[4],"SaudeUC: "+  date_format.format(consulta.date.getTime())+" "+consulta.hora+" "+"Medico: "+doctor.Name+" Especialidade: "+element.getName()+"\n");
		            			
		            			//sendSMS("967753345","SaudeUC: test msg");
		            			//sendSMS("911112937","SaudeUC: test msg");
		            			//System.out.println("done");
		            			
		            			
		            		//	sendSMS(userinfos[4],"SaudeUC: "+  date_format.format(consulta.date.getTime())+" "+consulta.hora+" "+"Medico:"+doctor.Name+" Especialidade:"+element.getName()+"\n");
		            			
		            			System.out.println("mensagem enviada");
							}
							else if (consulta.getId() == Integer.parseInt(userinfos[1]) && consulta.marcada == false && temp.getCredits() < consulta.price)
							{
								out.println(1 +"-Nao tem creditos suficientes");
							}
							else if (consulta.getId() == Integer.parseInt(userinfos[1]) && consulta.marcada == true)
							{
								out.println(2 +"-Consulta ja marcada");
							}
						}
					}
				}
        	}
        	else {
        		out.println(3 +"Invalid username");
        	}
        }
        
        else if (userinfos[0].equalsIgnoreCase("charge")){
        	
        	//--------------------------- Carregar -------------------------------------
        	temp = login(userinfos[1], userinfos[2]);
        	if (temp != null){
        		temp.setCredits(temp.getCredits()+75); 
        		out.println(temp.getCredits());
        	}
        	else{
        		out.println(-1);
        	}
        }
        
        else if (userinfos[0].equalsIgnoreCase("email")){
        	
        	String msg = "";
        	int estado = 0;
        	
        	for (String s: userinfos){
        		if (s.equals("#")){
        			estado =1;
        		}
        		if (estado == 1)
        			msg = msg + s +" ";
        	}
        	
        	sendEmail(userinfos[1], msg);
        	
        }
        
        
       
        server.close();
        
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
    
    User searchUser(String username){

    	for (int i = 0; i< ServerMain.users.size();i++){
    		if(ServerMain.users.get(i).getUsername().equalsIgnoreCase(username)){
    			return ServerMain.users.get(i);
    		}
    	}
    	return null;
    }
    
    void addUser(String username, String password){
    	User temp = new User();
    	temp.setUsername(username);
    	temp.setPassword(password);
    	
    	ServerMain.users.add(temp);
    	
    }

    User login(String username, String password){
    	
    	 User temp = this.searchUser(username);
    	 
         if (temp != null){
         	if (temp.getPassword().equalsIgnoreCase(password)){
         		 return temp;
         	}
         	else{
         		 System.out.println("Wrong Password.");
         		return null;
         	}
         }
         else{
 	        System.out.println("Username does not exist");
 	        return null;
         }
    }
   
    void sendSMS(String number, String tex){
    	
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
				InternetAddress.parse("smsgw@helpdesk.dei.uc.pt"));
			message.setSubject(number);
			message.setText(tex);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    	
    }
    
    void sendEmail(String destin, String tex){
    	
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
				InternetAddress.parse(destin));
			message.setSubject("saude UC - marcacao consulta");
			message.setText(tex);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    	
    }
}


