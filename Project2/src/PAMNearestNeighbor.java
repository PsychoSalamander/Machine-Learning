import java.util.Random;

public class PAMNearestNeighbor extends NearestNeighbor {

	PAMNearestNeighbor() {
		
	}
	int numClasses = 0;
	
	void runClass() {
		getClasses();
		
		int medoids[] = getMedoids(inPracticeData, numClasses);
		
		float correct = 0;
		float incorrect = 0;
		for(int x = 0; x < inTestData.length; x++) {
			float[] Xi = inTestData[x];
			
			int minMedoid = 0;
			float minDist = 1000000;
			for(int i = 0; i < medoids.length; i++) {
				if(getDistance(Xi, inTestData[medoids[i]]) < minDist) {
					minDist = getDistance(Xi, inTestData[medoids[i]]);
					minMedoid = i;
				}
			}
			
			int classification = (int) inTestData[medoids[minMedoid]][classLocation];
			
			if(classification == Xi[classLocation]) {
				correct += 1;
			} else {
				incorrect += 1;
			}
		}
		
		System.out.println(correct/(incorrect+correct));
	}
	
	void runRegress() {
		System.out.println("Empty Implementation!");
	}
	
	int[] getMedoids(float[][] D, int K) {
		int medoids[] = new int[K];
		
		for(int i = 0; i < K; i++) {
			Random r = new Random();
			medoids[i] = r.nextInt(D.length);
		}
		
		boolean movement = true;
		while(movement) {
			// Put data points in clusters
			movement = false;
			int cluster[] = new int[D.length];
			int clusterCount[] = new int[K];
			for(int x = 0; x < D.length; x++) {
				float Xi[] = D[x];
				
				// Get closest medoid, add data point to cluster
				int minMedoid = 0;
				float minDist = 1000000;
				for(int i = 0; i < medoids.length; i++) {
					if(getDistance(Xi, D[medoids[i]]) < minDist) {
						minDist = getDistance(Xi, D[medoids[i]]);
						minMedoid = i;
					}
				}
				
				cluster[x] = minMedoid;
				clusterCount[minMedoid] += 1;
			}
			
			// Calculate Medoid Distortion
			float distortions[] = new float[K];
			for(int i = 0; i < D.length; i++) {
				distortions[cluster[i]] += getDistance(D[medoids[cluster[i]]], D[i]);
			}
			
			// Iterate over all clusters
			for(int i = 0; i < K; i++) {
				// Iterate over all data points
				for(int j = 0; j < D.length; j++) {
					// Check that new data point is not the same as current medoid
					int newMedoid = j;
					if(j != medoids[i]) {
						// Calculate distortion of new medoid
						float newDistortion = 0;
						for(int d = 0; d < D.length; d++) {
							if(cluster[d] == i) {
								newDistortion += getDistance(D[newMedoid], D[i]);
							}
						}
						// Check to see if new medoid has less distortion than original medoid
						if(newDistortion < distortions[i]) {
							medoids[i] = newMedoid;
							distortions[i] = newDistortion;
							movement = true;
						}
					}
				}
			}
		}
		
		return medoids;
	}
	
	int[] getClasses() {
		int seen[] = new int[100];
		
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
}
