public class NearestNeighbor {
	
	public float inPracticeData[][];
	public float inTestData[][];
	int classLocation;
	
	NearestNeighbor() {
		
	}
	
	void runClass() {
		System.out.println("Empty Implementation!");
	}
	
	void runRegress() {
		System.out.println("Empty Implementation!");
	}
	
	// Get the Euclidian distance between two points in N-Space
	float getDistance(float[] p1, float[] p2) {
		if(p1.length == p2.length) {
			float d = 0;
			
			// Get the total distance
			for(int i = 0; i < p1.length; i++) {
				if(i != classLocation) {
					float t = p2[i] - p1[i];
					t = t * t;
					d = d + t;
				}
			}
			
			// Take Square root of that distance
			d = (float) java.lang.Math.sqrt(d);
			
			return d;
		} else {
			System.out.println("TRIED TO FIND THE DISTANCE BETWEEN POINTS IN DIFFERENT"
							 + "DIMENSION SPACES!");
			return 0;
		}
	}
	
	public float[] nearestNeighbor(float[][] temp, float[] interest){
		float[] closestPoint = new float[temp[0].length];
		float[] tempPoint = new float[temp[0].length];
		float distance = (float) 100000.0;
		float tempDistance = (float) 100000.0;
		for(int i = 0 ; i < temp.length ; i++) {
			tempPoint = temp[i];
			tempDistance = getDistance(tempPoint, interest);
			if(tempDistance < distance) {
				distance = tempDistance;
				closestPoint = temp[i];
			}
			tempDistance = (float) 0.0;
		}
		return closestPoint;
	}
	
	public void setPracticeData(float[][] d) {
		inPracticeData = d;
	}
	
	public void setTestData(float[][] d) {
		inTestData = d;
	}
	
	public void setClassLocation(int loc) {
		classLocation = loc;
	}
}
