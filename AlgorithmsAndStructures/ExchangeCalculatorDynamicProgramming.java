import java.io.IOException;
import java.util.StringTokenizer;


/*
 * Igor Nelson Garrido da Cruz - 2009111924
 * TP-3
 * 
 */

public class B {
	
	static int num_moedas_min=9999;
	static int num_moedas_actual=0;
	static int quantia_salva=-9999;
	static int quantia_actual=0;
	

	public static void main(String [] args){

		String txt_quantia = readLn(20);
		String txt_moedas = readLn(50);
		
		txt_quantia = txt_quantia.replace("\r", "");
		txt_moedas = txt_moedas.replace("\r", "");
		
		int quantia = Integer.parseInt(txt_quantia);
		int num_moedas = 0;
		
		StringTokenizer separador;
		separador = new StringTokenizer(txt_moedas," ");
		int numero_moedas = separador.countTokens();
		
		
		int [][] tabela = new int[1000][numero_moedas];

		// Estruturação do vector moedas
		
		
		int moedas[] = new int[numero_moedas];
		
		int troco_temp[] = new int[numero_moedas];
		for (int i=0;i<numero_moedas;i++){
			moedas[i] = Integer.parseInt(separador.nextToken());
			
		}
		
		//int quantia_actual = quantia;
		
		int qt =1;
		for (;qt<=quantia;qt++){
			
			quantia_actual = qt;
			calculaTroco(tabela,moedas,tabela[qt],troco_temp,0);
			
			for(int j=0; j<troco_temp.length; j++){		//repoe valores
				troco_temp[j]=0;
			}

			num_moedas_min=9999;	//repoe os valores globais
			num_moedas_actual=0;
			quantia_salva=-9999;
			
		}

		// Imprime o troco
		for(int moeda = 0; moeda<moedas.length; moeda++){
			System.out.print(tabela[quantia][moeda]+" "+moedas[moeda]+"\n");
			num_moedas +=tabela[quantia][moeda]; 
		}
		System.out.print(num_moedas+"\n");
		if (quantia_salva == 0)
			System.out.println("TROCO EXATO"+"\n");
		else
			System.out.println("SEM TROCO EXATO"+"\n");

	}
	
	static void calculaTroco(int[][] tabela,int moedas[], int troco[],int troco_temp[],int num_moedas_actual){
		
		int usa =0;
		
		if (quantia_actual<=0){
			if ((quantia_actual == quantia_salva && num_moedas_actual<num_moedas_min) || (quantia_actual > quantia_salva)){
				for(int j=0; j<troco_temp.length; j++){
					troco[j]=troco_temp[j];
				}
				quantia_salva = quantia_actual;				
				num_moedas_min = num_moedas_actual;
			}
			
		}
		if (quantia_actual>0){		
			if (estaVazio(moedas.length,tabela[quantia_actual])== 0){// verifiacamos se temos o valor na tabela e fazemos load desse valor
				usa =1;
				
				int num_moedas=0;
				int quant=0;
				
				for (int i =0;i<moedas.length;i++){
					num_moedas += troco[i];
				}
				
				for (int i =0;i<moedas.length;i++){
					troco_temp[i]+= tabela[quantia_actual][i];
					num_moedas+=tabela[quantia_actual][i];
					quant += tabela[quantia_actual][i]*moedas[i]; 
				}
				quantia_actual-= quant;
				calculaTroco (tabela, moedas,troco,troco_temp,num_moedas);
			}
		}
		if (usa ==0){
			for(int moeda=0; moeda<moedas.length; moeda++){						// chama recursao com os diferentes valores de moedas
				if (quantia_actual>0){											// adapta dados para chamar recursao
					num_moedas_actual++;
					troco_temp[moeda]++;
					quantia_actual -= moedas[moeda];
					calculaTroco (tabela, moedas,troco,troco_temp,num_moedas_actual);		// chama recursão
					quantia_actual+= moedas[moeda];								// repõe os dados para chamar recursao outra vez
					troco_temp[moeda]--;
					num_moedas_actual--;
				}
			}
		}
	}
	
	static int estaVazio(int tmnh,int[]vector){
		int n=0;
		
		for (int i =0;i<tmnh;i++){
			n+= vector[i];
		}
		if (n == 0)
			return 1;
		else
			return 0;
	}
	
	static String readLn (int maxLg) //utility function to read from stdin
	{
		byte lin[] = new byte [maxLg];
		int lg = 0, car = -1;
		try {
			while (lg < maxLg){
				car = System.in.read();
				if ((car < 0) || (car == '\n')) break;
				lin [lg++] += car;
			}
		}
		catch (IOException e){
			return (null);
		}
		if ((car < 0) && (lg == 0)) return (null); // eof
		return (new String (lin, 0, lg));
	}
}
