
public class FitnessCalc {
	

	/**
	 * Compute the fitness value from the table
	 * @param table
	 * @return
	 */
	
	
	public static double getFitness(double[][] table){
		int[] x = KUtility.getPX();
		int[] y = KUtility.getPY();
		
		
		
		
		double diffSum = 0.0;
		

		int count = 0;
		for(int i = 0; i < x.length; ++i){
			double num = (-1)*table[x[i]][y[i]];
			
			//if down only
			if(KUtility.isDownOnly(num)){
				diffSum += KUtility.sumDiffDown(table,x[i],y[i],num);
				++count;
			}
			// if right only
			else if(KUtility.isRightOnly(num)){
				diffSum += KUtility.sumDiffRight(table,x[i],y[i],num);
				++count;
			}
			// if down right
			else{
				diffSum += KUtility.sumDiffDown(table,x[i],y[i],num);
				diffSum += KUtility.sumDiffRight(table,x[i],y[i],num);
				count += 2;
			}
		}
		
		
		return diffSum/count;
	}
	
	/**
	 * get the maximum fitness
	 * @return
	 */
	static double getMaxFitness() {
        return 0;
    }
}
