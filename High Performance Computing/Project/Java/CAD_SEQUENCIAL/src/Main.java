import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Main {

	// DEFINES
	public static final int RULE_SIZE = 11;
	public static final int INPUT_SIZE = 10;
	private static final String RULES_FILE = "/dataset/THE_PROBLEM/rules2M.csv";
	private static final String INPUTS_FILE = "/dataset/THE_PROBLEM//trans_day_0.csv";
	private static final String OUTPUT_FILE = "output.csv";

	// Variables
	public static int ntransactions = 0;
	static BufferedWriter bw = null;
	static String toWrite = "";

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

		int[] input = new int[INPUT_SIZE];

		try {

			BufferedReader br = new BufferedReader(new FileReader(INPUTS_FILE));
			bw = new BufferedWriter(new FileWriter(OUTPUT_FILE));
			String line;
			String[] aux;

			while ((line = br.readLine())!= null){
				aux = line.split(",");

				for (int i = 0;i<INPUT_SIZE;i++){
					input[i] = Integer.parseInt(aux[i]);
				}
				searchRule(raiz, input, 0);

			}
			br.close();
			bw.close();
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

			if (index > 20){
				raiz.map = new HashMap<Short, Node>();
				for (Node link:raiz.links){
					raiz.map.put( link.Element ,link);
				}
				raiz.links.clear();
				raiz.links = null;

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


	private static void searchRule(Node raiz, int[] input, int pos) {
		if (pos == RULE_SIZE-1){
			if (raiz.map == null){
				toWrite = "";

				for (Node link:raiz.links){
					for (int i = 0;i<INPUT_SIZE;i++){
						toWrite += (input[i]+",");
					}
					toWrite += (link.Element+"\n");
					try{		
						bw.write(toWrite);
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
