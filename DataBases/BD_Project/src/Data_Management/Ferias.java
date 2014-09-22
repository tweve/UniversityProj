
package Data_Management;

public class Ferias {
    int id;
    String nome;
    String data_inicio;
    String data_fim;
    String confirma;
    
    Ferias(int id, String nome, String data_inicio, String data_fim,String confirma){
        this.id = id;
        this.nome = nome;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.confirma = confirma;
    }
    
    public int getID(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public String getInicio(){
        return this.data_inicio;
    }
    public String getFim(){
        return this.data_fim;
    }
    public String getConfirma(){
        return this.confirma;
    }
}
