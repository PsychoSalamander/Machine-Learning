public class Main {

	public static void main(String[] args)
	{
		DataPreprocessor dp = new DataPreprocessor();
		dp.createProcessedCSV("glass.data", "glass.csv");
		
		System.out.println("Ran!");
	}
}