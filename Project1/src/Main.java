import java.awt.List;
import java.util.*;

public class Main {

	public static void main(String[] args)
	{
		
		DataPreprocessor dp = new DataPreprocessor();
		dp.createProcessedCSV("DataSets/Glass/glass.data", "glass.csv", "glass");
		dp.createProcessedCSV("DataSets/BreastCancer/breast-cancer-wisconsin.data", "cancer.csv", "cancer");
		dp.createProcessedCSV("DataSets/Iris/iris.data", "iris.csv", "iris");
		dp.createProcessedCSV("DataSets/Soybean/soybean-small.data", "soybeans.csv", "soybean");
		dp.createProcessedCSV("DataSets/Vote/house-votes-84.data", "votes.csv", "vote");
		
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
		
		System.out.println("Ran!");
		// Let's Start with the boolean files and work into
		// the int/floats
		train(cancerData);
	}
	public static void train(int arr[][]) {
		// finding priors and setting up the arrays to
		// calculate part of Bayes Theorem
		int NOR = arr.length;
		int NOC = arr[0].length-1;
		ArrayList<Integer> classList = new ArrayList<Integer>();
		
		int contains;
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
			priors[1][k] = (double)identifier[1][k]/NOR;
		}
		System.out.print("found priors!");
		
		// Now we do the same for the attributes to find evidence
		// for the continuation of bayes theorem
		
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
			evidence[1][k] = (double)att[1][k]/NOR;
		}
		System.out.print("found Evidence, but some values are over 1,"
				+ " only answer i can see is diving those probabilities"
				+ " by the number of attributes!");
		
		// likelihood of evidence
		
		
	}
}