
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
    String nome;
    String password;
    boolean logged = false;

    public ArrayList<String> notifications = null;

    public User (String nome, String password){
        this.nome = nome;
        this.password = password;
        this.notifications = new ArrayList<String>();
    }
    
    public void addNotification(String notification){
        notifications.add(notification);
    }
    
    public String getNotifications(){
         String notes = null;
         if (notifications.size()>0)
             notes = new String();
         for (int i = 0;i<notifications.size();i++){
             notes = notes.concat(notifications.get(i)+"\n");
         }
         return notes;
    }
    public void deleteNotifications(){
        this.notifications = null;
    }
    

}
