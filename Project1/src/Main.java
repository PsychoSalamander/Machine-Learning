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
		int data[][] = dr.readArrayFromCSV("iris.csv");
		
		if(data != null)
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
		System.out.print("Iris ");
		drun.run10Tests(data);
		
		System.out.print("Iris Noisy ");
		data = dr.readArrayFromCSV("iris-shuffled.csv");
		drun.run10Tests(data);
		
		System.out.print("Glass ");
		data = dr.readArrayFromCSV("glass.csv");
		drun.run10Tests(data);
		
		System.out.print("Glass Noisy ");
		data = dr.readArrayFromCSV("glass-shuffled.csv");
		drun.run10Tests(data);
		
		System.out.print("Cancer ");
		data = dr.readArrayFromCSV("cancer.csv");
		drun.run10Tests(data);
		
		System.out.print("Cancer Noisy ");
		data = dr.readArrayFromCSV("cancer-shuffled.csv");
		drun.run10Tests(data);
		
		System.out.print("Soybeans ");
		data = dr.readArrayFromCSV("soybeans.csv");
		drun.run10Tests(data);
		
		System.out.print("Soybeans Noisy ");
		data = dr.readArrayFromCSV("soybeans-shuffled.csv");
		drun.run10Tests(data);
		
		data = dr.readArrayFromCSV("votes.csv");
		System.out.print("Votes ");
		drun.run10Tests(data);
		
		System.out.print("Votes Noisy ");
		data = dr.readArrayFromCSV("votes-shuffled.csv");
		drun.run10Tests(data);
	}	
}