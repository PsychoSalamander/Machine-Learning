import java.util.Random;

public class GeneticTrainer {
	
	final static int populationSize = 50;
	final static int numGenerations = 10;
	final static float swapThreshold = 0.5f;
	final static float IM = 10.0f;
	
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
	
	// Function to train classification
	public Gene runClass() {
		// Initialize the population randomly
		initializePopulation();
		
		// Run the trainer for the prespecified number of generations
		for(int i = 0; i < numGenerations; i++) {
			getClassFitnesses();
			replacePopulation();
		}
		
		// Find the best gene in the population
		getClassFitnesses();
		Gene currentBest = population[0];
		for(int i = 1; i < populationSize; i++) {
			if(population[i].fitness > currentBest.fitness) {
				currentBest = population[i];
			}
		}
		
		return currentBest;
	}
	
	// Function to train regression
	public Gene runRegress() {
		// Initialize the population randomly
		initializePopulation();
		
		// Run the trainer for the prespecified number of generations
		for(int i = 0; i < numGenerations; i++) {
			getRegressFitnesses();
			replacePopulation();
		}
		
		// Find the best gene in the population
		getRegressFitnesses();
		Gene currentBest = population[0];
		for(int i = 1; i < populationSize; i++) {
			if(population[i].fitness > currentBest.fitness) {
				currentBest = population[i];
			}
		}
		
		return currentBest;
	}
	
	// Function to randomly initialize weight matrices
	void initializePopulation() {
		population = new Gene[populationSize];
		
		// create temporary weight matrix
		float tempWeightMatrix[][][] = new float[numLayers - 1][][];
		for(int i = 0; i < numLayers - 1; i++) {
			tempWeightMatrix[i] = new float[numNodes[i]][numNodes[i + 1]];
		}
		
		Random r = new Random();
		
		// Iterate through population and create matrix for each individual
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
	
	// Function to get the fitnesses for each member of the population
	void getClassFitnesses() {
		populationFitness = 0;
		// for each individual in the population
		for(int i = 0; i < populationSize; i++) {
			
			// iterate through practice data
			int correct = 0;
			for(int j = 0; j < inPracticeData.length; j++) {
				
				// Get activations to get results from
				float[] activations = new float[inPracticeData[0].length-1];
				int iter = 0;
				for(int k = 0; k < inPracticeData[0].length; k++) {
					if(k != classLocation) {
						activations[iter] = inPracticeData[j][k];
					}
				}
				
				// get result from weight matrix
				population[i].activations = activations;
				float result = getResult(population[i].getResults());
				
				// check correctness of result
				if(result == inPracticeData[j][classLocation]) {
					correct++;
				}
			}
			
			// set gene fitness
			float fitness = ((float) correct) / ((float) inPracticeData.length) + .01f;
			
			populationFitness += fitness;
			population[i].fitness = fitness;
		}
	}
	
	// Function to get the fitnesses for each member of the population
	void getRegressFitnesses() {
		populationFitness = 0;
		// for each individual in the population
		for(int i = 0; i < populationSize; i++) {

			// iterate through practice data
			float error = 0;
			for(int j = 0; j < inPracticeData.length; j++) {
				
				// Get activations to get results from
				float[] activations = new float[inPracticeData[0].length-1];
				int iter = 0;
				for(int k = 0; k < inPracticeData[0].length; k++) {
					if(k != classLocation) {
						activations[iter] = inPracticeData[j][k];
					}
				}
				
				// get result from weight matrix
				population[i].activations = activations;
				float result = getResult(population[i].getResults());
				
				// check error of result
				float e = result - inPracticeData[j][classLocation];
				e = e * e;
				
				error += e;
			}
			
			// average error
			error = error / inPracticeData.length;
			
			// set gene fitness
			float fitness = 1 / error;
			
			populationFitness += fitness;
			population[i].fitness = fitness;
		}
	}
	
	// Function to get results from result array
	float getResult(float results[]) {
		int bigIndex = 0;
		float bigEstimate = 0;
		
		// iterate through result and check which value is largest
		for(int j = 0; j < results.length; j++) {
			if(results[j] > bigEstimate) {
				bigEstimate = results[j];
				bigIndex = j; 
			}
		}
		
		// Send result back
		return(resultArray[bigIndex]);
	}
	
	// Genetic algorithm function
	void replacePopulation() {
		Random r = new Random();
		
		// array to indicate which parents have already been selected
		boolean selected[] = new boolean[populationSize];
		
		// Weighted Roulette Selection
		for(int x = 0; x < populationSize / 2; x++) {
			int available = 0;
			float totalFitness = 0;
			
			// cound available parents
			for(int i = 0; i < populationSize; i++) {
				if(!selected[i]) {
					available++;
					totalFitness += population[i].fitness;
				}
			}
			
			// probability and availability arrays
			float cumulativeFitnesses[] = new float[available];
			int availableParents[] = new int[available];
			
			// Set probabilities
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
			
			// Select parents
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
			
			// create child weight matrices
			float weightMatrix1[][][] = population[parent1].weightMatrix;
			float weightMatrix2[][][] = population[parent2].weightMatrix;
			
			System.out.println("Selected Parents: " + parent1 + " & " + parent2);
			System.out.println("Parents fitness: " + population[parent1].fitness + " & " + population[parent2].fitness);
			
			// swap values
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
			
			// replace parents
			population[parent1].weightMatrix = weightMatrix1;
			population[parent2].weightMatrix = weightMatrix2;
		}
	}
}