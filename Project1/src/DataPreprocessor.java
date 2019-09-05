import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataPreprocessor {

	DataPreprocessor()
	{

	}
	
	// Main function that calls all processing functions
	boolean createProcessedCSV(String inURL, String outURL)
	{
		Path pIn = Paths.get(inURL);
		Path pOut = Paths.get(outURL);
		float inData[][];
		
		// Try to read the data into a 2D array
		try
		{
			inData = reader(pIn);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		// Try to write the data to the CSV file
		try {
			writeFile(pOut, inData);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Function to read out a file and return a 2D array of all the data without the column labels
	float[][] reader(Path path) throws IOException
	{
		// Open the file in the reader
		BufferedReader reader = Files.newBufferedReader(path);
		String line = reader.readLine();
		
		// Get number of columns and lines for formatting into 2D array of all the data
		int columns = line.length() - line.replace(",", "").length() + 1;
		int lines = 0;
		
		while(line != null) // Loop to count lines
		{
			lines++;
			line = reader.readLine();
		}
		
		float temp[][] = new float[lines][columns - 1];

		// Reset reader's position
		reader.close();
		reader = Files.newBufferedReader(path);
		
		// Take in strings of the line and then split it into the different parts of the
		// array so we can process it later
		for(int i = 0; i < lines; i++)
		{
			line = reader.readLine();
			String[] parts = line.split(",");
			
			for(int j = 1; j < columns; j++)
			{
				temp[i][j-1] = Float.parseFloat(parts[j]);
			}
		}
		
		reader.close();
		
		return temp;
	}
	
	// Function to write the processed 2D array to a CSV File
	void writeFile(Path path, float[][] data) throws IOException
	{
		// Open the file in the writer
		BufferedWriter writer = Files.newBufferedWriter(path);
		
		// Get the number of lines and columns in the data
		int lines = data.length;
		int columns = data[0].length;
		
		// Write the data to console and a file
		for(int i = 0; i < lines; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				System.out.print("[" + data[i][j] + "]");
				writer.write(data[i][j] + ",");
			}
			System.out.print("\n");
			writer.newLine();
		}
		
		writer.close();
	}
}
