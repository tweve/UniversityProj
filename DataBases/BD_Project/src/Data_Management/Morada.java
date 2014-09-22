/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Management;

/**
 *
 * @author Mateus
 */
public class Morada {
    
    //atributos de uma morada
    
    private String rua_resid = null;
    private String cod_postal;
    private String telefone;
    
    //constructor
    
    public Morada(String rua_resid, String cod_postal, String telefone) {
        
        this.rua_resid = rua_resid;
        this.cod_postal = cod_postal;
        this.telefone = telefone;
        
    }
    
    //interrogadores
    
    public String getAddress() { return this.rua_resid;  }
    
    public String getPostalCode() {return this.cod_postal; }
    
    public String getPhoneNumber() {return this.telefone; }
    
    //modificadores
    
    public void changeAdress(String rua) {
        
        this.rua_resid = rua;
    
    }
    
    public void changePhoneNumber(String phone) {
        
        this.telefone = phone;
    
    }
    
    public void changePostalCode(String cod_postal) {
        
        this.cod_postal = cod_postal;
        
    }
    
    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        
        s.append("*************Address Information****************\n");
        s.append("Rua de Residência: ").append(this.rua_resid.toString()).append("n");
        s.append("\n");
        s.append("Código Postal: ").append(this.cod_postal.toString()).append("\n\n");
        s.append("Telefone: ").append(this.telefone.toString()).append("\n");
        return s.toString();
    }
}
