
/* Copyright (C) 2011 Universidade de Coimbra - Departamento de Informática
 * This file implements a classifier that reads a file and classifies all
 * its sentences in emotions*/

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.jfree.io.IOUtils;


import cc.mallet.classify.*;
import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.types.*;
import cc.mallet.util.Maths;

public class EmotionalReader {

	public static int E_NUM = 7;
	private String[] labels;

	public EmotionalReader() {
	}

	//Train reader using Naive Bayes Algorithm
	//receives the file name
	public void trainEmotionalReader(String fileName) {
		trainEmotionalReader(fileName, 0);
	}

	//Train reader using Naive Bayes Algorithm
	//receives the file name and the percentage of validation
	public void trainEmotionalReader(String fileName, int perc_validation) {

		InstanceList[] ilists = readTrainFile(fileName, perc_validation);
		ClassifierTrainer<?> trainer = new cc.mallet.classify.NaiveBayesTrainer();
		Classifier classifier = trainer.train(ilists[0]);


		System.out.println("The training accuracy is " + classifier.getAccuracy(ilists[0]));
		if (perc_validation != 0) {
			System.out.println("The testing accuracy is " + classifier.getAccuracy(ilists[1]));
		}

		File file = new File("my.classifier");
		try {
			saveClassifier(classifier, file);
		} catch (IOException e) {
			System.out.println("Erro ao gravar classificador!");
		}
	}

	private InstanceList[] readTrainFile(String fileName, int perc) {
		InstanceList[] ilists = null;
		InstanceList ilist;

		try {

			Pipe instancePipe = new SerialPipes(new Pipe[] { 
					new Target2Label(), // Target String -> class label 
					new CharSequence2TokenSequence(), // Data String -> TokenSequence 
					new TokenSequenceLowercase(), // TokenSequence words lowercased 
					new TokenSequenceRemoveNonAlpha(), // Remove non-word tokens, but record 
					new TokenSequenceRemoveStopwords(), // Remove stopwords from sequence 
					new TokenSequence2FeatureSequence(), // Replace each Token with a feature index 
					new FeatureSequence2AugmentableFeatureVector(), // Collapse word order into a "feature vector"
			});
			ilist = new InstanceList(instancePipe);
			ilist.addThruPipe(new CsvIterator(fileName + ".txt", "^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$", 3, 2, 1));
			ilists = ilist.split(new double[] { (100 - perc) / (double) 100, perc / (double) 100 });

		} catch (FileNotFoundException e) {
			System.out.println("Erro ao ler ficheiro!");
			e.printStackTrace();
		}

		return ilists;
	}

	//classify book bookName
	public void classifyLyrics(String lyrics){
		int i = 0;

		ArrayList<Classification> classification = new ArrayList<Classification>();
		try {
			ArrayList<Classification> emotions = new ArrayList<Classification>();

			String charset = lyrics.toLowerCase(); // your charset
			byte[] bytes = charset.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			InputStreamReader fileReader = new InputStreamReader(bais);

			Iterator<Instance> csvIterator = new CsvIterator(fileReader, "(.*)$", 1, 0, 0);
			Classifier classifier = loadClassifier("my.classifier");
			labels = classifier.getLabelAlphabet().toString().split("\n");
			E_NUM = labels.length;

			Iterator<Instance> iterator = classifier.getInstancePipe().newIteratorFrom(csvIterator);
			while (iterator.hasNext()) {
				Instance instance = iterator.next();
				Classification c =classifier.classify(instance);

				classification.add(c);

				//System.out.println("Instance: " + c.getInstance().getName());
				System.out.print(c.getLabeling());
				System.out.println(i+" Value: " + c.getLabeling().getBestLabel());
				i++;
				emotions.add(c);
			}

			Song aux = new Song();
			aux.setC(emotions);
			String name = JOptionPane.showInputDialog("Insert Song Name");
			aux.setName(name);

			double proximity = 0;
			double best_proximity = 999999;
			Song most_similar = null;

			for (Song s:LyricsAnalyzer.songs){

				proximity = compareEmotions(aux,s);
				
				System.out.println(proximity);

				if (proximity < best_proximity){
					best_proximity = proximity;
					most_similar = s;
				}
			}

			if (proximity == -1)
				System.out.println("There are no songs to compare");
			else if(best_proximity > 100){
				System.out.println("There are no similar songs");
			}
			else {
				System.out.println("Most Similar song is "+ most_similar.getName());
			}
			try{
				Window.RecommendedLyrics.setText("Most Similar lyrics from "+ most_similar.getName());
				
			}
			catch(Exception e)
			{
				Window.RecommendedLyrics.setText("No songs in DB.");
			}
			LyricsAnalyzer.songs.add(aux);
			PlotData plotWindow = new PlotData();
			plotWindow.plot(emotions);

			//sendClassification(lyricsName, classification);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while reading lyrics!");
		}
	}

	public double compareEmotions(Song a, Song b){

		int sizeA = a.getC().size();
		int sizeB = b.getC().size();

		double distance = 0;

		if (sizeA > sizeB){
			// A é maior que B
			
			for (int i = 0;i<sizeA;i++){
				
				double[] distroSA ;
				double[] distroSB ;

				
				if (i>= sizeB){
					distroSB = createNeutralDistribution();
				}
				else{

					Classification cb = b.getC().get(i);
					distroSB = getProbDistribution(cb);
				}
				
				Classification ca = a.getC().get(i);
				distroSA= getProbDistribution(ca);
				distance += Maths.jensenShannonDivergence(distroSA,distroSB);

			}
		}
		else if (sizeB > sizeA){
			for (int i = 0;i<sizeB;i++){
				
				double[] distroSA ;
				double[] distroSB ;

				
				if (i>= sizeA){
					distroSA = createNeutralDistribution();
				}
				else{

					Classification ca = a.getC().get(i);
					distroSA = getProbDistribution(ca);
				}
				
				Classification cb = b.getC().get(i);
				distroSB= getProbDistribution(cb);
				distance += Maths.jensenShannonDivergence(distroSA,distroSB);

			}
		}
		else{
			// tmnh A == tmnh B
			for (int i = 0;i<sizeA;i++){

				Classification ca = a.getC().get(i);
				Classification cb = b.getC().get(i);

				double[] distroSA= getProbDistribution(ca);
				double[] distroSB = getProbDistribution(cb);

				distance += Maths.jensenShannonDivergence(distroSA,distroSB);
			}
		}
		System.out.println("parecença entre "+ a.getName() +" e "+ b.getName() +" = "+ distance);
		return  distance;
	}

	public double[] getProbDistribution(Classification c){

		double[] distro = new double[7];

		distro[0] = c.getLabeling().value(0);
		distro[1] = c.getLabeling().value(1);
		distro[2] = c.getLabeling().value(2);
		distro[3] = c.getLabeling().value(3);
		distro[4] = c.getLabeling().value(4);
		distro[5] = c.getLabeling().value(5);
		distro[6] = c.getLabeling().value(6);

		return distro;

	}
	
	public double[] createNeutralDistribution(){
		double[] distro = new double[7];

		distro[0] = 1.0;
		distro[1] = 0;
		distro[2] = 0;
		distro[3] = 0;
		distro[4] = 0;
		distro[5] = 0;
		distro[6] = 0;

		return distro;

	}

	public void saveClassifier(Classifier classifier, File serializedFile) throws IOException {

		// The standard method for saving classifiers in
		// Mallet is through Java serialization. Here we
		// write the classifier object to the specified file.

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serializedFile));
		oos.writeObject(classifier);
		oos.close();
	}

	public Classifier loadClassifier(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {

		// The standard way to save classifiers and Mallet data
		// for repeated use is through Java serialization.
		// Here we load a serialized classifier from a file.

		Classifier classifier;

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fileName)));
		classifier = (Classifier) ois.readObject();
		ois.close();

		return classifier;
	}
}
