import java.awt.List;
import java.util.*;

public class Main {

	
	public static void main(String[] args)
	{
		/*
		DataPreprocessor dp = new DataPreprocessor();
		dp.createProcessedCSV("DataSets/Glass/glass.data", "glass.csv", "glass-shuffled.csv", "glass");
		dp.createProcessedCSV("DataSets/BreastCancer/breast-cancer-wisconsin.data", "cancer.csv", "cancer-shuffled.csv", "cancer");
		dp.createProcessedCSV("DataSets/Iris/iris.data", "iris.csv", "iris-shuffled.csv", "iris");
		dp.createProcessedCSV("DataSets/Soybean/soybean-small.data", "soybeans.csv", "soybeans-shuffled.csv", "soybean");
		dp.createProcessedCSV("DataSets/Vote/house-votes-84.data", "votes.csv", "votes-shuffled.csv", "vote");
		*/
		
		/* cancer_lines = 683 | attributes = 10
		 * glass_lines = 214  | attributes = 10
		 * iris_lines = 150   | attributes = 5
		 * soybeans_lines = 47| attributes = 36
		 * votes_lines = 435  | attributes = 17
		 * total = 1529			total = 78
		 */
		//DataTrainer dt = new DataTrainer();
		/*for(int g =0; g < 10; g++)
		{
			float bb = dt.chooseAtt(g);
			float att[];
			//att[g] = bb;
			//System.out.println(att[g]);
		}*/
		
		
		DataReader dr = new DataReader();
		int cancerData[][] = dr.readArrayFromCSV("glass.csv");
		
		if(cancerData != null)
		{
			for(int i = 0; i < cancerData.length; i++)
			{
				for(int j = 0; j < cancerData[0].length; j++)
				{
					System.out.print("[" + cancerData[i][j] + "]");
				}
			System.out.print("\n");
			}
		}
		else
		{
			System.exit(0);
		}
		
		System.out.println("Ran!");

		// Let's Start with the boolean files and work into
		// the int/floats
		train(cancerData);
	}
	
	public static void train(int arr[][]) {
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
			priors[0][k] = (double)identifier[0][k];   		// type of glass
			priors[1][k] = (double)identifier[1][k]/NOR; 	//prior ( count(Y=c) / n_records) (size of 6)
			System.out.println(priors[1][k] + " priors");
			//horizontal columns
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
			evidence[1][k] = (double)att[1][k]/NOR; // probability (likelihood) of the evidence (size of 24) 
			System.out.println(evidence[1][k]);
			//vertical columns
			
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

		double[][] evidenceL = new double[5][identifierList.size()];
		for(int b = 0 ; b < tempsize; b++) {					
			evidenceL[0][b] = identifierList.get(b);			//class type1 = 16, type2 = 20, type3 = 14, type5 = 17, type6 = 14, type7 = 20 
			evidenceL[1][b] = evidenceList.get(b);				//attributes 
			evidenceL[2][b] = evidenceFreqList.get(b);			//frequency the attributes occur
			evidenceL[3][b] = identifierFreq.get(b);			//frequency class occurs
			evidenceL[4][b] = identifierFreq.get(b);			//probability of given class
			System.out.println(evidenceL[2][b]);
		}
		// counts the frequency the class appears and puts in the array
		for(int n =0; n < identifierList.size(); n++) {
			
			if(evidenceL[0][n] == identifierList.get(n)) {
				int spot = identifierList.get(n);
				double tempFreq = evidenceL[3][spot];
				double freq = ++tempFreq;
				System.out.println(spot + "spot");
				evidenceL[3][spot] = freq;								//sets the frequency of class
				evidenceL[4][spot] = freq / identifierList.size();		//sets probability of class (occurrence/total lines in data)
				//System.out.println(freq + "l");
				System.out.println(evidenceL[4][spot] + "d");
				
			}
		}
		double past = 0.0;
		//find the highest probability
		for(int m = 0; m < tempsize; m ++) {
			for(int p = 0; p < identifier[0].length;p++) {
				double clss = evidenceL[0][m];
				
				//double val =  ;
				//System.out.println(val + " val");
				//if(past < val) {
				//	past = val;
					
				}
				//System.out.println( past + " high");
			}
			
		//}
		double type1Prob = evidenceL[3][1]/101.0;
		double type2Prob = 20.0/101.0;
		double type3Prob = 14.0/101.0;
		double type5Prob = 17.0/101.0;
		double type6Prob = 14.0/101.0;
		double type7Prob = 20.0/101.0;
		
		
		
		System.out.println((type1Prob));
		System.out.println((type2Prob));
		System.out.println((type3Prob));
		System.out.println((type5Prob));
		System.out.println((type6Prob));
		System.out.println((type7Prob));
		
		
		for(int u = 0; u < 6 /*priors.length*/; u++) {  
			for(int i = 0; i < 24 /*evidence.length*/; i ++) {
				
			}
		}
			//System.out.println(priors.length);
				
	}
}