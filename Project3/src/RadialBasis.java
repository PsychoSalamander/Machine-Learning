import java.util.Arrays;

import datapkg.*;

public class RadialBasis {

    float[][] InputtedData;
    int NumberOfClasses;
    int ClassColumnPosition;

    RadialBasis(ProcessedData ImportedData) {
	InputtedData = ImportedData.getDataArrayShuffled();
	NumberOfClasses = ImportedData.getNumberOfClasses();
	ClassColumnPosition = ImportedData.getClassColumnPosition();

	runAll();
    }

    public void runAll() {

	float[][] datapointsKMeans = getKmeans();
	float[][] datapointsMedoids = getMedoids();

	System.out.println("KMeans");
	runNet(datapointsKMeans);

	System.out.println("Medoids");
	runNet(datapointsMedoids);

    }

    private void runNet(float[][] data) {
	float[][] inputData = decoupledInputs(data);
	float[] outputData = giveOutput(data);

	Network network = new Network(inputData, outputData);

	for (int i = 0; i < 100; i++) {
	    network.trainNetwork();
	}

	System.out.println("Network Trained.");
    }

    // returns an array of the input points, decoupled from the class
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

    // calculates the class given the data point
    private float[] giveOutput(float[][] meanData) {

	// get the expected size of the new array
	int dcRowLength = meanData.length;

	// instantiate the new array with the expected size
	float[] decoupledArray = new float[dcRowLength];

	for (int classIndex = 0; classIndex < NumberOfClasses; classIndex++) {
	    float minDist = 1000000;
	    int minIndex = 0;

	    // Check each of the data points in the practice set
	    for (int j = 0; j < InputtedData.length; j++) {

		// Check to find the closest point that has not already been used
		if ((getDistance(meanData[classIndex], InputtedData[j]) < minDist)) {
		    minDist = getDistance(meanData[classIndex], InputtedData[j]);
		    minIndex = j;
		}
	    }

	    // Set class for mean equal to the closest practice point
	    decoupledArray[classIndex] = InputtedData[minIndex][ClassColumnPosition];
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
	return KMeans.getMeans(InputtedData, NumberOfClasses).clone();
    }

    private float[][] getMedoids() {
	PAMNearestNeighbor Medoids = new PAMNearestNeighbor();
	Medoids.setClassLocation(ClassColumnPosition);
	int[] indiciesMedoids = Medoids.getMedoids(InputtedData, NumberOfClasses);
	float[][] datapointsMedoids = new float[indiciesMedoids.length][InputtedData[0].length];

	for (int i = 0; i < indiciesMedoids.length; i++) {
	    datapointsMedoids[i] = InputtedData[indiciesMedoids[i]];
	}

	return datapointsMedoids;
    }
}

class Network {
    float[][] inputData;
    float[] outputData;

    NetworkData[] networkData;

    Network(float[][] inputData, float[] outputData) {
	this.inputData = inputData;
	this.outputData = outputData;

	// create a two layer network
	networkData = new NetworkData[2];

	// initialize an initial center to all variables being 0
	float[] initialCluster = new float[inputData[0].length];
	for (int index = 0; index < initialCluster.length; index++) {
	    initialCluster[index] = 1.0f;
	}

	// initialize the layers of the network
	for (int i = 0; i < networkData.length; i++) {
	    networkData[i] = new NetworkData(initialCluster.clone());
	}
    }

    public void trainNetwork() {

	// for every point in the inputData
	for (int row = 0; row < inputData.length; row++) {

	    // get the output class for the given point
	    float outputClass = outputData[row];

	    // initialize the predicted class
	    float predictedClass = 0;

	    //for every layer in the network
	    for (int layer = 0; layer < networkData.length; layer++) {
		float phi = networkData[layer].phi(inputData[row]);
		float weight = networkData[layer].weightCoeff;

		// modify the predictedoutput based on the phi of the node times the weight coefficient
		predictedClass += phi * weight;
	    }

	    //for every layer in the network
	    for (int layer = 0; layer < networkData.length; layer++) {

		// update the network
		networkData[layer].update(inputData[row], outputClass, predictedClass);
	    }
	}
    }

}

class NetworkData {
    float weightCoeff;
    float clusterCenter[];
    float sigmaCoeff;
    float n1 = 0.1f;
    float n2 = 0.1f;

    NetworkData(float[] clusterCenter) {

	this.clusterCenter = clusterCenter;

	// set the initial sigma and weight coefficients to 0.5
	sigmaCoeff = 0.5f;
	weightCoeff = 0.5f;
    }

    // 
    void update(float[] input, float desired, float output) {
	float phi = phi(input);
	float diffOutput = desired - output;

	for (int i = 0; i < clusterCenter.length; i++) {
	    clusterCenter[i] = clusterCenter[i]
		    + (n1 * diffOutput * weightCoeff * phi * (input[i] - clusterCenter[i]) / (sigmaCoeff * sigmaCoeff));
	}
	
	weightCoeff = weightCoeff + (n2 * diffOutput * phi);
    }

    // returns the phi coefficient based on 
    float phi(float[] input) {

	// intialize the distance of the cluster
	double distanceSquared = 0;

	// for every value in the point
	for (int i = 0; i < clusterCenter.length; i++) {

	    // add the squared distance from the input value to the center value
	    distanceSquared += Math.pow(input[i] - clusterCenter[i], 2);
	}

	double distance = Math.sqrt(distanceSquared);

	double denominator = 2.0 * Math.pow(sigmaCoeff, 2); 	// 2 * (sigma ^ 2)
	double power = -distance / denominator; 		// -distance / (2 * (sigma ^ 2))
	double phi = Math.pow(Math.E, power);			// e ^ (-distance / (2 * (sigma ^ 2)))

	return (float) phi;
    }

}