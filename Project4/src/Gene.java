
public class Gene {
	public float weightMatrix[][][];
	public float velocityMatrix[][][];
	public float bestKnown[][][];
	public float activations[];
	public float bestKnownFitness;
	public float fitness;
	
	// Class to hold data for individuals of populations
	
	public Gene() {
		
	}
	
	public float[] getResults() {
		int resultLength = weightMatrix[0][weightMatrix[0].length - 1].length;
		float results[] = new float[resultLength];
		// Get initial activations from the example
		float currentActivations[] = activations;
		
		// Apply the weight matrix through the whole neural network
		for(int i = 0; i < weightMatrix.length; i++) {
			// Initialize the array that will hold multiplication results
			float newActivations[] = new float[weightMatrix[i][0].length];			
			
			// Iterate through the weight matrix, performing the matrix multiplication
			for(int j = 0; j < newActivations.length; j++) {
				for(int k = 0; k < currentActivations.length; k++) {
					newActivations[j] += currentActivations[k] * weightMatrix[i][k][j];
				}
				
				// Apply sigmoid function to results
				newActivations[j] = calcSig(newActivations[j]);
			}
			
			// Set the new activations to be the ones sent through the multiplication process
			results = newActivations;
			currentActivations = newActivations;
		}
		
		// Set the results equal to the final results of the multiplications
		return results;
	}
	
	// Function to get sigmoid
	float calcSig(float x) {
		float e = (float) Math.exp((double) x);
		float sig = (1/(1+e));
		
		return sig;
	}
}
