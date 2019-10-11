package datapkg;

public class ProcessedData {

    private float[][] DataArrayShuffled;    // array containing the data, after it has been processed AND shuffled
    private float[][] DataArrayClassSorted; // array containing the data, after it has been processed AND sorted
    private int ClassColumn; 		    // position of the class within both of the arrays
    private int UniqueClasses;		    // number of unique classes given within the data array

    public ProcessedData() {
    }

    /*
     * Set Methods
     */
    
    // sets the DataArrayShuffled to the input
    public void setDataArrayShuffled(float[][] DataArrayShuffled) {
	this.DataArrayShuffled = DataArrayShuffled;
    }

    // sets the DataArrayClassSorted to the input
    public void setDataArrayClassSorted(float[][] DataArrayClassSorted) {
	this.DataArrayClassSorted = DataArrayClassSorted;
    }

    // sets the ClassColumn to the input
    public void setClassColumn(int ClassColumn) {
	this.ClassColumn = ClassColumn;
    }

    // sets the UniqueClasses to the input
    public void setUniqueClasses(int UniqueClasses) {
	this.UniqueClasses = UniqueClasses;
    }

    /*
     * Get Methods
     */

    // returns the array containing the data, after it has been processed AND shuffled
    public float[][] getDataArrayShuffled() {
	return DataArrayShuffled.clone();
    }

    // returns array containing the data, after it has been processed AND sorted
    public float[][] getDataArrayClassSorted() {
	return DataArrayClassSorted.clone();
    }

    // returns array containing the data, after it has been processed AND sorted
    public int getClassColumnPosition() {
	return ClassColumn;
    }

    // returns array containing the data, after it has been processed AND sorted
    public int getUniqueClasses() {
	return UniqueClasses;
    }
}