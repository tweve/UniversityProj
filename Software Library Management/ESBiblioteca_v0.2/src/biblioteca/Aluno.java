package biblioteca;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * 
 * @author Tweve
 */
public class Aluno extends Utente implements Serializable{
	private String numeroAluno;
	private int limiteEmprestimos = 3;
	private int limiteReservas = 3;
        private int limiteTempo;

        /**
         * 
         * @param username nome de utilizador de utente
         * @param password password do utente
         * @param nome Nome do utente
         * @param numeroAluno numero de aluno
         * @param tel telefone de contacto
         */
        
        public Aluno(String username,String password, String nome,String numeroAluno, String tel){
            super(username,password);
            this.tipo = "aluno";
            this.numeroAluno = numeroAluno;
            this.nome = nome;
            this.telefone = tel;
            this.periodoEmprestimo = 3;
        }
        
        /**
         * 
         * @param username nome de utilizador
         * @param password password do utilizador
         */
        public Aluno(String username,String password){
            super(username,password);
        }
        
        /**
         * 
         * @return
         */
        public String getNumeroAluno() {
                return numeroAluno;
	}

        /**
         * 
         * @param numeroAluno String numero de Aluno
         */
        public void setNumeroAluno(String numeroAluno) {
                this.numeroAluno = numeroAluno;
        }

        public int getLimiteEmprestimos() {
                return limiteEmprestimos;
	}

        /**
         * 
         * @param limiteEmprestimos int limite de emprestimos para o utilizador
         */
        public void setLimiteEmprestimos(int limiteEmprestimos) {
                this.limiteEmprestimos = limiteEmprestimos;
	}

        public int getLimiteReservas() {
                return limiteReservas;
	}

        /**
         * 
         * @param limiteReservas int limite de reservas para o dado utilizador
         */
        public void setLimiteReservas(int limiteReservas) {
                this.limiteReservas = limiteReservas;
	}

        public int getLimiteTempo() {
                return limiteTempo;
	}

        /**
         * 
         * @param limiteTempo int limite de tempo para o usuário
         */
        public void setLimiteTempo(int limiteTempo) {
                this.limiteTempo = limiteTempo;
	}
        
        
}