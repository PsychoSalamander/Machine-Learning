import java.util.Random;

public class FeedForward {
	
	// Initialization Multiplier
	final static float IM = .5f;
	
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
		
		if(nodeCount.length != layerCount) {
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
			float results[] = getResults(inTestData[i]);
			
			int bigIndex = -1;
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
		
		System.out.println(correct / inTestData.length);
	}
	
	public void runRegress() {
		forwardPass();
		
		setRegressKey();
		
		for(int i = 0; i < inPracticeData.length; i++) {
			float activations[] = getActivations(inPracticeData[i]);
			
			float results[] = getResults(activations);
			
			updateWeightsRegress(results, inPracticeData[i][classLocation]);
		}
		
		int correct = 0;
		
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
		
		return activations;
	}
	
	private float[] getResults(float inExample[]) {
		float results[] = new float[numNodes[numLayers - 1]];
		
		float currentActivations[] = inExample;
		
		for(int i = 0; i < weightMatrix.length; i++) {
			float newActivations[] = new float[weightMatrix[i][0].length];
			
			for(int j = 0; j < newActivations.length; j++) {
				for(int k = 0; k < currentActivations.length; k++) {
					newActivations[j] += currentActivations[k] * weightMatrix[i][k][j];
				}
				
				newActivations[j] = calcSig(newActivations[j]);
			}
			
			currentActivations = newActivations;
		}
		
		results = currentActivations;
		
		return results;
	}
	
	private void updateWeightsClass(float results[], float example) {
		int bigIndex = -1;
		float bigEstimate = 0;
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
		
		float err = N * (correct / bigEstimate);
		
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				for(int k = 0; k < weightMatrix[i][j].length; j++) {
					weightMatrix[i][j][k] = weightMatrix[i][j][k] + err;
				}
			}
		}
	}
	
	private void updateWeightsRegress(float results[], float example) {
		int bigIndex = -1;
		float bigEstimate = 0;
		for(int i = 0; i < results.length; i++) {
			if(results[i] > bigEstimate) {
				bigEstimate = results[i];
				bigIndex = i;
			}
		}
		
		float correct = classKey[bigIndex];
		
		float err = N * (((example - correct) * (example - correct)) / bigEstimate);
		
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				for(int k = 0; k < weightMatrix[i][j].length; j++) {
					weightMatrix[i][j][k] = weightMatrix[i][j][k] + err;
				}
			}
		}
	}
	
	private void forwardPass() {
		
		Random r = new Random();
		
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				for(int k = 0; k < weightMatrix[i][j].length; j++) {
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
		inScales = scales;
	}
}
