
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
		
		//k needs to equal the amount of points returned from edited nearest neighbor and condensed
		float dataLength = data.length;
		float kSet = dataLength/k;
		//float kENN = datalength/returned points condensed
		//float kCNN = dataLenght/returned points condensed
		if(kSet < 1) {
			System.err.println("NOT ENOUGH DATA TO SPLIT");
			System.exit(-1);
		}
		
		double accuracy = 0.0d;
		
		for(int i = 0; i < k; i++) {
			
			int kStart = Math.round(kSet * i);
			float trainData[][] = new float[Math.round(kSet * (k-1))][data[0].length];
			float testData[][] = new float[(int)(dataLength - Math.round(kSet * (k-1)))][data[0].length];
			
			//get training data
			for(int x = 0; x < kStart; x++) {
				trainData[x] = data[x];
			}
			
			//get test data
//			for(int b = 0; b <)
			
		}
	}
}