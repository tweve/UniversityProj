
package interfacedropbox;

import java.io.Serializable;


public class EditFile implements Serializable{

	private static final long serialVersionUID = 1L;
	String oldname;
    String newname;
    
    public EditFile (String oldname, String newname){
       this.oldname = oldname;
       this.newname = newname;
    }
    
    public String getOldName(){
    	return this.oldname;
    }

	public String getNewName(){
    	return this.newname;
    }
    
}
