package interfacedropbox;

import java.io.Serializable;


public class Login implements Serializable{
    
	private static final long serialVersionUID = 1L;
	String login;	
	String pass;
	
	Login(){
		this.login="";
		this.pass="";
	}
	
	public Login(String login,String pass){
		this.login=login;
		this.pass=pass;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
