package Data_Management;

public class Recibo {
    String data;
    int id;
    double quantia;
    double descontos;
    int horas_trab;
    
    Recibo(int id,String data,double quantia,int horas_trab){
        this.id = id;
        this.data = data;
        this.quantia = quantia;
        this.horas_trab = horas_trab;
        
    }
    public int getID(){
        return this.id;
    }
    public String getData(){
        return this.data;
    }
    
    public double getQuantia(){
       return this.quantia; 
    }
    
    public double getDescontos(){
        return this.descontos;
    }
    public double getHorasTrabalho(){
        return this.horas_trab;
    }
    
    

}
