package interfacedropbox;

import OAuth.GoogleOAuth;
import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;



public class TaskList implements Serializable{
    private static final long serialVersionUID = 1L;
    int id  = 0;
    ArrayList<Task> tasks = new ArrayList<Task>();
    
    synchronized public void add(Task temp){
        
        String googleid = GoogleOAuth.addTask(temp.title);
        temp.googleid = googleid;
        temp.ID = this.id;
        this.tasks.add(temp);
        id++;
    }
    public int getid(){
        return this.id;
    }
    synchronized public boolean delete(int taskID){

        for (int i = 0;i<tasks.size();i++){
           Task temp = tasks.get(i);
           
           if ( temp.ID == taskID ){
               GoogleOAuth.deleteTask(temp.googleid);
               tasks.remove(temp);
               return true;
           }
        }
        return false;
    }
    
    public boolean rename(int taskID,String newName){

        for (int i = 0;i<tasks.size();i++){
           Task temp = tasks.get(i);
           
           if ( temp.ID == taskID ){
               GoogleOAuth.renameTask(temp.googleid,newName);
               temp.rename(newName);
               return true;
           }
           else{
               
           }
        }
        return false;
    }

    public void print(){
        for (int i = 0;i<tasks.size();i++){
           System.out.println(tasks.get(i).toString());
        }
    }
    public String toString(){
        String temp = new String();
        for (int i = 0;i<tasks.size();i++){
           temp = temp.concat(tasks.get(i).toString()+"\n");
        }
        return temp;
    }
     public void save(){
        String filename = "tasklist.bin";
        File f = new File(filename);
        try{
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            
            oos.writeObject( this );
            fos.close();
        }
        catch (IOException ioe){
        }
      
    }
}
