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
 * Derived from type Road
 * 
 * */
public class n2009111924_AutoEstrada extends n2009108991_Estrada implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**price for traveling in class A vehicle*/
	private double precoPortagemA;
	/**price for traveling in class B vehicle*/
	private double precoPortagemB;
	
	/**
	 * Class constructor
	 * 
	 * @param origem extreme A city
	 * @param destino extreme B city
	 * @param nome Road name
	 * @param distancia distance between both cities
	 * @param precoPortagemA price for vehicles Class A
	 * @param precoPortagemB price for vehicles Class B
	 */
	public n2009111924_AutoEstrada(n2009108991_Cidade origem, n2009108991_Cidade destino, String nome, int distancia ,double precoPortagemA,double precoPortagemB){
		super(origem, destino,nome,distancia);
		setPrecoPortagemA(precoPortagemA);
		setPrecoPortagemB(precoPortagemB);
		setVelocidadeMaximaA(120);
		setVelocidadeMaximaB(100);
		this.setTipo("autoestrada");
	}
	
	/**
	 * Allows us to get the Road's price for class A
	 * 
	 * @return Road's price for class A
	 */
	public double getPrecoPortagemA(){
		return precoPortagemA;
	}
	
	/**
	 * Allows us to get the Road's price for class B
	 * 
	 * @return Road's price for class B
	 */
	public double getPrecoPortagemB(){
		return precoPortagemB;	
	}
	
	/**
	 * Allows us to set the Road's price for class A
	 * 
	 * @param precoPortagemA Road's price for class A
	 */
	public void setPrecoPortagemA(double precoPortagemA){
		this.precoPortagemA = precoPortagemA;
	}
	
	/**
	 * Allows us to set the Road's price for class B
	 * 
	 * @param precoPortagemB Road's price for class B
	 */
	public void setPrecoPortagemB(double precoPortagemB){
		this.precoPortagemB = precoPortagemB;
	}
	
	/**
	 * Allows us to set the Road's details
	 * 
	 * @return Road's details in String
	 */
	public String toString(){
		String buff= new String();
		buff = buff.concat(super.toString());
		buff = buff.concat("Preco Poragem Classe A: "+ precoPortagemA+"\n");
		buff = buff.concat("Preco Poragem Classe B: "+ precoPortagemB+"\n");
		return buff;
		
	}
	
}
