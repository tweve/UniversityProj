package biblioteca;

import java.io.Serializable;
import java.util.ArrayList;


public class Livro implements Serializable{
	private String isbn;
	private String nome;
	private String autores;
	private int edicao;
	private double custoAquisicao;
	private int disponivel;
	private boolean bibliotecaCondicionada;
	private int quantidade;
        ArrayList<String> comentarios = new ArrayList<String>();
        
        /**
         * 
         * @param isbn String isbn unico do livro
         * @param nome String nome do livro
         * @param autores String autores do livro
         * @param edicao int edicao do livro
         * @param custoAquisicao double custo de aquisicao do livro
         * @param quantidade int quantidade de exemplares na biblioteca
         * @param bibliotecaCondicionada boolean condicionado ou nao
         */
        public Livro(String isbn, String nome, String autores, int edicao, double custoAquisicao, int quantidade, boolean bibliotecaCondicionada  ){
            this.isbn = isbn;
            this.nome = nome;
            this.autores = autores;
            this.edicao = edicao;
            this.custoAquisicao = custoAquisicao;
            this.quantidade = quantidade;
            this.bibliotecaCondicionada = bibliotecaCondicionada;
            if (bibliotecaCondicionada)
                this.disponivel = 0;
            else
                this.disponivel = quantidade;
        }

        /**
         * 
         * @return String isbn unico do livro
         */
        public String getIsbn() {
		return isbn;
	}

        /**
         * 
         * @param isbn String isbn do livro
         */
        public void setIsbn(String isbn) {
                this.isbn = isbn;
	}
        
        /**
         * 
         * @return String nome do livro
         */
        public String getNome() {
                return this.nome;
	}

        /**
         * 
         * @param nome String nome do livro
         */
        public void setNome(String nome) {
                this.nome = nome;
	}

        /**
         * 
         * @return String autores do Livro
         */
        public String getAutores() {
		return autores;
	}

        /**
         * 
         * @param autores String autores do Livro
         */
        public void setAutores(String autores) {
		this.autores =  autores;
	}
        
        /**
         * 
         * @return int edicao do Livro
         */
        public int getEdicao() {
		return this.edicao;
	}

        /**
         * 
         * @param edicao int edicao do livro
         */
        public void setEdicao(int edicao) {
                this.edicao = edicao;
	}
        
        /**
         * 
         * @return double custo de aquisicao do livro
         */
        public double getCustoAquisicao() {
                return this.custoAquisicao;
	}

        /**
         * 
         * @param custoAquisicao double custo de aquisicao
         */
        public void setCustoAquisicao(double custoAquisicao) {
                this.custoAquisicao = custoAquisicao;
	}
        
        /**
         * 
         * @return int quantidade de exemplares na biblioteca
         */
        public int getQuantidade() {
		return this.quantidade;
	}

        /**
         * 
         * @param quantidade int quantidade de exemplares na biblioteca
         */
        public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
        
        /**
         * 
         * @return boolean pertence ou nao a biblioteca condiciona
         */
        public boolean getBibliotecaCondicionada() {
		return this.bibliotecaCondicionada;
	}

        /**
         * 
         * @param bibliotecaCondicionada boolean pertence ou nao a biblioteca condiciona
         */
        public void setBibliotecaCondicionada(boolean bibliotecaCondicionada) {
		this.bibliotecaCondicionada = bibliotecaCondicionada;
	}
        
        /**
         * 
         * @return int disponibilidade do livro pode ser negativa
         */
        public int getDisponivel() {
		return this.disponivel;
	}

        /**
         * 
         * @param disponivel int disponibilidade do livro
         */
        public void setDisponivel(int disponivel) {
		this.disponivel = disponivel;
	}

        /**
         * 
         * @return ArrayList de Strings com os comentários
         */
        public ArrayList<String> getComentarios() {
                return comentarios;
	}

        /**
         * 
         * @param comentarios ArrayLists de Strings com os comentários do livro
         */
        public void setComentarios(ArrayList<String> comentarios) {
                this.comentarios = comentarios;
	}
        
        /**
         * 
         * @param txt String com o comentario a ser adicionado
         */
        public void adicionaComentario(String txt ){
            this.comentarios.add(txt);
        }
        
        /**
         * 
         * @param i int com id do comentario a devolver
         * @return String comentario correspondente ao id i
         */
        public String getComentario(int i){
            return this.comentarios.get(i);
        }
        
}