/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacedropbox;

import java.io.Serializable;


public class DeleteTask implements Serializable {

	private static final long serialVersionUID = 1L;
	public int id;
    public String login;
    
    public DeleteTask(int id, String login){
        this.id = id;
        this.login = login;
    }
    
    public int getid(){
    	return this.id;
    }
    
    public String getLogin(){
    	return this.login;
    }
    
}
