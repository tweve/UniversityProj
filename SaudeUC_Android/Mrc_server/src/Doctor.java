import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Doctor {
	String Name;
	ArrayList<Consulta> consultas = new ArrayList<Consulta>();
	
	public Doctor(String name){
		this.Name = name;
	}
	
	public String getSlots(){
		String temp = "";
		 SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yy");
		  
		for (int i = 0;i<consultas.size();i++){
			
				Consulta aux = consultas.get(i);
				if (!aux.marcada){
					temp = temp + aux.getId() + "," + date_format.format(aux.date.getTime())+"  "+ aux.hora + "  Custo: "+aux.price+" credits -";
				}
		}
		
		return temp;
	}
}
