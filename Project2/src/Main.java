import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

import datapkg.*;

public class Main {
    @SuppressWarnings("unused") //supresses the warnings for the DataProcessor instances below, as the constructor runs the code.
    
    public static void main(String[] args) throws IOException {

	// path of each data set import file
	Path abalonePath = Paths.get("dataSets/abalone/abalone.data");
	Path carPath = Paths.get("dataSets/car/car.data");
	Path forestfiresPath = Paths.get("dataSets/forestfires/forestfires.data");
	Path machinePath = Paths.get("dataSets/machine/machine.data");
	Path segmentationPath = Paths.get("dataSets/segmentation/segmentation.data");
	Path winequalityRedPath = Paths.get("dataSets/winequality-red/winequality-red.csv");
	Path winequalityWhitePath = Paths.get("dataSets/winequality-white/winequality-white.csv");

	/*
	 * instantiations of each ProcessedData object, to be dependency injected into
	 * the data processors below.
	 */
	ProcessedData abaloneData = new ProcessedData(8);
	ProcessedData carData = new ProcessedData(7);
	ProcessedData forestfiresData = new ProcessedData(12);
	ProcessedData machineData = new ProcessedData(8);
	ProcessedData segmentationData = new ProcessedData(0);
	ProcessedData winequalityRedData = new ProcessedData(11);
	ProcessedData winequalityWhiteData = new ProcessedData(11);

	/*
	 * Hashtables of any special roundings needed for each of the data sets.
	 * 
	 * If the hashtable is set to null, the processing program will assume that no
	 * special roundings are needed, and will use the default of rounding to two
	 * decimal places.
	 */
	Hashtable<Integer, String> abaloneSpecialRoundings = null;
	Hashtable<Integer, String> carSpecialRoundings = null;
	Hashtable<Integer, String> forestSpecialRoundings = null; //new Hashtable<Integer, String>();
	//forestSpecialRoundings.put(4, "####"); // Round FFMC to the nearest whole number.
	
	Hashtable<Integer, String> machineSpecialRoundings = null;
	Hashtable<Integer, String> segmentationSpecialRoundings = null;
	Hashtable<Integer, String> winequalityRedRoundings = null;
	Hashtable<Integer, String> winequalityWhiteRoundings = null;

	/*
	 * Instantiations of the data processors.
	 * 
	 * Declaring the data processor also will automatically update the
	 * instantiations of each of the previously declared ProcessedData objects using
	 * dependency injection.
	 */
	DataProcessor abaloneProcessor = new DataProcessor(abalonePath, abaloneData, false, abaloneSpecialRoundings);
	DataProcessor carProcessor = new DataProcessor(carPath, carData, false, carSpecialRoundings);
	DataProcessor forestfiresProcessor = new DataProcessor(forestfiresPath, forestfiresData, false,
		forestSpecialRoundings);
	DataProcessor machineProcessor = new DataProcessor(machinePath, machineData, false, machineSpecialRoundings);
	DataProcessor segmentationProcessor = new DataProcessor(segmentationPath, segmentationData, false,
		segmentationSpecialRoundings);
	DataProcessor winequalityRedProcessor = new DataProcessor(winequalityRedPath, winequalityRedData, true,
		winequalityRedRoundings);
	DataProcessor winequalityWhiteProcessor = new DataProcessor(winequalityWhitePath, winequalityWhiteData, true,
		winequalityWhiteRoundings);
	
	
	
	
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

	/*
	 * KNearestNeighbor run = new KNearestNeighbor();
	 * float[][] arrayTest = new float[4][4];
	 * arrayTest[0][0] = 10;
	 * arrayTest[0][1] = 33;
	 * arrayTest[0][2] = 55;
	 * arrayTest[0][3] = 22;
	 * arrayTest[1][0] = 20;
	 * arrayTest[1][1] = 33;
	 * arrayTest[1][2] = 23;
	 * arrayTest[1][3] = 21;
	 * arrayTest[2][0] = 12;
	 * arrayTest[2][1] = 30;
	 * arrayTest[2][2] = 25;
	 * arrayTest[2][3] = 15;
	 * arrayTest[3][0] = 21;
	 * arrayTest[3][1] = 51;
	 * arrayTest[3][2] = 81;
	 * arrayTest[3][3] = 31;
	 * float[] point = new float[3];
	 * point[0] = 33;
	 * point[1] = 55;
	 * point[2] = 20;
	 * 
	 * run.neighbor(point, arrayTest,1);
	 */

    }
}