package com.mrc.saudeuc;

import java.util.ArrayList;

public class Data {

	static String username;
	static String password;
	static String specialities[];
	static String doctors[];
	static String slots[];
	static String reservations[];
	static int buttonSpec;
	static int buttonDoc;
	static int buttonTime;
	static int credits;
	static String [] ids;
	static ArrayList<String> cons = new ArrayList<String>();
	static int alarme = 0;
	
	public static String getUsername() {
		return username;
	}
	
	public static void setUsername(String username) {
		Data.username = username;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static void setPassword(String password) {
		Data.password = password;
	}


	
	
	

}
