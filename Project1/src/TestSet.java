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

	public TrainedModel test() {

		ArrayList<Integer> classes = new ArrayList<Integer>();
		
		classes = getClasses(DataSet);

		double probOfClass[] = calcClassProbability(DataSet, classes);

		TrainedModel trainedMod = calcAttribGivenClassProbability(DataSet, classes, probOfClass);
		
		return trainedMod;

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

	public TrainedModel calcAttribGivenClassProbability(int data[][], ArrayList<Integer> classes, double[] classPercentage) {

		int numOfAttributes = data[0].length - 1; // Number of attributes
		int classPosition = data[0].length - 1; // Index location of the class
		int[] numOfExamples = new int[numOfAttributes]; // Number of examples per each attribute

		// define list of attributes
		ArrayList<ArrayList<Integer>> attributes = new ArrayList<ArrayList<Integer>>();

		// for every attribute
		for (int col = 0; col < numOfAttributes; col++) {

			// create an empty list of unique examples
			ArrayList<Integer> examples = new ArrayList<Integer>();

			// for every example in the attribute column
			for (int row = 0; row < data.length; row++) {

				// if the example is not added to the unique list of examples,
				if (!examples.contains(data[row][col])) {

					// add the example to the unique list
					examples.add(data[row][col]);

					// add to the total number of unique examples seen
					numOfExamples[col] += 1;
				}
			}

			// add the examples to the defined list of attributes
			attributes.add(examples);
		}

		if (debug) {
			for (int i = 0; i < numOfExamples.length; i++) {
				System.out.print(numOfExamples[i] + " ");
			}

			System.out.println("\n" + attributes);
		}

		// define low maximum
		int max = -9999;

		// find the maximum number of examples within a row, so that we know the
		// dimension of the last
		// part of the 3D array.
		for (int i = 0; i < numOfExamples.length; i++) {
			if (numOfExamples[i] > max) {
				max = numOfExamples[i];
			}
		}

		// amount of times an example was seen within an attribute
		int[][][] examplesInClassPerAttribute = new int[classes.size()][numOfAttributes][max];

		// for every class
		for (int i = 0; i < examplesInClassPerAttribute.length; i++) {

			// for every attribute in that class
			for (int col = 0; col < data[0].length - 1; col++) {

				// for every example in that attribute
				for (int row = 0; row < data.length; row++) {

					// if the row is part of the class
					if (data[row][classPosition] == classes.get(i)) {

						// add one to the seen amount for that example, in that attribute, for that
						// class
						examplesInClassPerAttribute[i][col][attributes.get(col).indexOf(data[row][col])]++;
					}
				}
			}
		}

		// probability that an example belongs within an attribute of a class
		double[][][] percentages = new double[classes.size()][numOfAttributes][max];

		// for every class
		for (int classIndex = 0; classIndex < examplesInClassPerAttribute.length; classIndex++) {
			
			// for every attribute in that class
			for (int attribIndex = 0; attribIndex < examplesInClassPerAttribute[0].length; attribIndex++) {
				
				// instantiate a counter for the amount of non-zero numbers within the example list
				int nonZeroCount = 0;

				//for every example
				for (int exIndex = 0; exIndex < examplesInClassPerAttribute[0][0].length; exIndex++) {
					
					//add the number of times something was seen for that example to the non-zero counter
					nonZeroCount += examplesInClassPerAttribute[classIndex][attribIndex][exIndex];
				}

				// for every example
				for (int exIndex = 0; exIndex < examplesInClassPerAttribute[0][0].length; exIndex++) {
					
					//calculate the percentage of the chance that a given example fits the criteria
					percentages[classIndex][attribIndex][exIndex] = (double) examplesInClassPerAttribute[classIndex][attribIndex][exIndex]
							/ (double) nonZeroCount;
				}
			}
		}
		

		
		for (int classIndex = 0; classIndex < examplesInClassPerAttribute.length; classIndex++) {
			System.out.println(classes.get(classIndex));
			for (int attribIndex = 0; attribIndex < examplesInClassPerAttribute[0].length; attribIndex++) {
				for (int exIndex = 0; exIndex < examplesInClassPerAttribute[0][0].length; exIndex++) {
					System.out.print("[" + examplesInClassPerAttribute[classIndex][attribIndex][exIndex] + "] ");
				}
				System.out.println();
			}
		}

		for (int classIndex = 0; classIndex < examplesInClassPerAttribute.length; classIndex++) {
			System.out.println(classes.get(classIndex));
			for (int attribIndex = 0; attribIndex < examplesInClassPerAttribute[0].length; attribIndex++) {
				for (int exIndex = 0; exIndex < examplesInClassPerAttribute[0][0].length; exIndex++) {
					System.out.print("[" + percentages[classIndex][attribIndex][exIndex] + "] ");
				}
				System.out.println();
			}
		}

		TrainedModel trainedModel = new TrainedModel(percentages, classPercentage, classes, attributes);
		
		return trainedModel;
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