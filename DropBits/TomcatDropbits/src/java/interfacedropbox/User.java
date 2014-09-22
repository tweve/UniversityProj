package interfacedropbox;
import java.util.Date;

public class User
{
  private String username;  
  private String password;
  private String loginDate;
  private TaskInterface ti;
  
  public User()
  {
    loginDate = (new Date()).toString();
  }

  public void setUsername(String username)
  {
    this.username = username;
  }
    
  public String getUsername()
  {
    return this.username;
  }      
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  public void setTaskInterface(TaskInterface ti)
  {
    this.ti = ti;
  }
  
  public TaskInterface getTaskInterface()
  {
    return this.ti;
  }
  
  public String getLoginDate()
  {
    return loginDate;
  }
    
}