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
		
		// Run test data set through NN
		int correct = 0;
		for(int i = 0; i < inTestData.length; i++) {
			// Get inital activations and results
			float activations[] = getActivations(inTestData[i]);
//			float results[][] = getResults(activations);
			
			// Get estimate from results
			int bigIndex = 0;
			float bigEstimate = 0;
			
//			for(int j = 0; j < results[results.length-1].length; j++) {
//				if(results[results.length - 1][j] > bigEstimate) {
//					bigEstimate = results[results.length - 1][j]; bigIndex = j; 
//				} 
//			}
			 
			
			// Check correctness
			System.out.println(bigIndex);
			System.out.println(classKey.length);
			System.out.println(" ");
			if(classKey[bigIndex] == inTestData[i][classLocation]) {
				correct++;
			}
		}
		
		// Calculate average correctness of classification
		float accuracy = ((float)correct / inTestData.length) * 100f;
		
		// Output final weight matrix and results to a file
		try {
			fw.append("Final Weight Matrix:\n");
			
			for(int i = 0; i < weightMatrix.length; i++) {
				for(int j = 0; j < weightMatrix[i].length; j++) {
					fw.append("[");
					for(int k = 0; k < weightMatrix[i][j].length; k++) {
						fw.append(weightMatrix[i][j][k] + "");
						if(k != weightMatrix[i][j].length - 1) {
							 fw.append(", ");
						}
					}
					fw.append("]\n");
				}
				fw.append("\n");
			}
			
			fw.append(" \n\nAccuracy: " + accuracy + "%");
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Print accuracy to console
		System.out.println(accuracy + "%");
		return accuracy;
	}
	
	// Function to run regression tests
	public float runRegress() {
		
		// Run test set through NN
		float meanErr = 0;
		for(int i = 0; i < 1; i++) {
			// Get activations and results
			float activations[] = getActivations(inTestData[i]);
//			float results[][] = getResults(activations);
			
			// Get estimate from results
			int bigIndex = 0;
			float bigEstimate = 0;
//			for(int j = 0; j < results.length; j++) {
//				if(results[results.length - 1][j] > bigEstimate) {
//					bigEstimate = results[results.length - 1][j];
//					bigIndex = j;
//				}
//			}
//			float result = results[results.length - 1][bigIndex];
			
			// Calculate error of result
//			float err = (float) Math.abs(inTestData[i][classLocation] - result);
			
//			meanErr += err;
		}
		
		// Take average of error
		meanErr = meanErr / inTestData.length;
		
		// Print error to console
		System.out.println("Error of Regression Test: " + meanErr);
		return meanErr;
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
		
		// Print scales to console
		System.out.print("scale: [");
		for(int i = 0; i < scales.length; i++) {
			System.out.print(scales[i] + ", ");
		}
		System.out.println("]");
		
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
