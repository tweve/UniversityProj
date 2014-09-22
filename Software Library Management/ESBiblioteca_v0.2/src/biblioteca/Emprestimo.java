package biblioteca;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

public class Emprestimo implements Serializable{
        Livro livro;
        Utente utente;
	private GregorianCalendar dataEmprestimo;
	private GregorianCalendar dataDevolucao;

        /**
         * 
         * @param livro Livro a ser emprestado
         * @param utente Utente que leva o livro
         */
        public Emprestimo(Livro livro, Utente utente){
            this.livro = livro;
            this.utente = utente;
            dataEmprestimo = new GregorianCalendar();
        }
        
        /**
         * 
         * @return Livro que foi emprestado
         */
        public Livro getLivro(){
            return this.livro;
        }
        
        /**
         * 
         * @return Utente que levou o livro emprestado
         */
        public Utente getUtente(){
            return this.utente;
        }
        
        /**
         * 
         * @return GregorianCalendar data de emprestimo do livro
         */
        public GregorianCalendar getDataEmprestimo() {
                return dataEmprestimo;
	}

        /**
         * 
         * @param GregorianCalendar data de emprestimo do livro 
         */
        public void setDataEmprestimo(GregorianCalendar dataEmprestimo) {
                this.dataEmprestimo = dataEmprestimo;
	}

        /**
         * 
         * @return GregorianCalendar data de devolucao do livro
         */
        public GregorianCalendar getDataDevolucao() {
                return dataDevolucao;
	}

        /**
         * 
         * @param dataDevolucao GregorianCalendar data de devolucao do livro
         */
        public void setDataDevolucao(GregorianCalendar dataDevolucao) {
                this.dataDevolucao = dataDevolucao;
	}
}