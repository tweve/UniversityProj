/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacedropbox;

import java.io.Serializable;

public class Logout implements Serializable{

	private static final long serialVersionUID = 1L;
	String login;
    
    public Logout (String username){
        this.login = username;
    }
    
    public String getLogin(){
    	return this.login;
    }
}
