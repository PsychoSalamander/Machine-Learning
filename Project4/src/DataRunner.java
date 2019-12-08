import datapkg.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataRunner {
	
	DataRunner() {
		
	}
	
	public float[] runFeedForward(ProcessedData inData, int runK, int[] nodeCount, int regressionOutputCount, boolean testClassification, String name, String folder) {
		
		float data[][] = inData.getDataArrayShuffled();
		int classLoc = inData.getClassColumnPosition();
		
		// Get length and the length of a tenth of the data
		int dataLength = data.length;
		float splitLen = ((float)dataLength)/runK;
		
		FeedForward algorithm;
		if(testClassification) {
			int classCount = 0;
			int foundKeys[] = new int[data.length];
					
			for(int i = 0; i < foundKeys.length; i++) {
				foundKeys[i] = -1;
			}
			
			for(int i = 0; i < data.length; i++) {
				int currentResult = (int) data[i][classLoc];
				
				boolean found = false;
				for(int j = 0; j < foundKeys.length; j++) {
					if(currentResult == foundKeys[j]) {
						found = true;
						break;
					}
				}
				
				if(!found) {
					foundKeys[i] = currentResult;
					classCount++;
				}
			}
			
			algorithm = new FeedForward(nodeCount.length, data[0].length - 1, classCount, nodeCount);
		} else {
			algorithm = new FeedForward(nodeCount.length, data[0].length - 1, regressionOutputCount, nodeCount);
		}

		
		// Check to make sure there are at least runK data points in the set
		if(splitLen < 1)
		{
			System.err.println("NOT ENOUGH DATA TO SPLIT INTO 10!");
			System.exit(-1);
		}
		
		float results[] = new float[runK];
		
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
			
			// Find scales
			float[] scales = new float[data[0].length - 1];
			for(int i = 0; i < data.length; i++) {
				int iter = 0;
				for(int j = 0; j < data[0].length; j++) {
					if(j != classLoc) {
						if(data[i][j] > scales[iter]) {
							scales[iter] = data[i][j];
						}
						
						iter++;
					}
				}
			}
			
			for(int i = 0; i < scales.length; i++) {
				if(scales[i] <= 0) {
					scales[i] = .0001f;
				}
			}
			
			// Set Scales for activation calculation
			algorithm.setScales(scales);
			
			
			
			// Set file output name and run algorithm
			if(testClassification) {
				Path PrintPath = Paths.get(folder + "/classification_" + name + "_" + t + ".txt");
				algorithm.setFileName(PrintPath);
				results[t] = algorithm.runClass();
			} else {
				Path PrintPath = Paths.get(folder + "/regression_" + name + "_" + t + ".txt");
				algorithm.setFileName(PrintPath);
				results[t] = algorithm.runRegress();
			}
		}
		
		return results;
	}
}