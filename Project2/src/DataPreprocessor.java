import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataPreprocessor {
	DataPreprocessor()
	{

	}

	public String[][] readData(Path path) throws IOException {
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
		
		String temp[][] = new String[lines][columns];

		// Reset reader's position
		reader.close();
		reader = Files.newBufferedReader(path);
		
		//moving all values into the temp array, will handle
		//classification attributes in a seperate function
		
		for(int i = 0; i < lines; i++)
		{
			line = reader.readLine();
			String[] parts = line.split(",");
			
			for(int j = 0; j < columns; j++)
			{
				temp[i][j] = parts[j];
			}
		}
		
		reader.close();
		
		return temp;
	}
	
	
}
