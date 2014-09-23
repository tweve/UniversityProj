import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JFrame;

import org.apache.commons.math3.genetics.Population;
import org.math.plot.Plot2DPanel;


public class GeneticAlgorithm {

	public static void main(String[] args) {

		Plot2DPanel plot = new Plot2DPanel();
		JFrame frame = new JFrame("Evolutionary Computing");
		frame.setSize(1024, 768);

		frame.setContentPane(plot);

		Problem p = new Problem("N3C1W1_R.BPP");

		int Nind = 100;
		int Ngen = 3000;
		int nruns = 3;
		int SP = 2;
		double Pm = 0.27;
		double Px = 0.2;

		// array para guardas os resultados de cada geracao e no fim colocar no gráfico
		int[][] aux = new int[Ngen][nruns];

		for(int exp = 0;exp <3;exp++){
			
		
			// Initialize random population
			for (int r =0;r<nruns;r++){

				SolutionSample pop = p.randomSolution(Nind);

				for (int g = 0;g<Ngen;g++){

					ArrayList<Double> utility = pop.evaluate();
					Solution best = pop.getBest();

					System.out.printf("gen: %d\t\tutility: %.3f\t\t%d\tvalid:%b\n",g,best.getUtility(),best.getBins(),best.getValid() );

					aux[g][r]= (best.getBins());

					ArrayList<Double> fitness =  ranking(utility, SP, Nind,p);

					ArrayList<Integer> selection =  sus(fitness, Nind);

					
					SolutionSample sons =  p.randomSolution(Nind);
					
					for (int ind = 0;ind <Nind;ind++){
						Solution s = new Solution( pop.getInd(selection.get(ind)) );		    	
						sons.setInd(ind,s);
						//pop.setInd(ind, s);
					}
			
				
					
					if (exp == 0)
						mutate(sons, Pm, Nind);
						
					
					if (exp == 1){
						mutate(sons, Pm, Nind);
						recombine(sons, Px, Nind,0);
				
					}
					if (exp == 2){
						mutate(sons, Pm, Nind);
						recombine(sons, Px, Nind,1);
					}
				//	
					sons.evaluate();
					
					ArrayList<Solution> templist = new ArrayList<Solution>();
					
					for (Solution auxV:pop.data){
						templist.add(auxV);
					}
					for (Solution auxV:sons.data){
						templist.add(auxV);
					}
					
				
					for (int ind = 0;ind <Nind;ind++){
				    	pop.setInd(ind,getBest(templist,p));
					}

				}

			}
			double[] x = new double[Ngen];

			for (int i =0;i<Ngen;i++){
				x[i] = i;
			}

			double[][] y = new double[Ngen][3];		

			for (int i =0;i<Ngen;i++){
				Arrays.sort(aux[i]);
							//y[i][0] = aux[i][1];
				y[i][1] = aux[i][1];
				//			y[i][2] = aux[i][2];
				//			y[i][3] = aux[i][15];
				//		y[i][4] = aux[i][20];
			}

			double[] temp = new double[Ngen];


			//		for (int i =0;i<5;i++){
			double[] yf = new double[Ngen];
			for (int j =0;j<Ngen;j++){
				yf[j] = y[j][1];
			}
			// add a line plot to the PlotPanel
		
			plot.addLinePlot(" "+exp, x, yf);
			
			
			plot.addLegend("SOUTH");


			frame.setVisible(true);
		}


	}
	private static Solution getBest(ArrayList<Solution> templist, Problem p) {
		
		Solution sf = templist.get(0);
		double temp = templist.get(0).getUtility();
		for (Solution s: templist){
			if (s.getUtility() > temp){
				temp = s.getUtility();
				sf = s;
			}
		}
		sf.utility = -p.N;
		return sf;
		
	}
	public static void mutate(SolutionSample population, double pm, int nind){
		for (int i = 0;i<nind;i++){
			if (Math.random() < pm)
				population.randomMove(i);
		}
	}

	public static void recombine(SolutionSample population, double px, int nind,int hungarianAlgo){
		for (int i = 0;i<nind;i++){
			if (Math.random() < px){
				int parent_2 =(int)(Math.random()* (nind));
				population.randomBeetween(i,parent_2, hungarianAlgo);
			}
		}	
	}

	public static ArrayList<Double> ranking(ArrayList<Double> utility, int sp, int Nind, Problem p){
		ArrayList<Double> utility_aux = new ArrayList<Double>(utility);
		ArrayList<Double> utility_aux_2 = new ArrayList<Double>(utility);	    
		ArrayList<Double> fitness = new ArrayList<Double>(utility);


		for (int i = 0;i<utility_aux.size();i++){
			Double max = Collections.max(utility_aux_2);
			int index = utility_aux.indexOf(max);
			fitness.set(index, sp - (sp-1.) * 2. * i / (Nind-1.));
			utility_aux.set(index, (double) (-p.N-1));
			utility_aux_2.set(index, (double) (-p.N-1));
		}

		return fitness;
	}

	public static ArrayList<Integer> sus(ArrayList<Double> fitness, int Nind){

		ArrayList<Double> cumfitness = new ArrayList<Double>(fitness);
		ArrayList<Double> ptr = new ArrayList<Double>(fitness);
		ArrayList<Integer> selection = new ArrayList<Integer>();


		Double temp = 0.0;
		for (int i = 0;i<cumfitness.size();i++){
			temp+= cumfitness.get(i);
			cumfitness.set(i, temp);
		}

		temp = Math.random();
		for (int i = 0;i<Nind;i++){
			ptr.set(i, i + temp);
		}

		for (int i = 0;i<Nind;i++){
			int index_father = 0;

			for (int j = 0;j<Nind;j++){
				if(ptr.get(i)>= cumfitness.get(j)){
					index_father++;
				}
				else{
				}
			}

			selection.add(index_father);
		}

		Collections.shuffle(selection);

		return selection;

	}
}
