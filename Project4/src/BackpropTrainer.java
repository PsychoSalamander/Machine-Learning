import java.util.Random;

public class BackpropTrainer {

	// Initialization Multiplier
	final static float IM = 5.0f;
	
	// Learning Multiplier
	final static float N = .1f;
	
	public float inPracticeData[][];
	
	private int numLayers;
	private int numNodes[];
	float weightMatrix[][][];
	
	int classLocation;
	public float classKey[];
	
	BackpropTrainer(int layerCount, int numInputs, int numOutputs, int nodeCount[]) {
		
		numLayers = layerCount + 2;
		numNodes = new int[numLayers];
		numNodes[0] = numInputs;
		numNodes[numLayers - 1] = numOutputs;
		
		if(nodeCount.length == layerCount) {
			for(int i = 0; i < nodeCount.length; i++) {
				numNodes[i+1] = nodeCount[i];
			}
		} else {
			System.out.println("Unequal Node/Layer Count!");
			System.exit(-1);
		}
		
		weightMatrix = new float[numLayers - 1][][];
		for(int i = 0; i < numLayers - 1; i++) {
			weightMatrix[i] = new float[numNodes[i]][numNodes[i + 1]];
		}
	}
	
	public Gene runClass() {
		
		// Run the forwardpass
		forwardPass();
		
		// Train the Neural Network
		for(int i = 0; i < inPracticeData.length; i++) {
			// Get the inital activations
			float[] activations = new float[inPracticeData[0].length-1];
			int iter = 0;
			for(int k = 0; k < inPracticeData[0].length; k++) {
				if(k != classLocation) {
					activations[iter] = inPracticeData[i][k];
				}
			}
			
			// Get the results from the NN
			float results[][] = getResults(activations);
			// Update the weights in the weight matrix
			backpropClass(results, inPracticeData[i][classLocation]);

		}
		
		Gene g = new Gene();
		g.weightMatrix = weightMatrix;
		
		return g;
	}
	
	public Gene runRegress() {
		// Run forward pass
		forwardPass();
		
		// Train neural network
		for(int i = 0; i < 3; i++) {
			// Get initial activations
			float activations[] = inPracticeData[i];
			
			// Get results
			float results[][] = getResults(activations);
			// Update weights based on the results
			backpropRegress(results, inPracticeData[i][classLocation]);
		}
		
		Gene g = new Gene();
		g.weightMatrix = weightMatrix;
		
		return g;
	}
	
	// Function to set the practice data for the nueral network
	public void setPracticeData(float[][] d) {
		inPracticeData = d;
	}
	
	// Function to set the class location for the nueral network
	public void setClassLocation(int loc) {
		classLocation = loc;
	}
	
	// Function to run the activations through the neural network
	private float[][] getResults(float inExample[]) {
		// Initialize result array
		float results[][] = new float[weightMatrix.length + 1][numNodes[numLayers - 1]];
		results[0] = inExample;
		// Get initial activations from the example
		float currentActivations[] = inExample;
		
		// Apply the weight matrix through the whole neural network
		for(int i = 0; i < weightMatrix.length; i++) {
			// Initialize the array that will hold multiplication results
			float newActivations[] = new float[weightMatrix[i][0].length];		
			
			// Iterate through the weight matrix, performing the matrix multiplication
			for(int j = 0; j < newActivations.length; j++) {
				for(int k = 0; k < currentActivations.length; k++) {
					// Print the multiplication being performed
					newActivations[j] += currentActivations[k] * weightMatrix[i][k][j];
				}
				
				// Apply sigmoid function to results
				newActivations[j] = calcSig(newActivations[j]);
			}
			
			// Set the new activations to be the ones sent through the multiplication process
			results[i+1] = newActivations;
			currentActivations = newActivations;
		}
		
		// Set the results equal to the final results of the multiplications
		
		return results;
	}
	
	float getResult(float results[]) {
		int bigIndex = 0;
		float bigEstimate = 0;
		
		for(int j = 0; j < results.length; j++) {
			if(results[j] > bigEstimate) {
				bigEstimate = results[j];
				bigIndex = j; 
			}
		}
		
		return(classKey[bigIndex]);
	}
	
	private void backpropClass(float results[][], float example) {
		// Get the desired value of each node
		float[] desired = new float[classKey.length];
		for(int i = 0; i < classKey.length; i++) {
			if(classKey[i] == example) {
				desired[i] = 1.0f;
			} else {
				desired[i] = 0.0f;
			}
		}
		
		// Iterate through every step of weights to update them
		for(int x = weightMatrix.length - 1; x > 0; x--) {
			// Get the squared error at each node
			float[] error = new float[desired.length];
			for(int i = 0; i < error.length; i++) {
				error[i] = (float) Math.pow((double) (results[x + 1][i] - desired[i]), 2);
			}
			
			// Initialize new desired and new weights arrays
			float[] newDesired = results[x];
			float[][] newWeights = weightMatrix[x];
			
			// Find new weights
			for(int i = 0; i < weightMatrix[x].length; i++) {
				for(int j = 0; j < weightMatrix[x][0].length; j++) {
					// Apply learning modifier and error in proportion to the level of new desired outputs
					newWeights[i][j] += -N * newDesired[i] * error[j];
				}
			}
			
			// Find new desired values
			for(int i = 0; i < weightMatrix[x].length; i++) {
				for(int j = 0; j < weightMatrix[x][0].length; j++) {
					// Set new desired values with respect to weights and error
					newDesired[i] += weightMatrix[x][i][j] * error[j];
				}
			}
			
			// Set desired and weights to their new values
			weightMatrix[x] = newWeights;
			desired = newDesired;
		}
	}
	
	private void backpropRegress(float results[][], float example) {
		// Get the desired value of each node
		float[] desired = new float[classKey.length];
		for(int i = 0; i < classKey.length; i++) {
			desired[i] = classKey[i] - example;
		}
		
		for(int x = weightMatrix.length - 1; x > 0; x--) {
			// Get the squared error at each node
			float[] error = new float[desired.length];
			for(int i = 0; i < error.length; i++) {
				error[i] = (float) Math.pow((double) ((results[x + 1][i] - example) - desired[i]), 2);
			}
			
			// Initialize new desired and new weights arrays
			float[] newDesired = results[x];
			float[][] newWeights = weightMatrix[x];
			
			// Find new weights
			for(int i = 0; i < weightMatrix[x].length; i++) {
				for(int j = 0; j < weightMatrix[x][0].length; j++) {
					// Apply learning modifier and error in proportion to the level of new desired outputs
					newWeights[i][j] += -N * newDesired[i] * error[j];
				}
			}
			
			// Find new desired values
			for(int i = 0; i < weightMatrix[x].length; i++) {
				for(int j = 0; j < weightMatrix[x][0].length; j++) {
					// Set new desired values with respect to weights and error
					newDesired[i] += weightMatrix[x][i][j] * error[j];
				}
			}
			
			// Set desired and weights to their new values
			weightMatrix[x] = newWeights;
			desired = newDesired;
		}
	}
		
	// Function to initialize weight matrix
	private void forwardPass() {
		
		Random r = new Random();
		
		// Iterate through weight matrix
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				for(int k = 0; k < weightMatrix[i][j].length; k++) {
					// Set each location in the weight matrix to be between zero
					// and the initialization multiplier
					weightMatrix[i][j][k] = r.nextFloat() * IM;
				}
			}
		}
	}
	
	// Function to calculate the sigmoid of a function
	float calcSig(float x) {
		float e = (float) Math.exp((double) x);
		float sig = (1/(1+e));
		
		return sig;
	}
}
