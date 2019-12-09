import java.util.Random;

public class ParticleTrainer {
	
	final static int populationSize = 50;
	final static int numGenerations = 5;
	final static float IM = 5.0f;
	final static float U = 5.0f;
	
	Gene population[];
	Gene currentBest;
	
	private int numLayers;
	private int numNodes[];
	
	public float inPracticeData[][];
	public int classLocation;
	
	public float resultArray[];
	
	ParticleTrainer(int layerCount, int numInputs, int numOutputs, int nodeCount[]) {

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
		initializeVelocities();
		
		currentBest = population[0];
		for(int i = 1; i < populationSize; i++) {
			if(population[i].fitness > currentBest.fitness) {
				currentBest = population[i];
			}
		}
		
		for(int i = 0; i < numGenerations; i++) {
			runClassParticles();
		}
		
		return currentBest;
	}
	
	public Gene runRegress() {
		initializePopulation();
		initializeVelocities();
		
		currentBest = population[0];
		for(int i = 1; i < populationSize; i++) {
			if(population[i].fitness > currentBest.fitness) {
				currentBest = population[i];
			}
		}
		
		for(int i = 0; i < numGenerations; i++) {
			runRegressParticles();
		}
		
		return currentBest;
	}
	
	void initializePopulation() {
		population = new Gene[populationSize];
		
		for(int x = 0; x < populationSize; x++) {
			population[x] = new Gene();
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
			population[x].bestKnown = tempWeightMatrix;
		}
		
		getClassFitnesses();
	}
	
	void initializeVelocities() {
		Random r = new Random();
		
		for(int x = 0; x < populationSize; x++) {
			population[x].velocityMatrix = population[x].weightMatrix;
			
			for(int i = 0; i < population[x].velocityMatrix.length; i++) {
				for(int j = 0; j < population[x].velocityMatrix[i].length; j++) {
					for(int k = 0; k < population[x].velocityMatrix[i][j].length; k++) {
						population[x].velocityMatrix[i][j][k] = (r.nextFloat() - 0.5f) * U;
					}
				}
			}
		}
	}
	
	void getClassFitnesses() {
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
			
			float fitness = ((float) correct) / ((float) inPracticeData.length);
			
			population[i].fitness = fitness;
			population[i].bestKnownFitness = fitness;
		}
	}
	
	void getRegressFitnesses() {
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
			
			population[i].fitness = fitness;
			population[i].bestKnownFitness = fitness;
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
	
	void runClassParticles() {
		Random r = new Random();
		
		for(int x = 0; x < populationSize; x++) {
			for(int i = 0; i < population[x].velocityMatrix.length; i++) {
				for(int j = 0; j < population[x].velocityMatrix[i].length; j++) {
					for(int k = 0; k < population[x].velocityMatrix[i][j].length; k++) {
						float Rp = r.nextFloat() * U;
						float Rg = r.nextFloat() * U;
						
						float newVelocity = population[x].velocityMatrix[i][j][k] + Rp*(population[x].bestKnown[i][j][k] - population[x].weightMatrix[i][j][k]) + Rg*(currentBest.weightMatrix[i][j][k] - population[x].weightMatrix[i][j][k]);
						population[x].velocityMatrix[i][j][k] = newVelocity;
						
						population[x].weightMatrix[i][j][k] += population[x].velocityMatrix[i][j][k];
					}
				}
			}
			
			int correct = 0;
			for(int j = 0; j < inPracticeData.length; j++) {
				
				float[] activations = new float[inPracticeData[0].length-1];
				int iter = 0;
				for(int k = 0; k < inPracticeData[0].length; k++) {
					if(k != classLocation) {
						activations[iter] = inPracticeData[j][k];
					}
				}
				
				population[x].activations = activations;
				float result = getResult(population[x].getResults());
				
				if(result == inPracticeData[j][classLocation]) {
					correct++;
				}
			}
			
			float fitness = ((float) correct) / ((float) inPracticeData.length);
			population[x].fitness = fitness;
			
			if(population[x].fitness > population[x].bestKnownFitness) {
				population[x].bestKnown = population[x].weightMatrix;
				population[x].bestKnownFitness = fitness;
			}
			
			if(population[x].fitness > currentBest.fitness) {
				currentBest = population[x];
			}
		}
	}
	
	void runRegressParticles() {
		Random r = new Random();
		
		for(int x = 0; x < populationSize; x++) {
			for(int i = 0; i < population[x].velocityMatrix.length; i++) {
				for(int j = 0; j < population[x].velocityMatrix[i].length; j++) {
					for(int k = 0; k < population[x].velocityMatrix[i][j].length; k++) {
						float Rp = r.nextFloat() * U;
						float Rg = r.nextFloat() * U;
						
						float newVelocity = population[x].velocityMatrix[i][j][k] + Rp*(population[x].bestKnown[i][j][k] - population[x].weightMatrix[i][j][k]) + Rg*(currentBest.weightMatrix[i][j][k] - population[x].weightMatrix[i][j][k]);
						population[x].velocityMatrix[i][j][k] = newVelocity;
						
						population[x].weightMatrix[i][j][k] += population[x].velocityMatrix[i][j][k];
					}
				}
			}
			
			float error = 0;
			for(int j = 0; j < inPracticeData.length; j++) {
				
				float[] activations = new float[inPracticeData[0].length-1];
				int iter = 0;
				for(int k = 0; k < inPracticeData[0].length; k++) {
					if(k != classLocation) {
						activations[iter] = inPracticeData[j][k];
					}
				}
				
				population[x].activations = activations;
				float result = getResult(population[x].getResults());
				
				float e = result - inPracticeData[j][classLocation];
				e = e * e;
				
				error += e;
			}
			
			error = error / inPracticeData.length;
			
			float fitness = 1 / error;
			
			population[x].fitness = fitness;
			
			if(population[x].fitness > population[x].bestKnownFitness) {
				population[x].bestKnown = population[x].weightMatrix;
				population[x].bestKnownFitness = fitness;
			}
			
			if(population[x].fitness > currentBest.fitness) {
				currentBest = population[x];
			}
		}
	}
}
