
public class TrainedModel {
	
	// Class to hold all of the training data to be used by the test classed.
	
	public Double[][] priors;
	public Double[][] evidence;
	public Double[][] evidenceL;
	
	TrainedModel() {
		priors = new Double[1][1];
		evidence = new Double[1][1];
		evidenceL = new Double[1][1];
	}
}
