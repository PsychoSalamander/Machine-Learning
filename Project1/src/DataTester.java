import java.util.ArrayList;

public class DataTester {

	public DataTester() {
	
	}
	
	public int smallTest(double probabilityOfClass[][], double probabilityOfEvidence[][], double probabilityGivenLiklihood[][], ArrayList<Integer> atts) {
		int perferredclass = 0;
		int totalValues = 0;
		float smoothingProbability = (float)0;
		float tempProbability = (float) 0.000000000000000000000000000000000000000000000000000001;
		boolean found = false;
		for(int i = 0; i < probabilityGivenLiklihood[0].length;i++) {
			totalValues += probabilityGivenLiklihood[2][i];
		}
		smoothingProbability = (float)1/totalValues;
		for(int h = 0 ; h < probabilityOfClass[0].length; h++) {
			float likelihoodProbability = (float)probabilityOfClass[1][h];
			for(int j = 0 ; j < atts.size(); j++) {
				for(int i = 0; i < probabilityGivenLiklihood[0].length;i++) {
					if(probabilityGivenLiklihood[1][i] == atts.get(j) && probabilityGivenLiklihood[0][i] == probabilityOfClass[0][h]) {
						likelihoodProbability *= (float)probabilityGivenLiklihood[3][i]+(float)smoothingProbability;
						found = true;
					}
				}
				if(!found) {
					likelihoodProbability *= (float)smoothingProbability;
				}
				found = false;
			}
			if(likelihoodProbability > tempProbability) {
				tempProbability = (float)likelihoodProbability;
				perferredclass = (int) probabilityOfClass[0][h];
			}
		}
		return perferredclass;
	}
}
