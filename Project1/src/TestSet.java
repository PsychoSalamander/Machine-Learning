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

		// this is the position of the classes within the data set.
		// it is assumed that they will always be in the last column, after being
		// pre-processed.
		int classPosition = data[0].length - 1;

		// List of the amount of times that an example occurs given an attribute
		ArrayList<ArrayList<Integer>> attributeExamples = new ArrayList<ArrayList<Integer>>(data[0].length);

		// List of the probability that the example occurs given an attribute
		ArrayList<ArrayList<Double>> attributeExampleOccourances = new ArrayList<ArrayList<Double>>(data[0].length);

		// List of the probabilities of examples occurring given an attribute, given a class
		ArrayList<ArrayList<ArrayList<Integer>>> classExamplesOccurances = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		//allocate the size of classExamples
		for(int i = 0; i < classes.size(); i++) {
			classExamplesOccurances.add(null);
		}
		
		// for every attribute in the data set
		for (int col = 0; col < data[0].length - 1; col++) {

			// create a new list for expected examples
			ArrayList<Integer> examples = new ArrayList<Integer>();

			// for every example of the attribute
			for (int row = 0; row < data.length; row++) {

				// if the example is not already in the examples list, add it
				if (!examples.contains(data[row][col])) {
					examples.add(data[row][col]);
				} 
			}
			
			//predefine the size of the ArrayList so that the numbers go into the expected positions
			for(int i = 0; i < classExamplesOccurances.size(); i++) {
				for(int j = 0; j < examples.size(); j++) {
					ArrayList<ArrayList<Integer>> hold = new ArrayList<ArrayList<Integer>>(examples.size());
					classExamplesOccurances.set(i,hold);
				}
			}
			
			System.out.println("HELLO " + classExamplesOccurances);
			
			//for every example of the attribute
			for (int row = 0; row < data.length; row++) {
				
				//for the index of example within the examples list, increase the listed
				//occurrence of the example by one
				int theClass = data[row][classPosition];
				
				//indexes of each respective component in the 3D list
				int classIndex = classExamplesOccurances.indexOf(classes.indexOf(theClass));
				int attributeIndex = classes.indexOf(col);
				int exampleIndex = examples.indexOf(data[row][col]);
				
				int oldNum;
				
				//if there is no old number, assume 0
				//else grab the old number
				if(classExamplesOccurances.get(classIndex).get(attributeIndex).get(exampleIndex) == null) {
					oldNum = 0;
				} else {
					oldNum = classExamplesOccurances.get(classIndex).get(attributeIndex).get(exampleIndex);
				}
				
				//set new num to oldNum + 1
				int newNum = oldNum + 1;
				
				classExamplesOccurances.get(classIndex).get(attributeIndex).set(exampleIndex,newNum);
			}

			//attributeExamples.add(examples);
			//attributeExampleOccourances.add(examplesOccurances);

			System.out.println(classExamplesOccurances);
		}

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