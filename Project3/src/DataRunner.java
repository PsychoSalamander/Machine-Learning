import datapkg.*;

public class DataRunner {
	
	DataRunner() {
		
	}
	
	public void runTests(NearestNeighbor algorithm, ProcessedData data, boolean runClassification, boolean runRegression, boolean editData, boolean fourthData) {
		
		float inData[][] = data.getDataArrayShuffled();
		int classLocation = data.getClassColumnPosition();
		
		// Check to make sure data is not edited and fourthed
		if(editData && fourthData) {
			System.out.println("Do not edit and fourth data!");
			System.exit(-1);
		} else {
			// Edit Data if that flag is ticked
			if(editData) {
				ENearestNeighbor ENN = new ENearestNeighbor();
				inData = ENN.runIt(inData);
			}
			// Fourth Data if that flag is ticked
			if(fourthData) {
				// Get fourth of data to run in regression algorithms
				int dataSize = inData.length;
				float fourthedData[][] = new float[Math.round(dataSize/4)][inData[0].length];
				
				// Fill in fourthedData structure
				for(int i = 0; i < Math.round(dataSize/4); i++) {
					fourthedData[i] = inData[i];
				}
				
				inData = fourthedData;
			}
		}
		
		// Run K-Fold tests for K values of 3, 5, and 7 for the algorithm
		System.out.println("k = 5");
		runKFold(10, 5, algorithm, classLocation, inData, runClassification, runRegression);
		System.out.println("k = 7");
		runKFold(10, 7, algorithm, classLocation, inData, runClassification, runRegression);
		System.out.println("k = 10");
		runKFold(10, 10, algorithm, classLocation, inData, runClassification, runRegression);
	}
	
	public void runKFold(int runK, int testK, NearestNeighbor algorithm, int classLoc, float[][] data, boolean testClassification, boolean testRegression) {
		
		// Get length and the length of a tenth of the data
		int dataLength = data.length;
		float splitLen = ((float)dataLength)/runK;
		
		// Check to make sure there are at least runK data points in the set
		if(splitLen < 1)
		{
			System.err.println("NOT ENOUGH DATA TO SPLIT INTO 10!");
			System.exit(-1);
		}
		
		// Run the training and test runK times		
		for(int t = 0; t < runK; t++)
		{			
			// get start index of test data
			int splitStart = Math.round(splitLen * t);
			
			float trainingData[][] = new float[Math.round(splitLen*(runK - 1))][data[0].length];
			float testData[][] =  new float[dataLength - Math.round(splitLen*(runK - 1))][data[0].length];
			
			// fill in training data up until test data
			for(int i = 0; i < splitStart; i++)
			{
				trainingData[i] = data[i];
			}
			
			// fill in test data
			int index = 0;
			for(int j = splitStart; j < splitStart + Math.round(splitLen) - 1; j++)
			{
				testData[index] = data[j];
				index++;
			}
			
			// fill in training data after test data
			index = 0;
			for(int k = splitStart + Math.round(splitLen); k < dataLength; k++)
			{
				trainingData[index] = data[k];
				index++;
			}
			
			// Set training data
			algorithm.setPracticeData(trainingData);
			
			// Set test data
			algorithm.setTestData(testData);
			
			// Set Class Location
			algorithm.setClassLocation(classLoc);
			
			if(testClassification) {
				algorithm.runClass(testK);
			}
			if(testRegression) {
				algorithm.runRegress(testK);
			}
		}		
	}
}