package datapkg;

import java.util.Dictionary;

public class ProcessedData {
	
	private double[][] DataArray;
	
	private Dictionary LookupTable;
	
	ProcessedData(double[][] DataArray, Dictionary LookupTable)
	{
		this.DataArray = DataArray;
		
		this.LookupTable = LookupTable;
	}

	public double[][] getDataArray()
	{
		return DataArray;
	}
	
	public Dictionary getLookupTable()
	{
		return LookupTable;
	}
}

