import java.util.ArrayList;


public class SolutionSample {


	private Problem of;
	ArrayList<Solution> data;

	public SolutionSample(Problem of, ArrayList<Solution> data) {
		this.of = of;
		this.data = data;
	}
	
	public ArrayList<Double> objValue(){
		return evaluate();
	}
	public ArrayList<Double> evaluate(){
		ArrayList<Double> temp = new ArrayList<Double>(data.size());
		for (int i = 0;i<data.size();i++){
			temp.add( data.get(i).evaluate());
		}
		return temp;
	}
	public void randomMove(int i){
		this.data.get(i).randomMove();
	}

	@Override
	public String toString() {
		String temp = "";
		for (Solution sol :data)
			temp += sol.toString();
		return temp ;
	}

	public Solution getBest() {
		Solution sf = data.get(0);
		double temp = data.get(0).getUtility();
		for (Solution s: data){
			if (s.getUtility() > temp){
				temp = s.getUtility();
				sf = s;
			}
		}
		return sf;
	}
	
	public void setInd( int pos , Solution s){
		this.data.set(pos, s);
	}
	public Solution getInd( int pos ){
		return this.data.get(pos);
	}

	public void randomBeetween(int parent_1, int parent_2, int hungarianAlgo) {

		this.data.get(parent_1).randomBeetween(this.data.get(parent_2), hungarianAlgo);
		
	}
	
	
}
