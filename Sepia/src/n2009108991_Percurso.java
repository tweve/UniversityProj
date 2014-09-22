package SEPIA;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Igor Cruz
 * @author Carlos Santos
 * 
 * @version 2
 * @since 0.1
 * 
 * This class is a major class since the start of the project.
 * It represents one route
 * */
public class n2009108991_Percurso  implements Serializable {
	
	/**Hash value used to check the version.*/
	private static final long serialVersionUID = 1L;
	
	/**Origin City.*/
	private n2009108991_Cidade origem;
	/**Destination City.*/
	private n2009108991_Cidade destino;
	/**Route's Vehicle*/
	private n2009108991_Viatura viatura;
	/**List of Roads*/
	private ArrayList<n2009108991_Estrada> listaEstradas = new ArrayList<n2009108991_Estrada>();
	/**Route's Type, it can be curto,rapido or turistico*/
	private String tipo;
	/**Route's, total distance*/
	private double distancia;
	/**Route's price*/
	private double preco;


	/**
	 * Class constructor
	 * 
	 * @param origem extreme A city
	 * @param destino extreme B city
	 * @param viatura vehicle
	 * @param tipo it can be curto,rapido or turistico
	 */
	public n2009108991_Percurso(n2009108991_Cidade origem, n2009108991_Cidade destino,n2009108991_Viatura viatura,String tipo){
		setOrigem(origem);
		setDestino(destino);
		setViatura(viatura);
		setTipo(tipo);
	}
	
	/**
	 * Allows us to get the Route's origin
	 * 
	 * @return 		Origin City
	 */
	public n2009108991_Cidade getOrigem(){
		return origem;
	}
	
	/**
	 * Allows us to get the Route's destination
	 * 
	 * @return  	Destination City
	 */
	public n2009108991_Cidade getDestino(){
		return destino;
	}
	
	/**
	 * Allows us to get the Route's Vehicle
	 * 
	 * @return 		route's Vehicle
	 */
	public n2009108991_Viatura getViatura(){
		return viatura;
	}
	
	/**
	 * Allows us to get the Route's type
	 * 
	 * @return 		route's type
	 */
	public String getTipo(){
		return tipo;
	}
	
	/**
	 * Allows us to set the Route's origin
	 * 
	 * @param 	origem	route's origin city
	 */
	public void setOrigem(n2009108991_Cidade origem){
		this.origem=origem;
	}
	
	/**
	 * Allows us to set the Route's destination
	 * 
	 * @param 	destino	route's destination city
	 */
	public void setDestino(n2009108991_Cidade destino){
		this.destino=destino;
	}
	
	/**
	 * Allows us to set the Route's vehicle
	 * 
	 * @param viatura route's vehicle
	 */
	public void setViatura(n2009108991_Viatura viatura){
		this.viatura=viatura;
	}
	
	/**
	 * Allows us to set the Route's Type
	 * 
	 * @param 	tipo	route's type
	 */
	public void setTipo(String tipo){
		this.tipo=new String (tipo);
	}
	
	/**
	 * Allows us to get a String representing a route
	 * 
	 * @return  		Route's description
	 */
	public String toString(){
		
		/** Variable that contains the final description.*/
		String buff= new String();
		/** Auxiliary variable, allows us to travel in the road's list*/
		n2009108991_Estrada aux;

		buff = buff.concat ("Origem: "+this.origem+ "\nDestino: "+this.destino+"\nViatura: "+this.viatura.getMatricula()+"\nTipo: "+this.tipo+"\nDistancia: "+this.distancia+"\n"+"Preco: "+this.preco+"\n\n");
		
		if(listaEstradas.size()==0){								//nao contem estradas
			buff = buff.concat("Estradas Inexistentes");
		}
		else{														//contem estradas
			for (int i=0;i<listaEstradas.size();i++){
				aux = listaEstradas.get(i);							// nome + distancia 
				buff = buff.concat(aux.toString()/*"Nome da estrada:"+ aux.getNome()+" Origem:"+ aux.getOrigem().getNome()+" Destino:"+ aux.getDestino().getNome()+" Distancia: "+aux.getDistancia()+"\n"*/);
				
				
			}
		}
		return buff;
	}
	
	/**
	 * Allows us to calculate a route
	 * 
	 * @param matrizIncidencia graph with the city connections
	 * @param INI Origin city location in the city's list
	 * @param FIM destination city location in the city's list
	 * @param MAX total number of nodes in the graph
	 * @param precoGasolina the actual price of fuel
	 * @param precoGasoleo the actual price of fuel
	 * 
	 * It uses the Dijkstra Algorithm
	 */
	void calcula(n2009111924_MatrizIncidencia matrizIncidencia ,int INI,int FIM,int MAX,double precoGasolina,double precoGasoleo){
		/**z is determines with one of the graphs we are currently using (the are 3) 0,1,2 (0 for distances, 1 for Speeds, 2 for number of interest points)*/
		int z = 0;
		
		n2009108991_Estrada estrada;
		n2009111924_AutoEstrada autoestrada;
		final int infinito = 9999999;
		
		int foi[]= new int [MAX],custo[]= new int[MAX],anterior[]=new int[MAX];		// declaracao de variaveis

		int i, k, aux=0,minimo=0,maximo = 999999;
		
		if (this.tipo.equals("curto")){//Dijkstra
			z=0;
		
		for(i=0 ; i<MAX ; i++) { 														// inicializaçao das variaveis custo e foi
			foi[i] = 0;
		    custo[i] = infinito;
		    anterior[i]= 0;
		}

		custo[INI] = 0;		// custo partida 0
		foi[INI] = 1;		// foi ao inicio
		k = INI;			// posiçao actual = inicio
		
		
		while(foi[FIM]==0 && minimo!=infinito){                                			// enquanto nao foi ate ao fim e equanto houver caminhos disponiveis
			
			minimo = infinito;

			for(i=0 ; i<MAX ; i++)														// percorremos os vértices
				if(foi[i] == 0){
					
						if(custo[i]>custo[k] + matrizIncidencia.getValue(k, i, z)&&matrizIncidencia.getValue(k, i, z)!=0 ){ /* relaxamento */
							custo[i]=custo[k] + matrizIncidencia.getValue(k, i, z);			// escolhemos o menor custo
							anterior[i]=k;
						}
						if(custo[i]<minimo){
							minimo=custo[i];
							aux=i;
						}
					
	    	}
		    foi[aux]=1;																	// foi a aux
		    k = aux;																	// pos = aux
		}
		}
		if (this.tipo.equals("rapido")){
			z=1;
			
			for(i=0 ; i<MAX ; i++) { 														// inicializaçao das variaveis custo e foi
				foi[i] = 0;
			    custo[i] = infinito;
			    anterior[i]= 0;
			}

			custo[INI] = 0;		// custo partida 0
			foi[INI] = 1;		// foi ao inicio
			k = INI;			// posiçao actual = inicio
			
			
			while(foi[FIM]==0 && minimo!=infinito){                                			// enquanto nao foi ate ao fim e equanto houver caminhos disponiveis
				
				minimo = infinito;

				for(i=0 ; i<MAX ; i++)														// percorremos os vértices
					if(foi[i] == 0){
						
							if(custo[i]>custo[k] + matrizIncidencia.getValue(k, i, z)&&matrizIncidencia.getValue(k, i, z)!=0 ){ /* relaxamento */
								custo[i]=custo[k] + matrizIncidencia.getValue(k, i, z);			// escolhemos o menor custo
								anterior[i]=k;
							}
							if(custo[i]<minimo){
								minimo=custo[i];
								aux=i;
							}
						
		    	}
			    foi[aux]=1;																	// foi a aux
			    k = aux;																	// pos = aux
			}
		}
		if (this.tipo.equals("turistico")){
			z=2;
			
			for(i=0 ; i<MAX ; i++) { 														// inicializaçao das variaveis custo e foi
				foi[i] = 0;
			    custo[i] = 0;
			    anterior[i]= 0;
			}

			custo[INI] = 0;		// custo partida 0
			foi[INI] = 1;		// foi ao inicio
			k = INI;			// posiçao actual = inicio
			
			while(foi[FIM]==0 && maximo!=0){                                			// enquanto nao foi ate ao fim e equanto houver caminhos disponiveis
				
				maximo = 0;

				for(i=0 ; i<MAX ; i++)														// percorremos os vértices
					if(foi[i] == 0){
							if(custo[i]<custo[k] + matrizIncidencia.getValue(k, i, z)&&matrizIncidencia.getValue(k, i, z)!=0 ){ /* relaxamento */
								custo[i]=custo[k] + matrizIncidencia.getValue(k, i, z);			// escolhemos o menor custo
								anterior[i]=k;
							}
							if(custo[i]>maximo){
								maximo=custo[i];
								aux=i;
							}
						}
		    	
			    foi[aux]=1;																	// foi a aux
			    k = aux;																	// pos = aux
			}
		}

			
		

		if(foi[FIM]==1){																// conseguimos chegar ao fim
			int actual= FIM;
			while(actual!= INI){														// vaos desde o fim ao inicio a adicionar as estradas no inicio da lista
				listaEstradas.add(0,matrizIncidencia.getRoad(anterior[actual],actual)); 
				actual = anterior[actual];
			}
			for (int pos = 0;pos< listaEstradas.size();pos++){							// percorremos todas as estradas
				estrada= listaEstradas.get(pos);
				this.distancia += estrada.getDistancia();								// somamos todas as distancias
				if (this.viatura.getTipoCombustivel().equals("Gasolina"))
					this.preco += (estrada.getDistancia()*this.viatura.getConsumo()/100)* (precoGasolina); // carros classe A
				else
					this.preco += (estrada.getDistancia()*this.viatura.getConsumo()/100)* (precoGasoleo); // pesados
				
				// de seguida preço portagens
				
				if (estrada.getTipo().equals("autoestrada")){
					autoestrada = (n2009111924_AutoEstrada) estrada;
					if (this.viatura.getClass().equals("A"))
						preco+=autoestrada.getPrecoPortagemA();
					else 
						preco+= autoestrada.getPrecoPortagemB();
				}
			}
			
			
			
			
		}
		else
			System.out.print("Nenhuma estrada satisfaz o percurso.");
	}
}

