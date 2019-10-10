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
		//System.out.print("yolo");
		//testing distance measure and ascending order of distance
		KNearestNeighbor run = new KNearestNeighbor();
		float[][] arrayTest = new float[4][3];
		arrayTest[0][0] = 10;
		arrayTest[0][1] = 0;
		arrayTest[0][2] = 55;
		arrayTest[1][0] = 200;
		arrayTest[1][1] = 6;
		arrayTest[1][2] = 22;
		arrayTest[2][0] = 60;
		arrayTest[2][1] = 1000;
		arrayTest[2][2] = 1005;
		arrayTest[3][0] = 20;
		arrayTest[3][1] = 51;
		arrayTest[3][2] = 81;
		run.neighbor(3,1,arrayTest);
	}
}
