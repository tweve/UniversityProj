package biblioteca;

/**
 * 
 * @author Tweve
 */
public class Docente extends Utente {
	private int limiteEmprestimos = 4;
	private int limiteReservas = 4;
        private int limiteTempo;
	

        /**
         * 
         * @param username String com username de Utente
         * @param password String com password do utente
         * @param nome String com nome do utentes
         * @param telefone String com numero de telefone do utente
         */
        public Docente(String username,String password, String nome, String telefone){
            super(username,password);
            this.tipo = "docente";
            this.nome = nome;
            this.telefone = telefone;
            this.periodoEmprestimo = 4;
        }
        
        /**
         * 
         * @return int limite de emprestimos para o utilizador
         */
        public int getLimiteEmprestimos() {
		return limiteEmprestimos;
	}

        /**
         * 
         * @param limiteEmprestimos int limite de emprestimos para o dado utilizador
         */
        public void setLimiteEmprestimos(int limiteEmprestimos) {
                this.limiteEmprestimos = limiteEmprestimos;
	}

        /**
         * 
         * @return int limite de reservas do utilizador
         */
        public int getLimiteReservas() {
                return limiteReservas;
	}

        /**
         * 
         * @param limiteReservas int limitereservas para o utilizador
         */
        public void setLimiteReservas(int limiteReservas) {
                this.limiteReservas = limiteReservas;
	}

        /**
         * 
         * @return int limite de tempo que o utilizador pode ter o livro emprestado
         */
        public int getLimiteTempo() {
                return limiteTempo;
	}

        /**
         * 
         * @param limiteTempo que o utilizador pode ter o livro emprestado
         */
        public void setLimiteTempo(int limiteTempo) {
                this.limiteTempo = limiteTempo;
	}
}