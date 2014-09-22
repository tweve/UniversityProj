import java.io.IOException;
import java.util.ArrayList;


/*
 * Igor Nelson Garrido da Cruz
 * 2009111924 TP3
 * */

public class MergeSort {
	
	static public class Entrada{
		
		float coordenadas[] = new float[6];
		StringBuilder descricao = new StringBuilder();
		
		public Entrada(String linha){
			
			String[] result = linha.split(" ");		// procede à divisao com base nos espaços
			
			for (int i = 0; i< 6;i++)				// atribui as coordenadas
				coordenadas[i] = Float.parseFloat(result[i]);
			
			for (int i = 6; i< result.length ;i++){	// atribui o a descriçao das coordenadas
				if (i <  result.length-1 )			// ate ao penultimo adicionamos um espaco
					descricao.append(result[i]+" ");					
				else 
					descricao.append(result[i]);
			}
		}
		
		public int compara(float coords2[]){
			/*
			 * Devolve -1 caso as coordenadas da entrada sejam menores
			 * Devolve 0 caso as coordenadas da entrada sejam iguais
			 * Devolve 1 caso as coordenadas da entrada sejam maiores
			 */

			for (int i = 0; i< 6;i++){
				if (coordenadas[i]<coords2[i])
					return -1;
				else if ((coordenadas[i]>coords2[i]))
					return 1;
			}
			return 0;
		}

		public String toString(){
			
			StringBuilder temp = new StringBuilder();
			String aux = new String();
			for (int i = 0; i< 6;i++){			// atribui as coordenadas
				aux = String.format("%.0f ", coordenadas[i]);
				temp.append(aux);
			}
			temp.append(descricao);
			return temp.toString();
		}
	}
			
	ArrayList<Entrada> listafinal= new ArrayList<Entrada>();
	
	public static void main(String [] args){
		/*
		 * - coordenadas repetidas elimina todos, menos o primeiro
		 * - ordenamento por latitude e depois por longitude
		 * X - input termina com \n 
		 * X - output termina com \n
		 */
		int n = 0;
		String txt = readLn(100);
		txt = txt.trim();
		n = Integer.parseInt(txt);
		
		Entrada lista[] = new Entrada[n];
		ArrayList <Entrada> listafinal;
		
		txt = readLn(100);
		txt = txt.trim();
		

		for (int ent = 0; ent <n;ent++){
			
			Entrada entrada = new Entrada(txt);

			lista[ent]=entrada;
			txt = readLn(100);
			
			if (txt != null)
				txt = txt.trim();
		}
		long startTime = System.currentTimeMillis();
		lista = Ordena(lista);
	
		listafinal = eliminaRepetidos(lista);
                long endTime = System.currentTimeMillis();
                 
                System.out.println("Total elapsed time is :"+ (endTime-startTime));
		for (int i = 0; i< listafinal.size();i++){
			System.out.println(listafinal.get(i));
		}
	}
	public static Entrada[] Ordena(Entrada[] lista){
		
		if (lista.length >1){
			Entrada[] esquerda = new Entrada[lista.length/2];
			System.arraycopy(lista, 0, esquerda, 0, esquerda.length);
			
			Entrada[] direita= new Entrada[lista.length-esquerda.length];
			System.arraycopy(lista, esquerda.length, direita, 0, direita.length);
			
			esquerda = Ordena(esquerda);
			direita = Ordena(direita);
			junta(esquerda,direita, lista);
			return lista;
		}
		else
			return lista;
	}
	public static void junta(Entrada[] esquerda,Entrada[] direita,Entrada[] lista){
		
		int indiceEsquerda = 0;
		int indiceDireita = 0;
		int indiceLista = 0;

		while (indiceEsquerda < esquerda.length && indiceDireita < direita.length) {
		    if (esquerda[indiceEsquerda].compara(direita[indiceDireita].coordenadas)>0) {
		    	lista[indiceLista] = direita[indiceDireita];
		    	indiceDireita++;
		    }
		    else
		    {
		    	lista[indiceLista] = esquerda[indiceEsquerda];
		    	indiceEsquerda++;
		    }    
		    indiceLista++;
		}
		
		Entrada restante[];
		int restIndex;
		if (indiceEsquerda >= esquerda.length){
		    restante = direita;
		    restIndex = indiceDireita;
		}
		else {
		    restante = esquerda;
		    restIndex = indiceEsquerda;
		}
		
		for (int i = restIndex; i<restante.length; i++) {
		    lista[indiceLista]= restante[i];
		    indiceLista++;
		}		
	}
	
	public static ArrayList<Entrada> eliminaRepetidos(Entrada[] lista){
		
		ArrayList<Entrada> listafinal = new ArrayList<Entrada>();
		
		for (int i = 0;i<lista.length;i++){
			if (listafinal.isEmpty()){
				listafinal.add(lista[i]);
			}
			else{
				
				if ((listafinal.get(listafinal.size()-1)).compara(lista[i].coordenadas)==0){ // ultimo elemento
					continue;
				}
				else 
					listafinal.add(lista[i]);
			}
		}
		return listafinal;
		
	}
	static String readLn (int maxLg) //utility function to read from stdin
	{
		byte lin[] = new byte [maxLg];
		int lg = 0, car = -1;
		try {
			while (lg < maxLg){
				car = System.in.read();
				if ((car < 0) || (car == '\n')) break;
				lin [lg++] += car;
			}
		}
		catch (IOException e){
			return (null);
		}
		if ((car < 0) && (lg == 0)) return (null); // eof
		return (new String (lin, 0, lg));
	}

}