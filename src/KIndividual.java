import java.util.Random;


public class KIndividual {
	private double[][] table;
	private int width;
	private int height;
	private double fitness = -1;
	
	private int[] puzzlex;
	private int[] puzzley;
	
	public KIndividual(KakuroTable atable){
		table = KUtility.CellsToDoubles(atable);
		width = atable.getWidth();
		height = atable.getHeight();
		
	}
	
	public double[][] getTable(){return table;}
	
	
	private boolean isExcessiveVal(int r,int c){
		int i = r-1,j = c;
		while(i >= 0 && (table[i][j] != Double.NaN || table[i][j] >= 0)){
			if(table[i][j] < 0 && table[i][j] < table[r][c]) return false;
			--i;
		}
		i = r; j = c-1;
		while(j >= 0 && (table[i][j] != Double.NaN || table[i][j] >= 0)){
			if(table[i][j] < 0 && table[i][j] < table[r][c]) return false;
			--j;
		}
		
		return true;

	}
	private boolean isDuplicated(int r,int c){
		int i = r-1,j = c;
		while(i >= 0 && (table[i][j] != Double.NaN && table[i][j] >= 0)){
			if(table[i][j] == table[r][c]) return true;
			--i;
		}
//		i = r-1;j = c;
//		while(i < size() && (table[i][j] != Double.NaN || table[i][j] >= 0)){
//			if(table[i][j] == table[r][c]) return true;
//			++i;
//		}
		
		i = r; j = c-1;
		while(j >= 0 && (table[i][j] != Double.NaN && table[i][j] >= 0)){
			if(table[i][j] == table[r][c]) return true;
			--j;
		}
//		i = r; j = c-1;
//		while(j < size() && (table[i][j] != Double.NaN || table[i][j] >= 0)){
//			if(table[i][j] == table[r][c]) return true;
//			++j;
//		}
		return false;
	}
	
	
	public void generateSolution(){
		
		Random rand = new Random();
		
		for(int r = 0; r < width; ++r){
			for(int c = 0; c < height; ++c){
				if(table[r][c] == 0.0){
					do{
						table[r][c] = rand.nextInt(10-1)+1;
					}while(isDuplicated(r,c) && isExcessiveVal(r,c));
				}
				
			}
		}
		
	}
	
	public int size(){
		return table.length;
	}
	
	public double getCell(int r,int c) {
        return table[r][c];
    }

	public void setCell(int row,int col,double val){
    	double tmp = table[row][col];
    	table[row][col] = val;
    	if(isExcessiveVal(row,col) && isDuplicated(row,col)) table[row][col] = tmp;
    }
	
	// replace all the value in the cells that related to the puzzle cell row,col
	// it will be called in the operation crossover in Algorithm class
    public void setCrossVal(int row,int col, KIndividual indv) {
    	int r,c;
    	double num = indv.getCell(row, col);
    	Random rand = new Random();
		
    	if(KUtility.isDownOnly(num)){
    		r = row + 1; c = col;
    		while(r < indv.size() && table[r][c] >= 0){
    			setCell(r,c,indv.getCell(r, c));
    			++r;
    		}
    	}
    	else if(KUtility.isRightOnly(num)){
    		r = row; c = col + 1;
    		while(c < indv.size() && table[r][c] >= 0){
    			setCell(r,c,indv.getCell(r, c));
    			++c;
    		}
    	}
    	else{
    		r = row + 1; c = col;
    		while(r < indv.size() && table[r][c] >= 0){
    			setCell(r,c,indv.getCell(r, c));
    			++r;
    		}
    		r = row; c = col + 1;
    		while(c < indv.size() && table[r][c] >= 0){
    			setCell(r,c,indv.getCell(r, c));
    			++c;
    		}
    		
    	}
    	
        fitness = 0;
    }
    
    public double getBestTrait(){
    	int[] pX = KUtility.getPX();
    	int[] pY = KUtility.getPY();
    	int r,c;
    	int minDiff = Integer.MAX_VALUE;
    	int idX = 0, idY = 0;
    	for(int i = 0; i < pX.length; ++i){
    		int diffSum = 0;
    		if(KUtility.isDownOnly(table[pX[i]][pY[i]])){
        		diffSum += KUtility.sumDiffDown(table, pX[i], pY[i], table[pX[i]][pY[i]]);
    		}
        	else if(KUtility.isRightOnly(table[pX[i]][pY[i]])){
        		diffSum += KUtility.sumDiffRight(table, pX[i], pY[i], table[pX[i]][pY[i]]);
        	}
        	else{
        		diffSum += KUtility.sumDiffDown(table, pX[i], pY[i], table[pX[i]][pY[i]]);
        		diffSum += KUtility.sumDiffRight(table, pX[i], pY[i], table[pX[i]][pY[i]]);
        		diffSum = diffSum/2;
        	}
    		if(minDiff > diffSum) {
    			minDiff = diffSum;
    			idX = pX[i]; idY = pY[i];
    		}
    	}
    	double fullIdx = (1.0)*idX + (idY/10.0);
    	return fullIdx;
    }
    
	public double getFitness(){
		if(fitness == -1){
			
			fitness = FitnessCalc.getFitness(this.table);
		}
		
		return fitness;
	}
	
	
}
