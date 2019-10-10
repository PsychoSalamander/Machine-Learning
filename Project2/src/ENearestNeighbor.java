
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
	for (int i = 0; i < row / 5; i++) { // first 1/5 training
	    for (int j = 0; j < column; j++) {
		training[i][j] = temp[i][j];
		temp[i][j] = 0;
	    }
	}
	for (int i = row / 5; i < 2 * row / 5; i++) { // second 1/5 test
	    for (int j = 0; j < column; j++) {
		testing[i][j] = temp[i][j];
		temp[i][j] = 0;
	    }
	}
	
	/*
	 * ---------------------------------------------------
	 * This will be our finishing 2D array that will
	 * contain the final list of points
	 * ---------------------------------------------------
	 */
	
	float[][] edited = new float[column][row];
	float firstAccuracy = 0;
	float secondAccuracy = 1;
	
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
	    for (int i = 2 * row / 5; i < row; i++) { // training
		if (temp[i][0] != 0) {
		    // data = new dataprocesser();
		    int tempClass = 0;// = data.nearestneighbor(temp, temp[i]);
		    if (tempClass != temp[i][column]) {
			for (int j = 0; j < column; j++) {
			    temp[i][j] = 0;
			}
		    }
		}
	    }
	    for (int k = 0; k < testing.length; k++) { // testing
		int tempClass = 0;// = data.nearestneighbor(temp, temp[i]);
	    }
	}
	
	/*
	 * then if we want to make this into a 2D array
	 * that completely gets rid of the zeros we will
	 * have to swap from an array to a 2D array list
	 * and back if needed
	 */
	
	return edited;
    }
}
