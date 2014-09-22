import java.io.IOException;
/*
 * Igor Nelson Garrido da Cruz
 * Número: 2009111924
 * 
 * */
public class AVP {
	
	static final int VERMELHO = 1;
	static final int PRETO = 0;
	
	
	final static class No{
		
		String palavra;
		int ocorrencias;
		No direita;
		No esquerda;
		No pai;
		int cor;
		
		
		No( String pal )			// cria um novo nó com a palavra pal e 1 ocorrencia
		{ 	
			palavra = pal;
			ocorrencias = 1;
			esquerda = null;
			direita = null;
			pai = null;
			cor = VERMELHO; 			//1 = vermelho;
		}
		
		No( )
		{ 	this( null ); }
	
		No tio(){
			 No avo = this.avo();
		
			 if (avo == null)
			 	return null;
			 if (this.pai == this.esquerda)
			 	return avo.direita;
			 else 
				 return avo.esquerda;
		}
		
		No irmao(){
			if (this == this.pai.esquerda)
				return this.pai.direita;
			else 
				return this.pai.esquerda;
		}
	
		No avo(){
			if ((this != null) && (this.pai != null))
	                return this.pai.pai;
	        else
	                return null;
		}
	}
	
	final static class Arvore
	{
		No raiz;
		
		public Arvore() {
		    this.raiz = null;
		}


		public void inserir(String palavra){			// insere a palavra na árvore
		    No inserido = new No(palavra);
			
		    if (raiz == null){
		    	raiz = inserido;				//insere o novo nó vermelho
		    }
			
			else{
				
				No iterador = raiz;
				while(true){
					if (palavra.compareTo( iterador.palavra)<0){
						if (iterador.esquerda == null){			// se for o ultimo
							iterador.esquerda = inserido;		// inserimos
							break;
						}
						else
							iterador = iterador.esquerda;		// escolhemos o caminho da esquerda
					}
					
					else if (palavra.compareTo( iterador.palavra)>0){
						if (iterador.direita == null){			// se for o ultimo
							iterador.direita = inserido;		// inserimos
							break;
						}
						else
							iterador = iterador.direita;		// escolhemos o caminho da esquerda
					}
					else{										//alcanamos um no com a palavra que procuramos
						iterador.ocorrencias++;					// incrementamos
						break;									// saimos do ciclo
					}		
				}
				inserido.pai = iterador;	
			}

			caso1(inserido);

		}
		
		void caso1(No no) {				// o inserido é a raiz
		    if (no.pai == null)
		        no.cor = PRETO;
		    else
		        caso2(no);
		}
		
		void caso2(No no) {				// o pai é preto
		    if (no.pai.cor == PRETO)
		        return;
		    else
		        caso3(no);
		}
		
		void caso3(No no) {
			No tio = no.tio();
			if (tio != null && tio.cor == VERMELHO){
				no.pai.cor = PRETO;
				no.tio().cor = PRETO;
				no.avo().cor = VERMELHO;
				caso1(no.avo());
			}
			else
				 caso4(no);
		}
		
		void caso4(No no) {
		    if (no == no.pai.direita && no.pai == no.avo().esquerda) {
		        rEsquerda(no.pai);
		        no = no.esquerda;
		    } 
		    else if ((no == no.pai.esquerda )&& (no.pai == no.avo().direita)) {
		        rDireita(no.pai);
		        no = no.direita;
		    }
		    caso5(no);
		}
		
		void caso5(No no) {
		    no.pai.cor = PRETO;
		    no.avo().cor = VERMELHO;
		    if (no == no.pai.esquerda && no.pai == no.avo().esquerda) {
		        rDireita(no.avo());
		    } 
		    else {
		        rEsquerda(no.avo());
		    }
		}

		void rEsquerda(No no) {

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
		}

		void rDireita(No no)
		{

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
				 arvore.inserir(result[i].toLowerCase());
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
