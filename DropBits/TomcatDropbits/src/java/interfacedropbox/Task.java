package interfacedropbox;



import java.io.Serializable;

public class Task implements Serializable{
    private static final long serialVersionUID = 1L;
    
    String author;
    String title;
    int ID;
    boolean realized;
    
    public Task(String author, String title){
        this.author = author;
        this.title = title;
        this.realized = false;
    }
    synchronized public void rename(String newName){
        this.title = newName;
    }
    public String toString(){
        String aux = null;
        aux = "ID:'"+ID+"'" + "\tAUTHOR:'"+author+"'" +"\tTITLE:'"+title+"'";
        return aux;
    }
}
