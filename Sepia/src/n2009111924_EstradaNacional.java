package SEPIA;

import java.io.Serializable;
import java.util.ArrayList;


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
class n2009111924_EstradaNacional extends n2009108991_Estrada implements Serializable {
	/**Hash Calculations*/
	private static final long serialVersionUID = 1L;

	/**Road's conditions*/
	private int estadoConservacao;
	/**Road's Interest points list*/	
	private ArrayList<n2009108991_PontoInteresse> listaPontosInteresse = new ArrayList<n2009108991_PontoInteresse>();
	
	/**
	 * Class constructor
	 * 
	 * @param origem extreme A city
	 * @param destino extreme B city
	 * @param nome Road name
	 * @param distancia distance between both cities
	 * @param estadoConservacao quality of the road 0,1,2 2 worst
	 */
	public n2009111924_EstradaNacional(n2009108991_Cidade origem, n2009108991_Cidade destino, String nome,int distancia,int estadoConservacao){
		super(origem, destino, nome,distancia);
		double velocidadeA=90,velocidadeB=70;
		
		setEstadoConservacao(estadoConservacao);
		if (estadoConservacao == 1){ 							// amarelo penalizaçoes 10 %
			velocidadeA = velocidadeA - (velocidadeA *0.1);
			velocidadeB = velocidadeB - (velocidadeB *0.1);
		}
		else if (estadoConservacao == 2){ 						// vermelho penalizaçoes 25 %
			velocidadeA = velocidadeA - (velocidadeA *0.25);
			velocidadeB = velocidadeB - (velocidadeB *0.25);
		}
		super.setVelocidadeMaximaA(velocidadeA);
		super.setVelocidadeMaximaB(velocidadeB);
		this.setTipo("nacional");
	}
	
	/**
	 * Allows us to set the Road's quality
	 * 
	 * @param estadoConservacao road quality 0 better 2 worst
	 */
	public void setEstadoConservacao(int estadoConservacao){
		this.estadoConservacao = estadoConservacao;
	}
	
	/**
	 * Allows us to get the Road's quality
	 * 
	 * @return road quality 0 better 2 worst
	 */
	public int getEstadoConservacao(){
		return estadoConservacao;
	}
	
	/**
	 * Allows us to get the Road's number of interest Points
	 * 
	 * @return number of interest points
	 */
	public int getNumPontosInteresse(){
		return listaPontosInteresse.size();
	}
	
	/**
	 * Allows us to add an Interest Point to the Road
	 * 
	 * @param interest point to be added
	 */
	public void addPontoInteresse(n2009108991_PontoInteresse PontoInteresse){
		this.listaPontosInteresse.add(PontoInteresse);
	}
	
	/**
	 * Allows us to delete an Interest Point of the Road
	 * 
	 * @param interest point to be deleted
	 */
	public void delPontoInteresse(n2009108991_PontoInteresse PontoInteresse){
		/**Auxiliar variable allow us to travel the list*/
		n2009108991_PontoInteresse actual;
		for (int i=0;i<this.listaPontosInteresse.size();i++){
			actual = listaPontosInteresse.get(i);
			if (actual.getNome().equals(PontoInteresse.getNome()))			// quando encontrar um ponto com o nome igual
				listaPontosInteresse.remove(i);								// remove-o
		}
	
	}
	

	/**
	 * Allows us to get details of the interest point
	 * 
	 * @return Point of interest details
	 */
	public String toString(){
		/**Variable that contains the details*/
		String buff= new String();
		buff = buff.concat(super.toString());
		buff=buff.concat("Estado de Conservacao: "+this.estadoConservacao+"\n");
		buff=buff.concat("Pontos de Interesse: \n");
		n2009108991_PontoInteresse actual;
		
		if (this.listaPontosInteresse.size()==0)							// a lista está vazia
			return buff+"Inexistentes\n";									// escreve "inexistentes"
		else{
				for (int i=0;i<this.listaPontosInteresse.size();i++){		// percorres a lista
					actual = listaPontosInteresse.get(i);
					buff=buff.concat(actual.toString());					// escreve os pontos de interesse
			}
		return buff;
		
		}
	}
}
