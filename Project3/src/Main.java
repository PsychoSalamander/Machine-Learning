import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
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
	
	
	System.out.println("abaloneData Number of Classes: " + abaloneData.getNumberOfClasses());
	RadialBasis abaloneRadialBasis = new RadialBasis(abaloneData);
	System.out.println();
	
	System.out.println("carData Number of Classes: " + carData.getNumberOfClasses());
	RadialBasis carRadialBasis = new RadialBasis(carData);
	System.out.println();
	
	System.out.println("forestfiresData Number of Classes: " + forestfiresData.getNumberOfClasses());
	RadialBasis forestfiresRadialBasis = new RadialBasis(forestfiresData);
	System.out.println();
	
	System.out.println("machineData Number of Classes: " + machineData.getNumberOfClasses());
	RadialBasis machineRadialBasis = new RadialBasis(machineData);
	System.out.println();
	
	System.out.println("segmentationData Number of Classes: " + segmentationData.getNumberOfClasses());
	RadialBasis segentationRadialBasis = new RadialBasis(segmentationData);
	System.out.println();
	
	System.out.println("winequalityRedData Number of Classes: " + winequalityRedData.getNumberOfClasses());
	RadialBasis winequalityRedRadialBasis = new RadialBasis(winequalityRedData);
	System.out.println();
	
	System.out.println("winequalityWhiteData Number of Classes: " + winequalityWhiteData.getNumberOfClasses());
	RadialBasis winequalityWhiteRadialBasis = new RadialBasis(winequalityWhiteData);
	System.out.println();
	
	DataRunner dr = new DataRunner();
	int nodes[] = {};
	Path p0 = Paths.get("0/Overall_0.csv");
	FileWriter F0 = new FileWriter(p0.toString());
	float temp[] = null;
	temp = dr.runFeedForward(abaloneData, 10, nodes, 10, true, "abalone0", "0");
	appendArray(F0, temp);
	temp = dr.runFeedForward(carData, 10, nodes, 10, true, "car0", "0");
	appendArray(F0, temp);
	temp = dr.runFeedForward(forestfiresData, 10, nodes, 10, true, "forest0", "0");
	appendArray(F0, temp);
	temp = dr.runFeedForward(machineData, 10, nodes, 10, false, "machine0", "0");
	appendArray(F0, temp);
	temp = dr.runFeedForward(segmentationData, 10, nodes, 10, false, "segmentation0", "0");
	appendArray(F0, temp);
	temp = dr.runFeedForward(winequalityRedData, 10, nodes, 10, false, "red0", "0");
	appendArray(F0, temp);
	temp = dr.runFeedForward(winequalityWhiteData, 10, nodes, 10, false, "white0", "0");
	appendArray(F0, temp);
	F0.flush();
	F0.close();
	
	int nodes1[] = {8};
	Path p1 = Paths.get("1/Overall_1.csv");
	FileWriter F1 = new FileWriter(p1.toString());
	temp = dr.runFeedForward(abaloneData, 10, nodes1, 10, true, "abalone1", "1");
	appendArray(F1, temp);
	temp = dr.runFeedForward(carData, 10, nodes1, 10, true, "car1", "1");
	appendArray(F1, temp);
	temp = dr.runFeedForward(forestfiresData, 10, nodes1, 10, true, "forest1", "1");
	appendArray(F1, temp);
	temp = dr.runFeedForward(machineData, 10, nodes1, 10, false, "machine1", "1");
	appendArray(F1, temp);
	temp = dr.runFeedForward(segmentationData, 10, nodes1, 10, false, "segmentation1", "1");
	appendArray(F1, temp);
	temp = dr.runFeedForward(winequalityRedData, 10, nodes1, 10, false, "red1", "1");
	appendArray(F1, temp);
	temp = dr.runFeedForward(winequalityWhiteData, 10, nodes1, 10, false, "white1", "1");
	appendArray(F1, temp);
	F1.flush();
	F1.close();
	
	int nodes2[] = {10, 7};
	Path p2 = Paths.get("2/Overall_2.csv");
	FileWriter F2 = new FileWriter(p2.toString());
	temp = dr.runFeedForward(abaloneData, 10, nodes2, 10, true, "abalone2", "2");
	appendArray(F2, temp);
	temp = dr.runFeedForward(carData, 10, nodes2, 10, true, "car2", "2");
	appendArray(F2, temp);
	temp = dr.runFeedForward(forestfiresData, 10, nodes2, 10, true, "forest2", "2");
	appendArray(F2, temp);
	temp = dr.runFeedForward(machineData, 10, nodes2, 10, false, "machine2", "2");
	appendArray(F2, temp);
	temp = dr.runFeedForward(segmentationData, 10, nodes2, 10, false, "segmentation2", "2");
	appendArray(F2, temp);
	temp = dr.runFeedForward(winequalityRedData, 10, nodes2, 10, false, "red2", "2");
	appendArray(F2, temp);
	temp = dr.runFeedForward(winequalityWhiteData, 10, nodes2, 10, false, "white2", "2");
	appendArray(F2, temp);
	F2.flush();
	F2.close();
    }
    
    static void appendArray(FileWriter fw, float[] a) {
    	for(int i = 0; i < a.length; i++) {
    		if(i != a.length-1) {
    			try {
					fw.append(a[i] + ",");
				} catch (IOException e) {
					e.printStackTrace();
				}
    		} else {
    			try {
					fw.append(a[i] + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
}