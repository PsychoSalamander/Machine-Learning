import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Responsible Team Member: Axel Ward

public class DataReader {

	// ONLY USE THIS CLASS ON PREPROCESSED DATA
	
	DataReader()
	{
		
	}
	
	// Function to read in data from the formatted CSV
	int[][] readArrayFromCSV(String inURL)
	{
		Path pIn = Paths.get(inURL);
		int inData[][];
		
		try
		{
			BufferedReader reader = Files.newBufferedReader(pIn);
			String line = reader.readLine();
			
			// Get number of columns and lines for formatting into 2D array of all the data
			int columns = line.length() - line.replace(",", "").length() + 1;
			int lines = 0;
			
			while(line != null) // Loop to count lines
			{
				lines++;
				line = reader.readLine();
			}
			
			inData = new int[lines][columns];

			// Reset reader's position
			reader.close();
			reader = Files.newBufferedReader(pIn);
			
			// Take in strings of the line and then split it into the different parts of the
			// array so we can process it later
			for(int i = 0; i < lines; i++)
			{
				line = reader.readLine();
				String[] parts = line.split(",");
				
				for(int j = 0; j < columns; j++)
				{
					inData[i][j] = Math.round(Float.parseFloat(parts[j]));
				}
			}
			
			return inData;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
