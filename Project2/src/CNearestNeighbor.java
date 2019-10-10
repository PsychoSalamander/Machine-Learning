
public class CNearestNeighbor extends NearestNeighbor {

	CNearestNeighbor() {
		
	}
	
	void runClass() {
		System.out.println("Empty Implementation!");
	}
	
	void runRegress() {
		System.out.println("Empty Implementation!");
	}
	
	public float[][] runIt(float[][] inputData) {
		System.out.println("Empty Implementation!");
		//---------------------------------------------------
		// input is stored as a temp data set, we will then
		// call nearest neighbor function, one at a time,
		// and if the classes match, we will not put into
		// the data set. This will find our extremes.
		//---------------------------------------------------
		float[][] temp = inputData;
		int column = temp[0].length;
		int row = temp.length;
		//---------------------------------------------------
		// initialize a new array and this will have all
		// of our extreme values and will swap all
		// uninteresting values to zero
		//---------------------------------------------------
		int count = 1;
		float[][] condensed = new float[count][column];
		float[][] tempCondensed = new float[count][column];
		//---------------------------------------------------
		// now we will call nearest neighbor, and if the
		// point of interest matches the class of it's
		// nearest neighbor we will throw it away. We
		// also have to put the first point in so it would
		// make sense to first scramble all the data.
		//---------------------------------------------------
		condensed[0] = temp[0];
		tempCondensed[0] = temp[0];
		for(int i = 0 ; i < row ; i++) {
			//data = new dataprocesser();
			int tempClass = 0;// = data.nearestneighbor(condensed, temp[i]);
			if(tempClass == temp[i][column]) {
				//for(int j = 0 ; j < column ; j++) {
					//condensed[i][j] = 0;
				//}
			}else {
				count++;
				condensed = new float[count][column];
				for(int j = 0 ; j < tempCondensed.length ; j++) {
					condensed[j] = tempCondensed[j];
				}
				condensed[count+1] = temp[i];
				tempCondensed = new float[count][column];
				tempCondensed = condensed;
			}
		}
		// then if we want to make this into a 2D array
		// that completely gets rid of the zeros we will
		// have to swap from an array to a 2D array list
		// and back if needed
		return condensed;
	}
}
