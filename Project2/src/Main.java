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
		String Adata[][] = dp.readData(Paths.get("dataSets/abalone/abalone.data"));
		String Cdata[][] = dp.readData(Paths.get("dataSets/car/car.data"));
		String Fdata[][] = dp.readData(Paths.get("dataSets/forestfires/forestfires.data"));
		String Mdata[][] = dp.readData(Paths.get("dataSets/machine/machine.data"));
		String Sdata[][] = dp.readData(Paths.get("dataSets/segmentation/segmentation.data"));
		System.out.print("yolo");
	}
}
