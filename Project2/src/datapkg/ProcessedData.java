package datapkg;

public class ProcessedData {

    private float[][] DataArrayShuffled;    // array containing the data, after it has been processed AND shuffled
    private int ClassColumn; 		    // position of the class within both of the arrays

    public ProcessedData(int ClassColumn) {
	this.ClassColumn = ClassColumn;
    }

    /*
     * Set Methods
     */
    
    // sets the DataArrayShuffled to the input
    public void setDataArrayShuffled(float[][] DataArrayShuffled) {
	this.DataArrayShuffled = DataArrayShuffled;
    }

    // sets the ClassColumn to the input
    public void setClassColumn(int ClassColumn) {
	this.ClassColumn = ClassColumn;
    }


    /*
     * Get Methods
     */

    // returns the array containing the data, after it has been processed AND shuffled
    public float[][] getDataArrayShuffled() {
	return DataArrayShuffled.clone();
    }

    // returns array containing the data, after it has been processed AND sorted
    public int getClassColumnPosition() {
	return ClassColumn;
    }
}