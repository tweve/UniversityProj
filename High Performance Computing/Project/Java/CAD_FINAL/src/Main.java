import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

	// DEFINES
	public static final int RULE_SIZE = 11;
	public static final int INPUT_SIZE = 10;
	private static final String RULES_FILE = "/dataset/THE_PROBLEM/rules2M.csv";
	private static final String INPUTS_FILE = "/dataset/THE_PROBLEM//trans_day_9.csv";
	private static final String OUTPUT_FILE = "output2.csv";
	private static final int NTHREADS = 2;
	private static final int WINDOW = 1000000;
	private static final int INDEX = 15;

	// Variables
	public static int ntransactions = 0;
	static BufferedWriter bw = null;


	public static void main(String[] args) {

		int[] rule = new int[RULE_SIZE];
		Node raiz = new Node((short) -1);

		long starttime= System.currentTimeMillis();

		try {
			BufferedReader br = new BufferedReader(new FileReader(RULES_FILE));
			String line;
			String[] aux;

			while ((line = br.readLine())!= null){

				aux = line.split(",");
				for (int i = 0;i<RULE_SIZE;i++){
					rule[i] = Integer.parseInt(aux[i]);
				}

				addRule(raiz, rule, 0);
			}

			br.close();
			processData(raiz);

		} catch (IOException e) {
			e.printStackTrace();
		}

		long endtimeTotal = System.currentTimeMillis();
		System.out.println("totalTime (programa): " + ((endtimeTotal-starttime)/1000));

	}

	static void processData(Node raiz){

		try {

			BufferedReader br = new BufferedReader(new FileReader(INPUTS_FILE));
			bw = new BufferedWriter(new FileWriter(OUTPUT_FILE));
			ArrayBlockingQueue<int[]> queue = new ArrayBlockingQueue<int[]>(WINDOW);

			WorkingThread[] threads = new WorkingThread[NTHREADS];

			for (int i = 0;i<NTHREADS;i++){
				threads[i] = new WorkingThread(queue,raiz);
				threads[i].start();
			}
			
			String line;
			String[] aux;
			while ((line = br.readLine())!= null){
				aux = line.split(",");

				int tempInput[] = new int[INPUT_SIZE];
				
				for (int i = 0;i<INPUT_SIZE;i++){
					tempInput[i] = Integer.parseInt(aux[i]);
				}
				try {
					queue.put(tempInput);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			int tempInput[] = new int[INPUT_SIZE];
			tempInput[0] = -1;
			
			for (int i = 0;i<NTHREADS+1;i++){
				try {
					queue.put(tempInput);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// após enviar enviar o trabalho para a arrayblockingqueue ajuda a processá-lo
		
			int[] input;
			while(true){
				try {
					input = queue.take();
					// se recebe sinal de fim termina
					if (input[0] == -1){
						break;
					}
					searchRule(raiz, input, 0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// espera pelas outras threads
			for (int i = 0;i<NTHREADS;i++){
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			bw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void addRule(Node raiz, int[] rule, int pos) {

		if (pos == RULE_SIZE){
			return;
		}

		int toAdd = rule[pos];
		Node toGo = null;

		if (raiz.map == null){
			int encontrado = 0;
			int index = 0;
			for (Node link:raiz.links){
				if (link.getElement() == toAdd){
					encontrado = 1;
					toGo = link;
				}
				index++;
			}
			if (encontrado == 0){
				Node temp = new Node((short) toAdd);
				raiz.links.add(temp);
				toGo = temp;
			}

			if (index > INDEX){
				raiz.map = new HashMap<Short, Node>();
				for (Node link:raiz.links){
					raiz.map.put( link.Element ,link);
				}
				//raiz.links.clear();
				//raiz.links = null;

			}

		}
		else{
			Node aux = raiz.map.get((short)toAdd);
			if (aux == null){ 
				Node temp = new Node((short) toAdd);
				raiz.map.put((short) rule[pos], temp);
				toGo = temp;
			}
			else{
				toGo = aux;
			}
		}
		addRule(toGo,rule,pos+1);

	}
	static void searchRule(Node raiz, int[] input, int pos) {
		if (pos == Main.RULE_SIZE-1){
			if (raiz.map == null){
				String toWrite = "";

				for (Node link:raiz.links){
					for (int i = 0;i<Main.INPUT_SIZE;i++){
						toWrite += (input[i]+",");
					}
					toWrite += (link.Element+"\n");
					try{
						Singleton.getInstance().writeToFile(toWrite);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			return;
		}

		int toSearch = input[pos];

		if (raiz.map == null){
			for (Node link:raiz.links){
				int linkValue = link.getElement();
				if (linkValue == toSearch || linkValue == 0){		
					searchRule(link,input,pos+1 );
				}
			}
		}
		else{
			Node temp = raiz.map.get((short)toSearch);
			if (temp != null)
				searchRule(temp,input,pos+1 );
			temp = raiz.map.get((short)0);
			if (temp != null)
				searchRule(temp,input,pos+1 );
		}

	}



}
