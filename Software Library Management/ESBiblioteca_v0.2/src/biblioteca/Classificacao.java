package biblioteca;

import java.io.Serializable;

public class Classificacao implements Serializable {
    
	private int pontos;
	public String utente;
        
        public Livro livro;
        
        /**
         * 
         * @param utente String nome de utilizador
         * @param livro livro a classificar
         * @param pontos pontuacao 1 a 5
         */
        public Classificacao(String utente, Livro livro, int pontos){
            this.utente = utente;
            this.livro = livro;
            this.pontos = pontos;
        }

        
        public int getPontos() {
		return this.pontos;
	}

        /**
         * 
         * @param pontos int pontuacao
         */
        public void setPontuacaoo(int pontos) {
		this.pontos = pontos;
	}

        /**
         * 
         * @param utente String utente a colocar como autor da classificacao
         */
        public void setUtente(String utente) {
		this.utente = utente;
	}

        /**
         * 
         * @return Utentes responsavel pela classificacao
         */
        
        public String getUtente() {
		return this.utente;
	}

        /**
         * 
         * @param livro Livro a colocar como classificado
         */
        public void setLivro(Livro livro) {
		this.livro = livro;
	}

        /**
         * 
         * @return Livro coorespondente a classificacao
         */
        public Livro getLivro() {
		return this.livro;
	}
}