import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import mpi.*;



public class Main {

	// DEFINES
	public static final int RULE_SIZE = 11;
	public static final int INPUT_SIZE = 10;
	//private static final String RULES_FILE = "/dataset/THE_PROBLEM/rules2M.csv";
	//private static final String INPUTS_FILE = "/dataset/THE_PROBLEM/trans_day_0.csv";

	private static String RULES_FILE = "/dataset/debug_extra_small/debug_rules_table.csv";
	private static String INPUTS_FILE = "/dataset/debug_extra_small/debug_input_transactions.csv";


	static String OUTPUT_FILE = "output2.csv";
	private static final int WINDOW = 1000000;
	private static final int INDEX = 15;
	static final int BUFFERWINDOW = 50000;

	// Variables
	public static int ntransactions = 0;
	static BufferedWriter bw = null;


	public static void main(String[] args) {

		int me,size;

		args = MPI.Init(args);
		me = MPI.COMM_WORLD.Rank();
		size = MPI.COMM_WORLD.Size();
		
		RULES_FILE = args[0];
		INPUTS_FILE = args[1];
		OUTPUT_FILE = args[2];
		
		if(me==0)
		{

			try {
				System.out.println(RULES_FILE);
				System.out.println(INPUTS_FILE);
				System.out.println(OUTPUT_FILE);
				
				long starttime= System.currentTimeMillis();
				bw = new BufferedWriter(new FileWriter(Main.OUTPUT_FILE));

				System.out.println("starting thread");

				while (size >1){

					//System.out.println("w8 info");
					int data[]=new int[RULE_SIZE];
					MPI.COMM_WORLD.Recv(data,0,RULE_SIZE,MPI.INT,MPI.ANY_SOURCE,99);


					if (data[0] == -1){
						System.out.println("decrease "+ size);
						size--;
						System.out.println(size);
					}

					else{

						String toWrite = "";

						for (int i = 0;i<Main.INPUT_SIZE;i++){
							toWrite += (data[i]+",");
						}
						toWrite += (data[Main.INPUT_SIZE]+"\n");

						bw.write(toWrite);

					}

				}

				long endtimeTotal = System.currentTimeMillis();
				System.out.println("totalTime (programa): " + ((endtimeTotal-starttime)/1000));


				System.out.println("finish");
				bw.close();

			} catch (Exception e1) {
				e1.printStackTrace();
			}



		}
		else{

			//char[] message = "Starting".toCharArray() ;
			//MPI.COMM_WORLD.Send(message, 0, message.length, MPI.CHAR, 0, 99) ;

			int[] rule = new int[RULE_SIZE];
			Node raiz = new Node((short) -1);

			int width = Math.round(100/(MPI.COMM_WORLD.Size()-1))+1;

			try {
				BufferedReader br = new BufferedReader(new FileReader(RULES_FILE));
				String line;
				String[] aux;


				while ((line = br.readLine())!= null){

					aux = line.split(",");
					for (int i = 0;i<RULE_SIZE;i++){
						rule[i] = Integer.parseInt(aux[i]);
					}
					if(rule[RULE_SIZE-1] >= ((MPI.COMM_WORLD.Rank()-1)* width) && rule[RULE_SIZE-1] < (MPI.COMM_WORLD.Rank()* width) )
						addRule(raiz, rule, 0);
				}


				//message = ("fimArv_"+me).toCharArray() ;
				//MPI.COMM_WORLD.Send(message, 0, message.length, MPI.CHAR, 0, 99) ;


				br.close();
				processData(raiz);

			} catch (IOException e) {
				e.printStackTrace();
			}

			//long endtimeTotal = System.currentTimeMillis();
			//System.out.println("totalTime (programa): " + ((endtimeTotal-starttime)/1000));*/
		}


		MPI.Finalize();
	}

	static void processData(Node raiz){

		try {

			BufferedReader br = new BufferedReader(new FileReader(INPUTS_FILE));
			//bw = new BufferedWriter(new FileWriter(OUTPUT_FILE));
			//ArrayBlockingQueue<int[]> queue = new ArrayBlockingQueue<int[]>(WINDOW);

			//WorkingThread[] threads = new WorkingThread[NTHREADS];
			/*
			for (int i = 0;i<NTHREADS;i++){
				threads[i] = new WorkingThread(queue,raiz);
				threads[i].start();
			}*/

			String line;
			String[] aux;
			int nline = 0;

			Buffer b = new Buffer();

			while ((line = br.readLine())!= null){


				aux = line.split(",");

				int tempInput[] = new int[INPUT_SIZE];
				for (int i = 0;i<INPUT_SIZE;i++){
					tempInput[i] = Integer.parseInt(aux[i]);
				}

				searchRule(raiz, tempInput, 0, b);

				nline++;
			}


			// o buffer nao estao cheio envia vector com -1 na ultima posicao para o outro processador saber que deve parar
			int[] temp = new int[Main.RULE_SIZE];
			temp[0] = -1;
			b.add(temp,-1);
			b.send();

			//bw.close();
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
	static void searchRule(Node raiz, int[] input, int pos, Buffer buffer) {
		if (pos == Main.RULE_SIZE-1){
			if (raiz.map == null){
				String toWrite = "";

				for (Node link:raiz.links){
					for (int i = 0;i<Main.INPUT_SIZE;i++){
						toWrite += (input[i]+",");
					}
					toWrite += (link.Element+"\n");


					if (buffer.isFull()){
						buffer.send();
						buffer.setEmpty();
					}
					buffer.add(input, link.Element);
				}
			}
			return;
		}

		int toSearch = input[pos];

		if (raiz.map == null){
			for (Node link:raiz.links){
				int linkValue = link.getElement();
				if (linkValue == toSearch || linkValue == 0){		
					searchRule(link,input,pos+1, buffer);
				}
			}
		}
		else{
			Node temp = raiz.map.get((short)toSearch);
			if (temp != null)
				searchRule(temp,input,pos+1, buffer );
			temp = raiz.map.get((short)0);
			if (temp != null)
				searchRule(temp,input,pos+1, buffer );
		}

	}



}
