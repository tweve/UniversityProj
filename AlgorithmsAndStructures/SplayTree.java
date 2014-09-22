/*
import java.io.IOException;

public class SplayTree
{
	static class No{
		
		String element;
		int ocorrencias;
		No right;
		No left;
		
		No( String pal )			// cria um novo nó com a palavra pal e 1 ocorrencia
		{ 	
			element = pal;
			ocorrencias = 1;
			left = null;
			right = null;
		}
		No( )						// cria árvore com raiz null
		{ 	this( null ); }
	}

	
    private static No root;
    private static No nullNode;
    
    static         // Static initializer for nullNode
    {
        nullNode = new No( null );
        nullNode.left = nullNode.right = nullNode;
    }

    public SplayTree( )
    {
        root = nullNode;
    }

    private static No newNode = null;  // Used between different inserts
    
    /**
     * Insert into the tree.
     * @param string the item to insert.
     * @throws DuplicateItemException if x is already present.
     
    public void insert( String string )
    {
    	
        if( newNode == null )
            newNode = new No( null );
        newNode.element = string;

        if( root == nullNode )
        {
            newNode.left = newNode.right = nullNode;
            root = newNode;
        }
        else
        {
            root = splay( string, root );
            if( string.compareTo( root.element ) < 0 )
            {
                newNode.left = root.left;
                newNode.right = root;
                root.left = nullNode;
                root = newNode;
            }
            else
            if( string.compareTo( root.element ) > 0 )
            {
                newNode.right = root.right;
                newNode.left = root;
                root.right = nullNode;
                root = newNode;
            }
            else
                System.out.print("Duplicado");
        }
        newNode = null;   // So next insert will call new
        
    }

   
    private static No header = new No( null ); // For splay
    
	No rotateWithLeftChild( No k2 )
    {
        No k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    No rotateWithRightChild( No k1 )
    {
        No k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    private No splay( String x, No t )
    {
        No leftTreeMax, rightTreeMin;

        header.left = header.right = nullNode;
        leftTreeMax = rightTreeMin = header;

        nullNode.element = x;   // Guarantee a match

        for( ; ; )
            if( x.compareTo( t.element ) < 0 )
            {
                if( x.compareTo( t.left.element ) < 0 )
                    t = rotateWithLeftChild( t );
                if( t.left == nullNode )
                    break;
                // Link Right
                rightTreeMin.left = t;
                rightTreeMin = t;
                t = t.left;
            }
            else if( x.compareTo( t.element ) > 0 )
            {
                if( x.compareTo( t.right.element ) > 0 )
                    t = rotateWithRightChild( t );
                if( t.right == nullNode )
                    break;
                // Link Left
                leftTreeMax.right = t;
                leftTreeMax = t;
                t = t.right;
            }
            else
                break;

        leftTreeMax.right = t.left;
        rightTreeMin.left = t.right;
        t.left = header.right;
        t.right = header.left;
        
        return t;
    }
    static void printInOrder(){
    	printInOrder(root);
    }
    static void printInOrder(No t)
	{
		if( root != null ){
			printInOrder( root.left ); 		// Left
			System.out.println( root.element+" "+root.ocorrencias ); // Node
			printInOrder( root.right);
		
		}
	}
    
public static void main(String [] args){
		
		String txt = readLn(500);
		txt = txt.trim();
		
		SplayTree arvore = new SplayTree();
		
		while (txt.isEmpty() == false){
			txt = txt.replace(",", "");
			String[] result = txt.split(" ");
		
			for (int i=0;i<result.length;i++){
				//arvore.printInOrder();
				arvore.insert(result[i].toLowerCase());
			}
			txt = readLn(500);
			txt = txt.trim();			
		}
		//arvore.printInOrder();
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




*/







import java.io.IOException;

/*
 * Igor Nelson Garrido da Cruz
 * Número: 2009111924
 * 
 * */
public class SplayTree {
	
	final static class No{
		
		String palavra;
		int ocorrencias;
		No direita;
		No esquerda;
		No pai;
		
		
		No( String pal )			// cria um novo nó com a palavra pal e 1 ocorrencia
		{ 	
			palavra = pal;
			ocorrencias = 1;
			esquerda = null;
			direita = null;
			pai = null;
		}
		
		No( )
		{ 	this( null ); }
		/*
		No tio(){
			 No avo = this.avo();
		
			 if (avo == null)
			 	return null;
			 if (this.pai == this.esquerda)
			 	return avo.direita;
			 else 
				 return avo.esquerda;
		}
		*/
		/*
		No irmao(){
			if (this == this.pai.esquerda)
				return this.pai.direita;
			else 
				return this.pai.esquerda;
		}*/
	
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
				splay(inserido);
			}
		}
		
		void splay(No no){
			if (no.pai != null)
				caso1(no);
			
		}
		
		void caso1(No no) {
			
		    if (no.pai == no.pai.direita && no.pai == no.avo().esquerda){
		    	rEsquerda(no);
		    	rDireita(no);
		    }
		    else if (no.pai == no.pai.esquerda && no.pai == no.avo().direita){
		    	rDireita(no);
		    	rEsquerda(no);
		    }
		    else
		        caso2(no);
		    	
		    }
		
		void caso2(No no) {
			
			if (no.pai != null){
			    if (no.pai == no.pai.direita && no.pai == no.avo().direita)
			    	rEsquerda(no.avo());
			    
			    else if (no.pai == no.pai.esquerda && no.pai == no.avo().esquerda)
			    	rDireita(no.avo());
			    
		    else
		        caso3(no);
		    
		    }
		}
		
		void caso3(No no) {

			
			if (no.pai == raiz){
				if (raiz.esquerda == no)
					rDireita(raiz); 		//roda raiz ou será no?
				
				else if (raiz.direita == no)
					rEsquerda(raiz);		// roda raiz
				
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










/*
 * Igor Nelson Garrido da Cruz
 * Número: 2009111924
 * 
 * */

/*
public class SplayTree {
	
	static public class Arvore{
		No raiz;
		
		No nullNode = new No(null);
		
		No novoNo = null;
		No header = new No(null);
	
		Arvore(){
			raiz = nullNode;
		}

		public void inserir(String palavra)
		{
			
			
			if (novoNo == null)
				novoNo = new No(palavra); 
			novoNo.palavra = palavra;
			
			if (raiz == nullNode){
				novoNo.esquerda = novoNo.direita = nullNode;
				raiz = novoNo;
			}
			else
			{
				raiz = splay(palavra,raiz);
				if (palavra.compareTo(raiz.palavra)<0)
				{
					novoNo.esquerda = raiz.esquerda;
					novoNo.direita = raiz;
					raiz.esquerda = nullNode;
					raiz = novoNo;
				}
				else if (palavra.compareTo(raiz.palavra)>0)
				{
					novoNo.direita = raiz.direita;
					novoNo.esquerda = raiz;
					raiz.direita = nullNode;
					raiz = novoNo;
				}
				else
					return;
				novoNo = null;
			}
		}
		
		No splay(String palavra, No no)
		{
			
			No MaxEsquerda, MinDireita;
			
			header.esquerda = header.direita = nullNode;
			MaxEsquerda = MinDireita = header;
			
			nullNode.palavra = palavra;
			
			for (;;)
			{
				if (palavra.compareTo(no.palavra)<0)
				{
					if (palavra.compareTo(no.esquerda.palavra)<0)
						no = rotateWithLeftChild(no);
					if (no.esquerda == nullNode)
						break;
					MinDireita.esquerda = no;
					MinDireita = no;
					no = no.esquerda;
				}
				else if( palavra.compareTo( no.palavra ) > 0 )
	            {
	                if( palavra.compareTo( no.direita.palavra ) > 0 )
	                    no = rotateWithRightChild( no );
	                if( no.direita == nullNode )
	                    break;
	                
	                MaxEsquerda.direita = no;
	                MaxEsquerda = no;
	                no = no.direita;
	            }
	            else
	                break;
			}

	        MaxEsquerda.direita = no.esquerda;
	        MinDireita.esquerda = no.direita;
	        no.esquerda = header.direita;
	        no.direita = header.esquerda;
	        return no;
	  	}
		
		No rotateWithLeftChild( No k2 )
	    {
	        No k1 = k2.esquerda;
	        k2.esquerda = k1.direita;
	        k1.direita = k2;
	        return k1;
	    }

	    No rotateWithRightChild( No k1 )
	    {
	        No k2 = k1.direita;
	        k1.direita = k2.esquerda;
	        k2.esquerda = k1;
	        return k2;
	    }
	    
	    void printInOrder(){
			printInOrder(raiz);
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
	
	static class No{
		
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

	
}
*/