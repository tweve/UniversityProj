
import java.util.*;
import java.io.*;

public class UserList implements Serializable {

    public ArrayList<User> users = new ArrayList<User>();

    synchronized public boolean add(String user, String pw) {
        boolean contains = false;
        for (int i = 0; i < this.users.size(); i++) {
            if (users.get(i).nome.equalsIgnoreCase(user)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            User temp = new User(user, pw);
            this.users.add(temp);
        }
        return !contains;
    }

    synchronized public boolean login(String user, String pw) {
        // Função devolve 1 caso user,pw exista na lista e loga o utilizador
        //        devolve 0 caso user,pw não exista na lista

        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).nome.equals(user) && this.users.get(i).password.equals(pw)) {
                this.users.get(i).logged = true;
                return true;
            }
        }
        return false;
    }

    synchronized public void logout(String user) {

        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).nome.equals(user)) {
                this.users.get(i).logged = false;
                //this.users.get(i).out = null;
            }
        }
    }

    synchronized public String getOnline() {

        String temp = new String();
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).logged) {
                temp = temp.concat(this.users.get(i).nome + "\t");
            }

        }
        return temp;

    }

    public void save() {
        String filename = "userlist.bin";
        File f = new File(filename);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);
            fos.close();
        } catch (IOException ioe) {
        }

    }

    public boolean contains(String name) {
        //check if userlist contains a given username
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).nome.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;

    }

    public void notifyOffline(String msg) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).logged == false) {
                users.get(i).addNotification(msg);
            }

        }
    }

    public String getNotifications(String user) {
        String temp = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).nome.equalsIgnoreCase(user)) {
                temp = users.get(i).getNotifications();
            }
        }
        return temp;
    }

    public void deleteNotifications(String user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).nome.equalsIgnoreCase(user)) {
                users.get(i).deleteNotifications();
            }
        }
    }

    public void setAllOffline() {
        for (int i = 0; i < users.size(); i++) {
            users.get(i).logged = false;
        }
    }
}
