import java.util.Arrays;
import java.util.Comparator;

public class KNearestNeighbor extends NearestNeighbor {
    	public int height = 0;
	public int width = 0;

    KNearestNeighbor() {
	
    }

    public void runClass() {
	System.out.println("Empty Implementation!");
    }

    public void neighbor(float point[], float data[][]) {

	// get dimensions of the array
	height = data.length;
	width = data[0].length;
	float[] pointComp = new float[width];
	// instantiating variables
	float[][] neighborDist = new float[height][width+1]; 	// distance from test point of the data element
	float neighbor[][] = new float[height][width+1];	   // dummy clone array of neighborDist
	float dataOrig[][] = data.clone();		   	// clone of the inputed data

	// shift through the array to find neighbor distance
	for (int y = 0; y < height; y++) {
	    for(int x = 0; x < width; x++) {
		if( x >= 1) {
		    neighborDist[y][x] = dataOrig[y][x-1];
		    neighbor[y][x] = dataOrig[y][x-1];
		}
		
		float distance = getDistance(pointComp, dataOrig, y);
		neighborDist[y][0] = distance;
		neighbor[y][0] = distance;
	    }
		
	    
	}
	neighborDist = sortDistance(neighborDist, height, width);
	
	// print statements to verify methods
	System.out.println("sorted distance");
	for (int b = 0; b < height; b++) {
	    
		System.out.println(neighborDist[b][0]);
	  
	}
	System.out.print("\n");
	System.out.println("unsorted distance");
	for (int b = 0; b < height; b++) {
	    for (int n = 0; n < width; n++) {
		System.out.println(neighbor[b][n]);
	    }
	}
	System.out.print("\n");
	System.out.println("Sorted distance data array");
	for (int b = 0; b < height; b++) {
	    for(int n = 0; n < width; n++) {
		System.out.println(neighborDist[b][n]);
	    }
		
	    
	}

    }

    // calculates how far the data point is from the reference point
    float getDistance(float pointComp[], float dataOrig[][], int row) {
	float calcDistance = 0;
	float dist = 0;
	//for loop to go through the columns of each "point" and get the distance
	
	for(int j  = 0; j < (width -1); j++) {		// for loop does not look at the last column value since that is the classifier not data 
		
	    dist = dist + ((dataOrig[row][j] - pointComp[j]) * (dataOrig[row][j] - pointComp[j]));
		
	}
	    calcDistance = (float) Math.sqrt(dist);	// sqrt of the total distance for the point compared to the inserted point
	   
	
	return calcDistance;

    }

    // method to sort the distances in ascending order
    float[][] sortDistance(float[][] neighborDist, int height, int width) {
	// Comparator to sort the data by the distance
	Arrays.sort(neighborDist, new Comparator<float[]>() {

	    @Override
	    public int compare(float[] o1, float[] o2) {

		 float data1 = o1[0];
		 float data2 = o2[0];
		return Float.compare(data1, data2);
	    }
	    
	});
	return neighborDist;

    }

    // get the value of the neighbors
    float whatNeighbor(int kValue) {
	float howdy = 0;

	float kArr[] = new float[kValue];

	for (int i = 0; i < kValue; i++) {

	}

	return howdy;

    }

    public void runRegress() {
	System.out.println("Empty Implementation!");
    }
}
