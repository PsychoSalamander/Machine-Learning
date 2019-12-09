import java.util.Random;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;

public class FeedForward {
		
	private int numLayers;
	private int numNodes[];
	private float weightMatrix[][][];
	
	private float inPracticeData[][];
	private float inTestData[][];
	
	int classLocation;
	private float inScales[];
	private float classKey[];
	
	private Path FileName;
	
	// Constructor
	public FeedForward(int layerCount, int numInputs, int numOutputs, int nodeCount[]) {
		
		// Initialize all variables
		numLayers = layerCount + 2;
		numNodes = new int[numLayers];
		numNodes[0] = numInputs;
		numNodes[numLayers - 1] = numOutputs;
		FileName = Paths.get("default");
		
		// Check that there are number of nodes assigned to each layer
		if(nodeCount.length == layerCount) {
			for(int i = 0; i < nodeCount.length; i++) {
				numNodes[i+1] = nodeCount[i];
			}
		} else {
			System.out.println("Unequal Node/Layer Count!");
			System.exit(-1);
		}
		
		// Create the initial weight matrix
		weightMatrix = new float[numLayers - 1][][];
		for(int i = 0; i < numLayers - 1; i++) {
			weightMatrix[i] = new float[numNodes[i]][numNodes[i + 1]];
		}
	}
	
	// Function to run classification
	public float runClass() {		
		// Create file for writing
		FileWriter fw = null;		
		try {
			fw = new FileWriter(FileName.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BackpropTrainer BT = new BackpropTrainer(numLayers, numNodes[0], numNodes[numLayers - 1], numNodes);
		GeneticTrainer GT = new GeneticTrainer(numLayers, numNodes[0], numNodes[numLayers - 1], numNodes);
		DifferentialTrainer DT = new DifferentialTrainer(numLayers, numNodes[0], numNodes[numLayers - 1], numNodes);
		ParticleTrainer PT = new ParticleTrainer(numLayers, numNodes[0], numNodes[numLayers - 1], numNodes);

		setClassKey();
		convertPracticeData();
		
		BT.classLocation = classLocation;
		GT.classLocation = classLocation;
		DT.classLocation = classLocation;
		PT.classLocation = classLocation;
		
		BT.classKey = classKey;
		GT.resultArray = classKey;
		DT.resultArray = classKey;
		PT.resultArray = classKey;
		
		BT.inPracticeData = inPracticeData;
		GT.inPracticeData = inPracticeData;
		DT.inPracticeData = inPracticeData;
		PT.inPracticeData = inPracticeData;
		
		System.out.println("Training BT c");
		Gene BTBest = BT.runClass();
		System.out.println("Training GT c");
		Gene GTBest = GT.runClass();
		System.out.println("Training DT c");
		Gene DTBest = DT.runClass();
		System.out.println("Training PT c");
		Gene PTBest = PT.runClass();
		
		convertTestData();
		float BTCorrect = 0, GTCorrect = 0, DTCorrect = 0, PTCorrect = 0;
		for(int i = 0; i < inTestData.length; i++) {
			float[] activations = new float[inPracticeData[0].length-1];
			int iter = 0;
			for(int k = 0; k < inTestData[0].length; k++) {
				if(k != classLocation) {
					activations[iter] = inPracticeData[i][k];
				}
			}
			
			BTBest.activations = activations;
			GTBest.activations = activations;
			DTBest.activations = activations;
			PTBest.activations = activations;
			
			if(BT.getResult(BTBest.getResults()) == inTestData[i][classLocation]) {
				BTCorrect++;
			}
			if(GT.getResult(GTBest.getResults()) == inTestData[i][classLocation]) {
				GTCorrect++;
			}
			if(DT.getResult(DTBest.getResults()) == inTestData[i][classLocation]) {
				DTCorrect++;
			}
			if(PT.getResult(PTBest.getResults()) == inTestData[i][classLocation]) {
				PTCorrect++;
			}
		}
		
		System.out.println("BT accuracy: " + BTCorrect/inTestData.length);
		System.out.println("GT accuracy: " + GTCorrect/inTestData.length);
		System.out.println("DT accuracy: " + DTCorrect/inTestData.length);
		System.out.println("PT accuracy: " + PTCorrect/inTestData.length);
		
		return 0;
	}
	
	// Function to run regression tests
	public float runRegress() {
		// Create file for writing
				FileWriter fw = null;		
				try {
					fw = new FileWriter(FileName.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				BackpropTrainer BT = new BackpropTrainer(numLayers, numNodes[0], numNodes[numLayers - 1], numNodes);
				GeneticTrainer GT = new GeneticTrainer(numLayers, numNodes[0], numNodes[numLayers - 1], numNodes);
				DifferentialTrainer DT = new DifferentialTrainer(numLayers, numNodes[0], numNodes[numLayers - 1], numNodes);
				ParticleTrainer PT = new ParticleTrainer(numLayers, numNodes[0], numNodes[numLayers - 1], numNodes);

				setClassKey();
				convertPracticeData();
				
				BT.classLocation = classLocation;
				GT.classLocation = classLocation;
				DT.classLocation = classLocation;
				PT.classLocation = classLocation;
				
				BT.classKey = classKey;
				GT.resultArray = classKey;
				DT.resultArray = classKey;
				PT.resultArray = classKey;
				
				BT.inPracticeData = inPracticeData;
				GT.inPracticeData = inPracticeData;
				DT.inPracticeData = inPracticeData;
				PT.inPracticeData = inPracticeData;
				
				System.out.println("Training BT r");
				Gene BTBest = BT.runClass();
				System.out.println("Training GT r");
				Gene GTBest = GT.runClass();
				System.out.println("Training DT r");
				Gene DTBest = DT.runClass();
				System.out.println("Training PT r");
				Gene PTBest = PT.runClass();
				
				convertTestData();
				float BTError = 0, GTError = 0, DTError = 0, PTError = 0;
				for(int i = 0; i < inTestData.length; i++) {
					float[] activations = new float[inPracticeData[0].length-1];
					int iter = 0;
					for(int k = 0; k < inTestData[0].length; k++) {
						if(k != classLocation) {
							activations[iter] = inPracticeData[i][k];
						}
					}
					
					BTBest.activations = activations;
					GTBest.activations = activations;
					DTBest.activations = activations;
					PTBest.activations = activations;
					
					float e = BT.getResult(BTBest.getResults()) - inTestData[i][classLocation];
					BTError += e*e;
					
					e = GT.getResult(GTBest.getResults()) - inTestData[i][classLocation];
					GTError += e*e;
					
					e = DT.getResult(DTBest.getResults()) - inTestData[i][classLocation];
					DTError += e*e;
					
					e = PT.getResult(PTBest.getResults()) - inTestData[i][classLocation];
					PTError += e*e;
				}
				
				System.out.println("BT error: " + BTError/inTestData.length);
				System.out.println("GT error: " + GTError/inTestData.length);
				System.out.println("DT error: " + DTError/inTestData.length);
				System.out.println("PT error: " + PTError/inTestData.length);
				
				return 0;
	}

	void convertPracticeData() {
		for(int i = 0; i < inPracticeData.length; i++) {
			int iter = 0;
			for(int j = 0; j < inPracticeData[i].length; j++) {
				if(j != classLocation) {
					inPracticeData[i][j] = inPracticeData[i][j]/inScales[iter];
					iter++;
				}
			}
		}
	}
	
	void convertTestData() {
		for(int i = 0; i < inTestData.length; i++) {
			int iter = 0;
			for(int j = 0; j < inTestData[0].length; j++) {
				if(j != classLocation) {
					inTestData[i][j] = inTestData[i][j]/inScales[iter];
					iter++;
				}
			}
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
	
	// Function to set the practice data for the nueral network
	public void setPracticeData(float[][] d) {
		inPracticeData = d;
	}
	
	// Function to set the test data for the nueral network
	public void setTestData(float[][] d) {
		inTestData = d;
	}
	
	// Function to set the class location for the nueral network
	public void setClassLocation(int loc) {
		classLocation = loc;
	}
	
	// Function to set the scales for the nueral network
	public void setScales(float scales[]) {		
		inScales = scales;
	}
	
	// Function to set the output file name for the nueral network
	public void setFileName(Path fn) {
		this.FileName = fn;
	}
	
	// Function to calculate the sigmoid of a function
	float calcSig(float x) {
		float e = (float) Math.exp((double) x);
		float sig = (1/(1+e));
		
		return sig;
	}
}
