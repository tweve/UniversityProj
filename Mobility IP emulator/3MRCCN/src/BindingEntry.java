
public class BindingEntry {
	String nodeName;
	String CoA;
	
	
	
	public BindingEntry(String nodeName, String coA) {
		super();
		this.nodeName = nodeName;
		CoA = coA;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getCoA() {
		return CoA;
	}
	public void setCoA(String coA) {
		CoA = coA;
	}
	
}
