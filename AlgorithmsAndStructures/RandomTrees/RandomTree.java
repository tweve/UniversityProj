import java.io.IOException;


/*
 * Igor Nelson Garrido da Cruz
 * Número: 2009111924
 * 
 * */

public class RandomTree {
	
	static class No {
		
		String palavra;
		int ocorrencias;
		No direita;
		No esquerda;
		int N; 		// tamanho | N = descendentes + 1  no entanto começamos a 0 para garantir que o primeiro no fica na raiz
		
		public No (String palavra){
			this.palavra = palavra;
			ocorrencias=1;
			direita = null;
			esquerda = null;
			N = 1; 
		}
		No( )						// cria árvore com raiz null
		{ 	this( null ); }
	}
		
	static class Arvore{
		No raiz;
		
		public Arvore(){	//alloca o header
			raiz = null;
		}
		
		public No copiaNo(No no){
			No temp = new No();
			
			temp.palavra = no.palavra;
	    	temp.direita = no.direita;
	    	temp.esquerda = no.esquerda;
	    	temp.ocorrencias = no.ocorrencias;
	    	temp.N = no.N;
	    	
			return temp;
		}
		public void limpaNo(No no){
			
			no.palavra = null;
	    	no.direita = null;
	    	no.esquerda = null;
	    	no.ocorrencias = 1;
	    	no.N = 0;
	    	
		}
		public No Procura(String pal, No raiz){
			
			No no = raiz;
			 
		    while (no != null) {
		        if (pal.equals(no.palavra))
		            return no;
		        else if (pal.compareTo(no.palavra)<0) {
		           	no = no.esquerda;   
		        } else {
		            no = no.direita;
		        }
		    } 

		    return null;
	    	
		}
		
		public void insere(String palavra){
			No aux = Procura(palavra, raiz);
			if (aux == null)
				this.raiz = inserir(palavra,this.raiz);
			else
				aux.ocorrencias++;
		}
		
		public No inserir(String palavra, No no){			// insere a palavra na árvore

			if (no == null){			// se a raiz esta a null
				no = new No(palavra);	// criamos um novo no e devolvemos esse valor
				return no;
			}
			else{						// caso contrário
				
				double probabilidade = (double)1/(no.N+1);
				double aleatorio = Math.random();
				
			    System.out.println(probabilidade);
			    System.out.println(aleatorio);	
			
		    // FALTA INCREMENTAR Ns

			    if (aleatorio < probabilidade ){
		    	
		    	
		    	
			    	if (palavra.compareTo( no.palavra)<0){
			    		No temp = copiaNo(no);
			    		limpaNo(no);
			    		no.palavra = palavra;
			    		no.direita = temp;
			    	}
			    	if (palavra.compareTo( no.palavra)>0){
			    		No temp = copiaNo(no);
			    		limpaNo(no);
			    		no.palavra = palavra;
			    		no.esquerda = temp;
		    		
		    		/*No temp = no;
		    		no = new No(palavra);
		    		no.esquerda = temp;*/
			    	}
		    	
		    }
		    else if (palavra.compareTo( no.palavra)<0)
		    {
				no.esquerda = inserir(palavra, no.esquerda);
		    }
			else if (palavra.compareTo( no.palavra)>0)
			{
				no.direita = inserir(palavra,no.direita);
			}
			else
				no.ocorrencias++;
		}
		return raiz;
	}
			
			
			
			
			
			/*
			
			
			
			
			double probabilidade=1;
			double aleatorio = 1;
			
			if (no == null){
				System.out.print("PRIMEIRO NULL");

				no = new No(palavra);
				return raiz;
				
			}
			else if (no.palavra == null){				// raiz - primeira iteraçao, muda o no return

				System.out.print("passa2");
				System.out.print("Subs Raiz\n");
				
				no = new No(palavra);
				no.N++;
				return no;
			}
			
			
				probabilidade = (double)1/(no.N+1);
				aleatorio = Math.random();
				
			    System.out.println(probabilidade);
			    System.out.println(aleatorio);	
			
		    

		    if (aleatorio < probabilidade ){
		    	// substitui raiz tem erro
		    	
		    		No aux = new No(palavra);
			    	No no_temp = copiaNo(no);
		    	
		    	if (aux.palavra.compareTo( no.palavra)<0){
		    		no = aux;
		    		
		    		aux.direita = no_temp;
		    		no.esquerda = aux;
		    	}
		    	
		    	else if(aux.palavra.compareTo( no.palavra)>0){
		    		no = aux;
	    		
	    		aux.esquerda = no_temp;
	    		no.direita = aux;
		    	}
		    }
			
		    else if (palavra.compareTo( no.palavra)<0){
				System.out.print("esquerda");
		    	no.N++;
		    	
				no.esquerda = inserir(palavra, no.esquerda);


		    }
			
			else if (palavra.compareTo( no.palavra)>0){
				System.out.print("direita");
				no.N++;
				no.direita = inserir(palavra,no.direita);


			}
			else 
				no.ocorrencias++;
		    return raiz;
		}*/


		void printInOrder(){
			printInOrder(raiz);
		}
		void printInOrder(No actual)
		{
			
			
			
			if( actual != null){
				printInOrder( actual.esquerda ); 		// Left
				System.out.println( actual.palavra+" "+actual.ocorrencias ); // Node
				printInOrder( actual.direita);
			
			}
		}
		
	}
	
	public static void main(String [] args){
		
		String txt = readLn(500);
		txt = txt.trim();
		Arvore arvore;
		arvore = new Arvore();

		while (txt.isEmpty() == false){
			txt = txt.replace(",", "");
			String[] result = txt.split(" ");

			for (int i=0;i<result.length;i++){
				 arvore.insere(result[i].toLowerCase());
				 arvore.printInOrder();
			}

			txt = readLn(500);
			txt = txt.trim();
			
			
		}
		arvore.printInOrder();
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
