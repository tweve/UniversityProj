import java.text.*;
import java.util.*;

// Classe que representa um servi�o com uma fila de espera associada

public class Servico {
	private int estado[]; // Vari�vel que regista o estado do servi�o: 0 - livre; 1 - ocupado
        private int atendidos; // N�mero de clientes atendidos at� ao momento
	private int atendedores;
        private double temp_ult, soma_temp_esp, soma_temp_serv; // Vari�veis para c�lculos estat�sticos
        
        private double mediaServico, desvioServico;
      
	private Vector fila; // Fila de espera do servi�o

	private Simulador s; // Refer�ncia para o simulador a que pertence o servi�o

	// Construtor
    Servico (Simulador s, int num_atendedores, double media, double desvio){
    	this.s = s;
		fila = new Vector(); // Cria fila de espera
               
                this.estado = new int[num_atendedores];                   // inicia os atendedores a 0 (livres)
                this.atendedores = num_atendedores;
                
		temp_ult = s.getInstante(); // Tempo que passou desde o �ltimo evento. Neste caso 0, porque a simula��o ainda n�o come�ou.
		atendidos = 0;  // Inicializa��o de vari�veis

		soma_temp_esp= 0;
		soma_temp_serv= 0;
                this.mediaServico = media;
                this.desvioServico = desvio;
		
	}

	// M�todo que insere cliente (c) no servi�o
    public void insereServico (Cliente c){
        
            Boolean atendido = false;
            for (int i = 0;i<this.estado.length;i++){     // Percorre os vários postos
                if (this.estado[i] == 0){                 // Se encontra um livre
                    s.insereEvento(new Saida(s.getInstante() + Aleatorio.normalGamaAF(mediaServico,desvioServico), c.getTipo(), s));
                    estado[i] = 1;
                    atendido = true;
                    break;
                }
            }
            if (!atendido)                                  // Se não é atendido
                fila.addElement(c);                         // Vai para a fila
    }
   
	// M�todo que remove cliente do servi�o AF
    public void removeServico(){
        
        atendidos++; // Regista que acabou de atender + 1 cliente
        if (fila.size()== 0){     // Se fila vazia liberta um posto
            for (int i = 0;i<this.estado.length;i++){     // Percorre os vários postos
                if (this.estado[i] == 1){                 // Se encontra um ocupado
                    this.estado[i] = 0;                  // liberta-o
                    break;                                  // para a procura
                }
            }
        }
        else { // Se n�o,
             // vai buscar pr�ximo cliente � fila de espera e
                 Cliente c = (Cliente)fila.firstElement();
                 fila.removeElementAt(0);
                 // agenda a sua saida para daqui a s.getMedia_serv() instantes
                 s.insereEvento (new Saida(s.getInstante()+ Aleatorio.normalGamaAG(mediaServico, desvioServico), c.getTipo() ,s));
        }
    }

	// M�todo que calcula valores para estat�sticas, em cada passo da simula��o ou evento
    public void act_stats(){
        // Calcula tempo que passou desde o �ltimo evento
		double temp_desde_ult = s.getInstante() - temp_ult;
		// Actualiza vari�vel para o pr�ximo passo/evento
		temp_ult = s.getInstante();
		// Contabiliza tempo de espera na fila
		
                // para todos os clientes que estiveram na fila durante o intervalo
		soma_temp_esp += fila.size() * temp_desde_ult;
               
		// Contabiliza tempo de atendimento
                for (int i = 0;i<this.estado.length;i++)
                    soma_temp_serv += estado[i] * temp_desde_ult;

	}

	// M�todo que calcula valores finais estat�sticos
    public String relat (){
        // Tempo m�dio de espera na fila
		double temp_med_fila = soma_temp_esp / (atendidos+fila.size());
               
               
                
		// Comprimento m�dio da fila de espera
		// s.getInstante() neste momento � o valor do tempo de simula��o,
                // uma vez que a simula��o come�ou em 0 e este m�todo s� � chamdo no fim da simula��o
		double comp_med_fila = soma_temp_esp / s.getInstante();
                
                
		// Tempo m�dio de atendimento no servi�o
		//double utilizacao_serv_AF = soma_temp_serv_AF / (s.getInstante()* s.getNumAtendedoresAF());
		//double utilizacao_serv_AG = soma_temp_serv_AG / (s.getInstante()* s.getNumAtendedoresAG());
                
                
                String relatorio = new String();
                DecimalFormat formato = new DecimalFormat("#0.000");
                // Apresenta resultados
                
		relatorio = relatorio.concat("   Tempo médio de espera "+formato.format(temp_med_fila)+"\n");
		relatorio = relatorio.concat("    Comp. médio da fila "+formato.format(comp_med_fila)+"\n");
		
		
              //  relatorio = relatorio.concat("Utilização do servi�oAF "+formato.format(utilizacao_serv_AF)+"\n");
                //	relatorio = relatorio.concat("Utilização do servi�oAG "+formato.format(utilizacao_serv_AG)+"\n");
		
                //relatorio = relatorio.concat("Tempo de simula��o "+formato.format(s.getInstante())+"\n"); // Valor actual
		relatorio = relatorio.concat("    Número de clientes atendidos "+atendidos+"\n");
		relatorio = relatorio.concat("    Número de clientes na fila "+fila.size()+"\n"); // Valor actual
                
                return relatorio;
	}

    public double getUtilizacao(){
        return  soma_temp_serv / (s.getInstante()* atendedores);
    }

    public String getTempoSimulacao(){
        DecimalFormat formato = new DecimalFormat("#0.000");
        return (formato.format(s.getInstante()));
    }
    
    // M�todo que devolve o n�mero de clientes atendidos no servi�o at� ao momento
    public int getAtendidos() {
        return atendidos;
    }
    public Boolean filaVazia(){
        if (fila.size() == 0)
            return true;
        else
            return false;
    }

}