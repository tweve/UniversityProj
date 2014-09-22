// Classe que representa a sa�da de um cliente. Deriva de Evento.

public class Saida extends Evento {


	//Construtor
	Saida (double i,int tipo ,Simulador s){
		super(i, s);
                this.tipo = tipo;
	}

	// M�todo que executa as ac��es correspondentes � sa�da de um cliente
	void executa (Servico serv){
            
            // Retira cliente do servi�o
          serv.removeServico();
   
        }

    // M�todo que descreve o evento.
    // Para ser usado na listagem da lista de eventos.
    public String toString(){
        if (this.tipo ==1)
         return "Saida AF em " + instante;
        else
           return "Saida AG em " + instante;
    }


}