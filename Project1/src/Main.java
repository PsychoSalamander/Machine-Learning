public class Main {

	public static void main(String[] args)
	{
		/*
		DataPreprocessor dp = new DataPreprocessor();
		dp.createProcessedCSV("DataSets/Glass/glass.data", "glass.csv", "glass");
		dp.createProcessedCSV("DataSets/BreastCancer/breast-cancer-wisconsin.data", "cancer.csv", "cancer");
		dp.createProcessedCSV("DataSets/Iris/iris.data", "iris.csv", "iris");
		dp.createProcessedCSV("DataSets/Soybean/soybean-small.data", "soybeans.csv", "soybean");
		dp.createProcessedCSV("DataSets/Vote/house-votes-84.data", "votes.csv", "vote");
		*/
		
		/* cancer_lines = 683 | attributes = 10
		 * glass_lines = 214  | attributes = 10
		 * iris_lines = 150   | attributes = 5
		 * soybeans_lines = 47| attributes = 36
		 * votes_lines = 435  | attributes = 17
		 * total = 1529			total = 78
		 */
		
		DataReader dr = new DataReader();
		float cancerData[][] = dr.readArrayFromCSV("cancer.csv");
		
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
		
		
	}
}