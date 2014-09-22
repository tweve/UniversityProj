/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Management;


public class Requisicao {
    String nome;
    String nome_sala;
    String data_inicio;
    String data_fim;

    
    Requisicao(String nome, String nome_sala, String data_inicio, String data_fim){

        this.nome = nome;
        this.nome_sala = nome_sala;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;

    }

    public String getNome(){
        return this.nome;
    }
    public String getNomeSala(){
        return this.nome_sala;
    }
    public String getInicio(){
        return this.data_inicio;
    }
    public String getFim(){
        return this.data_fim;
    }

}
