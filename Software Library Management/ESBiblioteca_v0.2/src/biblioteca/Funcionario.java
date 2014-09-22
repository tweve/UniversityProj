package biblioteca;


public class Funcionario extends Utente {
    
        /**
         * 
         * @param username String nome de Coordenados
         * @param password String password para o coordenador
         * @param nome String nome do coordenador
         * @param telefone String numero de telefone
         */
    public Funcionario(String username,String password, String nome, String tel){
            super(username,password);
            this.tipo = "func";
            this.nome = nome;
            this.telefone = tel;
        }
        
    public void reservarLivro() {
		throw new UnsupportedOperationException();
	}

        public void devolverLivro() {
		throw new UnsupportedOperationException();
	}

        public void levantarLivro() {
		throw new UnsupportedOperationException();
	}

        public void consultarLivro() {
		throw new UnsupportedOperationException();
	}

        public void inserirLivro() {
		throw new UnsupportedOperationException();
	}

        public void actualizarLivro() {
		throw new UnsupportedOperationException();
	}

        public void eliminarLivro() {
		throw new UnsupportedOperationException();
	}
        public void consultarConta() {
		throw new UnsupportedOperationException();
	}
        public void criarConta() {
		throw new UnsupportedOperationException();
	}

        public void modificarConta() {
		throw new UnsupportedOperationException();
	}
        public void eliminarConta() {
		throw new UnsupportedOperationException();
	}
}