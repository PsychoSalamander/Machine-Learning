import datapkg.*;

public class RadialBasis {

    float[][] InputtedData;
    int NumberOfClasses;
    int ClassColumnPosition;
    
    RadialBasis(ProcessedData ImportedData){
	InputtedData = ImportedData.getDataArrayShuffled();
	NumberOfClasses = ImportedData.getNumberOfClasses();
	ClassColumnPosition = ImportedData.getClassColumnPosition();
    }
    
    public void run() {
	
	KMeansNearestNeighbor KMeans = new KMeansNearestNeighbor();
	
	float[][] datapoints = KMeans.getMeans(InputtedData, NumberOfClasses);
	
    }
}
