import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import datapkg.*;

public class Main {
    public static void main(String[] args) throws IOException {
//---------------------------------------------
		/*
		 * ProcessedData abaloneData = new ProcessedData(); DataProcessor
		 * abaloneProcessor = new
		 * DataProcessor(Paths.get("dataSets/abalone/abalone.data"), abaloneData,
		 * false);
		 * 
		 * ProcessedData carData = new ProcessedData(); DataProcessor carProcessor = new
		 * DataProcessor(Paths.get("dataSets/car/car.data"), carData, false);
		 * 
		 * ProcessedData forestfiresData = new ProcessedData(); DataProcessor
		 * forestfiresProcessor = new
		 * DataProcessor(Paths.get("dataSets/forestfires/forestfires.data"),
		 * forestfiresData, false);
		 * 
		 * ProcessedData machineData = new ProcessedData(); DataProcessor
		 * machineProcessor = new
		 * DataProcessor(Paths.get("dataSets/machine/machine.data"), machineData,
		 * false);
		 * 
		 * ProcessedData segmentationData = new ProcessedData(); DataProcessor
		 * segmentationProcessor = new
		 * DataProcessor(Paths.get("dataSets/segmentation/segmentation.data"),
		 * segmentationData, false);
		 * 
		 * ProcessedData winequalityRedData = new ProcessedData(); DataProcessor
		 * winequalityRedProcessor = new DataProcessor(
		 * Paths.get("dataSets/winequality-red/winequality-red.csv"),
		 * winequalityRedData, true);
		 * 
		 * ProcessedData winequalityWhiteData = new ProcessedData(); DataProcessor
		 * winequalityWhiteProcessor = new DataProcessor(
		 * Paths.get("dataSets/winequality-white/winequality-white.csv"),
		 * winequalityWhiteData, true);
		 *///----------------------------------------------------------
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
	 */
	
	KNearestNeighbor run = new KNearestNeighbor();
	CNearestNeighbor runC = new CNearestNeighbor();
	ENearestNeighbor runE = new ENearestNeighbor();
	 float[][] arrayTest = new float[5][4];
	 arrayTest[0][0] = 10;
	 arrayTest[0][1] = 33;
	 arrayTest[0][2] = 55;
	 arrayTest[0][3] = 54;
	 
	 arrayTest[1][0] = 20;
	 arrayTest[1][1] = 33;
	 arrayTest[1][2] = 23;
	 arrayTest[1][3] = 54;
	 
	 arrayTest[2][0] = 12;
	 arrayTest[2][1] = 30;
	 arrayTest[2][2] = 25;
	 arrayTest[2][3] = 55;
	 
	 arrayTest[3][0] = 21;
	 arrayTest[3][1] = 51;
	 arrayTest[3][2] = 81;
	 arrayTest[3][3] = 53;
	 
	 arrayTest[4][0] = 12;
	 arrayTest[4][1] = 30;
	 arrayTest[4][2] = 25;
	 arrayTest[4][3] = 31;
	 //float[] point = new float[3];
	 //point[0] = 33;
	 //point[1] = 55;
	 //point[2] = 20;
	 runE.runIt(arrayTest);
	 //run.neighbor(point, arrayTest,1);
	 
    }
}
