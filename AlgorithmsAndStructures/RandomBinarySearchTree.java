import java.io.IOException;

/*
 * Igor Nelson Garrido da Cruz
 * Número: 2009111924
 * 
 * */

public class RBST {
	
	static int rotacoes=0;
	static int nos_atr = 0;
	
	static class No {
		
		String palavra;
		int ocorrencias;
		No direita;
		No esquerda;
		int N; 		// tamanho | N = descendentes + 1  no entanto começamos a 0 para garantir que o primeiro no fica na raiz
		int priority = 0;
		No pai = null;
		
		
		public No (String palavra){
			this.palavra = palavra;
			ocorrencias=1;
			direita = null;
			esquerda = null;
			N = 0; 
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
			if (raiz == null)
				raiz = new No(palavra);
			else{
				No aux = Procura(palavra, raiz);
				if (aux == null)
					this.raiz = inserir(palavra,this.raiz);
				else
					aux.ocorrencias++;
			}
		}
		
		public No inserir(String palavra, No no){			// insere a palavra na árvore

			int N=0;
			if (no == null)
				N = 0;
			else
				N = no.N;
			
			double probabilidade = (double)1/(N+1);
			double aleatorio = Math.random();
			
			if (aleatorio < probabilidade )
			    return split(palavra,no);

			else if (palavra.compareTo( no.palavra)<0)
		    {
				nos_atr++;
				no.esquerda = inserir(palavra, no.esquerda);
		    }
			else if (palavra.compareTo( no.palavra)>0)
			{
				nos_atr++;
				no.direita = inserir(palavra,no.direita);
			}
			
		no.N++;
		return no;
	}
			
			
	
	No split(String palavra, No no){
		if (no== null){
			No temp = new No(palavra);
			return temp;
		}
		if (no.palavra.compareTo(palavra)>0){
			no.esquerda = split(palavra, no.esquerda);
			no = rDireita(no);
		}
		else{
			no.direita = split(palavra,no.direita);
			no = rEsquerda(no);
		}
		return corrigeN(no);
		
	}
	public No corrigeN(No no)
	{
		if(no == null)
			return null;
		if(no.direita == null && no.esquerda == null)
			no.N = 1;
		else if(no.direita == null)
			no.N = corrigeN(no.esquerda).N +1;
		else if(no.esquerda == null)
			no.N = corrigeN(no.direita).N+1;
		else
			no.N = corrigeN(no.esquerda).N + corrigeN(no.direita).N + 1;
		return  no;
	}
	
	No rEsquerda(No no) {
		rotacoes++;
		No direito = no.direita;

	    if (no.pai == null)
	        raiz = direito;
	    else {
	        if (no == no.pai.esquerda)
	            no.pai.esquerda = direito;
	        else
	            no.pai.direita = direito;
	    }
	    if (direito != null) {
	        direito.pai = no.pai;
	    }
	    
	    if (direito != null)
	    	no.direita = direito.esquerda;
	    
	    if (direito.esquerda != null)
	        direito.esquerda.pai = no;
	    
	    direito.esquerda = no;
	    no.pai = direito;
	
	    return direito;
	}

	No rDireita(No no)
	{
		rotacoes++;
		No esquerdo = no.esquerda;
	    
		 if (no.pai == null)
		        raiz = esquerdo;
		 else {
	        if (no == no.pai.esquerda)
	            no.pai.esquerda = esquerdo;
	        else
	            no.pai.direita = esquerdo;
		 }
		 
	    if (esquerdo != null)
	    	esquerdo.pai = no.pai;
	    
	    
	    no.esquerda = esquerdo.direita;
	    if (esquerdo.direita != null)
	        esquerdo.direita.pai = no;
	    
	    esquerdo.direita = no;
	    no.pai = esquerdo;
	    
	    return esquerdo;
	}

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
			}

			txt = readLn(500);
			txt = txt.trim();
			
			
		}
		arvore.printInOrder();
		System.out.println("Número de nos percorridos: "+nos_atr);
		System.out.println("Número de rotações: "+rotacoes);
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
