import java.util.ArrayList;

public class TrainedModel {
	
	// Class to hold all of the training data to be used by the test classed.
	
	double percentages[][][];
	double classPercentages[];
	ArrayList<Integer> classes = new ArrayList<Integer>();
	ArrayList<ArrayList<Integer>> attributes;
	
	TrainedModel(double[][][] perc, double[] classPerc, ArrayList<Integer> classesList, ArrayList<ArrayList<Integer>> attributesList) {
		percentages = perc.clone();
		classPercentages = classPerc.clone();
		classes = classesList;
		attributes = attributesList;
	}
}
