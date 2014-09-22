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
 * It represents one road segment.
 * */
public class n2009108991_Estrada  implements Serializable {
	
	/**Hash value used to check the version.*/
	private static final long serialVersionUID = 1L;
	
	/**Road extreme A of type City*/
	private n2009108991_Cidade origem;
	/**Road extreme B of type City*/
	private n2009108991_Cidade destino;
	/**Road name*/
	private String nome;
	/**Road type (can be nacional or autoestrada) */
	private String tipo; 
	/**Road distance between the extremes */
	private int distancia;
	/**Road Maximum Speed for vehicles of Class A */
	private double velocidadeMaximaA;
	/**Road Maximum Speed for vehicles of Class B */
	private double velocidadeMaximaB;


	/**
	 * Class constructor
	 * 
	 * @param origem extreme A city
	 * @param destino extreme B city
	 * @param nome Road name
	 * @param distancia distance between both cities
	 */
	public n2009108991_Estrada(n2009108991_Cidade origem, n2009108991_Cidade destino, String nome, int distancia){
		
		setOrigem(origem);
		setDestino(destino);
		setDistancia(distancia);
		setNome(nome);
	}
	
	/**
	 * Allows us to get the Road's origin
	 * 
	 * @return Origin City
	 */
	public n2009108991_Cidade getOrigem(){
		return origem;
	}
	
	/**
	 * Allows us to get the Road's destination
	 * 
	 * @return Destination City
	 */
	public n2009108991_Cidade getDestino(){
		return destino;
	}
	
	/**
	 * Allows us to get the Road's name
	 * 
	 * @return City's name
	 */
	public String getNome(){
		return nome;
	}
	
	/**
	 * Allows us to get the Road's type
	 * 
	 * @return Road's type
	 */
	public String getTipo(){
		return tipo;
	}
	
	/**
	 * Allows us to get the Road's distance
	 * 
	 * @return Road's distance
	 */
	public int getDistancia(){
		return distancia;
	}
	
	/**
	 * Allows us to get the Road's maximum speed for Class A
	 * 
	 * @return Road's Maximum Speed for Vehicles class A
	 */
	public double getVelocidadeMaximaA(){
		return velocidadeMaximaA;
	}
	
	/**
	 * Allows us to get the Road's maximum speed for Class B
	 * 
	 * @return Road's Maximum Speed for Vehicles class B
	 */
	public double getVelocidadeMaximaB(){
		return velocidadeMaximaB;
	}

	/**
	 * Allows us to set the Road's Origin
	 * 
	 * @param origem Road's origin city
	 */
	public void setOrigem(n2009108991_Cidade origem){
		this.origem=origem;
	}
	
	/**
	 * Allows us to set the Road's Destination
	 * 
	 * @param destino Road's destination city
	 */
	public void setDestino(n2009108991_Cidade destino){
		this.destino=destino;
	}
	
	/**
	 * Allows us to set the Road's name
	 * 
	 * @param nome Road's name
	 */
	public void setNome(String nome){
		this.nome=new String (nome);
	}
	
	/**
	 * Allows us to set the Road's type
	 * 
	 * @param tipo Road's type
	 */
	public void setTipo(String tipo){
		this.tipo=new String (tipo);
	}
	
	/**
	 * Allows us to set the Road's distance
	 * 
	 * @param distancia distance between cities
	 */
	public void setDistancia(int distancia){
		this.distancia=distancia;
	}
	
	/**
	 * Allows us to set the Road's maximum Speed for Vehicles class A
	 * 
	 * @param velocidadeMaximaA Road's maximum Speed for Vehicles class A
	 */
	public void setVelocidadeMaximaA (double velocidadeMaximaA){
		this.velocidadeMaximaA=velocidadeMaximaA;
	}
	
	/**
	 * Allows us to set the Road's maximum Speed for Vehicles class B
	 * 
	 * @param velocidadeMaximaB Road's maximum Speed for Vehicles class B
	 */
	public void setVelocidadeMaximaB (double velocidadeMaximaB){
		this.velocidadeMaximaB=velocidadeMaximaB;
	}
	
	/**
	 * Allows us to get the Road's number of interest points
	 * 
	 * @return 			sum of origin interest points and national road interest points
	 */
	int getNumPontosInteresse(){
		int n = (origem.getNumPontosInteresse());				// num pontos de interesse da cidade origem
		n2009111924_EstradaNacional aux;
		
		if (this.tipo.equals("nacional")){						// fazemos downcasting para somar os PI da estrada
			aux = (n2009111924_EstradaNacional) this;
			n+=aux.getNumPontosInteresse();
		}
		return n;
	}
	/**
	 * Allows us to get a String with road details
	 * 
	 * @return 			details of the actual road
	 */
	public String toString(){
		
		/** Variable that contains the final description.*/
		String buff;
		
		buff =new String ("ExtremoA: "+origem.getNome() +"\n"+ 
				"ExtremoB: "+destino.getNome()+"\n"+
			   	"Nome: "	+nome   +"\n"+
			   	"Tipo: "	+tipo	+"\n"+
			   	"Distancia: "+distancia+"\n"+
			   	"Velocidade Maxima A: "+velocidadeMaximaA+"\n"+
			   	"Velocidade Maxima B: "+velocidadeMaximaB+"\n");
		return buff;
	
	}

}
