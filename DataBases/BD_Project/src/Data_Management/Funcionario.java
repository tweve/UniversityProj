/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Management;

import java.util.Calendar;

/**
 *
 * @author Mateus
 */
public class Funcionario extends Utilizador {
    

    private static long func_id;
    private String NIF;
    private Correio caixa_mensagens;
    private Morada func_morada;
    boolean logged = false;
    private static short times_logged = 0;  //controlar o numero de vezes que cada utilizador se pode logar em maquina diferentes
   
    public Funcionario(int id_user, String login, String pass, String nome) {
        
        super(id_user, login, pass, nome);
        
    }
    @Override
    public String getLogin() { return this.login; }
    
    @Override
    public String getPassWord() { return this.pass; }
    
    @Override
    public String getName() {return super.pNome; }
    
    @Override
    public int getID_User() {return  super.id_user; }
    
    
    @Override
    
    /*******************************************
     * Change user's password                  *
     *                                         *
     *******************************************/
    public void setPassWord(String n_pass) {
        
        this.pass = n_pass;
    }
    
    @Override
    public void setMorada(Morada n_morada) {
        
        System.out.println("Changing Address...");
        this.func_morada.changeAdress(n_morada.getAddress());
        this.func_morada.changePhoneNumber(n_morada.getPhoneNumber());
        this.func_morada.changePostalCode(n_morada.getPostalCode());
        System.out.println("Address changed successfully...");
        
    }

    public void showMensagensRecebidas(Correio pasta) {
        
        if (pasta.getCaixaEntrada().isEmpty() == true) {
            
            System.out.println("Não contém mensagens na sua caixa de entrada");
            
        }
        else {
            
            System.out.println("Mensagens Recebidas");
            System.out.println();
            
            for(Mensagem x: pasta.getCaixaEntrada()) {
                
                System.out.printf("Remetente: %s    Data Recepcao: %02d/%02d/%d\n", x.getRementente(), x.getDataRecepcao().get(Calendar.DAY_OF_MONTH), x.getDataRecepcao().get(Calendar.MONTH), x.getDataRecepcao().get(Calendar.YEAR));
                System.out.printf("Assunto: %s\n\n", x.getAssunto());
            }
        
        }
    }
    
    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        
        s.append("Nome: ").append(this.pNome).append("\n").append("ID_USER: ").append(this.id_user);
        s.append("\nLOGIN: ").append(this.login).append("\n").append("PASSWORD: ").append(this.pass);
        s.append("\n");
        
        return s.toString();
        
    }
}