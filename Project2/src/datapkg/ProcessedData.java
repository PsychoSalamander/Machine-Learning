package datapkg;

public class ProcessedData {

    private float[][] DataArrayShuffled;    // array containing the data, after it has been processed AND shuffled
    private float[][] DataArrayClassSorted; // array containing the data, after it has been processed AND sorted
    private int ClassColumn; 		    // position of the class within both of the arrays

    ProcessedData(float[][] DataArrayShuffled, float[][] DataArrayClassSorted, int ClassColumn) {
	this.DataArrayShuffled = DataArrayShuffled;
	this.DataArrayClassSorted = DataArrayClassSorted;
	this.ClassColumn = ClassColumn;
    }

    // returns the array containing the data, after it has been processed AND
    // shuffled
    public float[][] getDataArrayShuffled() {
	return DataArrayShuffled.clone();
    }

    // returns array containing the data, after it has been processed AND sorted
    public float[][] getDataArraySorted() {
	return DataArrayClassSorted.clone();
    }

    // returns array containing the data, after it has been processed AND sorted
    public int getClassColumnPosition() {
	return ClassColumn;
    }
}