import java.util.Random;

public class FeedForward {
	
	// Initialization Multiplier
	final static float IM = 1.0f;
	
	// Learning Multiplier
	final static float N = .1f;
	
	private int numLayers;
	private int numNodes[];
	private float weightMatrix[][][];
	
	private float inPracticeData[][];
	private float inTestData[][];
	
	private float inScales[];
	
	private float classKey[];
	private int classLocation;
	
	public FeedForward(int layerCount, int numInputs, int numOutputs, int nodeCount[]) {
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
	
	public void runClass() {
		forwardPass();
		
		setClassKey();
		
		for(int i = 0; i < inPracticeData.length; i++) {
			float activations[] = getActivations(inPracticeData[i]);
			
			float results[] = getResults(activations);
			
			updateWeightsClass(results, inPracticeData[i][classLocation]);
		}
		
		int correct = 0;
		for(int i = 0; i < inTestData.length; i++) {
			float activations[] = getActivations(inTestData[i]);
			float results[] = getResults(activations);
			
			int bigIndex = 0;
			float bigEstimate = 0;
			for(int j = 0; j < results.length; j++) {
				if(results[j] > bigEstimate) {
					bigEstimate = results[j];
					bigIndex = j;
				}
			}
			
			if(classKey[bigIndex] == inPracticeData[i][classLocation]) {
				correct++;
			}
		}
		
		System.out.println((float)correct / inTestData.length);
	}
	
	public void runRegress() {
		forwardPass();
		
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
		
		setRegressKey();
		
		for(int i = 0; i < 3; i++) {
			float activations[] = getActivations(inPracticeData[i]);
			
			float results[] = getResults(activations);
			
			updateWeightsRegress(results, inPracticeData[i][classLocation]);
		}
		
		float meanErr = 0;
		
		for(int i = 0; i < 1; i++) {
			float activations[] = getActivations(inTestData[i]);
			float results[] = getResults(activations);
			
			int bigIndex = 0;
			float bigEstimate = 0;
			for(int j = 0; j < results.length; j++) {
				if(results[j] > bigEstimate) {
					bigEstimate = results[j];
					bigIndex = j;
				}
			}
			
			float result = results[bigIndex];
			
			float err = (float) Math.exp(inTestData[i][classLocation] - result);
			
			meanErr += err;
		}
		
		meanErr = meanErr / inTestData.length;
		
		System.out.println("Mean Squared Error of Regression Test: " + meanErr);
	}
	
	private void setClassKey() {
		int foundKeys[] = new int[inPracticeData.length];
		
		for(int i = 0; i < foundKeys.length; i++) {
			foundKeys[i] = -1;
		}
		
		for(int i = 0; i < inPracticeData.length; i++) {
			int currentResult = (int) inPracticeData[i][classLocation];
			
			boolean found = false;
			for(int j = 0; j < foundKeys.length; j++) {
				if(currentResult == foundKeys[j]) {
					found = true;
					break;
				}
			}
			
			if(!found) {
				foundKeys[i] = currentResult;
			}
		}
		
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
	
	private void setRegressKey() {
		float largestKey = 0;
		
		for(int i = 0; i < inPracticeData.length; i++) {
			if(inPracticeData[i][classLocation] > largestKey) {
				largestKey = inPracticeData[i][classLocation];
			}
		}
		
		int outputs = numNodes[numNodes.length - 1];
		float bucketSize = largestKey / outputs;
		
		classKey = new float[outputs];
		float start = bucketSize / 2;
		for(int i = 0; i < outputs; i++) {
			classKey[i] = start;
			start += bucketSize;
		}
	}	
	
	private float[] getActivations(float[] inData) {
		float activations[] = new float[inData.length - 1];
		
		int iter = 0;
		for(int i = 0; i < inData.length; i++) {
			if(i != classLocation) {
				activations[iter] = inData[i]/inScales[iter];
				iter++;
			}
		}
		
		/*
		System.out.print("Activations: [");
		for(int i = 0; i < activations.length; i++) {
			System.out.print(activations[i] + ", ");
		}
		System.out.println("]");
		*/
		
		return activations;
	}
	
	private float[] getResults(float inExample[]) {
		float results[] = new float[numNodes[numLayers - 1]];
		
		float currentActivations[] = inExample;
		
		System.out.print("Initial Activations: [");
		for(int k = 0; k < currentActivations.length; k++) {
			System.out.print(currentActivations[k] + ", ");
		}
		System.out.println("]");
		
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
		
		for(int i = 0; i < weightMatrix.length; i++) {
			float newActivations[] = new float[weightMatrix[i][0].length];
			
			System.out.print("pre-Activations: [");
			for(int x = 0; x < currentActivations.length; x++) {
				System.out.print(currentActivations[x] + ", ");
			}
			System.out.println("]");
			
			
			
			for(int j = 0; j < newActivations.length; j++) {
				for(int k = 0; k < currentActivations.length; k++) {
					System.out.println("Multiplier: " + weightMatrix[i][k][j] + " * " + currentActivations[k]);
					newActivations[j] += currentActivations[k] * weightMatrix[i][k][j];
				}
				
				System.out.println("-----");
				
				if(i != weightMatrix.length - 1) {
					newActivations[j] = calcSig(newActivations[j]);
				}
			}
			
			System.out.print("post-Activations: [");
			for(int x = 0; x < newActivations.length; x++) {
				System.out.print(newActivations[x] + ", ");
			}
			System.out.println("]");
			
			currentActivations = newActivations;
		}
		
		results = currentActivations;
		/*
		System.out.print("Results: [");
		for(int i = 0; i < results.length; i++) {
			System.out.print(results[i] + ", ");
		}
		System.out.println("]");
		*/
		
		return results;
	}
	
	private void updateWeightsClass(float results[], float example) {
		int bigIndex = 0;
		float bigEstimate = -1;
		for(int i = 0; i < results.length; i++) {
			if(results[i] > bigEstimate) {
				bigEstimate = results[i];
				bigIndex = i;
			}
		}
		
		int result = (int) classKey[bigIndex];
		float correct = -1.0f;
		if(result == example) {
			correct = 1.0f;
		}
		
		System.out.print("Results: [");
		for(int x = 0; x < results.length; x++) {
			System.out.print(results[x] + ", ");
		}
		System.out.println("]");
		float err = N * (correct / (1.0001f - bigEstimate));
		System.out.println("Estimate: " + bigEstimate);
		
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				for(int k = 0; k < weightMatrix[i][j].length; k++) {
					weightMatrix[i][j][k] = weightMatrix[i][j][k] + err;
				}
			}
		}
	}
	
	private void updateWeightsRegress(float results[], float example) {
		int bigIndex = 0;
		float bigEstimate = -1;
		for(int i = 0; i < results.length; i++) {
			if(results[i] > bigEstimate) {
				bigEstimate = results[i];
				bigIndex = i;
			}
		}
		
		float correct = classKey[bigIndex];
		
		//System.out.println("Strength of estimate: " + bigEstimate);
		float err = N * (((example - correct) * (example - correct)) / (1.001f - bigEstimate));
		System.out.println("Error: " + err);
		
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				for(int k = 0; k < weightMatrix[i][j].length; k++) {
					weightMatrix[i][j][k] = weightMatrix[i][j][k] + err;
				}
			}
		}
	}
	
	private void forwardPass() {
		
		Random r = new Random();
		
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				for(int k = 0; k < weightMatrix[i][j].length; k++) {
					weightMatrix[i][j][k] = r.nextFloat() * IM;
				}
			}
		}
	}
	
	float calcSig(float x) {
		float e = (float) Math.exp((double) x);
		float sig = (1/(1+e));
		
		return sig;
	}
	
	public void setPracticeData(float[][] d) {
		inPracticeData = d;
	}
	
	public void setTestData(float[][] d) {
		inTestData = d;
	}
	
	public void setClassLocation(int loc) {
		classLocation = loc;
	}
	
	public void setScales(float scales[]) {
		
		System.out.print("scale: [");
		for(int i = 0; i < scales.length; i++) {
			System.out.print(scales[i] + ", ");
		}
		System.out.println("]");
		
		inScales = scales;
	}
}
