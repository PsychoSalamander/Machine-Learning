import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import datapkg.*;

public class Main {
    public static void main(String[] args) throws IOException {

	DataProcessor asdf = new DataProcessor(Paths.get("dataSets/abalone/abalone.data"));
	asdf.getProcessedData();
	
	/*
	 * // Start GENERALIZED preprocessing. For this I will use
	 * // text files and transfer them to 2D arrays. If wanting to
	 * // use array lists, a transfer will have to be made
	 * DataPreprocessor dp = new DataPreprocessor();
	 * String Adata[][] = dp.readData(Paths.get("dataSets/abalone/abalone.data"));
	 * String Cdata[][] = dp.readData(Paths.get("dataSets/car/car.data"));
	 * String Fdata[][] =
	 * dp.readData(Paths.get("dataSets/forestfires/forestfires.data"));
	 * String Mdata[][] = dp.readData(Paths.get("dataSets/machine/machine.data"));
	 * String Sdata[][] =
	 * dp.readData(Paths.get("dataSets/segmentation/segmentation.data"));
	 * // System.out.print("yolo");
	 * // testing distance measure and ascending order of distance
	 */
	
	KNearestNeighbor run = new KNearestNeighbor();
	 float[][] arrayTest = new float[4][4];
	 arrayTest[0][0] = 10;
	 arrayTest[0][1] = 33;
	 arrayTest[0][2] = 55;
	 arrayTest[0][3] = 22;
	 arrayTest[1][0] = 20;
	 arrayTest[1][1] = 33;
	 arrayTest[1][2] = 23;
	 arrayTest[1][3] = 21;
	 arrayTest[2][0] = 12;
	 arrayTest[2][1] = 30;
	 arrayTest[2][2] = 25;
	 arrayTest[2][3] = 15;
	 arrayTest[3][0] = 21;
	 arrayTest[3][1] = 51;
	 arrayTest[3][2] = 81;
	 arrayTest[3][3] = 31;
	 float[] point = new float[3];
	 point[0] = 33;
	 point[1] = 55;
	 point[2] = 20;
	 
	 run.neighbor(point, arrayTest,1);
	 
    }
}
