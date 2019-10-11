import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import datapkg.*;

public class Main {
    public static void main(String[] args) throws IOException {

	ProcessedData abaloneData = new ProcessedData();
	DataProcessor abaloneProcessor = new DataProcessor(Paths.get("dataSets/abalone/abalone.data"), abaloneData,
		false);

	ProcessedData carData = new ProcessedData();
	DataProcessor carProcessor = new DataProcessor(Paths.get("dataSets/car/car.data"), carData, false);

	ProcessedData forestfiresData = new ProcessedData();
	DataProcessor forestfiresProcessor = new DataProcessor(Paths.get("dataSets/forestfires/forestfires.data"),
		forestfiresData, false);

	ProcessedData machineData = new ProcessedData();
	DataProcessor machineProcessor = new DataProcessor(Paths.get("dataSets/machine/machine.data"), machineData,
		false);

	ProcessedData segmentationData = new ProcessedData();
	DataProcessor segmentationProcessor = new DataProcessor(Paths.get("dataSets/segmentation/segmentation.data"),
		segmentationData, false);

	ProcessedData winequalityRedData = new ProcessedData();
	DataProcessor winequalityRedProcessor = new DataProcessor(
		Paths.get("dataSets/winequality-red/winequality-red.csv"), winequalityRedData, true);

	ProcessedData winequalityWhiteData = new ProcessedData();
	DataProcessor winequalityWhiteProcessor = new DataProcessor(
		Paths.get("dataSets/winequality-white/winequality-white.csv"), winequalityWhiteData, true);

	//asdf.getProcessedData();

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
	 * KNearestNeighbor run = new KNearestNeighbor();
	 * float[][] arrayTest = new float[4][3];
	 * arrayTest[0][0] = 10;
	 * arrayTest[0][1] = 0;
	 * arrayTest[0][2] = 55;
	 * arrayTest[1][0] = 200;
	 * arrayTest[1][1] = 6;
	 * arrayTest[1][2] = 22;
	 * arrayTest[2][0] = 60;
	 * arrayTest[2][1] = 1000;
	 * arrayTest[2][2] = 1005;
	 * arrayTest[3][0] = 20;
	 * arrayTest[3][1] = 51;
	 * arrayTest[3][2] = 81;
	 * run.neighbor(3, 1, arrayTest);
	 */
    }
}
