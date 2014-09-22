
public class User {

	private String username;
	private String password;
	private boolean connected;
	private int credits;
	
	
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}
