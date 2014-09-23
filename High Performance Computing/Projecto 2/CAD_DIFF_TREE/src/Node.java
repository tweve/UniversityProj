import java.util.ArrayList;
import java.util.HashMap;


public class Node {

	short Element;
	ArrayList<Node> links;
	HashMap<Short , Node> map = null;
	
	Node(short Element){
		this.Element = Element;
		links = new ArrayList<>();
	}
	
	public short getElement() {
		return Element;
	}
	public void setElement(short element) {
		Element = element;
	}
	public ArrayList<Node> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<Node> links) {
		this.links = links;
	}

	@Override
	public String toString() {
		return ""+Element;
	}
	
	
}
