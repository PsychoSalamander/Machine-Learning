public class NearestNeighbor {
	
	float inPracticeData[][];
	float inTestData[][];
	
	NearestNeighbor() {
		
	}
	
	void runClass() {
		System.out.println("Empty Implementation!");
	}
	
	void runRegress() {
		System.out.println("Empty Implementation!");
	}
	
	// Get the distance between two points in N-Space
	float getDistance(float[] p1, float[] p2) {
		if(p1.length == p2.length) {
			float d = 0;
			
			// Get the total distance
			for(int i = 0; i < p1.length; i++) {
				float t = p2[i] - p1[i];
				t = t * t;
				d = d + t;
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
}
