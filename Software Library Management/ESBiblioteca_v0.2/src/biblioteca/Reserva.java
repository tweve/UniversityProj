package biblioteca;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Reserva implements Serializable{
	private GregorianCalendar data;
        public Utente utente;
        public Livro livro;

        /**
         * 
         * @param id int id do utilizador
         * @param loggedUser Utente autenticado no sistema 
         */
        public Reserva(int id, Utente loggedUser){
            this.livro= Biblioteca.listaLivros.get(id);
            this.utente = loggedUser;
        }
        
        /**
         * 
         * @param id int id do user
         * @param loggedUser Utente autenticado no sistema
         * @param acal GregorianCalendar data da reserva
         */
        public Reserva(int id, Utente loggedUser, GregorianCalendar acal){
            this.livro= Biblioteca.listaLivros.get(id);
            this.utente = loggedUser;
            this.data = acal;
        }
        
        /**
         * 
         * @return
         */
        public GregorianCalendar getData() {
		return this.data;
	}

        /**
         * 
         * @param data GregorianCalendar data da reserva
         */
        public void setData(GregorianCalendar data) {
                this.data = data;
        }
        
        /**
         * 
         * @return Utente utente responsavel pela reserva
         */
        public Utente getUtente(){
                return this.utente;
        }
        
        /**
         * 
         * @param utente Utente que pediu a reserva
         */
        public void setUtente(Utente utente){
                this.utente = utente;
        }
        
        /**
         * 
         * @return Livro rservado
         */
        public Livro getLivro(){
                return this.livro;
        }
        
        /**
         * 
         * @param livro rservado
         */
        public void setLivro(Livro livro){
                this.livro = livro;
        }   
        /**
         * 
         * @return boolean devolve true caso a data tenha expirado e false caso contrario
         */
        public boolean outOfDate(){
            GregorianCalendar d ;
            if(this.data.get(Calendar.HOUR) > this.data.get(Calendar.HOUR)+24) {
                return true;
            }
            else{
                return false;
            }
        }
}