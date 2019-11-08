import java.util.Arrays;

import datapkg.*;

public class RadialBasis {

    int NumberOfClasses;
    int ClassColumnPosition;

    ArraySet DataArraySet;

    RadialBasis(ProcessedData ImportedData) {
	DataArraySet = splitArray(ImportedData.getDataArrayShuffled());
	NumberOfClasses = ImportedData.getNumberOfClasses();
	ClassColumnPosition = ImportedData.getClassColumnPosition();

	runAll();
    }

    public void runAll() {

	float[][] datapointsKMeans = getKmeans();
	float[][] datapointsMedoids = getMedoids();

	System.out.print("KMeans: ");
	runNet(datapointsKMeans);

	System.out.print("Medoids: ");
	runNet(datapointsMedoids);

	System.out.println("Data set training completed.");
    }

    private void runNet(float[][] calculatedClusterPoints) {

	float[][] trainingInputData = decoupledInputs(DataArraySet.trainingData.clone());
	float[] trainingOutputData = decoupledOutputs(DataArraySet.trainingData.clone());

	float[][] givenClustersInput = decoupledInputs(calculatedClusterPoints.clone());
	float[] givenClustersOutput = calculateClustersOutput(calculatedClusterPoints.clone());

	float[][] testingInputData = decoupledInputs(DataArraySet.testingData.clone());
	float[] testingOutputData = decoupledOutputs(DataArraySet.testingData.clone());

	Network network = new Network(trainingInputData, trainingOutputData, givenClustersInput, givenClustersOutput);

	boolean betterThanLast = true;
	float bestPercentage = 0.0f;
	int numberOfTrainings = 0;

	while (betterThanLast) {
	    numberOfTrainings++;
	    network.trainNetwork();

	    float testPercentage = network.testNetwork(testingInputData, testingOutputData);

	    if (testPercentage > bestPercentage) {
		bestPercentage = testPercentage;
		//System.out.printf("\tNew best percentage, %f\n", bestPercentage);
	    } else {
		betterThanLast = false;
		System.out.printf("\tWas %.2f percent correct after training %d times\n", bestPercentage * 100,
			numberOfTrainings++);
	    }

	}
    }

    // splits the shuffled data set into a set to be clustered, a set to train the data, and a set to test the data
    private ArraySet splitArray(float[][] array) {

	int length = array.length;

	int clusterDataSplitPos = (int) (length * 0.6);
	int trainingDataSplitPos = (int) (length * 0.8);

	float[][] clusterData = Arrays.copyOfRange(array, 0, clusterDataSplitPos);
	float[][] trainingData = Arrays.copyOfRange(array, clusterDataSplitPos, trainingDataSplitPos);
	float[][] testingData = Arrays.copyOfRange(array, trainingDataSplitPos, length - 1);

	ArraySet arraySet = new ArraySet(clusterData, trainingData, testingData);

	return arraySet;
    }

    // returns an array of the input points, decoupled from the classes
    private float[][] decoupledInputs(float[][] data) {

	// get the expected size of the new array
	int dcRowLength = data.length;
	int dcColLength = data[0].length - 1;

	// instantiate the new array with the expected size
	float[][] decoupledArray = new float[dcRowLength][dcColLength];

	// for every row in the data set
	for (int row = 0; row < data.length; row++) {

	    //for every column in the data set
	    for (int col = 0; col < data[0].length; col++) {

		// initialize the correct column position for the decoupled array
		int offsetColumnPosition;

		// if the column within the loop is greater than the position of the column, the actual column should be one less in order to not run out of bounds.
		if (col > ClassColumnPosition) {
		    offsetColumnPosition = col - 1;
		} else {
		    offsetColumnPosition = col;
		}

		// if the column is not the ClassColumnPosition, add the point to the decoupledArray
		if (col != ClassColumnPosition) {
		    decoupledArray[row][offsetColumnPosition] = data[row][col];
		}
	    }
	}

	// return the array without the class column
	return decoupledArray;
    }

    // returns an array of the class outputs, decoupled from the attributes
    private float[] decoupledOutputs(float[][] data) {

	float[] decoupledArray = new float[data.length];

	for (int row = 0; row < data.length; row++) {
	    decoupledArray[row] = data[row][ClassColumnPosition];
	}

	return decoupledArray;
    }

    // calculates the class given the data point
    private float[] calculateClustersOutput(float[][] meanData) {

	// get the expected size of the new array
	int dcRowLength = meanData.length;

	// instantiate the new array with the expected size
	float[] decoupledArray = new float[dcRowLength];

	for (int classIndex = 0; classIndex < NumberOfClasses; classIndex++) {
	    float minDist = 1000000;
	    int minIndex = 0;

	    // Check each of the data points in the practice set
	    for (int j = 0; j < DataArraySet.clusterData.length; j++) {

		// Check to find the closest point that has not already been used
		if ((getDistance(meanData[classIndex], DataArraySet.clusterData[j]) < minDist)) {
		    minDist = getDistance(meanData[classIndex], DataArraySet.clusterData[j]);
		    minIndex = j;
		}
	    }

	    // Set class for mean equal to the closest practice point
	    decoupledArray[classIndex] = DataArraySet.clusterData[minIndex][ClassColumnPosition];
	}

	return decoupledArray;
    }

    // prints the 2D array of floats
    public void print2DArray(float[][] array) {

	System.out.println("2D Array Visualized:");

	for (int row = 0; row < array.length; row++) {
	    System.out.println(Arrays.toString(array[row]));
	}
    }

    // prints the 1D array of floats
    public void print1DArray(float[] array) {

	System.out.println("1D Array Visualized:");

	for (int row = 0; row < array.length; row++) {
	    System.out.println("[" + array[row] + "]");
	}
    }

    // Calculates the Euclidian distance between two points in N-Space
    float getDistance(float[] p1, float[] p2) {
	if (p1.length == p2.length) {
	    float d = 0;

	    // Get the total distance
	    for (int i = 0; i < p1.length; i++) {
		if (i != ClassColumnPosition) {
		    float t = p2[i] - p1[i];
		    t = t * t;
		    d = d + t;
		}
	    }

	    // Take Square root of that distance
	    d = (float) java.lang.Math.sqrt(d);

	    return d;
	} else {
	    System.out.println("TRIED TO FIND THE DISTANCE BETWEEN POINTS IN DIFFERENT" + "DIMENSION SPACES!");
	    return 0;
	}
    }

    private float[][] getKmeans() {
	KMeansNearestNeighbor KMeans = new KMeansNearestNeighbor();
	KMeans.setClassLocation(ClassColumnPosition);
	return KMeans.getMeans(DataArraySet.clusterData, NumberOfClasses).clone();
    }

    private float[][] getMedoids() {
	PAMNearestNeighbor Medoids = new PAMNearestNeighbor();
	Medoids.setClassLocation(ClassColumnPosition);
	int[] indiciesMedoids = Medoids.getMedoids(DataArraySet.clusterData, NumberOfClasses);
	float[][] datapointsMedoids = new float[indiciesMedoids.length][DataArraySet.clusterData[0].length];

	for (int i = 0; i < indiciesMedoids.length; i++) {
	    datapointsMedoids[i] = DataArraySet.clusterData[indiciesMedoids[i]];
	}

	return datapointsMedoids;
    }
}

class ArraySet {
    float[][] clusterData, trainingData, testingData;

    ArraySet(float[][] clusterData, float[][] trainingData, float[][] testingData) {
	this.clusterData = clusterData;
	this.trainingData = trainingData;
	this.testingData = testingData;
    }

    public float[][] getClusterData() {
	return clusterData;
    }

    public float[][] getTrainingData() {
	return trainingData;
    }

    public float[][] getTestingData() {
	return testingData;
    }

}

class Network {
    float[][] trainData;
    float[] outputData;

    ClusterData[] clusterData;

    Network(float[][] trainDataInput, float[] trainDataOutput, float[][] givenClustersInput,
	    float[] givenClustersOutput) {

	this.trainData = trainDataInput;
	this.outputData = trainDataOutput;

	// create a two layer network
	clusterData = new ClusterData[givenClustersInput.length];

	// initialize the layers of the network
	for (int i = 0; i < clusterData.length; i++) {
	    clusterData[i] = new ClusterData(givenClustersInput[i].clone(), givenClustersOutput[i]);
	}
    }

    public void trainNetwork() {

	// for every point in the training set
	for (int row = 0; row < trainData.length; row++) {

	    // get the output class for the given point
	    float outputClass = outputData[row];

	    //initialize the strongest position and number
	    int strongestClusterPosition = 0;
	    float strongestNumber = -1000;

	    //for every cluster, find the cluster that strongest agrees with the point
	    for (int clusterPosition = 0; clusterPosition < clusterData.length; clusterPosition++) {

		// calculate the phi of the point given the cluster
		float phi = clusterData[clusterPosition].phi(trainData[row]);

		// grab the weight of the cluster
		float weight = clusterData[clusterPosition].weightCoeff;

		// multiply them together
		float number = phi * weight;

		// if this number is larger than the current largest, update the strongest position and number
		if (number > strongestNumber) {
		    strongestNumber = number;
		    strongestClusterPosition = clusterPosition;
		}
	    }

	    // check if the class of the training point was equal to the class of the cluster
	    boolean wasExpected = outputClass != clusterData[strongestClusterPosition].clusterCenterClass;

	    // update the cluster based on the assertion
	    clusterData[strongestClusterPosition].updateWeight(trainData[row], wasExpected);
	}
    }

    public float testNetwork(float[][] testDataInput, float[] testDataOutput) {
	boolean[] correctTests = new boolean[testDataInput.length];

	//for every test data point
	for (int row = 0; row < testDataInput.length; row++) {

	    // get the output class for the given point
	    float outputClass = testDataOutput[row];

	    //initialize the strongest position and number
	    int strongestClusterPosition = 0;
	    float strongestNumber = -1000;

	    //for every cluster, find the cluster that strongest agrees with the point
	    for (int clusterPosition = 0; clusterPosition < clusterData.length; clusterPosition++) {

		// calculate the phi of the point given the cluster
		float phi = clusterData[clusterPosition].phi(testDataInput[row]);

		// grab the weight of the cluster
		float weight = clusterData[clusterPosition].weightCoeff;

		// multiply them together
		float number = phi * weight;

		// if this number is larger than the current largest, update the strongest position and number
		if (number > strongestNumber) {
		    strongestNumber = number;
		    strongestClusterPosition = clusterPosition;
		}
	    }

	    // check if the class of the training point was equal to the class of the cluster
	    boolean wasExpected = outputClass != clusterData[strongestClusterPosition].clusterCenterClass;

	    correctTests[row] = wasExpected;
	}

	return calcPercentTrue(correctTests);
    }

    public float calcPercentTrue(boolean[] input) {
	int totalNumOfResults = input.length;

	int numberCorrect = 0;

	for (int i = 0; i < totalNumOfResults; i++) {
	    if (input[i]) {
		numberCorrect++;
	    }
	}

	float correctAmount = (float) numberCorrect / (float) totalNumOfResults;

	return correctAmount;
    }
}

class ClusterData {
    float weightCoeff;
    float clusterCenterPoint[];
    float clusterCenterClass;
    float sigmaCoeff;
    float coefficient = 0.1f;

    ClusterData(float[] clusterCenterPoint, float clusterCenterClass) {

	this.clusterCenterPoint = clusterCenterPoint;
	this.clusterCenterClass = clusterCenterClass;

	// set the initial sigma and weight coefficients to 0.5
	sigmaCoeff = 0.5f;
	weightCoeff = 0.5f;
    }

    // updates the weight of the cluster given the phi and whether it was the desired class
    void updateWeight(float[] input, boolean desiredClass) {
	float phi = phi(input);

	float reinforcement;

	if (desiredClass) {
	    reinforcement = coefficient;
	} else {
	    reinforcement = -coefficient;
	}

	weightCoeff += (reinforcement * phi);
    }

    // returns the phi coefficient based on 
    float phi(float[] input) {

	// intialize the distance of the cluster
	double distanceSquared = 0;

	// for every value in the point
	for (int i = 0; i < clusterCenterPoint.length; i++) {

	    // add the squared distance from the input value to the center value
	    distanceSquared += Math.pow(input[i] - clusterCenterPoint[i], 2);
	}

	double distance = Math.sqrt(distanceSquared);

	double denominator = 2.0 * Math.pow(sigmaCoeff, 2); 	// 2 * (sigma ^ 2)
	double power = -distance / denominator; 		// -distance / (2 * (sigma ^ 2))
	double phi = Math.pow(Math.E, power);			// e ^ (-distance / (2 * (sigma ^ 2)))

	return (float) phi;
    }
}