package SEPIA;

import java.io.Serializable;
import java.util.*;

/**
 * 
 * @author Igor Cruz
 * @author Carlos Santos
 * 
 * @version 1
 * @since 0.1
 * 
 * This class is a major class since the start of the project.
 * In our project a city can contain Interest points like a national road.
 * */

public class n2009108991_Cidade implements Serializable {

	/**Hash value used to check the version.*/
	private static final long serialVersionUID = 1L;
	
	/**Name of the city.*/
	private String nome;
	/**List of interest points.*/
	private ArrayList<n2009108991_PontoInteresse> listaPontosInteresse = new ArrayList<n2009108991_PontoInteresse>();
	
	
	/**
	 * Class constructor
	 * 
	 * @param nome city name
	 */
	public n2009108991_Cidade(String nome){
		setNome(nome);
	}
	
	/**
	 * This method allows us to get a city name
	 * 
	 * @return 		city name
	 */
	public String getNome(){
		return nome;
	}
	
	/**
	 * This method allows us to set a city name
	 * 
	 * @param nome city name
	 */
	public void setNome(String nome){
		this.nome=new String (nome);
	}
	
	/**
	 * This method allows us to add a point of interest to the list
	 * 
	 * @param PontoInteresse the Interest Point to be added
	 */
	public void addPontoInteresse(n2009108991_PontoInteresse PontoInteresse){
		this.listaPontosInteresse.add(PontoInteresse);
	}

	/**
	 * This method allows us to delete an interest point from the list
	 * 
	 * @param PontoInteresse the Interest Point to be delected
	 */
	public void delPontoInteresse(n2009108991_PontoInteresse PontoInteresse){
		/** Auxiliary variable, allows us to travel in the list*/
		n2009108991_PontoInteresse actual;
		
		for (int i=0;i<this.listaPontosInteresse.size();i++){			// percorremos a lista
			actual = listaPontosInteresse.get(i);
			if (actual.getNome().equals(PontoInteresse.getNome()))		// se o ponto de interesse for igual
				listaPontosInteresse.remove(i);							// removemos
		}
	}
	
	/**
	 * Allows us to know how many interest points the list has.
	 *  
	 * @return 			number of Points of Interest
	 */
	public int getNumPontosInteresse(){
		int n=listaPontosInteresse.size();
		return n;
	}
	
	
	/**
	 * Returns a String representing the actual object
	 *  
	 * @return 			description of the object
	 */
	public String toString(){
		
		/** Variable that contains the final description.*/
		String buffer;
		/** Auxiliary variable, allows us to travel in the interest points list*/
		n2009108991_PontoInteresse aux;
		
		buffer = new String("Nome: "+ nome + "\n"+ "Pontos de Interesse:"+"\n");
		if (listaPontosInteresse.size()==0){					// caso nao existam pontos de interesse na lista
			return (buffer+"Inexistentes\n");					// adicionamos inexistentes
		}
		else{													// caso contrario
			for (int i=0;i<this.listaPontosInteresse.size();i++){
				aux = this.listaPontosInteresse.get(i);			// obtemos o ponto de interesse		
				buffer=buffer.concat(aux.toString());			// adicionamos o seu nome
			}
		}
		buffer = buffer+"\n";
		return (buffer);
	}
}
