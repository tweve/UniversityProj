
package interfacedropbox;

import java.io.Serializable;


public class AddTask implements Serializable {

	private static final long serialVersionUID = 1L;
	String login;
    Task task;
    
    public AddTask(String login, Task temp){
        this.login = login;
        this.task = temp;
    }
    public String getLogin(){
    	return this.login;
    }
    public Task getTask(){
    	return this.task;
    }
    
}
