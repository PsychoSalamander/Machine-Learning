package datapkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collection;

public class DataProcessor {

    private Path FilePath; 					// the file path of the input data
    private Path OutputFilePath;  				// the file path of the output data file
    final private String outputNameModifier = "_processed.csv"; // the modifier that will be added to the end of the OutputFilePath
    private ProcessedData DataOutput;  				// instance of DataOutput that will be later stored and created
    private float[][] RawData; 					// instance of the raw data after being imported
    private float[] classifierOffset;				// iteration offset of the classifier
    private boolean HasHeader;					// true = input file has a header, false if it doesn't
    private int height;						// number of rows in the data array

    // class constructor
    public DataProcessor(Path FilePath, ProcessedData DataOutput, boolean HasHeader) {

	this.FilePath = FilePath;
	this.OutputFilePath = generateOutputFileName(FilePath); // get the OutputFilePath file path from generateOutputFileName
	this.DataOutput = DataOutput;
	this.HasHeader = HasHeader;

	try {
	    this.RawData = new float[countLines()][];
	} catch (FileNotFoundException e) {
	    System.out.println("Error: COULD NOT FIND FILE \"" + FilePath.toString() + "\"");
	    e.printStackTrace();
	}
    }

    // loads the data into DataOutput
    public void loadProcessedData() {

	boolean BeenProcessed = hasItBeenProcessed(); // see if the data has not already been processed

	// if the data has not been processed, then process the data
	if (!BeenProcessed) {
	    try {
		processTheData();
	    } catch (IOException e) {
		System.out.println(String.format("Error: File path \"%s\" not found.", FilePath.toString()));
		e.printStackTrace();
	    }
	} else {

	}
    }

    // counts the number of lines given in the input file
    private int countLines() throws FileNotFoundException {
	Scanner file = new Scanner(new File(FilePath.toString())); // load in the input file
	String fileContents = file.useDelimiter("\\Z").next();	   // convert the file to a single String

	// create regex pattern to count number of newlines
	Pattern pattern = Pattern.compile("\\n");
	Matcher matcher = pattern.matcher(fileContents);

	int numOfLines;

	// if the file has a header, start the counter at 0
	if (HasHeader) {
	    numOfLines = 0;
	}
	// if the file does not have a header, start the counter at 1
	else {
	    numOfLines = 1;
	}

	// for every match, increment the number of lines by one
	while (matcher.find()) {
	    numOfLines++;
	}

	file.close();

	return numOfLines;
    }

    private void instantiateArrays(int width) {
	RawData = new float[height][width];
	classifierOffset = new float[width];
    }

    // processes the FileInput into a data array
    private float[][] processTheData() throws IOException {

	BufferedReader csvReader = new BufferedReader(new FileReader(FilePath.toString()));
	String row;

	// if the file has a header
	if (HasHeader) {
	    row = csvReader.readLine(); // skip the first row by iterating it before the while loop
	}

	/*
	 * create an ArrayList of Dictionaries in order to create a lookup table for
	 * each attribute that can tell you what the data should look like if the String
	 * input has already been scrubbed and given a corresponding float for the
	 * element
	 */
	ArrayList<Hashtable<String, Float>> lookupTable = new ArrayList<Hashtable<String, Float>>();

	int rowNumber = 0;

	// for every row in the data file
	while (((row = csvReader.readLine()) != null) && ((row = csvReader.readLine()) != "")) {

	    // split the string into an array, deliminated by every comma in the row
	    String[] elements = row.split(",");
	    
	    // if it is the first row number, instantiate the array size
	    if (rowNumber == 0) {
		instantiateArrays(elements.length);
	    }

	    // for every column in the string array
	    for (int index = 0; index < elements.length; index++) {
		
		String key = elements[index];		// key for the current index
		boolean isAFloat = isElementFloat(key); // check if the element is a float

		// if the element is a float, then round the element
		if (isAFloat) {
		    key = roundElement(key);
		}

		key = key.toLowerCase(); 		        // send the data to lower case
		Hashtable<String, Float> attributeLookupTable = lookupTable.get(index); // grab the lookup table for the current attribute

		// if the element DOES NOT HAVE the key in the dictionary
		if (attributeLookupTable.containsKey(key)) {
		    float value = classifierOffset[index]++; // the value for this key will be classifierOffset, which will then be incremented
		    attributeLookupTable.put(key, value);    // set the value for the key
		} 
		
		RawData[rowNumber][index] = attributeLookupTable.get(key); // set the RawData[row][col] to the value containted by the key
	    }
	    rowNumber++; // increase the position of the rowNumber
	}

	csvReader.close();

	return null;
    }

    // method that checks whether the output file exists already
    private boolean hasItBeenProcessed() {
	File temp = new File(OutputFilePath.toString());
	return temp.exists();
    }

    // takes the path of an input file name and inserts the outputNameModifier and changes the output file type to match outputNameModifier.
    private Path generateOutputFileName(Path InputFilePath) {
	String outputFilePath = InputFilePath.toString();			       // convert the input to a string for easier manipulation
	outputFilePath = outputFilePath.substring(0, outputFilePath.lastIndexOf('.')); // remove the file extension (ie "Name.csv" --> "Name")
	outputFilePath = outputFilePath.concat(outputNameModifier);	   	       // concatenate the outputNameModifier

	return Paths.get(outputFilePath); // convert outputFilePath to a path and return it
    }

    // checks if a given string is able to be converted to a float
    private boolean isElementFloat(String element) {
	try {
	    float d = Float.parseFloat(element);
	} catch (NumberFormatException | NullPointerException nfe) {
	    return false;
	}

	return true;
    }

    // rounds the string to 2 decimal places
    public String roundElement(String element) {
	DecimalFormat roundingFormat = new DecimalFormat("######.00"); // set the format to round to 2 decimal places

	float number;
	String roundedNumber;

	// try parsing the number from string to float
	try {
	    number = Float.parseFloat(element);
	    roundedNumber = roundingFormat.format(number); // round the number
	} catch (NumberFormatException | NullPointerException nfe) {
	    System.out.println("ERROR: ELEMENT \"" + element + "\" IS NOT A FLOAT");
	    roundedNumber = null;
	}

	return roundedNumber;
    }
}
