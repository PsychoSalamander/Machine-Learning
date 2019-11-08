import java.util.Arrays;
import java.util.Random;

public class KMeansNearestNeighbor extends NearestNeighbor {

	KMeansNearestNeighbor() {
		
	}
	
	KNearestNeighbor KNN = new KNearestNeighbor();
	
	// Parameter to define how close the mean has to be to the actual mean to accept
	private float MT = 0.0f;
	
	void runClass(int K) {
		
		// Get the means for the data set
		float means[][] = getMeans(inPracticeData, K);
		
		for(int i = 0; i < K; i++) {
			float minDist = 1000000;
			int minIndex = 0;
			
			// Check each of the data points in the practice set
			for(int j = 0; j < inPracticeData.length; j++) {
				
				// Check to find the closest point that has not already been used
				if((getDistance(means[i], inPracticeData[j]) < minDist)) {
					minDist = getDistance(means[i], inPracticeData[j]);
					minIndex = j;
				}
			}
			
			// Set class for mean equal to the closest practice point
			means[i][classLocation] = inPracticeData[minIndex][classLocation];
		}
		
		// Setup K nearest neighbor with means and test data
		KNN.setClassLocation(this.classLocation);
		KNN.setPracticeData(means);
		KNN.setTestData(this.inTestData);
		
		// run classification for testData against means
		KNN.runClass(1);
	}
	
	void runRegress(int K) {
		float means[][] = getMeans(inPracticeData, K);
		
		for(int i = 0; i < K; i++) {
			float minDist = 1000000;
			int minIndex = 0;
			
			// Check each of the data points in the practice set
			for(int j = 0; j < inPracticeData.length; j++) {
				
				// Check to find the closest point that has not already been used
				if((getDistance(means[i], inPracticeData[j]) < minDist)) {
					minDist = getDistance(means[i], inPracticeData[j]);
					minIndex = j;
				}
			}
			
			// Set regression value for mean equal to the closest practice point's regression value
			means[i][classLocation] = inPracticeData[minIndex][classLocation];
		}
		
		// Setup K nearest neighbor with means and test data
		KNN.setClassLocation(this.classLocation);
		KNN.setPracticeData(means);
		KNN.setTestData(this.inTestData);
				
		// run regression for testData against means
		KNN.runRegress(1);
	}
	
	float[][] getMeans(float[][] D, int K) {
		float means[][] = new float[K][D[0].length];

		// Randomly Generate Mean Start Points
		for(int i = 0; i < D[0].length; i++) {
			if(i != classLocation) {
				float randMin = 1000000;
				float randMax = -1000000;
				
				// Get min and max for the feature
				for(int j = 0; j < D.length; j++) {
					if(D[j][i] > randMax) {
						randMax = D[j][i];
					}
					if(D[j][i] < randMin) {
						randMin = D[j][i];
					}
				}
				
				// Generate a random number based on the range of the feature
				for(int j = 0; j < means.length; j++) {
					Random r = new Random();
					
					means[j][i] = (r.nextFloat()*(randMax - randMin)) + randMin;
				}
			}
		}
		
		Boolean movement = true;
		// Find means until they don't move anymore
		while(movement) {
			int cluster[] = new int[D.length];
			int clusterCount[] = new int[K];
			for(int x = 0; x < D.length; x++) {
				float Xi[] = D[x];
				
				// Get closest mean, add data point to cluster
				int minMean = 0;
				float minDist = 1000000;
				for(int i = 0; i < means.length; i++) {
					if(getDistance(Xi, means[i]) < minDist) {
						minDist = getDistance(Xi, means[i]);
						minMean = i;
					}
				}
				
				cluster[x] = minMean;
				clusterCount[minMean] += 1;
			}
			
			// Add up examples in each cluster
			float newMeans[][] = new float[means.length][means[0].length];
			for(int x = 0; x < cluster.length; x++) {
				for(int i = 0; i < D[0].length; i++) {
					if(i != classLocation) {
						newMeans[cluster[x]][i] += D[x][i];
					}
				}
			}
			
			
			printArray(clusterCount);
			// Divide by number of examples to get means
			for(int i = 0; i < newMeans.length; i++) {
				for(int j = 0; j < newMeans[0].length; j++) {
					if(j != classLocation) {
						if(clusterCount[i] != 0) {
							newMeans[i][j] = newMeans[i][j] / clusterCount[i];
						} else {
							newMeans[i][j] = means[i][j];
						}
					}
				}
			}
			
			// Check if means are within movement threshold
			movement = false;
			for(int i = 0; i < newMeans.length; i++) {
				for(int j = 0; j < newMeans[0].length; j++) {
					if(j != classLocation) {
						if((newMeans[i][j] < means[i][j] + MT) && (newMeans[i][j] > means[i][j] - MT)) {
							movement = true;
							break;
						}
					}
				}
			}
			
			means = newMeans;
		}
		
		return means;
	}
	
	int[] getClasses() {
		int seen[] = new int[100];
		int numClasses = 0;
		
		for(int i = 0; i < inPracticeData.length; i++) {
			if(seen[(int)inPracticeData[i][classLocation]] != 1) {
				numClasses += 1;
				seen[(int)inPracticeData[i][classLocation]] = 1;
			}
		}
		
		int classes[] = new int[numClasses];
		
		seen = new int[100];
		int iter = 0;
		for(int i = 0; i < inPracticeData.length; i++) {
			if(seen[(int)inPracticeData[i][classLocation]] != 1) {
				classes[iter] = (int)inPracticeData[i][classLocation];
				seen[(int)inPracticeData[i][classLocation]] = 1;
				iter += 1;
			}
		}
		
		return classes;
	}
	
    public void printArray(int[] array) {
		System.out.println("Array Visualized:");
		
		for (int row = 0; row < array.length; row++) {
			System.out.println(Arrays.toString(array)); 
		}
    }
}
