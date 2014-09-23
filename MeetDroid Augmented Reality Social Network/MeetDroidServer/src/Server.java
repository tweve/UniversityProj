
import java.io.*;
import java.net.*;
import java.security.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Title:        Sample Server
 * Description:  This utility will accept input from a socket, posting back to the socket before closing the link.
 * It is intended as a template for coders to base servers on. Please report bugs to brad at kieser.net
 * Copyright:    Copyright (c) 2002
 * Company:      Kieser.net
 * @author B. Kieser
 * @version 1.0
 */

public class Server {

	static ArrayList<User> users = new ArrayList<User>();
	static ArrayList<Message> messages = new ArrayList<>();
	static HashMap<User,ArrayList<User>> friends = new HashMap<User,ArrayList<User>>();
	static HashMap<Message,Calendar> toDelete =new  HashMap<Message,Calendar>();


	static synchronized void addMessage(Message m){
		messages.add(m);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 1);
		toDelete.put(m, cal);
		System.out.println(cal);
		System.out.println(toDelete);
	}
	static synchronized void putFriend(User key, ArrayList<User> value){
		friends.put(key, value);
	}
	synchronized static void addUser(String username, String password, String n, String g){
		User temp = new User();
		temp.setUsername(username);
		temp.setPassword(password);
		temp.setName (n);
		temp.setGender(g);

		users.add(temp);

	}

	private static int port=80, maxConnections=0;
	// Listen for incoming connections and handle them
	public static void main(String[] args) {

		new Thread(){
			public void run() {
				while(true){
					Calendar cal = Calendar.getInstance();

					for (Message m : toDelete.keySet()){
												
						if (Server.toDelete.get(m).compareTo(cal)<0){
							Server.toDelete.remove(m);
						}
						else{
						}
					}
					try {
						sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();


		try {

			FileInputStream fin = new FileInputStream("md_users.mdb");
			FileInputStream fin3 = new FileInputStream("md_friends.mdb");


			ObjectInputStream ois = new ObjectInputStream(fin);
			ObjectInputStream ois3 = new ObjectInputStream(fin3);


			users =  (ArrayList<User>) ois.readObject();
			friends = ( HashMap<User,ArrayList<User>>) ois3.readObject();

			ois.close();
			ois3.close();


		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Problem with files setting up new environment.");
			User adm = new User();
			adm.setUsername("admin");
			adm.setPassword("q");
			adm.setConnected(false);

			users.add(adm);


			/*Message m = new Message();
			m.setAltitude(1);
			m.setLatitude(40.348466);
			m.setLongitude(-8.430376);
			m.setSender("Igor");
			m.setText("at home");
			messages.add(m);*/
		}


		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				try{


					System.out.println("Terminating");

					FileOutputStream fout = new FileOutputStream("md_users.mdb");
					FileOutputStream fout3 = new FileOutputStream("md_friends.mdb");


					ObjectOutputStream oos = new ObjectOutputStream(fout);
					ObjectOutputStream oos3 = new ObjectOutputStream(fout3);


					oos.writeObject(users);
					oos3.writeObject(friends);

					oos.close();
					oos3.close();

					super.run();
				}
				catch (Exception e) {
					System.out.println("Error while writing to file");
				}
			}
		});

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
	private String line,input;

	doComms(Socket server) {
		this.server=server;
	}

	public void run () {

		input="";

		try {
			// Get input from the client
			DataInputStream in = new DataInputStream (server.getInputStream());
			DataOutputStream out = new DataOutputStream(server.getOutputStream());

			String received = in.readUTF();

			System.out.println(received);


			Object ob=JSONValue.parse(received);
			JSONObject b = (JSONObject) ob;


			if (((String)b.get("command")).equalsIgnoreCase("login")){

				//--------------------------- LOGIN -------------------------------------
				if (login( ((String)b.get("username")), ((String)b.get("password"))) == 1){

					JSONObject obj=new JSONObject();
					obj.put("command","login_response");				 
					obj.put("response","ok");
					System.out.println(obj.toJSONString());
					out.writeUTF(obj.toJSONString());
				}
				else{
					JSONObject obj=new JSONObject();
					obj.put("command","login_response");				 
					obj.put("response","error");
					System.out.println(obj.toJSONString());
					out.writeUTF(obj.toJSONString());
				}
			}
			else if (((String)b.get("command")).equalsIgnoreCase("register")){
				User temp = this.searchUser((String)b.get("username"));
				if (temp != null){
					// username ja em uso
					JSONObject obj=new JSONObject();
					obj.put("command","register_response");				 
					obj.put("response","in_use");
					System.out.println(obj.toJSONString());
					out.writeUTF(obj.toJSONString());
				}
				else{
					//sucesso
					Server.addUser( (String)b.get("username"), (String) b.get("password"), (String) b.get("name"), (String) b.get("gender"));
					JSONObject obj=new JSONObject();
					obj.put("command","register_response");				 
					obj.put("response","ok");
					System.out.println(obj.toJSONString());
					out.writeUTF(obj.toJSONString());
				}
			}
			else if (((String)b.get("command")).equalsIgnoreCase("request")){


					JSONArray array = new JSONArray();

					System.out.println(Server.messages.size());

					float userlat = Float.parseFloat(""+b.get("latitude"));				 
					float userlon = Float.parseFloat(""+b.get("longitude"));

					float radius = Float.parseFloat(""+b.get("radius"));


					for (Message m : Server.messages){

						if (distFrom((float)m.latitude,(float)m.longitude,userlat,userlon)<(radius*1000)){  // retorna pontos num raio de 1km
							JSONObject obj=new JSONObject();
							
							User temp = searchUser(m.sender);
							obj.put("username",temp.name);				 
							obj.put("title",m.text);
							obj.put("latitude",m.latitude);
							obj.put("longitude",m.longitude);
							obj.put("altitude",m.altitude);
							array.add(obj);
						}
					}

					System.out.println(array.toJSONString());
					out.writeUTF(array.toJSONString());

			}
			else if (((String)b.get("command")).equalsIgnoreCase("post")){

					Message m = new Message();
					m.text = ""+b.get("text");
					m.sender = ""+b.get("sender");
					m.latitude =Double.parseDouble(""+b.get("latitude")); 
					m.longitude=Double.parseDouble(""+b.get("longitude")); 
					m.altitude =Double.parseDouble(""+b.get("altitude")); 

					Server.addMessage(m);
					System.out.println(Server.messages.size());

			}
			else if (((String)b.get("command")).equalsIgnoreCase("get_friends")){


					ArrayList<User> friends = Server.friends.get(searchUser((String)b.get("username")));

					JSONArray array = new JSONArray();

					if (friends != null){
						for (User f : friends){
							JSONObject obj=new JSONObject();
							obj.put("username",f.name);				 
							array.add(obj);
						}
					}

					System.out.println(array.toJSONString());
					out.writeUTF(array.toJSONString());

			}

			else if (((String)b.get("command")).equalsIgnoreCase("add_friend")){


					String destination = (String)b.get("friend");

					System.out.println("has:"+ Server.friends.get((String)b.get("username")));
					System.out.println((String)b.get("username"));
					System.out.println(destination);


					if ( (Server.friends.get(searchUser((String)b.get("username"))) == null) ) {
						System.out.println("user has no friends");
						User person = searchUser((String)b.get("username"));
						User friend = searchUserWithName((String)b.get("friend"));
						ArrayList<User> tempArraylist = new ArrayList<User>();
						tempArraylist.add(friend);
						Server.putFriend(person, tempArraylist);

					}
					else{
						System.out.println("user has friends");
						ArrayList<User> flist = Server.friends.get(searchUser((String)b.get("username")));
						User person = searchUser((String)b.get("username"));
						User toadd = searchUserWithName((String)b.get("friend"));
						flist.add(toadd);
						Server.putFriend(person, flist);
					}

				}

			else if (((String)b.get("command")).equalsIgnoreCase("person_info")){


					User person = searchUserWithName(""+b.get("person"));

					JSONObject obj=new JSONObject();
					obj.put("command","person_info_response");	
					obj.put("person",""+b.get("person"));	
					
					System.out.println(""+b.get("username"));
					
					obj.put("name",searchUser(""+b.get("username")).name);
					
					obj.put("gender",person.gender);

					if ( (Server.friends.get(searchUser((String)b.get("username"))) != null) ) {
						ArrayList<User> flist = Server.friends.get(searchUser((String)b.get("username")));
						User dst = searchUser((String)b.get("person"));
						if (flist.contains(dst)){
							obj.put("friends","true");
						}
						else{
							obj.put("friends","false");
						}
					}
					else{
						obj.put("friends","false");
					}

					System.out.println(obj.toJSONString());
					out.writeUTF(obj.toJSONString());

				}

			System.out.println("");
			server.close();

		} catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
	}

	User searchUser(String username){

		for (int i = 0; i< Server.users.size();i++){
			if(Server.users.get(i).getUsername().equalsIgnoreCase(username)){
				return Server.users.get(i);
			}
		}
		return null;
	}
	User searchUserWithName(String name){

		for (int i = 0; i< Server.users.size();i++){
			if(Server.users.get(i).name!= null && (Server.users.get(i).name.equalsIgnoreCase(name))){
				return Server.users.get(i);
			}
		}
		return null;
	}
	int login(String username, String password){

		User temp = this.searchUser(username);

		if (temp != null){
			if (temp.getPassword().equalsIgnoreCase(password)){
				return 1;
			}
			else{
				System.out.println("Wrong Password.");
				return 0;
			}
		}
		else{
			System.out.println("Username does not exist");
			return 0;
		}
	}

	public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
				Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double dist = earthRadius * c;

		int meterConversion = 1609;

		return new Float(dist * meterConversion).floatValue();
	}
}
