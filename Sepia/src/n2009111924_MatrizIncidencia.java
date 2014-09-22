package SEPIA;

import java.util.ArrayList;

/**
 * 
 * @author Igor Cruz
 * @author Carlos Santos
 * 
 * @version 1
 * @since 0.1
 * 
 * Auxiliary class built in version 0.3 that allows to build matrices to a faster route calculation with Dijkstra Algorithm
 * 
 * This Matrix have 3 levels in z dimension [0],[1],[2]
 * [0] Stores the distances between 2 connected cities
 * [1] Stores the estimated times to travel between 2 connected cities * 100 (*100 to overcome approximation to integer)
 * [2] Stores the number of Interest points of Origin city(not required in project assignment) and national road
 * 
 * */
public class n2009111924_MatrizIncidencia {
	
	/**Matrix with connections between cities*/
	private int matrizIncidencia[][][];
	/**Matrix size x and y*/	
	private int tmnhx; int tmnhy;
	
	/**Auxiliary Lists to build and manage the Matrix Cities List*/
	private ArrayList<n2009108991_Cidade>listaCidades;
	/**Auxiliary Lists to build and manage the Matrix Roads List*/
	private ArrayList<n2009108991_Estrada>listaEstradas;
	
	/**
	 * Class constructor
	 * 
	 * @param listaCidades ArrayList of cities
	 * @param listaEstradas ArrayList of Roads
	 * @param tmnhx 		matrix size X
	 * @param tmnhy 		matrix size y
	 * @param veiculo		vehicle used to travel the route
	 */
	public n2009111924_MatrizIncidencia(ArrayList<n2009108991_Cidade> listaCidades, ArrayList<n2009108991_Estrada> listaEstradas,int tmnhx,int tmnhy,n2009108991_Viatura veiculo ){
		
		this.tmnhx = tmnhx;															//Atribuiçao de valores às variáveis do objecto
		this.tmnhy = tmnhy;
		this.listaEstradas = listaEstradas;
		this.listaCidades = listaCidades;
		matrizIncidencia = new int[tmnhx][tmnhy][3];
		n2009108991_Estrada estrada;
		
		/**Auxiliary allows to travel in the matrix*/
		int x,y;
		
			for (int i = 0;i< listaEstradas.size();i++){	
				estrada = listaEstradas.get(i);	// percorre estradas
				x=getPosCidade(estrada.getOrigem().getNome()); 				// posicao origem 
				y=getPosCidade(estrada.getDestino().getNome()); 				//posicao destino
				if (x==-1||y==-1){
					System.out.print("Ocorreu um erro grave, cidade nao localizada");
				}
				else{
					matrizIncidencia[x][y][0]=estrada.getDistancia();   				// estabelecemos ligaçao num sentido
					matrizIncidencia[y][x][0]=estrada.getDistancia();  				// estabelecemos ligaçao no outro sentido
					
					if (veiculo.getTipo().equals("A")){

						matrizIncidencia[x][y][1]=(int) Math.round((estrada.getDistancia()/estrada.getVelocidadeMaximaA())*100);	// estabelecemos ligaçao num sentido
						matrizIncidencia[y][x][1]=(int) Math.round((estrada.getDistancia()/estrada.getVelocidadeMaximaA())*100);	// estabelecemos ligaçao no outro sentido
					}
					else{
						matrizIncidencia[x][y][1]=(int) Math.round((estrada.getDistancia()/estrada.getVelocidadeMaximaB())*100);	// estabelecemos ligaçao no outro sentido
						matrizIncidencia[y][x][1]=(int) Math.round((estrada.getDistancia()/estrada.getVelocidadeMaximaB())*100);	// estabelecemos ligaçao no outro sentido
					}
					matrizIncidencia[x][y][2]=1 + estrada.getNumPontosInteresse(); 		// estabelecemos ligaçao num sentido  que todas as estradas teem nivel de interesse 1 para que a ligacao fique definida
					matrizIncidencia[y][x][2]=1 + estrada.getNumPontosInteresse(); 		// estabelecemos ligaçao no outro sentido
				}
			}
	}
	
	/**
	 * prints the matrix
	 * 
	 */
	public void imprimeMatrizIncidencia(){
		
		for(int x =0;x<tmnhx;x++){
			for(int y=0;y<tmnhy;y++)
				System.out.print(matrizIncidencia[x][y][1]+" ");
			System.out.print("\n");
			
		}
	
	}
	
	/**
	 * Used to get the position index of a given city in the cities list
	 * @param nome Name of the city
	 * @return 		index of the city, in case of inexistent city returns -1
	 */
	public int getPosCidade(String nome){
	
		int existe = 0;										// nao existe
		int i=0;
		for(;i<listaCidades.size();i++){
			if (listaCidades.get(i).getNome().equals(nome)){// encontra
				existe = 1;
				break;
			}
		}
		if (existe == 1)
			return i;
		else 
			return -1;
	}
	
	/**
	 * Used to get the position index of a given city in the cities list

	 * @param x 	Position x of the matrix
	 * @param y 	Position y of the matrix
	 * @param z		Position z of the matriz
	 * @return 		value for the required position, 0 if not connected
	 */
	public int getValue(int x,int y, int z){
		return this.matrizIncidencia[x][y][z];
	}
	
	/**
	 * Used to get the Road that connects 2 given cities

	 * @param x 	Position of the origin city
	 * @param y 	Position of the destination city
	 * @return 		the road that connects both cities
	 */
	public n2009108991_Estrada getRoad(int x,int y){
		/**Origin city*/
		n2009108991_Cidade origem= getCity(x);		
		/**Destination city*/
		n2009108991_Cidade destino= getCity(y);
		/**Auxiliary road allows to travel the list*/
		n2009108991_Estrada aux= listaEstradas.get(0);
		
		for(int i =0;i<listaEstradas.size();i++){
			aux = listaEstradas.get(i);
			// caution the next line used both origin and destination for verification, because the road can connect city A to B, but roads are double sided,  so at the same time it connects B to A
			if (aux.getOrigem().getNome().equals(origem.getNome()) && aux.getDestino().getNome().equals(destino.getNome()) || aux.getOrigem().getNome().equals(destino.getNome()) && aux.getDestino().getNome().equals(origem.getNome()) )
				return aux;
		}
		return aux;
	}
	
	/**
	 * Used to get the city in the given index

	 * @param x 	Position of the city
	 * @return 		City
	 */
	public n2009108991_Cidade getCity(int x){
		return listaCidades.get(x);		
	}
}
