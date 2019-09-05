import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class DataPreprocessor {

	DataPreprocessor()
	{

	}
	
	// Main function that calls all processing functions
	boolean createProcessedCSV(String inURL, String outURL, String identifier)
	{
		Path pIn = Paths.get(inURL);
		Path pOut = Paths.get(outURL);
		float inData[][];
		
		// Try to read the data into a 2D array
		try
		{
			if(identifier == "cancer")
			{
				inData = readCancer(pIn);
				System.out.println("Read Cancer!");
			}
			else if(identifier == "glass")
			{
				inData = readGlass(pIn);
				System.out.println("Read Glass!");
			}
			else if(identifier == "iris")
			{
				inData = readIris(pIn);
				System.out.println("Read Iris!");
			}
			else if(identifier == "soybean")
			{
				inData = readSoybean(pIn);
				System.out.println("Read Soybean!");
			}
			else
			{
				inData = readVote(pIn);
				System.out.println("Read Vote!");
			}

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
	
	// Function to read out the glass and return a 2D array of all the data without the column labels
	float[][] readGlass(Path path) throws IOException
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
	
	// Function to read out the Cancer file and return a 2D array of all the data without the column labels
	float[][] readCancer(Path path) throws IOException
	{
		// Open the file in the reader
		BufferedReader reader = Files.newBufferedReader(path);
		String line = reader.readLine();
		
		// Get number of columns and lines for formatting into 2D array of all the data
		int columns = line.length() - line.replace(",", "").length() + 1;
		int lines = 0;
		
		while(line != null) // Loop to count lines
		{
			if(line.indexOf("?") == -1) // Check to make sure there are no missing values
			{
				lines++;
			}
			line = reader.readLine();
		}
		
		float temp[][] = new float[lines][columns - 1];

		// Reset reader's position
		reader.close();
		reader = Files.newBufferedReader(path);
		
		System.out.println(lines);
		
		// Take in strings of the line and then split it into the different parts of the
		// array so we can process it later
		for(int i = 0; i < lines;)
		{
			line = reader.readLine();
			if(line.indexOf("?") == -1) // If there are missing values, throw the line of data out
			{
				String[] parts = line.split(",");
				
				for(int j = 1; j < columns; j++)
				{
					temp[i][j-1] = Float.parseFloat(parts[j]);
				}
				
				i++;
			}
		}
		
		reader.close();
		
		return temp;
	}
		
	// Function to read out the Iris file and return a 2D array of all the data without the column labels
	float[][] readIris(Path path) throws IOException
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
		
		float temp[][] = new float[lines][columns];

		// Reset reader's position
		reader.close();
		reader = Files.newBufferedReader(path);
		
		// Take in strings of the line and then split it into the different parts of the
		// array so we can process it later
		for(int i = 0; i < lines; i++)
		{
			line = reader.readLine();
			String[] parts = line.split(",");
			
			// Replace text classes with number results to make it usable by the program
			if(parts[columns-1].equals("Iris-setosa"))
			{
				parts[columns-1] = "0";
			}
			else if(parts[columns-1].equals("Iris-versicolor"))
			{
				parts[columns-1] = "1";
			}
			else if(parts[columns-1].equals("Iris-virginica"))
			{
				parts[columns-1] = "2";
			}
			
			for(int j = 0; j < columns; j++)
			{
				temp[i][j] = Float.parseFloat(parts[j]);
			}
		}
		
		reader.close();
		
		return temp;
	}
	
	// Function to read out the soy bean file and return a 2D array of all the data without the column labels
	float[][] readSoybean(Path path) throws IOException
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
		
		float temp[][] = new float[lines][columns];

		// Reset reader's position
		reader.close();
		reader = Files.newBufferedReader(path);
		
		// Take in strings of the line and then split it into the different parts of the
		// array so we can process it later
		for(int i = 0; i < lines; i++)
		{
			line = reader.readLine();
			String[] parts = line.split(",");
			
			// Replace text classes with number results to make it usable by the program
			if(parts[columns-1].equals("D1"))
			{
				parts[columns-1] = "1";
			}
			else if(parts[columns-1].equals("D2"))
			{
				parts[columns-1] = "2";
			}
			else if(parts[columns-1].equals("D3"))
			{
				parts[columns-1] = "3";
			}
			else if(parts[columns-1].equals("D4"))
			{
				parts[columns-1] = "4";
			}
			
			for(int j = 0; j < columns; j++)
			{
				temp[i][j] = Float.parseFloat(parts[j]);
			}
		}
		
		reader.close();
		
		return temp;
	}
	
	// Function to read out the Vote file and return a 2D array of all the data without the column labels
	float[][] readVote(Path path) throws IOException
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
		
		float temp[][] = new float[lines][columns];

		// Reset reader's position
		reader.close();
		reader = Files.newBufferedReader(path);
		
		Random r = new Random();
		
		// Take in strings of the line and then split it into the different parts of the
		// array so we can process it later
		for(int i = 0; i < lines; i++)
		{
			line = reader.readLine();
			String[] partsTemp = line.split(",");
			String[] parts = line.split(",");
			
			parts[columns-1] = parts[0];
			for(int j = 0; j < columns - 1; j++) {
				parts[j] = partsTemp[j+1];
			}
			
			// Replace text classes with number results to make it usable by the program
			if(parts[columns-1].equals("republican"))
			{
				parts[columns-1] = "1";
			}
			else if(parts[columns-1].equals("democrat"))
			{
				parts[columns-1] = "2";
			}
			
			for(int j = 0; j < columns; j++)
			{
				// Replace text with usable values
				if(parts[j].equals("y"))
				{
					parts[j] = "1";
				}
				else if(parts[j].equals("n"))
				{
					parts[j] = "0";
				}
				else if(parts[j].equals("?"))
				{
					parts[j] = Integer.toString(r.nextInt(2));
				}
				
				temp[i][j] = Float.parseFloat(parts[j]);
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
