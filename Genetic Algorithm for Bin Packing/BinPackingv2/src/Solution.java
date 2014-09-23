import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.util.MathUtils;


public class Solution {



	private Problem of;
	private HashMap<Integer,ArrayList<Integer>> data;
	double utility;
	private boolean valid;

	private double averageWeightsPerBin;
	private int parent2_nbins;


	public int getBins() {
		return data.size();
	}
	public double getUtility() {
		return utility;
	}
	public double getAVG() {
		return averageWeightsPerBin;
	}
	public HashMap<Integer, ArrayList<Integer>> getData(){
		return this.data;
	}
	public boolean getValid() {
		return this.valid;
	}




	public Solution(Problem of, ArrayList<Integer> data) {
		this.of = of;
		this.data = new HashMap<Integer,ArrayList<Integer>>();
		int bin = 0;
		int nbins = 0;
		boolean insert_at_last = false;

		ArrayList<Integer> bin_objs = new ArrayList<>();

		for (Integer x : data){

			bin = bin + of.weights.get(x);
			bin_objs.add(x);
			if (bin> of.capacity){
				nbins += 1;
				int temp = bin_objs.get(bin_objs.size()-1);
				bin_objs.remove(bin_objs.size()-1);

				this.data.put(nbins,bin_objs);
				bin = of.weights.get(x);
				bin_objs = new ArrayList<>();
				bin_objs.add(temp);

			}

		}
		nbins += 1;
		this.data.put(nbins,bin_objs);
	}

	public Solution(Solution best) {
		this.of = best.of;
		this.data = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 1;i<best.data.size()+1;i++){
			ArrayList<Integer> temp = best.data.get(i);
			ArrayList<Integer> aux = new ArrayList<Integer>(temp);
			this.data.put(i, aux);

		}
		this.setUtility(best.getUtility());
		this.averageWeightsPerBin = best.averageWeightsPerBin;
	}

	public double objValue(){
		return evaluate();
	}

	public double evaluate(){


		//Fitness Fuction FF by Falkenauer(1994)
		double total_sum = 0;
		int invalid = 0;

		for (int i: this.data.keySet()){
			int sum = 0;
			ArrayList<Integer> objx = this.data.get(i);
			for (Integer o: objx)
				sum+= of.weights.get(o);
			// experimentar várias potencias para o relatório
			//System.out.println("soma :" +sum);
			//System.out.println("capacity:"+ of.capacity);
			if (sum>this.of.capacity){
				invalid++;
			}
			total_sum+= Math.pow((sum/(double)of.capacity),2);
		}

		if (invalid>0){
			this.valid = false;

			this.setUtility((double) -invalid);
			return (double) -invalid;

		}

		this.valid = true;
		this.setUtility(total_sum);
		return total_sum;

	}


	public int calcSwap(){

		int aux = 0;
		int totalObj = 0;

		for (int i:this.data.keySet() ){
			int numObj = this.data.get(i).size();
			totalObj += numObj;
			try {
				aux += ArithmeticUtils.binomialCoefficient( numObj, 2 );

			} catch (Exception e) {
				aux += 0;
			}

		}

		return (int) ArithmeticUtils.binomialCoefficient( totalObj, 2 ) - aux;
	}

	public void randomMove() {


		double swap = calcSwap();

		double insert = this.of.N * this.data.size()-1 ;

		double total = swap +insert;

		double ps = swap/total;


		//System.out.println("s-"+swap);
		//System.out.println("t-"+total);


		if (Math.random()< ps){
			// do swap

			//System.out.println("swap");
			boolean try_again = true;
			int tries = 0;

			//while (try_again && tries < this.of.N){
			tries++;

			int bin_1 = 1 + (int)(Math.random()* ((this.data.size())));
			int item_1 =(int)(Math.random()* ((this.data.get(bin_1).size()-1)));

			int bin_2 = 1 + (int)(Math.random()* ((this.data.size())));
			while(bin_1 == bin_2){
				bin_2 = 1 + (int)(Math.random()* ((this.data.size())));
			}
			int item_2 =(int)(Math.random()* ((this.data.get(bin_2).size()-1)));

			//System.out.println(bin_1);
			//	System.out.println(bin_2);
			//
			//	System.out.println(this.data.get(bin_1));
			//System.out.println(this.data.get(bin_2));

			int soma_bin1 = 0;
			for(int i:this.data.get(bin_1))
				soma_bin1 += i;

			int soma_bin2 = 0;
			for(int i:this.data.get(bin_2))
				soma_bin2 += i;



			//if ( (soma_bin1+this.data.get(bin_2).get(item_2) <= of.capacity )&& (soma_bin2+this.data.get(bin_1).get(item_1) <= of.capacity )){


			//System.out.println("swap");
			//System.out.println(this.data.get(bin_1));
			//System.out.println(this.data.get(bin_2));

			this.data.get(bin_1).add( this.data.get(bin_2).get(item_2));
			this.data.get(bin_2).add( this.data.get(bin_1).get(item_1));

			this.data.get(bin_1).remove(item_1);
			this.data.get(bin_2).remove(item_2);

			//	System.out.println(this.data.get(bin_1));
			//System.out.println(this.data.get(bin_2));


			//	try_again = false;
			//}
			//else{
			//	try_again = true;
			//}
			//}

		}
		else{
			//System.out.println("insert");
			// do insert (get from bin1 put into bin2)
			boolean try_again = true;
			int tries = 0;

			//while (try_again && tries < this.of.N){

			//tries++;

			int bin_1 = 1 + (int)(Math.random()* ((this.data.size())));
			int item_1 =(int)(Math.random()* ((this.data.get(bin_1).size()-1)));

			int bin_2 = 1 + (int)(Math.random()* ((this.data.size())));
			while(bin_1 == bin_2){
				bin_2 = 1 + (int)(Math.random()* ((this.data.size())));
			}

			int soma_bin2 = 0;
			for(int i:this.data.get(bin_2))
				soma_bin2 += i;


			//if ( soma_bin2+this.data.get(bin_1).get(item_1) <= of.capacity ){

			this.data.get(bin_2).add(this.data.get(bin_1).get(item_1));

			this.data.get(bin_1).remove(item_1);

			if (this.data.get(bin_1).size() ==0){

				this.data.remove(bin_1);

				HashMap<Integer, ArrayList<Integer>> aux = new HashMap<>();

				for (int i:this.data.keySet() ){
					if (i>bin_1)
						aux.put(i-1,this.data.get(i));
					else
						aux.put(i,this.data.get(i));

				}
				this.data.clear();
				this.data = aux;
				//System.out.println(this.data.get(bin_1));
				//System.out.println(this.data.get(bin_2));
			}
			//try_again = false;
			//}
			//else{
			//try_again = true;
			//}
			//}

		}



	}

	@Override
	public String toString() {
		String temp = data.toString() + " nbins: " +this.data.size() +" cost: "+this.getUtility()+"\n";
		return temp;
	}

	public int[] convertRepresentation(Solution s) {
		/*ArrayList<Integer> weights = s.of.weights;
		ArrayList<Integer> aux_weights = new ArrayList<>(s.of.weights);		

		

		for (int key: s.data.keySet()){
			ArrayList<Integer> objx = s.data.get(key);
			for (int obj :objx){
				int index = aux_weights.indexOf(obj);
				rep[index] = key;
				aux_weights.set(index, -1);
			}
		}
		this.parent2_nbins = s.data.size();
		return rep;*/
		
		
		int[] rep = new int[of.weights.size()];
		
		for (int key: s.data.keySet()){
			ArrayList<Integer> objx = s.data.get(key);
			for (int obj :objx){
				rep[obj] = key;
			}
		}
		
		return rep;

	}

	public void randomBeetween(Solution parent_2, int hungarianAlgo) {

		int[] rep1 = convertRepresentation(this);
		int[] rep2 = convertRepresentation(parent_2);
		this.parent2_nbins = parent_2.data.size();

		int[] repf;

		if(hungarianAlgo == 1){
			double[][] assignMatrix = matrix(rep1,rep2);

			HungarianAlgorithm algo = new HungarianAlgorithm(assignMatrix);

			int[] values = algo.execute();

			for (int pos = 0; pos < rep1.length; pos++) {

				try {
					rep1[pos] = values[rep1[pos]];
				} catch (Exception e) {
					// acontece quando a repreesntacao 1 tem mais bins do que a representacao 2
					// e necessário manter os bins k sao diferentes
					rep1[pos] = rep1[pos];
				}

			}
		}


		// RECOMBINACAO --------------


		boolean rep1_bigger = false;

		if (rep1.length>= rep2.length){
			repf = new int[rep1.length];
			rep1_bigger = true;
		}
		else{
			repf = new int[rep2.length];		
			rep1_bigger = false;
		}

		if (rep1_bigger){
			for (int i = 0;i<rep1.length;i++){
				try{
					if (Math.random()<0.5)
						repf[i] = rep1[i];
					else
						repf[i] = rep2[i];
				}catch (Exception e) {
					repf[i] = rep1[i];
				}
			}
		}
		else{
			for (int i = 0;i<rep2.length;i++){
				try{
					if (Math.random()<0.5)
						repf[i] = rep1[i];
					else
						repf[i] = rep2[i];
				}catch (Exception e) {
					repf[i] = rep2[i];
				}
			}
		}
		

		this.data = new HashMap<Integer, ArrayList<Integer>>();

		for (int i = 0;i<repf.length;i++){


			if (this.data.containsKey(repf[i])){
				this.data.get(repf[i]).add(i);
			}
			else{
				ArrayList<Integer> a = new ArrayList<Integer>();
				a.add(i);
				this.data.put(repf[i], a);

			}

		}
		



		HashMap<Integer,ArrayList<Integer>> aux = new HashMap<Integer, ArrayList<Integer>>();
		
		int it = 1;
		for (int i : this.data.keySet()){
			aux.put(it, this.data.get(i));
			it++;
		}
		
		this.data = aux;
		
	
		// --------------------------


		/*for (int d :rep1)
			System.out.print(d+" ");
		System.out.println();
		for (int d :rep2)
			System.out.print(d+" ");
		System.out.println();


		for (int d :repf)
			System.out.print(d+" ");
		System.out.println();
		for (int d :repf)
			System.out.print(d+" ");
		System.out.println();
		 */
	}


	double[][] matrix(int vec1[], int vec2[]){

		double[][] matrix = new double[this.data.size()][this.parent2_nbins];

		for (int i = 0;i<this.data.size();i++){
			for(int j = 0;j<this.parent2_nbins;j++){
				for(int k = 0;k<Math.min(vec1.length,vec2.length);k++){
					if (i == vec1[k] && j == vec2[k] ){
						matrix[i][j]++;
					}	
				}
			}
		}

		for (int i = 0;i<this.data.size();i++){
			for(int j = 0;j<this.parent2_nbins;j++){
				if (matrix[i][j] == 0.0)
					matrix[i][j] = this.of.N+ 1;

				else
					matrix[i][j] = 1/(double)matrix[i][j];
			}
		}

		return matrix;

	}
	public void setUtility(double utility) {
		this.utility = utility;
	}

}
