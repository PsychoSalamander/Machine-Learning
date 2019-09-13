import java.awt.List;
import java.util.*;

//Responsible Team Member: Axel Ward

public class Main {

	
	public static void main(String[] args)
	{
		
		DataPreprocessor dp = new DataPreprocessor();
		dp.createProcessedCSV("DataSets/Glass/glass.data", "glass.csv", "glass-shuffled.csv", "glass");
		dp.createProcessedCSV("DataSets/BreastCancer/breast-cancer-wisconsin.data", "cancer.csv", "cancer-shuffled.csv", "cancer");
		dp.createProcessedCSV("DataSets/Iris/iris.data", "iris.csv", "iris-shuffled.csv", "iris");
		dp.createProcessedCSV("DataSets/Soybean/soybean-small.data", "soybeans.csv", "soybeans-shuffled.csv", "soybean");
		dp.createProcessedCSV("DataSets/Vote/house-votes-84.data", "votes.csv", "votes-shuffled.csv", "vote");
		
		DataReader dr = new DataReader();
		int cancerData[][] = dr.readArrayFromCSV("votes.csv");
		
		if(cancerData != null)
		{
			/*
			for(int i = 0; i < cancerData.length; i++)
			{
				for(int j = 0; j < cancerData[0].length; j++)
				{
					System.out.print("[" + cancerData[i][j] + "]");
				}
			System.out.print("\n");
			}
			*/
		}
		else
		{
			System.exit(0);
		}
		
		DataRunner drun = new DataRunner();
		
		drun.run10Tests(cancerData);

		// Let's Start with the boolean files and work into
		// the int/floats
		/*
		TrainedModel t  = train(cancerData);
		double[][] probabilityOfClass = t.priors;
		double[][] probabilityOfEvidence = t.evidence;
		double[][] probabilityGivenLiklihood = t.evidenceL;
		System.out.println("hell yeah");
		ArrayList<Integer> atts = new ArrayList<Integer>();
		int win = 0;
		int loss = 0;
		int total = 0;
		for(int i = 0; i < cancerData.length ; i+=5) {
			for(int j = 0; j < cancerData[0].length-2 ; j++) {
				atts.add(cancerData[i][j]);
			}
			int guessedClass = smallTest(probabilityOfClass,probabilityOfEvidence,probabilityGivenLiklihood,atts);
			if(guessedClass == cancerData[i][cancerData[0].length-1]) {
				win += 1;
			}else {
				loss += 1;
			}
			atts.clear();
			total += 1;
		}
		System.out.println((float)win/total);
		*/
	}
	
	// unused training function
	public static int smallTest(double probabilityOfClass[][], double probabilityOfEvidence[][], double probabilityGivenLiklihood[][], ArrayList<Integer> atts) {
		int perferredclass = 0;
		int totalValues = 0;
		float smoothingProbability = (float)0;
		float tempProbability = (float) 0.000000000000000000000000000000000000000000000000000001;
		boolean found = false;
		for(int i = 0; i < probabilityGivenLiklihood[0].length;i++) {
			totalValues += probabilityGivenLiklihood[2][i];
		}
		smoothingProbability = (float)1/totalValues;
		for(int h = 0 ; h < probabilityOfClass[0].length; h++) {
			float likelihoodProbability = (float)probabilityOfClass[1][h];
			for(int j = 0 ; j < atts.size(); j++) {
				for(int i = 0; i < probabilityGivenLiklihood[0].length;i++) {
					if(probabilityGivenLiklihood[1][i] == atts.get(j) && probabilityGivenLiklihood[0][i] == probabilityOfClass[0][h]) {
						likelihoodProbability *= (float)probabilityGivenLiklihood[3][i]+(float)smoothingProbability;
						found = true;
					}
				}
				if(!found) {
					likelihoodProbability *= (float)smoothingProbability;
				}
				found = false;
			}
			if(likelihoodProbability > tempProbability) {
				tempProbability = (float)likelihoodProbability;
				perferredclass = (int) probabilityOfClass[0][h];
			}
		}
		return perferredclass;
	}
	public static TrainedModel train(int arr[][]) {
		// finding priors and setting up the arrays to
		// calculate part of Bayes Theorem ( Step 1 of 3 )
		int NOR = arr.length; // Number of Rows
		int NOC = arr[0].length-1; // Number of columns
		ArrayList<Integer> classList = new ArrayList<Integer>();
		
		int contains;   // some local variables
		int count = 0;
		boolean found = false;
		for(int i = 0; i < NOR; i++) {
			contains = arr[i][NOC];
			if(!classList.contains(contains)) {
				classList.add(contains);
			}
		}
		int[][] identifier = new int [2][classList.size()];
		for(int i = 0; i < NOR; i++) {
			contains = arr[i][NOC];
			for(int l = 0; l < identifier[0].length; l++) {
				if(identifier[0][l] == contains) {
					found = true;
					identifier[1][l] += 1;
				}
			}
			if(!found) {
				identifier[0][count] = contains;
				identifier[1][count] = 1;
				count++;
			}
			found = false;
		}
		count = 0;
		
		double[][] priors = new double[2][classList.size()];
		for(int k = 0; k < identifier[0].length; k++) {
			priors[0][k] = (double)identifier[0][k];
			priors[1][k] = (double)identifier[1][k]/(double)NOR;
		}

		System.out.print("found priors! \n");

		System.out.println("found priors!");

		
		// Now we do the same for the attributes to find evidence
		// for the continuation of bayes theorem ( Step 2 of 3 )
		
		ArrayList<Integer> attList = new ArrayList<Integer>();
		
		int containsE;
		int countE = 0;
		boolean foundE = false;
		for(int i = 0; i < NOR; i++) {
			for(int m = 0; m < NOC; m++) {
				containsE = arr[i][m];
				if(!attList.contains(containsE)) {
					attList.add(containsE);
				}
			}
		}
		int[][] att = new int [2][attList.size()];
		for(int i = 0; i < NOR; i++) {
			for(int n = 0; n < NOC ;n++) {
				containsE = arr[i][n];
				for(int l = 0; l < att[0].length; l++) {
					if(att[0][l] == containsE) {
						foundE = true;
						att[1][l] += 1;
					}
				}
				if(!foundE) {
					att[0][countE] = containsE;
					att[1][countE] = 1;
					countE++;
				}
				foundE = false;
			}
		}
		countE = 0;
		
		double[][] evidence = new double[2][attList.size()];
		for(int k = 0; k < att[0].length; k++) {
			evidence[0][k] = (double)att[0][k];
			evidence[1][k] = (double)((double)att[1][k]/(double)NOR/(double)NOC);
		}
		System.out.println("found Evidence, but some values are over 1,"
				+ " only answer i can see is diving those probabilities"
				+ " by the number of attributes!");
		



		// likelihood of evidence ( Step 3 of 3 )
		ArrayList<Integer> identifierList = new ArrayList<Integer>(); 		// class
		ArrayList<Integer> identifierFreq = new ArrayList<Integer>();		// class frequency
		ArrayList<Integer> evidenceList = new ArrayList<Integer>();			// attributes
		ArrayList<Integer> evidenceFreqList = new ArrayList<Integer>();		// frequency of occurrence
		boolean matched = false;

		for(int x = 0; x < NOR; x++) {
			for(int y = NOC-1; y >= 0; y-- ) {
				if(evidenceList.contains(arr[x][y])) {
					// This section does not check for the class to add to the attributes frequency
					// After this is solved, we can find the likelihood of an attribute happening
					// given a certain class. This will be the final values to find to calculate\
					// bayes theorem
					
					for(int d = 0; d < evidenceList.size(); d++) {
						if(evidenceList.get(d) == arr[x][y]) {
								if(identifierList.get(d) == arr[x][NOC]) {
									
									int temp = evidenceFreqList.get(d);
									evidenceFreqList.set(d, ++temp);
									
									matched = true;
									
								}
						}
					}if(!matched) {
						identifierList.add(arr[x][NOC]);
						evidenceList.add(arr[x][y]);
						evidenceFreqList.add(1);
						identifierFreq.add(0);

						
					}
				}else {
					identifierList.add(arr[x][NOC]);
					evidenceList.add(arr[x][y]);
					evidenceFreqList.add(1);
					identifierFreq.add(0);
				}
				matched = false;
			}
			
		}
		int tempsize = identifierList.size();
		System.out.println(priors[0].length + "priors length");
		double[][] evidenceL = new double[4][identifierList.size()];
		for(int b = 0 ; b < tempsize; b++) {
			evidenceL[0][b] = identifierList.get(b);
			evidenceL[1][b] = evidenceList.get(b);
			evidenceL[2][b] = evidenceFreqList.get(b);
			for(int e = 0; e<identifier[0].length; e++) {
				if(evidenceL[0][b] == identifier[0][e]) {
					evidenceL[3][b] = (double)evidenceFreqList.get(b)/(double)identifier[1][e]/(double)NOC/(double)identifier[0].length;
				}
			}
		}
		System.out.println("hge");
		TrainedModel t  = new TrainedModel();
		t.priors = priors;
		t.evidence = evidence;
		t.evidenceL = evidenceL;
		
		return t;
	}
}