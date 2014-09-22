package interfacedropbox;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TaskInterface extends Remote {
	public void subscribe(ClientInterface client) throws
	RemoteException;
	public String register(String login, String password)throws RemoteException;
	public AnswerLogin login(String login, String password)throws RemoteException;
	public String showTasks()throws RemoteException;
	public String addTask(String login,Task task)throws RemoteException;
	public String editTask(String login, int id, String newTaskName)throws RemoteException;
	public String deleteTask(String login , int id)throws RemoteException;
	public String showOnline()throws RemoteException;
	public String showFiles()throws RemoteException; 
	public void editFile(String filename, String newFileName) throws RemoteException; 
	public void deleteFile(String name)throws RemoteException; 
	public void logout(String login)throws RemoteException; 
	public String getNotifications(String login)throws RemoteException;
}
