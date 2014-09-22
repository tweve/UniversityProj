import java.util.ArrayList;


public class Speciality {

	String name;
	public ArrayList<Doctor> docs = new ArrayList<Doctor>();
	public ArrayList<Integer> dias = new ArrayList<Integer>();

	
	public Speciality(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
