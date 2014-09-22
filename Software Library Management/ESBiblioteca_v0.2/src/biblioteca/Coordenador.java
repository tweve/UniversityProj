package biblioteca;

import java.util.ArrayList;

public class Coordenador extends Utente {
	private ArrayList<Relatorio> relatorios = new ArrayList<Relatorio>();

        /**
         * 
         * @param username String nome de Coordenados
         * @param password String password para o coordenador
         * @param nome String nome do coordenador
         * @param telefone String numero de telefone
         */
        public Coordenador(String username, String password, String nome, String telefone){
            super(username,password);
            this.tipo = "coord";
            this.nome = nome;
            this.telefone = telefone;
        }
        
        /**
         * 
         * @return ArrayList de Relatorios
         */
        public ArrayList<Relatorio> getRelatorios(){
            return relatorios;
        }
        /**
         * 
         * @param relatorios ArrayList de Relatorios
         */
        public void setRelatorios(ArrayList<Relatorio> relatorios){
            this.relatorios = relatorios;
        }
        
        /**
         * 
         */
        public void consultaRelatorio() {
		throw new UnsupportedOperationException();
	}

        /**
         * 
         */
        public void fazerRelatorio() {
		throw new UnsupportedOperationException();
	}
}