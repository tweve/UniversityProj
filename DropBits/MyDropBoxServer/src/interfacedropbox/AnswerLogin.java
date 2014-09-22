
package interfacedropbox;

import java.io.Serializable;

public class AnswerLogin implements Serializable {
    
	private static final long serialVersionUID = 1L;
	String message;
    String login;
    String password;
    
    public AnswerLogin(String login,String pw,String message){
        this.message = message;
        this.login = login;
        this.password =pw;
    }
    
    public String getLogin(){
    	return this.login;
    }
    
    public String getMessage(){
    	return this.message;
    }
    
    public String getPassword(){
    	return this.password;
    }
}
