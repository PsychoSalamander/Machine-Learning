import java.util.Random;

public class BackpropTrainer {

	// Initialization Multiplier
	final static float IM = 5.0f;
	
	// Learning Multiplier
	final static float N = .1f;
	
	float inPracticeData[][];
	
	private int numLayers;
	private int numNodes[];
	float weightMatrix[][][];
	
	int classLocation;
	private float inScales[];
	private float classKey[];
	
	public BackpropTrainer() {
		
	}
	
	public float[][][] trainBackprop() {
		
		// Run the forwardpass
		forwardPass();
		
		// Match each output node to a class
		setClassKey();
		
		// Train the Neural Network
		for(int i = 0; i < inPracticeData.length; i++) {
			// Get the inital activations
			float activations[] = getActivations(inPracticeData[i]);
			
			// Get the results from the NN
			float results[][] = getResults(activations);
			// Update the weights in the weight matrix
			backpropClass(results, inPracticeData[i][classLocation]);

		}
		
		return weightMatrix;
	}
	
	public float[][][] trainBackpropRegress() {
		// Run forward pass
		forwardPass();
		
		// Print the initial weight matrix to console
		System.out.println("Initial Weight Matrix:");
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				System.out.print("[");
				for(int k = 0; k < weightMatrix[i][j].length; k++) {
					System.out.print(weightMatrix[i][j][k] + ", ");
				}
				System.out.println("]");
			}
			System.out.println("\n");
		}
		
		// Assign regression guesses to each output node
		setRegressKey();
		
		// Train neural network
		for(int i = 0; i < 3; i++) {
			// Get initial activations
			float activations[] = getActivations(inPracticeData[i]);
			
			// Get results
			float results[][] = getResults(activations);
			// Update weights based on the results
			backpropRegress(results, inPracticeData[i][classLocation]);
		}
		
		return weightMatrix;
	}
	
	// Function to set the practice data for the nueral network
	public void setPracticeData(float[][] d) {
		inPracticeData = d;
	}
	
	// Function to set the class location for the nueral network
	public void setClassLocation(int loc) {
		classLocation = loc;
	}
	
	// Function to set the scales for the nueral network
	public void setScales(float scales[]) {
		
		// Print scales to console
		System.out.print("scale: [");
		for(int i = 0; i < scales.length; i++) {
			System.out.print(scales[i] + ", ");
		}
		System.out.println("]");
		
		inScales = scales;
	}
	
	// Function to run the activations through the neural network
	private float[][] getResults(float inExample[]) {
		// Initialize result array
		float results[][] = new float[weightMatrix.length + 1][numNodes[numLayers - 1]];
		results[0] = inExample;
		// Get initial activations from the example
		float currentActivations[] = inExample;
		
		// Print initail activations to console
		System.out.print("Initial Activations: [");
		for(int k = 0; k < currentActivations.length; k++) {
			System.out.print(currentActivations[k] + ", ");
		}
		System.out.println("]");
		
		// Print weight matrix to console
		System.out.println("Weight Matrix:");
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				System.out.print("[");
				for(int k = 0; k < weightMatrix[i][j].length; k++) {
					System.out.print(weightMatrix[i][j][k] + ", ");
				}
				System.out.println("]");
			}
			System.out.println("\n");
		}
		
		// Apply the weight matrix through the whole neural network
		for(int i = 0; i < weightMatrix.length; i++) {
			// Initialize the array that will hold multiplication results
			float newActivations[] = new float[weightMatrix[i][0].length];
			
			// Print the activations being multiplied
			System.out.print("pre-Activations: [");
			for(int x = 0; x < currentActivations.length; x++) {
				System.out.print(currentActivations[x] + ", ");
			}
			System.out.println("]");			
			
			// Iterate through the weight matrix, performing the matrix multiplication
			for(int j = 0; j < newActivations.length; j++) {
				for(int k = 0; k < currentActivations.length; k++) {
					// Print the multiplication being performed
					System.out.println("Multiplier: " + weightMatrix[i][k][j] + " * " + currentActivations[k]);
					newActivations[j] += currentActivations[k] * weightMatrix[i][k][j];
				}
				System.out.println("-----");
				
				// Apply sigmoid function to results
				newActivations[j] = calcSig(newActivations[j]);
			}
			
			// Print out the post activations
			System.out.print("post-Activations: [");
			for(int x = 0; x < newActivations.length; x++) {
				System.out.print(newActivations[x] + ", ");
			}
			System.out.println("]");
			
			// Set the new activations to be the ones sent through the multiplication process
			results[i+1] = newActivations;
			currentActivations = newActivations;
		}
		
		// Set the results equal to the final results of the multiplications
		
		return results;
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

	// Function to assign classes to output nodes of the neural network
	private void setClassKey() {
		// Initalize key matrix
		int foundKeys[] = new int[inPracticeData.length];
		for(int i = 0; i < foundKeys.length; i++) {
			foundKeys[i] = -1;
		}
		
		// Find keys in practice data
		for(int i = 0; i < inPracticeData.length; i++) {
			int currentResult = (int) inPracticeData[i][classLocation];
			
			// Check to see if current key has already been seen
			boolean found = false;
			for(int j = 0; j < foundKeys.length; j++) {
				if(currentResult == foundKeys[j]) {
					found = true;
					break;
				}
			}
			
			// Add key to keys if new
			if(!found) {
				foundKeys[i] = currentResult;
			}
		}
		
		// Fill out classKey based on keys found above
		classKey = new float[numNodes[numNodes.length - 1]];
		int iter = 0;
		for(int i = 0; i < foundKeys.length; i++) {
			if(foundKeys[i] != -1) {
				if(iter != classKey.length) {
					classKey[iter] = foundKeys[i];
					iter++;
				}
			}
		}
	}	
	
	// Function to assign regression estimates to output nodes of the neural network
	private void setRegressKey() {
		// Search for largest value in the results of the practice set
		float largestKey = 0;
		for(int i = 0; i < inPracticeData.length; i++) {
			if(inPracticeData[i][classLocation] > largestKey) {
				largestKey = inPracticeData[i][classLocation];
			}
		}
		
		// get the number of outputs
		int outputs = numNodes[numNodes.length - 1];
		
		// Get how large each estimate bucket should be
		float bucketSize = largestKey / outputs;
		
		// Assign each output node an estimate in the middle of the bucket size
		classKey = new float[outputs];
		float start = bucketSize / 2;
		for(int i = 0; i < outputs; i++) {
			classKey[i] = start;
			start += bucketSize;
		}
	}	
	
	// Function to get inital activations that will be sent into the NN
	private float[] getActivations(float[] inData) {
		// Initialize the activation matrix
		float activations[] = new float[inData.length - 1];
		
		// Get the activations
		int iter = 0;
		for(int i = 0; i < inData.length; i++) {
			// Check to make sure the value being checked isn't the result
			if(i != classLocation) {
				// Divide the current value by the highest value possible
				// to get a value between 0 and 1
				activations[iter] = inData[i]/inScales[iter];
				iter++;
			}
		}
		
		return activations;
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
