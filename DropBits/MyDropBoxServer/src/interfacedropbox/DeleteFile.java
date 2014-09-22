/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacedropbox;

import java.io.Serializable;


public class DeleteFile implements Serializable{

	private static final long serialVersionUID = 1L;
	String filename;
    
    public DeleteFile(String filename){
        this.filename = filename;
    }
    public String getFilename(){
    	return this.filename;
    }
}
