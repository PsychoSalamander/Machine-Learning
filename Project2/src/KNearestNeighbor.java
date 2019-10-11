public class KNearestNeighbor extends NearestNeighbor {

public int height = 0;
public int width = 0;
public int kVal = 0;
public float classif = 0;		// variable for the classification value in the regression method
public float classification = 0;	// variable to save the classification value returned by regression
    KNearestNeighbor() {
	
    }

    // Run classification based on K nearest neighbors
    public void runClass(int K) {
    	int classCount[] = new int[100];
    	
    	int correct = 0;
    	int incorrect = 0;
    	for(int x = 0; x < inTestData.length; x++) {
    		int nearestNeighbors[] = new int[K];
    		
    		// Get test point
    		float[] Xi = inTestData[x];
    		
    		// Set all nearest neighbor indices to -1
    		for(int i = 0; i < K; i++) {
    			nearestNeighbors[i] = -1;
    		}
    		
    		// Find K nearest neighbors
    		for(int i = 0; i < K; i++) {
    			float minDist = 1000000;
				int minIndex = 0;
				
				// Check each of the data points in the practice set
    			for(int j = 0; j < inPracticeData.length; j++) {
    				
    				// Check to find the closest point that has not already been used
    				if((getDistance(Xi, inPracticeData[j]) < minDist) && !inArray(j, nearestNeighbors)) {
    					minDist = getDistance(Xi, inPracticeData[j]);
    					minIndex = j;
    				}
    			}
    			
    			// Add nearest unused neighbor to array
    			nearestNeighbors[i] = minIndex;
    		}
    		
    		// Count the number of occurences of each class in the nearest neighbors
    		for(int i = 0; i < K; i++) {
    			classCount[(int) inPracticeData[nearestNeighbors[i]][classLocation]] += 1;
    		}
    		
    		// Check to find which class occurs the most
    		int highestValue = 0;
    		int highClass = 0;
    		for(int i = 0; i < classCount.length; i++) {
    			if(classCount[i] > highestValue) {
    				highClass = i;
    				highestValue = classCount[i];
    			}
    		}
    		
    		// Use highest occurrence class as guess
    		int classGuess = highClass;
    		
    		// Check guess to see if it's correct
    		if(classGuess == (int) Xi[classLocation]) {
    			correct += 1;
    		} else {
    			incorrect += 1;
    		}
    	}
    	
    	// Get percent correctness and print it to console
    	System.out.println("Percent correctness with " + K + " nearest neighbors: " + (correct/(incorrect+correct)));
    }

    // Run regression based on K nearest neighbors
    public void runRegress(int K) {
    	float accuracy = 0;
    	for(int x = 0; x < inTestData.length; x++) {
    		int nearestNeighbors[] = new int[K];
    		
    		// Get test Point
    		float[] Xi = inTestData[x];
    		
    		// Set all nearest neighbor indices to -1
    		for(int i = 0; i < K; i++) {
    			nearestNeighbors[i] = -1;
    		}
    		
    		// Find K nearest neighbors
    		for(int i = 0; i < K; i++) {
    			float minDist = 1000000;
				int minIndex = 0;
				
				// Check each of the data points in the practice set
    			for(int j = 0; j < inPracticeData.length; j++) {
    				
    				// Check to find the closest point that has not already been used
    				if((getDistance(Xi, inPracticeData[j]) < minDist) && !inArray(j, nearestNeighbors)) {
    					minDist = getDistance(Xi, inPracticeData[j]);
    					minIndex = j;
    				}
    			}
    			
    			// Add nearest unused neighbor to array
    			nearestNeighbors[i] = minIndex;
    		}
    		
    		// Sum the values of the nearest neighbor
    		float practiceSum = 0;
    		for(int i = 0; i < K; i++) {
    			practiceSum += inPracticeData[nearestNeighbors[i]][classLocation];
    		}
    		
    		// Take the average of the sum, giving you you're estimated value
    		float regressionEst = (1/K)*practiceSum;
    		
    		// Add difference to accuracy value
    		accuracy += Math.abs(Xi[classLocation] - regressionEst);
    	}
    	
    	// Take average of differences, and print it
    	System.out.println("Estimate was off by an average of: " + (accuracy / inTestData.length));
    }
    
    // See if given element is in given array
    public boolean inArray(int element, int[] array) {
    	for(int i : array) {
    		if(element == i) {
    			return true;
    		}
    	}
    	
    	return false;
    }
}
