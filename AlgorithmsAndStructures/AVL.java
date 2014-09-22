import java.io.IOException;


/*
 * Igor Nelson Garrido da Cruz
 * Número: 2009111924
 * 
 * */
public class AVL {
	static int nos_atr = 0;
	static int rotacoes = 0;
	final static class No{
		
		String palavra;
		int ocorrencias;
		No direita;
		No esquerda;
		
		No( String pal )			// cria um novo nó com a palavra pal e 1 ocorrencia
		{ 	
			palavra = pal;
			ocorrencias = 1;
			esquerda = null;
			direita = null;
		}
		
		No( )						// cria árvore com raiz null
		{ 	this( null ); }
	}
	
	final static class Arvore
	{
		No raiz;
		
		public Arvore() {
		    this.raiz = null;
		}
		
		public void insere(String palavra){
			this.raiz = inserir(palavra,this.raiz);
			
		}

		public No inserir(String palavra, No no){			// insere a palavra na árvore

		    
			if (no == null){
				
		    	no = new No (palavra);
		    }
			
			else if (palavra.compareTo( no.palavra)<0){
				
	        	 nos_atr++;
				no.esquerda = inserir(palavra, no.esquerda);
				if (profundidade(no.esquerda)-profundidade(no.direita)==2){
					if (palavra.compareTo( no.esquerda.palavra)<0)
						no = withLeftChild(no);
					else
						no = doubleWithLeftChild(no);
				}
			}
			
			else if (palavra.compareTo( no.palavra)>0){
				nos_atr++;
				no.direita = inserir(palavra,no.direita);
				if (profundidade(no.direita)-profundidade(no.esquerda)==2){
					if (palavra.compareTo( no.direita.palavra)>0)
						no = withRightChild(no);
					else
						no = doubleWithRightChild(no);
				}
				
			}
			else {
				no.ocorrencias++;
			}
			return no;
		}

		
		static No withLeftChild( No k1 ){
			rotacoes++;
			No k2 = k1.esquerda;
			k1.esquerda = k2.direita;
			k2.direita = k1;
			
			return k2;
		}

		static No withRightChild( No k1 ){
			 rotacoes++;
			 No k2 = k1.direita;
			 k1.direita = k2.esquerda;
			 k2.esquerda = k1;
			 
			 return k2;
		}
		static No doubleWithLeftChild( No k1 ){
			rotacoes++;
			k1.esquerda = withRightChild( k1.esquerda );
			return withLeftChild( k1 );
		}
		
		static No doubleWithRightChild( No k1 ){
			rotacoes++;
			k1.direita = withLeftChild( k1.direita );
			return withRightChild( k1 );
		}
		
		int profundidade(No actual){
			// devolve a profundidade de um determinado nó
			if (actual == null) 
				return 0;
			else {
				int profEsquerda = profundidade(actual.esquerda);
				int profDireita = profundidade(actual.direita);
				if (profEsquerda >= profDireita)
					return profEsquerda+1;
				else
					return profDireita+1;
					
			}
		}
	
		void printInOrder(){
			printInOrder(this.raiz);
		}
		void printInOrder(No actual)
		{
			if( actual != null ){
				printInOrder( actual.esquerda ); 		// Left
				System.out.println( actual.palavra+" "+actual.ocorrencias ); // Node
				printInOrder( actual.direita);
			
			}
		}
		
}

	
	public static void main(String [] args){
		
		String txt = readLn(500);
		txt = txt.trim();
		Arvore arvore = new Arvore();

		while (txt.isEmpty() == false){
			txt = txt.replace(",", "");
			String[] result = txt.split(" ");
			
			
			
			for (int i=0;i<result.length;i++){
				 arvore.insere(result[i].toLowerCase());
			}
			
			
			
			
			txt = readLn(500);
			txt = txt.trim();
			
			
		}
		
		arvore.printInOrder();

		System.out.print("Número de nos percorridos: "+nos_atr);
		System.out.print("Número de rotações: "+rotacoes);
		
		
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
