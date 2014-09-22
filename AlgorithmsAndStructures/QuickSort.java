import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/*
 * Igor Nelson Garrido da Cruz
 * 2009111924 TP3
 * */

public class QuickSort {
	 public static final Random Aleatorio = new Random();
	
	static public class Entrada{
		
		float coordenadas[] = new float[6];
		StringBuilder descricao = new StringBuilder();
		int id;
		
		public Entrada(String linha, int id){
			this.id = id;
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
			
	static ArrayList<Entrada> listafinal= new ArrayList<Entrada>();
	
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
		
		
		txt = readLn(100);
		txt = txt.trim();
		

		for (int ent = 0; ent <n;ent++){
			
			Entrada entrada = new Entrada(txt, ent);

			lista[ent]=entrada;
			txt = readLn(100);
			
			if (txt != null)
				txt = txt.trim();
		}
		
                long startTime = System.currentTimeMillis();
                
		Ordena(lista,0 , lista.length-1);

                long endTime = System.currentTimeMillis();
		listafinal = repoeOrdem(lista);
	
                
                //System.out.println("Total elapsed time is :"+ (endTime-startTime));
                
		for(int i =0;i<listafinal.size();i++){
			System.out.println(listafinal.get(i));
		}
		
	}
	public static void Ordena (Entrada[] lista, int inicio, int fim){
		
                //System.out.println(lista);
		if (inicio>=fim)
                    return;
                if (fim> inicio){
                        
			int indice = divide(lista,inicio,fim);
			Ordena(lista, inicio, indice-1);	// Ordena anterior
			Ordena(lista, indice+1, fim);	//Ordena posterior
			
			
		}

	}
	public static int divide(Entrada[] lista, int inicio, int fim){
		int ind, j;
		ind =inicio + Aleatorio.nextInt(fim-inicio +1);
		Entrada tmp = lista[ind];
		

                lista[ind] = lista[fim];
                lista[fim] = tmp;
               
                ind = inicio;
                for (int i = ind; i < fim; i++ ){
                    if (lista[i].compara(tmp.coordenadas) <= 0) {
                        Entrada aux = lista[ind];
		

                        lista[ind] = lista[i];
                        lista[i] = aux;
                        
                        ind++;
                }
            }
		
		tmp = lista[ind];
		lista[ind]= lista[fim];
		lista[fim]= tmp;
		
		return ind;
	}
	public static ArrayList<Entrada> repoeOrdem(Entrada[] lista){
				
		int i = 0, inicio=0;
		int menor, pos=0;
		
		while(i < lista.length){
			menor=(2^31-1);
			inicio = i;
			while( i< lista.length-1 && lista[i].compara(lista[i+1].coordenadas) == 0)
				i++;
			
			
			pos = inicio;
			for(int j =inicio;j<=i;j++){
				if (lista[j].id < menor){
					menor = lista[j].id;
					pos = j;
				}
			}
			listafinal.add(lista[pos]);
			i++;
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