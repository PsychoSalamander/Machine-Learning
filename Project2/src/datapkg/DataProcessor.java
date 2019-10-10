package datapkg;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataProcessor {

    // the file path for the processed data document
    private Path ProcessedFile = Paths.get("dataSets");

    // the file path of the input data
    private Path FilePath;

    // the file path of the output data file
    private Path OutputFilePath;
    final private String outputNameModifier = "_processed.csv";
    
    // instance of DataOutput that will be later stored and created
    private ProcessedData DataOutput;

    public DataProcessor(Path FilePath) {
	this.FilePath = FilePath;

	String FP = FilePath.toString();
	FP = FP.substring(0, FP.lastIndexOf('.')); // remove the file extension
	FP = FP.concat(outputNameModifier);	   // concatenate the outputNameModifier

	OutputFilePath = Paths.get(FP); // get the file path

	System.out.println(ProcessedFile.toString());
    }

    private void loadProcessedData() {

	boolean BeenProcessed = hasItBeenProcessed(); // see if the data has not already been processed

	// if the data has not been processed, then process the data
	if (!BeenProcessed) {
	    processTheData();
	}

    }

    private void processTheData() {

    }

    // method that checks whether the output file exists already
    private boolean hasItBeenProcessed() {
	File temp = new File(OutputFilePath.toString());
	return temp.exists();
    }

    // method that returns the data after it has been processed
    public ProcessedData getProcessedData() {

	// if the DataOutput is null, load the data
	if (DataOutput == null) {
	    loadProcessedData();
	}

	return DataOutput;
    }

}
