import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import cc.mallet.classify.Classification;

public class PlotData {

	public void plot(	ArrayList<Classification> emotions) {
		
		XYSeries neutral = new XYSeries("Neutral");
		XYSeries sadness = new XYSeries("Sadness");
		XYSeries disgust = new XYSeries("Disgust");
		XYSeries anger = new XYSeries("Anger");
		XYSeries surprise = new XYSeries("Surprise");
		XYSeries hapiness = new XYSeries("Hapiness");
		
		for(int i = 0;i< emotions.size();i++){
			neutral.add(i, emotions.get(i).getLabeling().value(0));
			sadness.add(i, emotions.get(i).getLabeling().value(1));
			disgust.add(i, emotions.get(i).getLabeling().value(2));
			anger.add(i, emotions.get(i).getLabeling().value(3));
			surprise.add(i, emotions.get(i).getLabeling().value(4));
			hapiness.add(i, emotions.get(i).getLabeling().value(5));
		}
		
		// Add the series to your data set
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(neutral);
		dataset.addSeries(sadness);
		dataset.addSeries(disgust);
		dataset.addSeries(anger);
		dataset.addSeries(surprise);
		dataset.addSeries(hapiness);
		
		
		
		// Generate the graph
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Emotions Felt Per Strophe", // Title
				"Lyrics", // x-axis Label
				"Emotional Intensity", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		/*try {
			ChartUtilities.saveChartAsJPEG(new File("C:\\chart.jpg"), chart, 500, 300);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}*/
		
		
		ChartFrame frame = new ChartFrame("Results", chart);
		frame.pack();
		frame.setVisible(true);
	}
}