/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Management;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Mateus
 */
public class Mensagem {
    
    private String msg;
    private String assunto;
    private String remetente;
    private ArrayList<Utilizador> destinatarios;    //procurar outra solucao?
    private static int message_id;     //identificador da mensagem na caixa de entrada
    private Calendar data_envio;
    private Calendar data_recepcao;
    
    //Constructores
    
    public Mensagem(String msg, String remetente, ArrayList<Utilizador> dest) {
        
        this.msg = msg;
        this.assunto = null;
        this.remetente = remetente;
        this.destinatarios = dest;
        
    }
    
    public Mensagem(String remetente,String assunto,String msg) {
         
        this.msg = msg;
        this.assunto = assunto;
        this.remetente = remetente;
        
    }
    
    //interrogadores
    
    public String getMessage() { return this.msg; }
    
    public String getRementente() { return this.remetente; }
    
    public String getAssunto() { return this.assunto; }
    
    public ArrayList<Utilizador> getDestinatarios() { return this.destinatarios; }
    
    public int getMessageID() { return this.message_id; }
    
    public Calendar getDataEnvio() {return this.data_envio; }
    
    public Calendar getDataRecepcao() { return this.data_recepcao; }
    
    //modificadores
    
    public void setDataEnvio(Calendar date) {
        
        this.data_envio = date;
    }
    
    public void setDataRecepcao(Calendar date) {
        
        this.data_recepcao = date;
        
    }
}
