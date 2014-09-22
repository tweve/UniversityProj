package biblioteca;

import desnecessarias.Multa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

public abstract class Utente implements Serializable{
  
        protected String tipo;
        protected String nome;
        protected String username;
        protected String password;
        protected String telefone;
        protected String morada;
        protected int nEmprestimos = 0;
        protected int nReservas = 0;
        protected int periodoEmprestimo;
        protected  int livrosEmAtraso= 0;
        
        private int limiteEmprestimos = 0;
	private int limiteReservas = 0;
        
        
        
        Utente(String username,String password){
            this.username = username;
            this.password = password;
            //multas = new <Multa>ArrayList();
        }
        
        /**
         * 
         * @return String tipo de utente
         */
        public String getTipo(){
            return this.tipo;
        }
        
        /**
         * 
         * @param tipo String tipo de utente
         */
        public void setTipo(String tipo){
            this.tipo = tipo;
        }
        
        /**
         * 
         * @return String nome do utente
         */
        public String getNome() {
		return this.nome;
	}
        /**
         * 
         * @return int livros em atraso
         */
        public int getLivrosNAtrasados() {
		return this.livrosEmAtraso;
	}
         /**
          * 
          */
         public void setLivrosNAtrasados() {
		 this.livrosEmAtraso= 0;
	}
         /**
          * 
          * @param nome String nome do utentes
          */
         public void setNome(String nome) {
		this.nome = nome;
	}
         
        /*public boolean getMulta() {
                     
            if (!multas.isEmpty()){
                for(int j=0; j< Biblioteca.fifoReservas.size();j++){
                    Utente t= Biblioteca.fifoReservas.get(j).getUtente();
                    if(t.equals(this) && Biblioteca.fifoReservas.get(j).outOfDate()){
                        this.livrosEmAtraso ++; 
                        
                    }
                }
                if(this.livrosEmAtraso!= 0){
                    return false;  
                }              
            }           
           return true;
	}*/
         
       /* public ArrayList<Multa> getMultas() {
             return this.multas;
         }
        */
        /**
         * 
         * @return String username do utente
         */
        public String getUsername() {
		return this.username;
	}
        
        /**
         * 
         * @param username String username do utente
         */
        public void setUsername(String username){
                this.username = username;
        }
        
        /**
         * 
         * @return String password do utente
         */
        public String getPassword() {
		return this.password;
	}
        
        /**
         * 
         * @param password String password do utente
         */
        public void setPassword(String password){
                this.password = password;
        }
        
        /**
         * 
         * @return String telefone do utente
         */
        public String getTelefone() {
                return telefone;
	}

        /**
         * 
         * @param telefone String telefone do utente
         */
        public void setTelefone(String telefone) {
                this.telefone = telefone;
	}
        
        /**
         * 
         * @return String morado do utente
         */
        public String getMorada() {
                return morada;
    	}

        /**
         * 
         * @param morada String morada do Utente
         */
        public void setMorada(String morada) {
                this.morada = morada;
	}
                
        /**
         * 
         * @return int nemprestimos do utente
         */
        public int getNEmprestimos() {
                return nEmprestimos;
	}

        /**
         * 
         * @param nEmprestimos int numero do emprestimos do utente
         */
        public void setNEmprestimos(int nEmprestimos) {
                this.nEmprestimos = nEmprestimos;
	}
        
        /**
         * 
         * @return int numero reservas do utente
         */
        public int getNReservas() {
                return nReservas;
	}

        /**
         * 
         * @param nReservas int numero de reservas do utente
         */
        public void setNReservas(int nReservas) {
                this.nReservas = nReservas;
	}        

        /**
         * 
         * @return int periodo de emrpestimo
         */
        public int getPeriodoEmprestimo() {
                return periodoEmprestimo;
	}

        /**
         * 
         * @param periodoEmprestimo int periodo de empretimo
         */
        public void setPeriodoEmprestimo(int periodoEmprestimo) {
                this.periodoEmprestimo = periodoEmprestimo;
	}
        
        /**
         * 
         * @return int limite de emprestimos
         */
        public int getLimiteEmprestimos() {
		return limiteEmprestimos;
	}

        /**
         * 
         * @param limiteEmprestimos int limite de emprestimos
         */
        public void setLimiteEmprestimos(int limiteEmprestimos) {
                this.limiteEmprestimos = limiteEmprestimos;
	}

        /**
         * 
         * @return limite de reservas
         */
        public int getLimiteReservas() {
                return limiteReservas;
	}

        /**
         * 
         * @param limiteReservas int limite de reservas
         */
        public void setLimiteReservas(int limiteReservas) {
                this.limiteReservas = limiteReservas;
	}
}