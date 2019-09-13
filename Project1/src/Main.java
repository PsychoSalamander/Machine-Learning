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
		int cancerData[][] = dr.readArrayFromCSV("cancer.csv");
		
		if(cancerData != null)
		{

		}
		else
		{
			System.exit(0);
		}
		
		DataRunner drun = new DataRunner();

		System.out.println("Cancer Data:");
		drun.run10Tests(cancerData);
		
		cancerData = dr.readArrayFromCSV("glass.csv");
		System.out.println("Glass Data:");
		drun.run10Tests(cancerData);
		
		cancerData = dr.readArrayFromCSV("iris.csv");
		System.out.println("Iris Data:");
		drun.run10Tests(cancerData);

		cancerData = dr.readArrayFromCSV("soybeans.csv");
		System.out.println("Soybean Data:");
		drun.run10Tests(cancerData);
		
		cancerData = dr.readArrayFromCSV("votes.csv");
		System.out.println("Vote Data:");
		drun.run10Tests(cancerData);
		
		System.out.println("\n\nShuffled Data:\n");
		
		cancerData = dr.readArrayFromCSV("cancer-shuffled.csv");
		System.out.println("Cancer Data:");
		drun.run10Tests(cancerData);
		
		cancerData = dr.readArrayFromCSV("glass-shuffled.csv");
		System.out.println("Glass Data:");
		drun.run10Tests(cancerData);
		
		cancerData = dr.readArrayFromCSV("iris-shuffled.csv");
		System.out.println("Iris Data:");
		drun.run10Tests(cancerData);

		cancerData = dr.readArrayFromCSV("soybeans-shuffled.csv");
		System.out.println("Soybean Data:");
		drun.run10Tests(cancerData);
		
		cancerData = dr.readArrayFromCSV("votes-shuffled.csv");
		System.out.println("Vote Data:");
		drun.run10Tests(cancerData);
	}
}