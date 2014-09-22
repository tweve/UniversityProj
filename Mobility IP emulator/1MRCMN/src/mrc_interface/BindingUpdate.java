package mrc_interface;

import java.io.Serializable;

public class BindingUpdate implements Serializable{
	
	private static final long serialVersionUID = 4447453986488117933L;
	String CoA;
	String Node;

	public String getCoA() {
		return CoA;
	}

	public void setCoA(String coA) {
		CoA = coA;
	}

	public String getNode() {
		return Node;
	}

	public void setNode(String node) {
		Node = node;
	}
	
	
}
