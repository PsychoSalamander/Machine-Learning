
//
//	Responsible team member: Blake Mitchell
//

import java.util.ArrayList;

public class TestSet {

	int DataSet[][];
	boolean debug = true;

	TestSet(int data[][]) {
		DataSet = data.clone();
	}

	public void train() {

		ArrayList<Integer> classes = new ArrayList<Integer>();

		classes = getClasses(DataSet);
		int numberOfUniqueClasses = classes.size();

		double probOfClass[] = calcClassProbability(DataSet, classes);

		ArrayList<Double> probAttribGivenClass = new ArrayList<Double>();

		calcAttribGivenClassProbability(DataSet, classes);

	}

	// This method returns the list of unique class names given in the data set.
	public ArrayList<Integer> getClasses(int data[][]) {

		// this is the position of the classes within the data set.
		// it is assumed that they will always be in the last column, after being
		// pre-processed.
		int classPosition = data[0].length - 1;

		// list of classes that will be contained in the data
		ArrayList<Integer> classes = new ArrayList<Integer>();

		// for every row contained in the data
		for (int i = 0; i < data.length; i++) {

			// if the class is not already in the list, add it to the list
			if (!classes.contains(data[i][classPosition])) {
				classes.add(data[i][classPosition]);
			}
		}

		if (debug) {
			System.out.println(classes);
		}

		return classes;
	}

	// This returns the probability that a unique class will appear
	public double[] calcClassProbability(int data[][], ArrayList<Integer> classes) {

		// the number of rows contained within the data set
		int numOfRows = data.length;

		// the number of times a class occurs within a data set
		int classOccurances[] = calcClassOccurances(data, classes);

		// probability of a class occurring
		double probability[] = new double[classes.size()];

		// calculate the probabilities of each class
		for (int i = 0; i < probability.length; i++) {
			probability[i] = (double) classOccurances[i] / (double) numOfRows;
		}

		return probability;
	}

	public double[] calcAttribGivenClassProbability(int data[][], ArrayList<Integer> classes) {

		
		int numOfClasses = classes.size();					// Number of classes
		int numOfAttributes = data[0].length - 1;			// Number of attributes
		int[] numOfExamples = new int[numOfAttributes];		// Number of examples per each attribute

		ArrayList<ArrayList<Integer>> attributes = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> emptyAttributes = new ArrayList<ArrayList<Integer>>();
		
		for (int col = 0; col < numOfAttributes; col++) {

			ArrayList<Integer> examples = new ArrayList<Integer>();
			ArrayList<Integer> emptySlots = new ArrayList<Integer>();

			for (int row = 0; row < data.length; row++) {
				if (!examples.contains(data[row][col])) {
					examples.add(data[row][col]);
					numOfExamples[col] += 1;
					
					emptySlots.add(0);
				}
			}
			
			attributes.add(examples);
			emptyAttributes.add(emptySlots);
		}

		if(debug) {
			for (int i = 0; i < numOfExamples.length; i++) {
				System.out.print(numOfExamples[i] + " ");
			}

			System.out.println("\n" + attributes);
			System.out.println(emptyAttributes);
			
		}
		
		ArrayList<ArrayList<ArrayList<Integer>>> examplesInClassPerAttribute = new ArrayList<ArrayList<ArrayList<Integer>>>(classes.size());
		
		System.out.println(examplesInClassPerAttribute.size());
		
		for(int i = 0; i < examplesInClassPerAttribute.size(); i++) {
			examplesInClassPerAttribute.add(emptyAttributes);
		}

		System.out.println(examplesInClassPerAttribute);
		
		
		
		
		

		double something[] = new double[1];
		return something;
	}

	public int[] calcClassOccurances(int data[][], ArrayList<Integer> classes) {
		// this is the position of the classes within the data set.
		// it is assumed that they will always be in the last column, after being
		// pre-processed.
		int classPosition = data[0].length - 1;

		// The amount of times that a given class occurs within the data set
		int classOccurances[] = new int[classes.size()];

		// for every row contained in the data
		for (int i = 0; i < data.length; i++) {

			// grab the class from the row
			int theClass = data[i][classPosition];

			// position of class within the classes list
			int classIndex = classes.indexOf(theClass);

			classOccurances[classIndex] += 1;
		}

		return classOccurances;
	}
}