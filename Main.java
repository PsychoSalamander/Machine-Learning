package project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		reader();
		
	}

	public static void reader() throws IOException
	{
		Path path = Paths.get("src/glass.data");

		BufferedReader reader = Files.newBufferedReader(path);
		String line = reader.readLine();
		StringBuilder builder = new StringBuilder();
		
		while(line != null)
		{
			System.out.println(line);
			builder.append(line);
			line = reader.readLine();
		}
		
		
	}
}
