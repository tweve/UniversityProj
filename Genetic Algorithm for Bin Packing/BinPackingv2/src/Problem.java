import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Problem {

	int N;
	int capacity;
	ArrayList<Integer> weights;

	public Problem(String file) {
		File f = new File(file);
		Scanner sc;
		try {
			sc = new Scanner(f);

			this.N = sc.nextInt();
			this.capacity = sc.nextInt();
			this.weights = new ArrayList<Integer>();

			for (int i = 0;i<this.N;i++){
				this.weights.add(sc.nextInt()); 	
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}

	public SolutionSample randomSolution(int nind) {
		ArrayList<Solution> aux  = new ArrayList<Solution>(nind);
		for (int i = 0;i<nind;i++){
			Solution sol = this.randomSolution();
			aux.add(sol);
		}		
		return (new SolutionSample(this,aux));
	}
	
	public Solution randomSolution() {
		ArrayList<Integer> data = new ArrayList<Integer>();
		for (int i = 0;i<this.weights.size();i++){
			data.add(i);
		}
		Collections.shuffle(data);
		
		return (new Solution(this,data));
	}
	
}
