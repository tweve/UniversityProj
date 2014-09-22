package biblioteca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Biblioteca {
    
    public static ArrayList<Utente> listaUtentes = new ArrayList<Utente>();
    public static ArrayList<Livro> listaLivros = new ArrayList<Livro>();
	private static ArrayList listaFuncionarios;
        public static ArrayList<Reserva> fifoReservas= new ArrayList<Reserva>();
        public static ArrayList<Emprestimo> listaEmprestimos= new ArrayList<Emprestimo>();
        public static ArrayList<Classificacao> classificacoes = new ArrayList<Classificacao>();
        public static ArrayList<Livro> listaDevolucoes = new ArrayList<Livro>();
        public static ArrayList<Relatorio> listaRelatorios = new ArrayList<Relatorio>();
	private Utente utenteAutenticado;
        
        static Utente user = null;
        Verifica verifica = new Verifica();

        public void notificar() {
		throw new UnsupportedOperationException();
	}
        
        /**
         * 
         * @param username String nome de usuário
         * @param pw String password do usuário
         * @return boolean true se registado, false caso contrario
         */
        public static boolean registar(String username, String pw) {
            Boolean found = false;
            Utente temp;
            for (int i = 0;i<listaUtentes.size();i++){
                temp = listaUtentes.get(i);
                if(temp.getUsername().equalsIgnoreCase(username)){
                    found = true;
                    break;
                }
            }
            
            if (!found){
                
                listaUtentes.add(new Aluno(username,pw));
                return true;
            }
            
            return false;
                
	}
        
        /**
         * 
         * @param username String nome de usuário
         * @param password String password do usuário
         * @return true se autenticado , false caso contrario
         */
        public static boolean autenticar(String username, char[] password) {
            String pw = new String(password);
            Utente temp;
            for (int i = 0;i<listaUtentes.size();i++){
                temp = listaUtentes.get(i);
                if(temp.getUsername().equalsIgnoreCase(username)){
                    if (temp.getPassword().equalsIgnoreCase(pw)){
                        user = temp;
                        return true;
                    }
                    else
                        break;
                }
            }
            return false;
            
	}
        
        /**
         * 
         * @param temp Livro livro a adicionar
         */
        synchronized public static void addLivro(Livro temp){
           
                listaLivros.add(temp);
            
        }
        
        /**
         * 
         * @param temp Livro adiciona devolucao
         */
        synchronized public static void addDevolucao(Livro temp){
                listaDevolucoes.add(temp);
            
        }
        
         /**
          * 
          * @param i int id do livro na listas de livros
          * @param temp Livro a novas propriedades do livro
          */
         synchronized public static void editLivro(int i, Livro temp){
            // O método é synchornized para o caso de 2 possiveis ediçoes ao mesmo tempo n levarem a inconsistência na arraylist
          
                listaLivros.set(i, temp);
            
        }
        
         /**
          * 
          * @param id int id do livro na lista
          * @param quantity int quantidade a eliminar
          * @return -1 caso nao existam livros suficientes , 1 caso contrario
          */
         synchronized public static int deleteBook(int id, int quantity){
            
            Livro book = listaLivros.get(id);
            
            
            if (book.getQuantidade() < quantity){
                // Não existe esta quantidade de liivros
                return -1;
            }
            if (book.getQuantidade() == quantity){
                // se existirem na biblioteca tantos ou menos livros quantos queremos remover
                // removemos o proprio identificador do livro
                listaLivros.remove(id);
            }
            else {
                // caso contrário apenas decrementamos a quantidade
                book.setQuantidade(book.getQuantidade()-quantity);
            }
            return 1;
        }
        

        /**
         * 
         * @param isbn String isbn do livro a pesquisar
         * @return ArrayList de Strings
         */
        public static ArrayList<String> search(String isbn){
            ArrayList<String> lista = new ArrayList<String>();
            Livro temp;
            for (int i = 0;i< listaLivros.size();i++){
                temp = listaLivros.get(i);
                if (temp.getIsbn().equals(isbn)){
                    lista.add(i+"- "+ temp.getNome()+ " "+ temp.getAutores()+" " +"Qtd:"+temp.getQuantidade()+" Disp:"+temp.getDisponivel());
                    break;
                }
            }
            return lista;
        }
        
        /**
         * 
         * @return ArrayList de Strings
         */
        public static ArrayList<String> searchR(){
            ArrayList<String> lista = new ArrayList<String>();
            
            Reserva temp;
            for (int i = 0;i< fifoReservas.size();i++){
                temp = fifoReservas.get(i);                
                 lista.add(i+" "+ temp.getLivro().getNome());
            }
            return lista;
        }
        
         /**
          * 
          * @param nome String nome do Livro
          * @return ArrayList de Strings
          */
         public static ArrayList<String> searchByName(String nome){
            ArrayList<String> lista = new ArrayList<String>();
            Livro temp;
            for (int i = 0;i< listaLivros.size();i++){
                temp = listaLivros.get(i);
                if (temp.getNome().contains(nome)){
                    lista.add(i+"- "+ temp.getNome()+ " "+ temp.getAutores()+" " +"Qtd:"+temp.getQuantidade()+" Disp:"+temp.getDisponivel());
                }
            }
            return lista;
        }
         
         /**
          * 
          * @param nome String com o nome Livro
          * @return ArrayList de Strings
          */
         public static ArrayList<String> searchRByName(String nome){
            ArrayList<String> lista = new ArrayList<String>();
            Reserva temp;
            for (int i = 0;i< fifoReservas.size();i++){
                temp = fifoReservas.get(i);
                if (temp.getLivro().getNome().contains(nome)){
                    lista.add(i+"- "+ temp.getLivro().getNome());
                }
            }
            return lista;
        }
         /**
          * 
          * @return ArraList de Strings com as infos dos livros
          */
         public static ArrayList<String> search(){
            ArrayList<String> lista = new ArrayList<String>();
            Livro temp;
            for (int i = 0;i< listaLivros.size();i++){
                temp = listaLivros.get(i);                
                 lista.add(i+"- "+ temp.getNome()+ " "+ temp.getAutores()+" " +"Qtd:"+temp.getQuantidade()+" Disp:"+temp.getDisponivel());
            }
            return lista;
        }
        
        /**
         * 
         * @param u String username de utente
         * @param id int posicao do livro na lista
         * @param pontos int com a classificacao de 1 a 5
         * @return
         */
        public static int classifica(String u,int id, int pontos){
            int classificado = 1;
            Livro temp = listaLivros.get(id);
            
            for (int i = 0;i<classificacoes.size();i++){
                Classificacao cla = classificacoes.get(i);
                if (cla.getLivro().getIsbn().equals(temp.getIsbn()) && cla.getUtente().equals(u)){
                    
               
                    classificado = -1;
                    return classificado;
                }
                    
          
            }
            
            Classificacao classifica = new Classificacao(u,temp,pontos);
                 classificacoes.add(classifica);
              return classificado;
        
            }
            
        /**
         * 
         * @param livro String com nome do livro
         * @return pontuacao/nutilizadores
         */
        public static double getClassificacao(String livro){
            //System.out.println("encontra");
            double pontuacao = 0;
            int nUtilizadores = 0;
            
            for (int i = 0;i<classificacoes.size();i++){
                Classificacao cla = classificacoes.get(i);
                if (cla.getLivro().getNome().equals(livro)){
                    
                    pontuacao += cla.getPontos();
                    nUtilizadores++;
                }
            }
            double finalp = pontuacao/nUtilizadores;
            return finalp;
        }
        
        
        public void emprestar() {
		throw new UnsupportedOperationException();
	}

        /**
         * 
         */
        public void reservar() {
		throw new UnsupportedOperationException();
	}

        /**
         * 
         */
        public void devolver() {
		throw new UnsupportedOperationException();
	}

        /**
         * 
         */
        public void pesquisar() {
		throw new UnsupportedOperationException();
	}
        /**
         * 
         * @return
         */
        public static Utente getUser(){
            return user;
        }
        
        /**
         * 
         * @param temp Utente utilizador a ser adicionado
         */
        public static void addUser(Utente temp){
            listaUtentes.add(temp);
        }
        
        /**
         * 
         */
        public static void saveData() {
            String filename = "listaLivros.bin";
            String filename1 = "fifoReservas.bin";
            String filename2 = "listaEmprestimos.bin";
            String filename3 = "classificacoes.bin";
            String filename4 = "utentes.bin";
            String filename5 = "listaRelatorios.bin";
            String filename6 = "listaDevolucoes.bin";
            
                    
            File f = new File(filename);
            File f1 = new File(filename1);
            File f2 = new File(filename2);
            File f3 = new File(filename3);
            File f4 = new File(filename4);
            File f5 = new File(filename5);
            File f6 = new File(filename6);
            
            
            try {
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                
                FileOutputStream fos1 = new FileOutputStream(f1);
                ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
                
                FileOutputStream fos2 = new FileOutputStream(f2);
                ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
                
                FileOutputStream fos3 = new FileOutputStream(f3);
                ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
                
                   
                FileOutputStream fos4 = new FileOutputStream(f4);
                ObjectOutputStream oos4 = new ObjectOutputStream(fos4);
               
                FileOutputStream fos5 = new FileOutputStream(f5);
                ObjectOutputStream oos5 = new ObjectOutputStream(fos5);

                FileOutputStream fos6 = new FileOutputStream(f6);
                ObjectOutputStream oos6 = new ObjectOutputStream(fos6);

                oos.writeObject(listaLivros);
                oos1.writeObject(fifoReservas);
                oos2.writeObject(listaEmprestimos);
                oos3.writeObject(classificacoes);
                oos4.writeObject(listaUtentes);
                oos5.writeObject(listaRelatorios);
                oos6.writeObject(listaDevolucoes);
                
                
                System.out.println("SALVO");
                fos.close();
            } catch (IOException ioe) {
                                            }
        }
        /**
         * 
         */
        public static void loadData() {
             ObjectInputStream inputStream = null;
            try {
                
                
                inputStream = new ObjectInputStream(new FileInputStream("listaLivros.bin"));
                listaLivros =  (ArrayList<Livro>)inputStream.readObject();
                if (listaLivros == null)
                    listaLivros = new ArrayList<Livro>();
                inputStream.close();
                
                
                
                 inputStream = new ObjectInputStream(new FileInputStream("fifoReservas.bin"));
                fifoReservas =  (ArrayList<Reserva>)inputStream.readObject();
                if (fifoReservas == null)
                    fifoReservas = new ArrayList<Reserva>();
                inputStream.close();
                
                
                inputStream = new ObjectInputStream(new FileInputStream("listaEmprestimos.bin"));
                listaEmprestimos =  (ArrayList<Emprestimo>)inputStream.readObject();
                if (listaEmprestimos == null)
                    listaEmprestimos = new ArrayList<Emprestimo>();
                inputStream.close();
                
                 inputStream = new ObjectInputStream(new FileInputStream("listaRelatorios.bin"));
                listaRelatorios =  (ArrayList<Relatorio>)inputStream.readObject();
                if (listaRelatorios == null)
                    listaRelatorios= new ArrayList<Relatorio>();
                inputStream.close();
                
                 inputStream = new ObjectInputStream(new FileInputStream("listaDevolucoes.bin"));
                listaDevolucoes =  (ArrayList<Livro>)inputStream.readObject();
                if (listaDevolucoes == null)
                    listaDevolucoes= new ArrayList<Livro>();
                inputStream.close();
                
                
                inputStream = new ObjectInputStream(new FileInputStream("classificacoes.bin"));
                classificacoes =  (ArrayList<Classificacao>)inputStream.readObject();
                if (classificacoes == null)
                    classificacoes = new ArrayList<Classificacao>();
                inputStream.close();
                
                
                
                inputStream = new ObjectInputStream(new FileInputStream("utentes.bin"));
                listaUtentes =  (ArrayList<Utente>)inputStream.readObject();
                if (listaUtentes == null)
                    listaUtentes = new ArrayList<Utente>();
                inputStream.close();
                
                System.out.println("all data loaded");
                
                
            } catch (FileNotFoundException e) {
                System.out.println("livros nf .");
            } catch (ClassNotFoundException e) {
              
            } catch (Exception e) {
            }
        }
        
           
}