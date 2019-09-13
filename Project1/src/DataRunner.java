import java.util.Random;

public class DataRunner {

	DataRunner() {
		
	}
	
	public void run10Tests(int[][] data) {
		
		// Get length and the length of a tenth of the data
		int dataLength = data.length;
		float splitLen = ((float)dataLength)/10;
		
		System.out.println(dataLength);
		System.out.println(splitLen);
		
		// Check to make sure there are at least 10 data points in the set
		if(splitLen < 1)
		{
			System.err.println("NOT ENOUGH DATA TO SPLIT INTO 10!");
			System.exit(-1);
		}
		
		// randomize order of data such that all of one class doesn't get lumped into one test
		Random r = new Random();
		for(int i = 0; i < dataLength/2; i++)
		{
			int spot1 = r.nextInt(dataLength);
			int spot2 = r.nextInt(dataLength);
			
			int[] temp = data[spot1];
			data[spot1] = data[spot2];
			data[spot2] = temp;
		}
		
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
			for(int j = splitStart; j < splitStart + Math.round(splitLen); j++)
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
			
			System.out.println("Training Data: " + t);
			for(int i = 0; i < trainingData.length; i++)
			{
				for(int j = 0; j < trainingData[0].length; j++)
				{
					System.out.print("[" + trainingData[i][j] + "]");
				}
				System.out.println("");
			}
			
			//Run Test Here with testData[][]
			
			System.out.println("\nTest Data: " + t);
			for(int i = 0; i < testData.length; i++)
			{
				for(int j = 0; j < testData[0].length; j++)
				{
					System.out.print("[" + testData[i][j] + "]");
				}
				System.out.println("");
			}
			System.out.println("\n==========================\n==========================\n");
		}
	}
}