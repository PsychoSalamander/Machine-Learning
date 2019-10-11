public class KNearestNeighbor extends NearestNeighbor {

public int height = 0;
public int width = 0;
public int kVal = 0;
public float classif = 0;		// variable for the classification value in the regression method
public float classification = 0;	// variable to save the classification value returned by regression
    KNearestNeighbor() {
	
    }

    public void runClass() {
	System.out.println("Empty Implementation!");
    }

    public void neighbor(float point[], float data[][], int k) {

	// get dimensions of the array
	height = data.length;
	width = data[0].length;
	kVal = k;
	float[] pointComp = point.clone();
	// instantiating variables
	float[][] neighborDist = new float[height][width+1]; 	// distance from test point of the data element
	float neighbor[][] = new float[height][width+1];	   // dummy clone array of neighborDist // dont really need this in the end
	float dataOrig[][] = data.clone();		   	// clone of the inputed data

	// shift through the array to find neighbor distance
	for (int y = 0; y < height; y++) {
	    for(int x = 0; x <= width + 1; x++) {		// Leaves column #1 open to put the distance calculation
		if(x == 0) {					
		    neighborDist[y][x] = dataOrig[y][x];	// copies the passed in array into new array
		    neighbor[y][x] = dataOrig[y][x];		// the way this is set up the "neighbor" array is just for sanity check. it is not needed now for final results
		    
		}
		if( x != 1 && x != 0 && x != (width +1)) {	// array is offset so "x-1" is used to put the original data in the new array as if it was shifted by 1
		    neighborDist[y][x] = dataOrig[y][x-1];	// still leaves column 1 empty for the distance data

		    neighbor[y][x] = dataOrig[y][x-1];
		    
		}

	    }

		float distance = getDistance(pointComp, dataOrig, y); 	// gets the distance calculation
		neighborDist[y][1] = distance;				// saves the distance
		neighbor[y][1] = distance;
		
	}
	
	neighborDist = sortDistance(neighborDist, height, width); 	// sorts the distances in ascending order
	if(kVal < height) {
	    classification = whatNeighbor(kVal, neighborDist);		// if k value is too large for the total arrray doesnt return a calssification
	}
	
	// print statements to verify methods can delete
	System.out.println("sorted distance");
	for (int b = 0; b < height; b++) {
	    
		System.out.println(neighborDist[b][1]);
	  
	}
	System.out.print("\n");
	System.out.println("unsorted distance");
	for (int b = 0; b < height; b++) {
	    for (int n = 0; n < width + 1; n++) {
		System.out.println(neighbor[b][n]);
	    }
	}
	System.out.print("\n");
	System.out.println("Sorted distance data array");
	for (int b = 0; b < height; b++) {
	    for(int n = 0; n < width + 1; n++) {
		System.out.println(neighborDist[b][n]);
	    }
	}
	System.out.println("\nClassification = "+classification);
	 // end of verification print methods   
    }

    // calculates how far the data point is from the reference point
    float getDistance(float pointComp[], float dataOrig[][], int row) {
	float calcDistance = 0;
	float dist = 0;
	//for loop to go through the columns of each "point" and get the distance
	int l = 0;
	for(int j  = 1; j < width; j++) {		// for loop does not look at the last column value since that is the classifier not data 
	    
	    dist = dist + ((dataOrig[row][j] - pointComp[l]) * (dataOrig[row][j] - pointComp[l]));
	    l++;
	    
	}
	    calcDistance = (float) Math.sqrt(dist);	// sqrt of the total distance for the point compared to the inserted point
	   
	
	return calcDistance;

    }

    // method to sort the distances in ascending order
    float[][] sortDistance(float[][] neighborDist, int height, int width) {
	// Comparator to sort the data by the distance (moves the columns by comparing the values of column 1, the distance values, and moves the rows accordingly
	Arrays.sort(neighborDist, new Comparator<float[]>() {

	    @Override
	    public int compare(float[] o1, float[] o2) {
		 float data1 = o1[1];
		 float data2 = o2[1];
		return Float.compare(data1, data2);
	    }
	    
	});
	return neighborDist;
    }

    // get the regression
    float whatNeighbor(int kValue, float[][] neighborDist) {
	float howdy = 0;
// list of all unique classifications
// frequency of the classes that occur in the nearest neighbors
// highest frequency is the class the point should be?????

	// had stuff but ended up not doing the intended function
	howdy = classif;
	return howdy;

    }

    public void runRegress() {
	System.out.println("Empty Implementation!");
=======
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
>>>>>>> Axel
    }
}
