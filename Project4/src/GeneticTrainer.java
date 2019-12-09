import java.util.Random;

public class GeneticTrainer {
	
	final static int populationSize = 50;
	final static int numGenerations = 5;
	final static float swapThreshold = 0.5f;
	final static float IM = 5.0f;
	
	Gene population[];
	float populationFitness;
	
	private int numLayers;
	private int numNodes[];
	
	public float inPracticeData[][];
	public int classLocation;
	
	public float resultArray[];
	
	GeneticTrainer(int layerCount, int numInputs, int numOutputs, int nodeCount[]) {

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
		initializePopulation();
		
		for(int i = 0; i < numGenerations; i++) {
			getClassFitnesses();
			replacePopulation();
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
	
	public Gene runRegress() {
		initializePopulation();
		
		for(int i = 0; i < numGenerations; i++) {
			getRegressFitnesses();
			replacePopulation();
		}
		
		getRegressFitnesses();
		Gene currentBest = population[0];
		for(int i = 1; i < populationSize; i++) {
			if(population[i].fitness > currentBest.fitness) {
				currentBest = population[i];
			}
		}
		
		return currentBest;
	}
	
	void initializePopulation() {
		population = new Gene[populationSize];
		
		float tempWeightMatrix[][][] = new float[numLayers - 1][][];
		for(int i = 0; i < numLayers - 1; i++) {
			tempWeightMatrix[i] = new float[numNodes[i]][numNodes[i + 1]];
		}
		
		Random r = new Random();
		
		for(int x = 0; x < populationSize; x++) {
			population[x] = new Gene();
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
		populationFitness = 0;
		for(int i = 0; i < populationSize; i++) {

			int correct = 0;
			for(int j = 0; j < inPracticeData.length; j++) {
				
				float[] activations = new float[inPracticeData[0].length-1];
				int iter = 0;
				for(int k = 0; k < inPracticeData[0].length; k++) {
					if(k != classLocation) {
						activations[iter] = inPracticeData[j][k];
					}
				}
				
				population[i].activations = activations;
				float result = getResult(population[i].getResults());
				
				if(result == inPracticeData[j][classLocation]) {
					correct++;
				}
			}
			
			float fitness = ((float) correct) / ((float) inPracticeData.length) + .01f;
			
			populationFitness += fitness;
			population[i].fitness = fitness;
		}
	}
	
	void getRegressFitnesses() {
		populationFitness = 0;
		for(int i = 0; i < populationSize; i++) {

			float error = 0;
			for(int j = 0; j < inPracticeData.length; j++) {
				
				float[] activations = new float[inPracticeData[0].length-1];
				int iter = 0;
				for(int k = 0; k < inPracticeData[0].length; k++) {
					if(k != classLocation) {
						activations[iter] = inPracticeData[j][k];
					}
				}
				
				population[i].activations = activations;
				float result = getResult(population[i].getResults());
				
				float e = result - inPracticeData[j][classLocation];
				e = e * e;
				
				error += e;
			}
			
			error = error / inPracticeData.length;
			
			float fitness = 1 / error;
			
			populationFitness += fitness;
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
	
	void replacePopulation() {
		System.out.println("Replacing Population");
		Random r = new Random();
		
		boolean selected[] = new boolean[populationSize];
		
		// Weighted Roulette Selection
		for(int x = 0; x < populationSize / 2; x++) {
			System.out.println(x + "/" + populationSize / 2);
			int available = 0;
			float totalFitness = 0;
			for(int i = 0; i < populationSize; i++) {
				if(!selected[i]) {
					available++;
					totalFitness += population[i].fitness;
				}
			}
			
			float cumulativeFitnesses[] = new float[available];
			int availableParents[] = new int[available];
			
			// Set Weights
			float currentValue = 0;
			int iter = 0;
			for(int i = 0; i < populationSize; i++) {
				if(!selected[i]) {
					cumulativeFitnesses[iter] = currentValue + (population[i].fitness / totalFitness);
					currentValue += (population[i].fitness / totalFitness);
					availableParents[iter] = i;
					iter++;
				}
			}
			
			int parent1 = 0;
			int parent2 = 0;
			
			for(int p = 0; p < 2; p++) {
				boolean foundParent = false;
				while(!foundParent) {
					float selection = r.nextFloat();
					
					boolean select = false;
					int tryIndex = 0;
					while(!select) {
						if(selection < cumulativeFitnesses[tryIndex]) {
							select = true;
						} else {
							tryIndex++;
						}
					}
					
					tryIndex = availableParents[tryIndex];
					
					foundParent = true;
					selected[tryIndex] = true;
						
					if(p == 0) {
						parent1 = tryIndex;
					} else {
						parent2 = tryIndex;
					}
				}
			}
			
			float weightMatrix1[][][] = population[parent1].weightMatrix;
			float weightMatrix2[][][] = population[parent2].weightMatrix;
			
			for(int i = 0; i < weightMatrix1.length; i++) {
				for(int j = 0; j < weightMatrix1[i].length; j++) {
					for(int k = 0; k < weightMatrix1[i][j].length; k++) {
						if(r.nextFloat() > swapThreshold) {
							float tempValue = weightMatrix1[i][j][k];
							weightMatrix1[i][j][k] = weightMatrix2[i][j][k];
							weightMatrix2[i][j][k] = tempValue;
						}
					}
				}
			}
			
			population[parent1].weightMatrix = weightMatrix1;
			population[parent2].weightMatrix = weightMatrix2;
		}
		
		System.out.println("Finished replacement");
	}
}