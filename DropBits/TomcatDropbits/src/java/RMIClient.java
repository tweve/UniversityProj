
import java.rmi.server.UnicastRemoteObject;

import interfacedropbox.ClientInterface;
import java.rmi.RemoteException;


public class RMIClient extends UnicastRemoteObject implements ClientInterface {
    
    
    RMIClient() throws RemoteException{
        super();
       
    }
    
    public void print_on_client(String s)throws RemoteException{
        System.out.println(s);
        NotificationsServlet.sendMessageToAll(s);
    }
    
}
