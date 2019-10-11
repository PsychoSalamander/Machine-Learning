import java.util.Random;

public class KMeansNearestNeighbor extends NearestNeighbor {

	KMeansNearestNeighbor() {
		
	}
	
	// Parameter to define how close the mean has to be to the actual mean to accept
	private float MT = 0.1f;
	
	void runClass() {
		
		// Get number of classes and the means for the dataset
		int numClasses = 5;
		float means[][] = getMeans(inPracticeData, numClasses);
		int clusterClass[] = new int[means.length];
		
		// Assign a class to each mean cluster
		int usedClusters[] = new int[means.length];
		for(int i = 0; i < numClasses; i++) {
			
			// Initialize point search variables
			boolean foundDataPoint = false;
			int iter = 0;
			float[] dp = inPracticeData[iter];
			
			// Find point that has a class that hasn't been seen yet
			while(!foundDataPoint) {
				if(dp[dp.length - 1] == i) {
					foundDataPoint = true;
				} else if(iter == inPracticeData.length - 1) {
					System.out.println("Could not find data for class: " + i);
				} else {				
					dp = inPracticeData[iter];
					iter++;
				}
			}
			
			float minDist = 1000000;
			int minCluster = 0;
			for(int j = 0; j < means.length; j++) {
				if((getDistance(dp, means[j]) < minDist) && (usedClusters[j] == 0)) {
					minDist = getDistance(dp, means[i]);
					minCluster = j;
				}
			}
			
			usedClusters[minCluster] = 1;
			clusterClass[minCluster] = (int) dp[dp.length-1];
		}
		
		// Check the nearest cluster to each point in the test set, and check to see if the class matches.
		int correct = 0;
		int incorrect = 0;
		for(int i = 0; i < inTestData.length; i++) {
			float dp[] = inTestData[i];
			
			float minDist = 1000000;
			int minCluster = 0;
			for(int j = 0; j < means.length; j++) {
				if((getDistance(dp, means[j]) < minDist)) {
					minDist = getDistance(dp, means[i]);
					minCluster = j;
				}
			}
			
			if(clusterClass[minCluster] == inTestData[i][inTestData.length-1]) {
				correct += 1;
			} else {
				incorrect += 1;
			}
		}
		
		// Print number of classes that are correct
		System.out.println(correct/(incorrect+correct));
	}
	
	void runRegress() {
		System.out.println("Empty Implementation!");
	}
	
	float[][] getMeans(float[][] D, int K) {
		float means[][] = new float[K][D[0].length];

		// Randomly Generate Mean Start Points
		for(int i = 0; i < D[0].length; i++) {
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
					newMeans[cluster[x]][i] += D[x][i];
				}
			}
			
			// Divide by number of examples to get means
			for(int i = 0; i < newMeans.length; i++) {
				for(int j = 0; j < newMeans[0].length; j++) {
					newMeans[i][j] = newMeans[i][j] / clusterCount[i];
				}
			}
			
			// Check if means are within movement threshold
			movement = false;
			for(int i = 0; i < newMeans.length; i++) {
				for(int j = 0; j < newMeans[0].length; j++) {
					if((newMeans[i][j] < means[i][j] + MT) && (newMeans[i][j] > means[i][j] - MT)) {
						movement = true;
						break;
					}
				}
			}
			
			means = newMeans;
		}
		
		return means;
	}
}
