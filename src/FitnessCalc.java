
public class FitnessCalc {
	
	
	
	
	
	
	private static boolean checkDown(double[][] table,int row,int col,double num){
		int r = row + 1,c = col;
		String[] arr=String.valueOf(num).split("\\.");
		double val = num;
		
		double sum = 0.0;
		while(r < table.length && table[r][c] >= 0){
			sum += table[r][c];
			++r;
			
		}
		if(Integer.parseInt(arr[0]) == sum) return true;
		return false;
	}
	
	private static boolean checkRight(double[][] table,int row,int col,double num){
		int r = row,c = col + 1;
		
		String[] arr=String.valueOf(num).split("\\.");
		double sum = 0.0;
		while(c < table.length && table[r][c] >= 0){
			sum += table[r][c];
			++c;
			
		}
		if(Integer.parseInt(arr[1]) == sum) return true;
		return false;
	}
	private static boolean checkDownRight(double[][] table,int row,int col,double num){
		return checkDown(table,row,col,num) && checkRight(table,row,col,num);
	
	}
	
	
	
	
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
	
	static double getMaxFitness() {
        //int maxFitness = KUtility.getPX().length;
        return 0;
    }
}
