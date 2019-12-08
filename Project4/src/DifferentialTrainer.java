import java.util.Random;

public class DifferentialTrainer {

	final static int populationSize = 50;
	final static int numGenerations = 5;
	final static float IM = 5.0f;
	final static float B = 1.0f;
	final static float swapThreshold = 0.5f;
	
	Gene population[];
	
	private int numLayers;
	private int numNodes[];
	
	public float inPracticeData[][];
	public int classLocation;
	
	public float resultArray[];
	
	DifferentialTrainer(int layerCount, int numInputs, int numOutputs, int nodeCount[]) {
		
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
	}
	
	public Gene runClass() {
		float results = 0;
		
		initializePopulation();
		
		for(int i = 0; i < numGenerations; i++) {
			getClassFitnesses();
			runClassDifferential();
		}
		
		getClassFitnesses();
		Gene currentBest = population[0];
		for(int i = 1; i < populationSize; i++) {
			if(population[i].fitness > currentBest.fitness) {
				currentBest = population[i];
			}
		}
		
		return currentBest;
	}
	
	public float runRegress() {
		float results = 0;
		
		return results;
	}
	
	void initializePopulation() {
		population = new Gene[populationSize];
		
		for(int x = 0; x < populationSize; x++) {
			Random r = new Random();
			
			float tempWeightMatrix[][][] = new float[numLayers - 1][][];
			for(int i = 0; i < numLayers - 1; i++) {
				tempWeightMatrix[i] = new float[numNodes[i]][numNodes[i + 1]];
			}
			
			// Iterate through weight matrix
			for(int i = 0; i < tempWeightMatrix.length; i++) {
				for(int j = 0; j < tempWeightMatrix[i].length; j++) {
					for(int k = 0; k < tempWeightMatrix[i][j].length; k++) {
						// Set each location in the weight matrix to be between zero
						// and the initialization multiplier
						tempWeightMatrix[i][j][k] = (r.nextFloat() * IM) - (IM/2);
					}
				}
			}
			
			population[x].weightMatrix = tempWeightMatrix;
		}
	}
	
	void getClassFitnesses() {
		for(int i = 0; i < populationSize; i++) {

			int correct = 0;
			for(int j = 0; j < inPracticeData.length; j++) {
				population[i].activations = inPracticeData[j];
				float result = getResult(population[i].getResults());
				
				if(result == inPracticeData[j][classLocation]) {
					correct++;
				}
			}
			
			float fitness = ((float) correct) / ((float) inPracticeData.length);
			
			population[i].fitness = fitness;
		}
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
		
		return(resultArray[bigIndex]);
	}
	
	void runClassDifferential() {
		Random r = new Random();
		
		for(int x = 0; x < populationSize; x++) {
			int x1 = r.nextInt(populationSize);
			int x2 = r.nextInt(populationSize);
			int x3 = r.nextInt(populationSize);
			
			Gene v = new Gene();
			
			float replacementMatrix[][][] = population[x1].weightMatrix;
			
			for(int i = 0; i < replacementMatrix.length; i++) {
				for(int j = 0; j < replacementMatrix[i].length; j++) {
					for(int k = 0; k < replacementMatrix[i][j].length; k++) {
						if(r.nextFloat() > swapThreshold) {
							float tempValue = B * (population[x2].weightMatrix[i][j][k] - population[x3].weightMatrix[i][j][k]);
							replacementMatrix[i][j][k] += tempValue;
						}
					}
				}
			}
			
			v.weightMatrix = replacementMatrix;
			
			for(int i = 0; i < v.weightMatrix.length; i++) {
				for(int j = 0; j < v.weightMatrix.length; j++) {
					for(int k = 0; k < v.weightMatrix.length; k++) {
						if(r.nextFloat() > swapThreshold) {
							v.weightMatrix[i][j][k] = population[x].weightMatrix[i][j][k];
						}
					}
				}
			}
			
			int correct = 0;
			for(int j = 0; j < inPracticeData.length; j++) {
				v.activations = inPracticeData[j];
				float result = getResult(v.getResults());
				
				if(result == inPracticeData[j][classLocation]) {
					correct++;
				}
			}
			
			v.fitness = ((float) correct) / ((float) inPracticeData.length);
			
			if(v.fitness > population[x].fitness) {
				population[x] = v;
			}
		}
	}
}
