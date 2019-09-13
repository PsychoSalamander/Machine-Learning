
// Responsible team member: Spencer Raymond

public class DataTester {

	TrainedModel tm;
	int testData[][];
	
	DataTester(TrainedModel t, int dat[][]) {
		tm = t;
		testData = dat.clone();
	}
	
	// Section to check the accuracy of the algorithms guesses.
	public double checkAccuracy() {
		
		int total = 0;
		int correct = 0;
		
		// Iterate through the array of data to be tested
		for(int t = 0; t < testData.length; t++) {
			
			// Get the current data line and the real class of the data line
			int[] currentData = testData[t];
			int realClass = currentData[currentData.length-1];
			
			// Initialize probability array
			double classP[] = new double[tm.classes.size()];
			
			// Apply formula 3 from the paper
			for(int c = 0; c < tm.classes.size(); c++) {
				
				// Probability of the class to check occurring
				classP[c] = tm.classPercentages[c];
				
				// Multiplied by the probability of example alpha given class c
				for(int i = 0; i < currentData.length-1; i++) {
					 if(tm.attributes.get(i).contains(currentData[i])) {
						 int location = tm.attributes.get(i).indexOf(currentData[i]);
						 classP[c] = classP[c] * (tm.percentages[c][i][location] + 1/testData.length);
					 }
					
				}
			}
			
			// Find the arg max of the list of probabilities.
			int estClass = 0;
			double largest = 0;
			
			for(int i = 0; i < classP.length; i++) {
				if(largest < classP[i]) {
					estClass = i;
					largest = classP[i];
				}
			}
			
			// Get estimated class from list of classes
			estClass = tm.classes.get(estClass);
			
			// Check if correct
			if(estClass == realClass) {
				correct++;
			}
			total++;
		}
		
		// Print and return the number correct percentages
		//System.out.println(correct + "/" + total + "=" + ((double)correct/(double)total));
	
		return((double)correct/(double)total);
	}
}
