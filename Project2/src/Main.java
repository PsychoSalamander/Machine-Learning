import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) throws IOException
	{
		//Start GENERALIZED preprocessing. For this I will use
		//text files and transfer them to 2D arrays. If wanting to
		//use array lists, a transfer will have to be made
		DataPreprocessor dp = new DataPreprocessor();
		String data[][] = dp.readData(Paths.get("dataSets/abalone/abalone.data"));
		System.out.print("yolo");
	}
}
