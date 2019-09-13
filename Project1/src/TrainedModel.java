
public class TrainedModel {
	
	// Class to hold all of the training data to be used by the test classed.
	
	public double[][] priors;
	public double[][] evidence;
	public double[][] evidenceL;
	
	TrainedModel() {
		priors = new double[1][1];
		evidence = new double[1][1];
		evidenceL = new double[1][1];
	}
}
