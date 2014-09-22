package mrc_interface;
import java.io.Serializable;


public class Pacote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5150060816193249625L;
	
	private String source;
	private String destination;
	private Object data;
	
	public Pacote(String source, String destination, Object data) {
		this.source = source;
		this.destination = destination;
		this.data = data;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Object getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "Pacote [source=" + source + ", destination=" + destination
				+ ", data=" + data + "]";
	}
}
