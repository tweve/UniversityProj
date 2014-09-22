/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Management;

import java.util.ArrayList;

/**
 *
 * @author Mateus
 */
public class Correio {
    
    private ArrayList<Mensagem> caixa_Entrada = null;
    private ArrayList<Mensagem> caixa_saida = null;
    
    /*public Correio() {

        this.caixa_Entrada = new ArrayList<Mensagem>();
        this.caixa_saida = new ArrayList<Mensagem>();

    }*/
    
    //interrogadores
    
    public ArrayList<Mensagem> getCaixaEntrada() { return this.caixa_Entrada; }
    
    public ArrayList<Mensagem> getCaixaSaida() { return this.caixa_saida; }
    
    
}
