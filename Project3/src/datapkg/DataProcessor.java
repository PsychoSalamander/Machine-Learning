package datapkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class DataProcessor {

    private Path FilePath; 					// the file path of the input data
    private Path OutputFilePath;  				// the file path of the output data file
    final private String outputNameModifier = "_processed.csv"; // the modifier that will be added to the end of the OutputFilePath
    private ProcessedData DataOutput;  				// instance of DataOutput that will be later stored and created
    private float[][] RawData; 					// instance of the raw data after being imported
    private float[] classifierOffset;				// iteration offset of the classifier
    private boolean HasHeader;					// true = input file has a header, false if it doesn't
    private int height;						// number of rows in the data array
    ArrayList<Hashtable<String, Float>> lookupTable;		// lookup table for inputed attribute elements to their corresponding new value
    Hashtable<Integer, Integer> SpecialRoundings;		// flag the data at a certain column if it needs to be rounded with a special format, instead of the default of 2 decimal places

    // class constructor
    public DataProcessor(Path FilePath, ProcessedData DataOutput, boolean HasHeader,
	    Hashtable<Integer, Integer> SpecialRoundings) {

	this.FilePath = FilePath;
	this.OutputFilePath = generateOutputFileName(FilePath); // get the OutputFilePath file path from generateOutputFileName
	this.DataOutput = DataOutput;
	this.HasHeader = HasHeader;
	this.SpecialRoundings = SpecialRoundings;

	try {
	    this.height = countLines();
	} catch (FileNotFoundException e) {
	    System.out.println("Input file not found for \"" + FilePath.toString() + "\"");
	    e.printStackTrace();
	}

	loadProcessedData(); // generate the struct for DataOutput
    }

    // takes the path of an input file name and inserts the outputNameModifier and changes the output file type to match outputNameModifier.
    private Path generateOutputFileName(Path InputFilePath) {
	String outputFilePath = InputFilePath.toString();			       // convert the input to a string for easier manipulation
	outputFilePath = outputFilePath.substring(0, outputFilePath.lastIndexOf('.')); // remove the file extension (ie "Name.csv" --> "Name")
	outputFilePath = outputFilePath.concat(outputNameModifier);	   	       // concatenate the outputNameModifier

	return Paths.get(outputFilePath); // convert outputFilePath to a path and return it
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

    // loads the data into DataOutput
    private void loadProcessedData() {

	boolean BeenProcessed = hasItBeenProcessed(); // see if the data has not already been processed

	Path fileName = null;

	try {
	    // if the data has not been processed, then process the data
	    if (!BeenProcessed) {
		fileName = FilePath;
		processTheData(); // read in the data to RawData
		saveTheData();	  // save the data contained in RawData
	    } else {
		fileName = OutputFilePath;
		loadTheData();	  // load the already made csv file that was generated
	    }
	} catch (IOException e) {
	    System.out.println(String.format("Error: File path \"%s\" not found.", fileName.toString()));
	    e.printStackTrace();
	}

	shuffleArray(); // shuffle the array and set the shuffled array in the DataOutput
	countClasses(); // counts the classes given in a data set and stores it into the DataOutput
    }

    // instantiates the arrays neccessary, after the size of them is known
    private void instantiateArrays(int width) {
	RawData = new float[height][width]; 			 // set RawData to size height x width
	classifierOffset = new float[width];			 // set the classifierOffset array to size width
	lookupTable = new ArrayList<Hashtable<String, Float>>(); // instantiate the lookupTable ArrayList

	// for every column in the file
	for (int index = 0; index < width; index++) {
	    lookupTable.add(new Hashtable<String, Float>()); // add and instantiate the Hashtable to the ArrayList
	}
    }

    // processes the FileInput into a data array
    private void processTheData() throws IOException {

	BufferedReader csvReader = new BufferedReader(new FileReader(FilePath.toString()));
	String row;

	// if the file has a header
	if (HasHeader) {
	    row = csvReader.readLine(); // skip the first row by iterating it before the while loop
	}
	
	int rowNumber = 0;

	// for every row in the data file
	while ((row = csvReader.readLine()) != null) {

	    String[] elements = null; // instatiate the elements array

	    // split the string into an array, deliminated by every separator in the row
	    if (row.contains(",")) {
		elements = row.split(",");
	    } else {
		elements = row.split(";");
	    }

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
		    key = roundElement(key, index);
		}

		key = key.toLowerCase(); 		             			// send the data to lower case
		Hashtable<String, Float> attributeLookupTable = lookupTable.get(index); // grab the lookup table for the current attribute

		// if the element DOES NOT HAVE the key in the dictionary
		if (!attributeLookupTable.containsKey(key)) {
		    float value = classifierOffset[index]++; // the value for this key will be classifierOffset, which will then be incremented
		    attributeLookupTable.put(key, value);    // set the value for the key
		}

		float num = attributeLookupTable.get(key);
		RawData[rowNumber][index] = num;	      // set the RawData[row][col] to the value containted by the key
		lookupTable.set(index, attributeLookupTable); // replace the index of the lookupTable with the new attributeLookupTable just made
	    }
	    rowNumber++; // increase the position of the rowNumber
	}

	csvReader.close(); // close the csvReader
    }

    // method that checks whether the output file exists already
    private boolean hasItBeenProcessed() {
	File temp = new File(OutputFilePath.toString());
	return temp.exists();
    }

    // checks if a given string is able to be converted to a float
    private boolean isElementFloat(String element) {
	try {
	    float f = Float.parseFloat(element);
	} catch (NumberFormatException | NullPointerException nfe) {
	    return false;
	}

	return true;
    }

    // rounds the string to 2 decimal places
    private String roundElement(String element, int column) {
	double scale;

	// if there are any special roundings
	if (SpecialRoundings != null) {
	    boolean includesKey = SpecialRoundings.containsKey(column); // see if the column number is a special rounding format

	    if (includesKey) {
		scale = Math.pow(10, SpecialRoundings.get(column)); // grab the special value from the hashtable
	    } else {
		scale = Math.pow(10, 2); // set the format to round to 2 decimal places
	    }
	} else {
	    scale = Math.pow(10, 2); // set the format to round to 2 decimal places
	}

	double number;
	String roundedNumber;

	// try parsing the number from string to float
	try {
	    number = Double.parseDouble(element);
	    roundedNumber = Double.toString(Math.round(number * scale) / scale);
	} catch (NumberFormatException | NullPointerException nfe) {
	    System.out.println("ERROR: ELEMENT \"" + element + "\" IS NOT A DOUBLE");
	    roundedNumber = null;
	}

	return roundedNumber;
    }

    // saves the data contained in the RawData array into the OutputFilePath file
    private void saveTheData() throws IOException {
	int height = RawData.length;
	int width = RawData[0].length;

	FileWriter csvWriter = new FileWriter(OutputFilePath.toString());

	// for every row in RawData
	for (int row = 0; row < height; row++) {
	    
	    // for every column in RawData
	    for (int col = 0; col < width; col++) {

		// append the float at the position of the array to csvWriter
		csvWriter.append(Float.toString(RawData[row][col]));

		// if it is NOT the last column, append a comma char ','
		if (col != width - 1) {
		    csvWriter.append(",");
		}
	    }
	    
	    // if it is NOT the last row, append a newline char '\n'
	    if (row != height - 1) {
		csvWriter.append("\n");
	    }
	}
	
	csvWriter.flush(); // write the data to the file
	csvWriter.close(); // close the file
    }

    // loads the data contained in the OutputFilePath file into the RawData array
    private void loadTheData() throws IOException {
	
	BufferedReader csvReader = new BufferedReader(new FileReader(OutputFilePath.toString()));

	int rowNumber = 0;
	String row;

	while ((row = csvReader.readLine()) != null) {

	    // split the string into an array, deliminated by every comma in the row
	    String[] elements = row.split(",");

	    // if it is the first row number, instantiate the array size
	    if (rowNumber == 0) {
		instantiateArrays(elements.length);
	    }

	    // for every column in the string array
	    for (int index = 0; index < elements.length; index++) {

		String element = elements[index];	    // element for the current index
		boolean isAFloat = isElementFloat(element); // check if the element is a float

		// if the element is a NOT float, then throw an error
		if (!isAFloat) {
		    csvReader.close(); // close the csvReader
		    
		    // Expected float at (row,col), instead got [element] for file "[OutputFilePath]"
		    throw new IllegalArgumentException("Expected float at (" + rowNumber + ", " + index
			    + "), instead got \"" + element + "\" for file \"" + OutputFilePath.toString() + "\"");
		}

		RawData[rowNumber][index] = Float.parseFloat(element); // set the RawData[row][col] to the value containted by the element
	    }
	    rowNumber++; // increase the position of the rowNumber
	}

	csvReader.close(); // close the csvReader
    }

    // shuffles the array 
    private void shuffleArray() {
	float[][] shuffledArray = RawData.clone();

	Random randomGenerator = new Random(); // create a random generator

	// for every item in the array
	for (int index = 0; index < shuffledArray.length; index++) {
	    int randomPosition = randomGenerator.nextInt(shuffledArray.length); // generate a new random position to swap with
	    float[] hold = shuffledArray[index];				// create a temp variable to hold the current position
	    shuffledArray[index] = shuffledArray[randomPosition];		// take the current position and set it equal to the randomly selected position
	    shuffledArray[randomPosition] = hold;				// take the random position and set it equal to the temp variable
	}

	DataOutput.setDataArrayShuffled(shuffledArray.clone()); // set the DataOutput's shuffled array to a clone of the shuffledArray
    }
    
    // calculates the number of classes given in a data set
    private void countClasses() {
	
	// create a list to store the classes found
	ArrayList<Float> hasItBeenSeen = new ArrayList<Float>();  
	
	// for every row in the raw data column
	for (int position = 0; position < height; position++) {
	    
	    // grab the element at the position
	    float element = RawData[position][DataOutput.getClassColumnPosition()];
	    
	    // check if the key is contained in the hashtable already
	    if (!hasItBeenSeen.contains(element)) {
		
		// if it is not in the list, put the element into the list
		hasItBeenSeen.add(element);
	    }
	}
	
	// set the number of classes equal to the size of the list
	int numberOfClasses = hasItBeenSeen.size();
	
	// store this data into the DataOutput
	DataOutput.setNumberOfClasses(numberOfClasses);
    }
}
