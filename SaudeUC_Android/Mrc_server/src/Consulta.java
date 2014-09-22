import java.util.Calendar;


public class Consulta {

	public static int lastId = 0;
	public boolean marcada= false;
	String username;
	Calendar date;
	String hora;
	int price = 50;
	int id;
	
	Consulta(String hora, Calendar date){
		this.date = date;
		this.hora = hora;
		this.id = lastId;
		lastId++;
	}
	
	synchronized boolean marcada(){
		return marcada;
	}
	synchronized void marca(String username){
		marcada = true;
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
