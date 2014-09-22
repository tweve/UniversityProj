package SEPIA;

import java.io.Serializable;


/**
 * 
 * @author Igor Cruz
 * @author Carlos Santos
 * 
 * @version 1
 * @since 0.1
 * 
 * This class is a major class since the start of the project.
 * */
public class n2009108991_Viatura  implements Serializable {
	
	/**Hash verification */
	private static final long serialVersionUID = 1;
	
	/** Vehicles registration number */
	private String matricula;
	/** Vehicles class A or B*/
	private String classe;
	/**Gasolina or Gasoleo */
	private String tipoCombustivel;
	/**Consumption of 100 km */
	private double consumo;
	
	/**
	 * Class constructor
	 * 
	 * @param classe Vehicle's class
	 * @param matricula vehicles ID number
	 * @param tipoCombustivel Gasolina or Gasoleo
	 * @param consumo Vehicle's consumption
	 */
	public n2009108991_Viatura(String classe,String matricula, String tipoCombustivel,double consumo){
		
		setClasse(classe);
		setMatricula(matricula);
		setTipoCombustivel(tipoCombustivel);
		setConsumo(consumo);
	}
	
	/**
	 * This method allows us to get a vehicle's ID
	 * 
	 * @return 		vehicles registration ID
	 */
	public String getMatricula(){
		return matricula;
	}
	
	/**
	 * This method allows us to get a vehicle's class
	 * 
	 * @return 		vehicles class (A or B)
	 */
	public String getTipo(){
		return classe;
	}
	
	/**
	 * This method allows us to get a vehicle's fuel type
	 * 
	 * @return 		Gasolina or Gasoleo
	 */
	public String getTipoCombustivel(){
		return tipoCombustivel;
	}
	
	/**
	 * This method allows us to get a vehicle's consumption at 100km
	 * 
	 * @return 		vehicles consumption type double
	 */
	public double getConsumo(){
		return consumo;
	}
	
	/**
	 * This method allows us to set the Vehicles registration ID
	 * 
	 * @param matricula vehicle's registration ID
	 */
	public void setMatricula(String matricula){
		this.matricula=new String (matricula);
	}
	
	/**
	 * This method allows us to set the Vehicles class
	 * 
	 * @param classe vehicle's class (A or B)
	 */
	public void setClasse(String classe){
		this.classe=new String (classe);
	}
	
	/**
	 * This method allows us to set the Vehicles type of fuel
	 * 
	 * @param tipoCombustivel vehicle's fuel type (Gasolina or Gasoleo)
	 */
	public void setTipoCombustivel(String tipoCombustivel){
		this.tipoCombustivel=new String (tipoCombustivel);
	}
	
	/**
	 * This method allows us to set the Vehicles consumption
	 * 
	 * @param consumo vehicle's consumption
	 */
	public void setConsumo(double consumo){
		this.consumo = consumo;
	}
	
	/**
	 * This method allows us to get Vehicle's details
	 * 
	 * @return  vehicle's details
	 */
	public String toString(){
		
		return ("Matricula: "    + matricula      +"\n"+
				 "Classe: "       + classe         +"\n"+
				 "Combustivel: "  + tipoCombustivel+"\n"+
				 "Consumo 100km :"+ consumo     +"\n"
		);
	}
	
}
