import java.util.ArrayList;
import java.util.Random;

//Responsible Team Member: Franki Taylor

public class DataRunner {

	DataRunner() {
		
	}
	
	public void run10Tests(int[][] data) {
		
		// Get length and the length of a tenth of the data
		int dataLength = data.length;
		float splitLen = ((float)dataLength)/10;
		
		// Check to make sure there are at least 10 data points in the set
		if(splitLen < 1)
		{
			System.err.println("NOT ENOUGH DATA TO SPLIT INTO 10!");
			System.exit(-1);
		}
		
		int correct = 0;
		int total = 0;
		
		// Run the training and test 10-fold times
		for(int t = 0; t < 10; t++)
		{
			// get start index of test data
			int splitStart = Math.round(splitLen * t);
			
			int trainingData[][] = new int[Math.round(splitLen*9)][data[0].length];
			int testData[][] =  new int[dataLength - Math.round(splitLen*9)][data[0].length];
			
			// fill in training data up until test data
			for(int i = 0; i < splitStart; i++)
			{
				trainingData[i] = data[i];
			}
			
			// fill in test data
			int index = 0;
			for(int j = splitStart; j < splitStart + Math.round(splitLen) - 1 ; j++)
			{
				testData[index] = data[j];
				index++;
			}
			
			// fill in training data after test data
			index = splitStart;
			for(int k = splitStart + Math.round(splitLen); k < dataLength; k++)
			{
				trainingData[index] = data[k];
				index++;
			}
			
			//Run Training Here with trainingData[][]
			
			TrainedModel tm = new TrainedModel();
			tm = new DataTrainer().train(trainingData);
			
			//Run Test Here with testData[][]
			
			double[][] probabilityOfClass = tm.priors;
			double[][] probabilityOfEvidence = tm.evidence;
			double[][] probabilityGivenLiklihood = tm.evidenceL;
			ArrayList<Integer> atts = new ArrayList<Integer>();
			for(int i = 0; i < testData.length; i++)
			{
				for(int j = 0; j < testData[0].length - 1; j++) {
					atts.add(testData[i][j]);
				}
				
				int tempy = new DataTester().smallTest(probabilityOfClass,probabilityOfEvidence,probabilityGivenLiklihood,atts);
				if(tempy == testData[0][testData[0].length - 1])
				{
					correct++;
				}
				total++;
				
				atts.clear();
			}
		}
		
		System.out.println("Correctness: " + correct + "/" + total + "=" + Math.round((((float)correct/(float)total)*100)) + "%");
	}
}