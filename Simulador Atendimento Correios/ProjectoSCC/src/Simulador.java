/*
 * SCC 2011
 * Igor Nelson Garrido da Cruz
 * Gonçalo Silva Pereira
 * 
*/

public class Simulador {

 	// Rel�gio de simula��o - vari�vel que cont�m o valor do tempo em cada instante
    private double instante;
    // M�dias das distribui��es de chegadas e de atendimento no servi�o
    private double media_cheg, media_servAF,media_servAG,desvio_servAF,desvio_servAG;
	// N�mero de clientes que v�o ser atendidos
    private double percentagem_servAF;
    private int tempoSimulacao;
    
    private Boolean partilhaFunc;
    
	// Servi�o - pode haver mais do que um num simulador
    private Servico servicoAF;
    private Servico servicoAG;
    
    // Lista de eventos - onde ficam registados todos os eventos que v�o ocorrer na simula��o
    // Cada simulador s� tem uma
    private ListaEventos lista;
    
    private int num_atendedoresAF;
    private int num_atendedoresAG;

    // Construtor
    public Simulador() {
		// Inicializa��o de par�metros do simulador
                tempoSimulacao = 480;
                media_cheg = 1.2;
                percentagem_servAF = 0.2;
                
                num_atendedoresAF = 1;
                num_atendedoresAG = 2;
		
                media_servAF = 4;
                desvio_servAF = 1.5;
                media_servAG = 3.5;
                desvio_servAF = 1;
                
                partilhaFunc = false;
                
                
                
		// Inicializa��o do rel�gio de simula��o
		instante = 0;
                
		// Cria��o da lista de eventos
		lista = new ListaEventos(this);
		// Agendamento da primeira chegada
                // Se n�o for feito, o simulador n�o tem eventos para simular
		insereEvento (new Chegada(instante, this));
    }

        // programa principal
        public static void main(String[] args) {
            //cria GUI
            InterfaceGrafica form = new InterfaceGrafica();
            form.setVisible(true);

        }

    // M�todo que insere o evento e1 na lista de eventos
	void insereEvento (Evento e1){
		lista.insereEvento (e1);
	}

    // M�todo que actualiza os valores estat�sticos do simulador
	private void act_stats(){
		servicoAF.act_stats();
                servicoAG.act_stats();
                
	}

    // M�todo que apresenta os resultados de simula��o finais
	public String getRelat (){
                    
            return " Serviço Financeiro:\n " + servicoAF.relat() + "\n Serviço Geral:\n " +servicoAG.relat();
	}
        
         public double getUtilizacaoAF(){
            return servicoAF.getUtilizacao();
        }
         
        public double getUtilizacaoAG(){
            return servicoAG.getUtilizacao();
        }

    // M�todo executivo do simulador
	public void executa (){
            
                // Cria��o do servi�o
		servicoAF = new Servico (this,num_atendedoresAF, media_servAF, desvio_servAF);
                servicoAG = new Servico (this,num_atendedoresAG, media_servAG, desvio_servAG);
		Evento e1;
		// Enquanto n�o atender todos os clientes
		//while (servicoAF.getAtendidos() + servicoAG.getAtendidos() < n_clientes){
                while (instante < tempoSimulacao){
                    
    		lista.print();  // Mostra lista de eventos - desnecess�rio; � apenas informativo
			e1 = (Evento)(lista.removeFirst());  // Retira primeiro evento (� o mais iminente) da lista de eventos
			instante = e1.getInstante();         // Actualiza rel�gio de simula��o
			act_stats();                         // Actualiza valores estat�sticos
			if (e1.getTipo() == 1){
                            // se é do tipo financeiro
                            if (!partilhaFunc)                  // Não existe partilha de funcionários
                                e1.executa(servicoAF);          // Executa evento
                            else{
                                if (e1.isChegada()){           // É uma entrada    
                                    if (!servicoAG.filaVazia()){    // A outra fila nao esta vazia 
                                        e1.executa(servicoAF);       // Procede normalmente
                                    }
                                    else{                           // A outra fila está vazia
                                        e1.setTipo(0);             // Trocamos o tipo de evento
                                        e1.executa(servicoAG);       // Colocamos na outra fila
                                    }
                                }
                                else                                // É uma saida
                                    if (e1.getTipo()==1)            // AF
                                        e1.executa(servicoAF);
                                    else                            //AG
                                        e1.executa(servicoAG);      
                                        
                            }
                        }
                        else{
                            if (!partilhaFunc)                  // nao existe partilha
                               e1.executa(servicoAG);
                            else {
                                if (e1.isChegada()){
                                    if (!servicoAF.filaVazia()){
                                        e1.executa(servicoAG);
                                    }
                                    else{
                                        e1.setTipo(1);
                                        e1.executa(servicoAF);
                                    }
                                }
                                else{                                // É uma saida
                                        if (e1.getTipo()==1)            // AF
                                            e1.executa(servicoAF);
                                        else                            //AG
                                            e1.executa(servicoAG);
                                }
                            }
                        }
                        
		}
	}

    // M�todo que devolve o instante de simula��o corrente
    public double getInstante() {
        return instante;
    }

    public double getTempoSimulacao() {
        return tempoSimulacao;
    }
    public void setTempoSimulacao(int n) {
        this.tempoSimulacao = n;
    }
    // M�todo que devolve a m�dia dos intervalos de chegada
    public double getMedia_cheg() {
        return media_cheg;
    }
    public void setMedia_cheg(double m) {
        this.media_cheg = m;
    }

    public void setPercentagem_servAF(double m) {
        this.percentagem_servAF = m;
    }
     public double getPercentagem_servAF() {
        return this.percentagem_servAF;
    }
    
    //Storages
    
    public int getNumAtendedoresAF(){
        return this.num_atendedoresAF;
    }
    
    public int getNumAtendedoresAG(){
        return  this.num_atendedoresAG;
    }
    
    public void setNumAtendedoresAF(int n){
        this.num_atendedoresAF = n;
    }
    
    public void setNumAtendedoresAG(int n){
        this.num_atendedoresAG = n;
    }
    
    
    // M�todo que devolve a m�dia dos tempos de servi�o
    public double getMedia_servAF() {
        return media_servAF;
    }
    
    public double getMedia_servAG() {
        return media_servAG;
    }
    

    public void setMedia_servAF(double m) {
        this.media_servAF = m;
    }
    
    public void setMedia_servAG(double m) {
        this.media_servAG = m;
    }
    
    public void setDesvio_servAF(double m) {
        this.desvio_servAF = m;
    }
    
    public void setDesvio_servAG(double m) {
        this.desvio_servAG = m;
    }
     public double getDesvio_servAF() {
        return this.desvio_servAF;
    }
    
    public double getDesvio_servAG() {
        return this.desvio_servAG;
    }
    public void setPartilha(Boolean partilha){
        this.partilhaFunc = partilha;
    }

}