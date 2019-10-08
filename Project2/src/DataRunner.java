
public class DataRunner {
	
	private KNearestNeighbor KNN;
	private ENearestNeighbor ENN;
	private CNearestNeighbor CNN;
	private KMeansNearestNeighbor KMNN;
	private PAMNearestNeighbor PAMNN;
	
	DataRunner() {
		KNN = new KNearestNeighbor();
		ENN = new ENearestNeighbor();
		CNN = new CNearestNeighbor();
		KMNN = new KMeansNearestNeighbor();
		PAMNN = new PAMNearestNeighbor();
	}
	
	public void runTests() {
		
	}
	
	public void runKFold(int k, NearestNeighbor algorithm, float data[][]) {
		
	}
}
