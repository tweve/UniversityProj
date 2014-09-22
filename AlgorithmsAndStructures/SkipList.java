import java.io.IOException;

/*
 * Igor Nelson Garrido da Cruz
 * Número: 2009111924
 * 
 * */

public class SkipList {
	
	public final static int max_niveis = 5;
	
	public static int niveis(){
		/*Assumindo que no maximo queremos 5 */
		int nivel = (int)(Math.log(1.-Math.random())/Math.log(1.-max_niveis));
		return Math.min(nivel, max_niveis);
	}
	
	static class No {
		
		String palavra;
		int ocorrencias=1;
		No[] ponteiros;
		
		public No (int niveis, String palavra){
			this.palavra = palavra;
			ponteiros = new No[max_niveis+1];  				// 0 corresponde ao elemento seguinte 6 corresponde ao nivel maximo
		}
	}
		
	static class listaSaltos{
		No header;
		int posicao;
		
		public listaSaltos(){	//alloca o header
			this.header = new No(max_niveis, null);
			this.posicao = 0;
		}
		
		public void inserir(String palavra)
		{
		    No aux = header;	
		    No[] update = new No[max_niveis + 1];
		    
		    for (int i = posicao; i >= 0; i--) {
		        while (aux.ponteiros[i] != null && aux.ponteiros[i].palavra.compareTo(palavra) < 0) {
		            aux = aux.ponteiros[i];
		        }
		        update[i] = aux; 
		    }
		    aux = aux.ponteiros[0];

		    if (aux == null || aux.palavra.equals(palavra)== false) {        
		        int lvl = niveis();
		        // grava actualizacoes
		        if (lvl > posicao) {
		            for (int i = posicao + 1; i <= lvl; i++) {
		                update[i] = header;
		            }
		            posicao = lvl;
		        }
		        // insere o novo no
		        aux = new No(lvl, palavra);
		        for (int i = 0; i <= lvl; i++) {
		        	aux.ponteiros[i] = update[i].ponteiros[i];
	                update[i].ponteiros[i] = aux;
	            }
	        }
		    else 
		    	aux.ocorrencias++;
		}
		
		public void imprime(){
			No auxiliar = header.ponteiros[0];
			while (auxiliar != null){
				System.out.println(auxiliar.palavra+" "+auxiliar.ocorrencias);
				auxiliar = auxiliar.ponteiros[0];
			}
		}
		
	}
	
	public static void main(String [] args){
		
		String txt = readLn(500);
		txt = txt.trim();
		listaSaltos lista;
		lista = new listaSaltos();

		while (txt.isEmpty() == false){
			txt = txt.replace(",", "");
			String[] result = txt.split(" ");

			for (int i=0;i<result.length;i++){
				 lista.inserir(result[i].toLowerCase());
			}

			txt = readLn(500);
			txt = txt.trim();
			
			
		}
		lista.imprime();
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
