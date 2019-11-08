package datapkg;

public class ProcessedData {

    private float[][] DataArrayShuffled;    // array containing the data, after it has been processed AND shuffled
    private int ClassColumn; 		    // position of the class within both of the arrays
    private int NumberOfClasses;	    // number of classes for a given dataset

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
    
    // sets the number of classes given in a data set
    public void setNumberOfClasses(int NumberOfClasses) {
	this.NumberOfClasses = NumberOfClasses;
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
    
    // returns the number of classes given in a data set
    public int getNumberOfClasses() {
	return NumberOfClasses;
    }
}