import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class KNearestNeighbor extends NearestNeighbor {
    int zeroX = 0;
    int zeroY = 0;

    KNearestNeighbor() {
    }

    public void runClass() {
	System.out.println("Empty Implementation!");
    }

    public void neighbor(int pointX, int pointY, float data[][]) {

	// get dimensions of the array
	int height = data.length;
	int width = data[0].length;

	// instantiating variables
	float[][] neighborDist = new float[height][width]; // distance from test point of the data element
	float neighbor[][] = new float[height][width];	   // dummy clone array of neighborDist
	float dataOrig[][] = data.clone();		   // clone of the inputed data

	// shift through the array to find neighbor distance
	for (int x = 0; x < height; x++) {
	    for (int y = 0; y < width; y++) {
		float distance = getDistance(pointX, pointY, x, y);
		neighborDist[x][y] = distance;
		neighbor[x][y] = distance;
	    }
	}

	// float kNeighbor[] = new float[kVal];
	
	neighborDist = sortDistance(neighborDist, height, width);
	
	// print statements to verify methods
	System.out.println("sorted distance");
	for (int b = 0; b < height; b++) {
	    for (int n = 0; n < width; n++) {
		System.out.println(neighborDist[b][n]);
	    }
	}
	System.out.print("\n");
	System.out.println("unsorted distance");
	for (int b = 0; b < height; b++) {
	    for (int n = 0; n < width; n++) {
		System.out.println(neighbor[b][n]);
	    }
	}
	System.out.print("\n");
	System.out.println("original data array");
	for (int b = 0; b < height; b++) {
	    for (int n = 0; n < width; n++) {
		System.out.println(dataOrig[b][n]);
	    }
	}

    }

    // calculates how far the data point is from the reference point
    float getDistance(int pointX, int pointY, int compX, int compY) {

	float calcDistance = 0;

	float distX = (compX - pointX) * (compX - pointX);
	float distY = (compY - pointY) * (compY - pointY);

	calcDistance = distX + distY;

	return calcDistance;

    }

    // method to sort the distances in ascending order
    float[][] sortDistance(float[][] neighborDist, int height, int width) {
	int k = 0;
	float tempArr[] = new float[height * width];
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {

		tempArr[k++] = neighborDist[i][j];

	    }
	}
	Arrays.sort(tempArr);
	int l = 0;
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
		neighborDist[i][j] = tempArr[l];
		l++;
	    }
	}

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
