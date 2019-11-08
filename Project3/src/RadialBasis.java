import java.util.Arrays;

import datapkg.*;

public class RadialBasis {

    float[][] InputtedData;
    int NumberOfClasses;
    int ClassColumnPosition;
    
    RadialBasis(ProcessedData ImportedData){
	InputtedData = ImportedData.getDataArrayShuffled();
	NumberOfClasses = ImportedData.getNumberOfClasses();
	ClassColumnPosition = ImportedData.getClassColumnPosition();
	
	run();
    }
    
    public void run() {
	
	KMeansNearestNeighbor KMeans = new KMeansNearestNeighbor();
	
	float[][] datapoints = KMeans.getMeans(InputtedData, NumberOfClasses);
	
	System.out.println("InputtedData Length: " + InputtedData.length + " \t datapoints Length: " + datapoints.length);
	System.out.println("InputtedData Width: " + InputtedData[0].length + " \t datapoints Width: " + datapoints[0].length);
	
	printArray(datapoints);
    }
    
    public void printArray(float[][] array) {
	System.out.println("Array Visualized:");
	
	for (int row = 0; row < array.length; row++) {
            System.out.println(Arrays.toString(array[row])); 
	}
    }
}
