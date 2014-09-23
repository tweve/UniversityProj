import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Configuration {

	//threshold for similarity
		private double threshold = 0.06;
		//number of sections considered of a book
		private int n_sections = 30;
		private String confFile = "conf";
		private int MAX = 256;
		
		public Configuration(){
			readValues();
		}
		
		private void readValues(){
			char [] aux = new char[MAX];
			
			try {
				InputStreamReader input = new InputStreamReader(new FileInputStream(confFile));
				input.read(aux);
				String [] values = String.valueOf(aux).split("\n");
				threshold = Double.parseDouble(values[0]);
				n_sections = Integer.parseInt(values[1]);
				input.close();
			} catch (Exception e) {
				try {
					OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(confFile));
					output.write(threshold + "");
					output.write('\n');
					output.write(n_sections + "");
					output.write('\n');
					output.close();
				} catch (Exception e1) {
					//e1.printStackTrace();
				}
			}
		}
		
		public double getThreshold() {
			return threshold;
		}
		public void setThreshold(double threshold) {
			this.threshold = threshold;
		}
		public int getNSections() {
			return n_sections;
		}
		public void setNSections(int n_sections) {
			this.n_sections = n_sections;
		}


}
