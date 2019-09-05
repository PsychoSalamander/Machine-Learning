public class Main {

	public static void main(String[] args)
	{
		DataPreprocessor dp = new DataPreprocessor();
		dp.createProcessedCSV("DataSets/Glass/glass.data", "glass.csv", "glass");
		dp.createProcessedCSV("DataSets/BreastCancer/breast-cancer-wisconsin.data", "cancer.csv", "cancer");
		dp.createProcessedCSV("DataSets/Iris/iris.data", "iris.csv", "iris");
		dp.createProcessedCSV("DataSets/Soybean/soybean-small.data", "soybeans.csv", "soybean");
		dp.createProcessedCSV("DataSets/Vote/house-votes-84.data", "votes.csv", "vote");
		System.out.println("Ran!");
	}
}