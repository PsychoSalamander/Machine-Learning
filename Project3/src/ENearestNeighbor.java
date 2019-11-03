
public class ENearestNeighbor extends NearestNeighbor {

    ENearestNeighbor() {

    }

    void runClass(int K) {
    	
    	float[][] CNearestNeighborArray = runIt(inPracticeData);
    	KNearestNeighbor run = new KNearestNeighbor();
    	
    	run.setClassLocation(this.classLocation);
    	run.setPracticeData(CNearestNeighborArray);
    	run.setTestData(this.inTestData);
    	
    	run.runClass(K);
    	
    }

    public float[][] runIt(float[][] inputData) {
	
	/*
	 * ---------------------------------------------------
	 * we will need 3 different sections. One for
	 * the data set, one for training, and one for testing.
	 * We will start with the full data set, then we will
	 * work through the training set and compare values.
	 * if the values do not match, we will remove the point
	 * from the data set. We will continue looping through
	 * this until the testing data stops improving
	 * ---------------------------------------------------
	 */

    	float[][] temp = inputData;
	
    	int column = temp[0].length;
    	int row = temp.length;
	
    	float[][] training = new float[row / 5][column];
    	float[][] testing = new float[row / 5][column];
	
    	int count = row-(row/5)-(row/5);
    	float[][] edited = new float[count][column]; // full set

	
	
    	for (int i =0,j = 0; i < row / 5; i++,j++) { // first 1/5 training
		
    		training[j] = temp[i];
	
    	}
	
    	for (int i = row / 5, j = 0; i < (row / 5) + (row/5); i++, j++) { // second 1/5 test
		
    		testing[j] = temp[i];
	
    	}
	
    	for (int i = (row / 5) + (row/5), j = 0; i < row ; i++,j++) { // everything else edited
		
    		edited[j] = temp[i];
	
    	}
	
    	int tempCount = edited.length;
	
	/*
	 * ---------------------------------------------------
	 * This will be our accuracy testing to know when we
	 * need to stop removing points
	 * ---------------------------------------------------
	 */
	
    	float firstAccuracy = (float) 0.02;
    	float secondAccuracy = (float) 0.01;
    	int firstAccCount = 0;
    	int totalAcc = 0;
	
	/*
	 * ---------------------------------------------------
	 * now we will call nearest neighbor, and if the
	 * point of interest does not match the class of it's
	 * nearest neighbor we will throw the point in
	 * the data set away. We will continue to loop through
	 * this training set until we see a decrease of
	 * accuracy on the test set.
	 * ---------------------------------------------------
	 */
	
    	while (secondAccuracy < firstAccuracy && edited.length > testing.length) {
		
    		float[][] tempEdited = new float[tempCount][column]; // kept points
    		tempEdited = edited;
		
    		for (int i = 0 ; i < training.length ; i++) { // training
	    	
    			float[] tempPoint = nearestNeighbor(edited, training[i]);
    			float tempClass = tempPoint[classLocation];
	    	
    			if (tempClass != training[i][classLocation]) {
		    	
    				int multiplePoints = 0;
		    	
    				for(int l = 0 ; l < edited.length ; l++) {
		    		
    					if(edited[l] == tempPoint) {
		    			
    						multiplePoints++;
		    		
    					}
		    	
    				}
		    	
    				tempCount= tempCount-multiplePoints;
    				tempEdited = new float[tempCount][column];
		    	
    				for(int j = 0, k = 0 ; j < edited.length ; j++) {
		    		
    					if(edited[j] != tempPoint) {
    						tempEdited[k] = edited[j];
    						k++;
    					}
		    				    	
    				}
		    			    	
    				edited = new float[tempCount][column];		    	
    				edited = tempEdited;
		    			   
    			}
	    
    		}
	    
    		for (int i = 0 ; i < testing.length ; i++) { // testing
		   
    			float[] tempPoint = nearestNeighbor(edited, testing[i]);
    			float tempClass = tempPoint[classLocation];
		   
    			if (tempClass == training[i][classLocation]) {
		    	
    				firstAccCount++;
		   
    			}
		    
    			totalAcc++;
    		}
	    
    		secondAccuracy = firstAccuracy;
    		firstAccuracy = (float)firstAccCount/totalAcc;
    		totalAcc = 0;
    		firstAccCount = 0;
	    
    	}
	
    	return edited;
	
    }
}
