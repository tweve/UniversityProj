// Classe que representa um cliente
// Como s�o indistintos neste exemplo, est� vazia

public class Cliente {

    private int tipo;                                                                   // 0 - Cliente para atendimento geral
    
    
	Cliente(double percentagemAF){
            double aleatorio = RandomGenerator.rand(2);                         // seed 2
           
            
            if (aleatorio < percentagemAF)
                this.tipo = 1;
            else 
                this.tipo = 0;
        }
	
	int getTipo(){
		return this.tipo;
	}
	
}