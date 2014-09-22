package interfacedropbox;


import java.rmi.*;

public interface ClientInterface extends Remote{
public void print_on_client(String s) throws java.rmi.RemoteException;
}