/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Management;

/**
 *
 * @author Mateus
 */
public abstract class Utilizador {
    
    protected int id_user;
    protected String login;
    protected String pass;
    protected String pNome;
    protected String uNome;
    protected Morada ut_morada;
    
    //contructor - por definir?
    
    public Utilizador(int id_user, String nome, String login, String pass ) {
        
        this.id_user = id_user;
        this.login = login;
        this.pass = pass;
        this.pNome = nome;
        
    }
    //métodos a herdar
    
    //protected abstract String getName();
    
    public abstract String getLogin();
    
    public abstract String getPassWord();
    
    public abstract String getName();
    
    public abstract int getID_User();
    
    //modificadores
    
    public abstract void setPassWord(String n_pass);
    
    public abstract void setMorada(Morada n_morada);
    
    
}
