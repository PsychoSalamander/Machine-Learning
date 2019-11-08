import java.util.Random;

public class PAMNearestNeighbor extends NearestNeighbor {

	PAMNearestNeighbor() {
		
	}
	
	float tolerance = 0.5f;
	
	KNearestNeighbor KNN = new KNearestNeighbor();
	
	void runClass(int K) {
		// Get medoid indices from function
		int medoids[] = getMedoids(inPracticeData, K);
		
		// Fill in "practice" data with medoid data
		float outPracticeData[][] = new float[medoids.length][inPracticeData[0].length];
		for(int i = 0; i < medoids.length; i++) {
			outPracticeData[i] = inPracticeData[medoids[i]];
		}
		
		// Set up KNN to test medoids
		KNN.setClassLocation(this.classLocation);
		KNN.setPracticeData(outPracticeData);
		KNN.setTestData(inTestData);
		
		// Run classification test
		KNN.runClass(1);
	}
	
	void runRegress(int K) {
		// Get medoids indices from function
		int medoids[] = getMedoids(inPracticeData, K);
		
		// Fill in "practice" data with medoid data
		float outPracticeData[][] = new float[medoids.length][inPracticeData[0].length];
		for(int i = 0; i < medoids.length; i++) {
			outPracticeData[i] = inPracticeData[medoids[i]];
		}
		
		// Set up KNN to test medoids
		KNN.setClassLocation(this.classLocation);
		KNN.setPracticeData(outPracticeData);
		KNN.setTestData(inTestData);
		
		// Run regression test
		KNN.runRegress(1);
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
								newDistortion += getDistance(D[newMedoid], D[d]);
							}
						}
						// Check to see if new medoid has less distortion than original medoid						
						if(newDistortion < distortions[i] - tolerance) {
							medoids[i] = newMedoid;
							distortions[i] = newDistortion;
							movement = true;
						} else {
							
						}
					}
				}
			}
		}
		
		return medoids;
	}
}
