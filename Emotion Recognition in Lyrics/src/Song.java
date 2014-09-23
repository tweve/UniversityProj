import java.io.Serializable;
import java.util.ArrayList;

import cc.mallet.classify.Classification;


public class Song implements Serializable{

	String name;
	ArrayList <Classification> c;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Classification> getC() {
		return c;
	}
	public void setC(ArrayList<Classification> c) {
		this.c = c;
	}
	
	
	
}
