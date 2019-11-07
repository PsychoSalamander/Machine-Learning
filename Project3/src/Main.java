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
	ProcessedData carData = new ProcessedData(6);
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
	Hashtable<Integer, Integer> abaloneSpecialRoundings = null;
	Hashtable<Integer, Integer> carSpecialRoundings = null;
	Hashtable<Integer, Integer> forestSpecialRoundings = new Hashtable<Integer, Integer>();
	forestSpecialRoundings.put(4, 1); // Round FFMC to the nearest whole number.
	
	Hashtable<Integer, Integer> machineSpecialRoundings = new Hashtable<Integer, Integer>();
	machineSpecialRoundings.put(9, -9); // Round ERP to 0.
	
	Hashtable<Integer, Integer> segmentationSpecialRoundings = new Hashtable<Integer, Integer>();
	segmentationSpecialRoundings.put(17, 1); // Round saturatoin-mean to the tenths place.
	segmentationSpecialRoundings.put(18, 1); // Round hue-mean to the tenths place.
	
	Hashtable<Integer, Integer> winequalityRedRoundings = new Hashtable<Integer, Integer>();
	winequalityRedRoundings.put(7, 3); // Round density to the thousandths place.
	
	Hashtable<Integer, Integer> winequalityWhiteRoundings = new Hashtable<Integer, Integer>();
	winequalityWhiteRoundings.put(7, 3); // Round density to the thousandths place.

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
	
	DataRunner dr = new DataRunner();

	KNearestNeighbor KNN = new KNearestNeighbor();
	ENearestNeighbor ENN = new ENearestNeighbor();
	CNearestNeighbor CNN = new CNearestNeighbor();
	KMeansNearestNeighbor KMNN = new KMeansNearestNeighbor();
	PAMNearestNeighbor PAMNN = new PAMNearestNeighbor();
	
	int nodes[] = {10, 7};
	
	// dr.runFeedForward(carData, 10, nodes, 10, true, "car");
	dr.runFeedForward(machineData, 10, nodes, 10, false, "machine");
	
	/*
	// Run KNN Algorithm
	System.out.println("Running KNN Algorithm:");
	System.out.println("Running Abalone Data:");
	dr.runTests(KNN, abaloneData, true, false, false, false);
	System.out.println("Running Car Data:");
	dr.runTests(KNN, carData, true, false, false, false);
	System.out.println("Running Segmentation Data:");
	dr.runTests(KNN, segmentationData, true, false, false, false);
	System.out.println("Running Forest Fire Data:");
	dr.runTests(KNN, forestfiresData, false, true, false, false);
	System.out.println("Running Machine Data:");
	dr.runTests(KNN, machineData, false, true, false, false);
	System.out.println("Running Red Wine Data:");
	dr.runTests(KNN, winequalityRedData, false, true, false, false);
	System.out.println("Running White Wine Data:");
	dr.runTests(KNN, winequalityWhiteData, false, true, false, false);
	
	// Run ENN Algorithm
	System.out.println("Running ENN Algorithm:");
	System.out.println("Running Abalone Data:");
	dr.runTests(ENN, abaloneData, true, false, false, false);
	System.out.println("Running Car Data:");
	dr.runTests(ENN, carData, true, false, false, false);
	System.out.println("Running Segmentation Data:");
	dr.runTests(ENN, segmentationData, true, false, false, false);
	
	// Run CNN Algorithm
	System.out.println("Running CNN Algorithm:");
	System.out.println("Running Abalone Data:");
	dr.runTests(CNN, abaloneData, true, false, false, false);
	System.out.println("Running Car Data:");
	dr.runTests(CNN, carData, true, false, false, false);
	System.out.println("Running Segmentation Data:");
	dr.runTests(CNN, segmentationData, true, false, false, false);
	
	// Run KMNN Algorithm
	System.out.println("Running KMNN Algorithm:");
	System.out.println("Running Abalone Data:");
	dr.runTests(KMNN, abaloneData, true, false, true, false);
	System.out.println("Running Car Data:");
	dr.runTests(KMNN, carData, true, false, true, false);
	System.out.println("Running Segmentation Data:");
	dr.runTests(KMNN, segmentationData, true, false, true, false);
	System.out.println("Running Forest Fire Data:");
	dr.runTests(KMNN, forestfiresData, false, true, false, false);
	System.out.println("Running Machine Data:");
	dr.runTests(KMNN, machineData, false, true, false, true);
	System.out.println("Running Red Wine Data:");
	dr.runTests(KMNN, winequalityRedData, false, true, false, true);
	System.out.println("Running White Wine Data:");
	dr.runTests(KMNN, winequalityWhiteData, false, true, false, true);
	
	// Run PAMNN Algorithm
	System.out.println("Running PAMNN Algorithm:");
	System.out.println("Running Abalone Data:");
	dr.runTests(PAMNN, abaloneData, true, false, true, false);
	System.out.println("Running Car Data:");
	dr.runTests(PAMNN, carData, true, false, true, false);
	System.out.println("Running Segmentation Data:");
	dr.runTests(PAMNN, segmentationData, true, false, true, false);
	System.out.println("Running Forest Fire Data:");
	dr.runTests(PAMNN, forestfiresData, false, true, false, false);
	System.out.println("Running Machine Data:");
	dr.runTests(PAMNN, machineData, false, true, false, true);
	System.out.println("Running Red Wine Data:");
	dr.runTests(PAMNN, winequalityRedData, false, true, false, true);
	System.out.println("Running White Wine Data:");
	dr.runTests(PAMNN, winequalityWhiteData, false, true, false, true);
	*/
    }
}