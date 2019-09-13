//gotta catch'em all
public class DataTrainer {
	/* cancer_lines = 683 | attributes = 10
	 * glass_lines = 214  | attributes = 10
	 * iris_lines = 150   | attributes = 5
	 * soybeans_lines = 47| attributes = 36
	 * votes_lines = 435  | attributes = 17
	 * total = 1529			total = 78
	 */
	//cancer attribute instances to be divided by total set # in training set equation step
	float clumpThickDT = 683; 			//Clump Thickness
	float unifCellSizeDT = 683;			//Uniformity of Cell Size 
	float unifCellShapeDT = 683;		//Uniformity of Cell Shape 
	float margAdhDT = 683;				//Marginal Adhesion
	float snglEpCellSizeDT = 683;		// Single Epithelial Cell Size
	float bareNucDT = 683;				//Bare Nuclei
	float blandChnDT = 683;				//Bland Chromatin
	float NormNcliDT = 683;				//Normal Nucleoli
	float mitossisDT = 683;				//Mitosis
	float classDT = 683;				//class
	float cancerAtt = 10;
	
	DataTrainer()
	{
		
	}
	
	//function to choose 80% of the data for training
	float chooseAtt(int g)
	{
		float AttAmt = (float) Math.ceil(g*0.8);
		return AttAmt;
	
	}
}
