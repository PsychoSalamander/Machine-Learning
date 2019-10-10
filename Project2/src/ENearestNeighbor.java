
public class ENearestNeighbor extends NearestNeighbor {

    ENearestNeighbor() {

    }

    void runClass() {
	System.out.println("Empty Implementation!");
    }

    void runRegress() {
	System.out.println("Empty Implementation!");
    }

    public float[][] runIt(float[][] inputData) {
	System.out.println("Empty Implementation!");
	
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
	int tempCount = 0;
	
	float[][] edited = new float[count][column]; // full set

	
	for (int i = 0; i < row / 5; i++) { // first 1/5 training
	    for (int j = 0; j < column; j++) {
		training[i][j] = temp[i][j];
	    }
	}
	for (int i = row / 5; i < 2 * row / 5; i++) { // second 1/5 test
	    for (int j = 0; j < column; j++) {
		testing[i][j] = temp[i][j];
	    }
	}
	for (int i = 2 * row / 5; i < row ; i++) { // everything else edited
	    for (int j = 0; j < column; j++) {
		edited[i][j] = temp[i][j];
	    }
	}
	
	/*
	 * ---------------------------------------------------
	 * This will be our accuracy testing to know when we
	 * need to stop removing points
	 * ---------------------------------------------------
	 */
	
	float firstAccuracy = (float) 0.0;
	float secondAccuracy = (float) 1.0;
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
	
	while (secondAccuracy > firstAccuracy) {
		secondAccuracy = firstAccuracy;
		float[][] tempEdited = new float[tempCount][column]; // kept points
		float[][] secondTempEdited = new float[tempCount][column]; // kept points
	    for (int i = 0 ; i < training.length ; i++) { // training
		    // data = new dataprocesser();
		    //float[] tempPoint = data.nearestneighbor(edited[][], training[i]);
	    	int tempClass = 0;// = tempPoint[0][column];
		    if (tempClass == training[i][column]) {
		    	tempCount++;
		    	secondTempEdited = new float[tempCount][column]; // kept points
		    	for(int j = 0 ; j < tempEdited.length ; j++) {
		    		secondTempEdited[j] = tempEdited[j];
		    	}
		    	//secondTempEdited[tempEdited.length] = tempPoint[]; 
		    	tempEdited = new float[tempCount][column];
		    	tempEdited = secondTempEdited;
		    	firstAccCount++;
		    }
		    totalAcc++;
	    }
	    edited = new float[tempCount][column];
	    edited = tempEdited;
	    firstAccuracy = totalAcc/totalAcc;
	    totalAcc = 0;
	    firstAccCount = 0;
	    tempCount = 0;
	}
	
	return edited;
    }
}
