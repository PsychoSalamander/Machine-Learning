package datapkg;

public class ProcessedData {

    private double[][] DataArrayShuffled;    // array containing the data, after it has been processed AND shuffled
    private double[][] DataArrayClassSorted; // array containing the data, after it has been processed AND sorted
    private int ClassColumn; 		     // position of the class within both of the arrays

    ProcessedData(double[][] DataArrayShuffled, double[][] DataArrayClassSorted, int ClassColumn) {
	this.DataArrayShuffled = DataArrayShuffled;
	this.DataArrayClassSorted = DataArrayClassSorted;
	this.ClassColumn = ClassColumn;
    }

    // returns the array containing the data, after it has been processed AND
    // shuffled
    public double[][] getDataArrayShuffled() {
	return DataArrayShuffled;
    }

    // returns array containing the data, after it has been processed AND sorted
    public double[][] getDataArraySorted() {
	return DataArrayClassSorted;
    }

    // returns array containing the data, after it has been processed AND sorted
    public int getClassColumnPosition() {
	return ClassColumn;
    }
}