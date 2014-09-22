/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacedropbox;

import java.io.Serializable;


public class EditTask implements Serializable{

	private static final long serialVersionUID = 1L;
	int id;
    String login;
    String newTaskName;
    
    public EditTask(int id,String login,String newTaskName){
        this.id = id;
        this.newTaskName = newTaskName;
        this.login = login;
    }
    public int getid(){
    	return this.id;
    }
    
    public String getLogin(){
    	return this.login;
    }
    
    public String getTaskName(){
    	return this.newTaskName;
    }
}
