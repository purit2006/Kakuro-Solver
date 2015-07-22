import java.util.Random;


public class KIndividual {
	// Main table which represents the puzzles and solutions
	private double[][] table;
	private int width;
	private int height;
	
	
	//fitness value
	//-1 indicates that this individual has gone through changes and it's fitness has to be calculated again
	private double fitness = -1;
	
	/**
	 * Constructor
	 * @param atable
	 */
	public KIndividual(KakuroTable atable){
		table = KUtility.CellsToDoubles(atable);
		width = atable.getWidth();
		height = atable.getHeight();
		
	}
	
	/**
	 * return the table
	 * @return
	 */
	public double[][] getTable(){return table;}
	
	
	
	
	/**
	 * Test if the value in table[r][c] exceed it's correspond puzzle value or not
	 * @param r Row where the target cell is
	 * @param c Col where the target cell is
	 * @return
	 */
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
	
	/**
	 * Test if the value in table[r][c] has it's duplicated value in the same vertical
	 * and horizontal line or not
	 * @param r Row where the target cell is
	 * @param c Col where the target cell is
	 * @return
	 */
	private boolean isDuplicated(int r,int c){
		int i = r-1,j = c;
		while(i >= 0 && (table[i][j] != Double.NaN && table[i][j] >= 0)){
			if(table[i][j] == table[r][c]) return true;
			--i;
		}
		i = r+1;j = c;
		while(i < size() && (table[i][j] != Double.NaN && table[i][j] >= 0)){
			if(table[i][j] == table[r][c]) return true;
			++i;
		}
		
		i = r; j = c-1;
		while(j >= 0 && (table[i][j] != Double.NaN && table[i][j] >= 0)){
			if(table[i][j] == table[r][c]) return true;
			--j;
		}
		i = r; j = c+1;
		while(j < size() && (table[i][j] != Double.NaN && table[i][j] >= 0)){
			if(table[i][j] == table[r][c]) return true;
			++j;
		}
		return false;
	}
	
	/**
	 * Automatically random the numbers and assign them into an empty play cell
	 */
	public void generateSolution(){
		
		Random rand = new Random();
		
		for(int r = 0; r < width; ++r){
			for(int c = 0; c < height; ++c){
				if(table[r][c] == 0.0){
					do{
						table[r][c] = rand.nextInt(10-1)+1;
					}while(isDuplicated(r,c) || isExcessiveVal(r,c));
				}
				
			}
		}
		// Have to reset the fitness value because of there is changes within the individual
		// it has to be recalculated again.
		fitness = -1;
	}
	
	/**
	 * return the size of the tabl (only one side, the table intend to be created as a square)
	 * @return
	 */
	public int size(){
		return table.length;
	}
	
	/**
	 * get the value in the cell
	 * @param r Row of the target cell
	 * @param c Column of the target cell
	 * @return
	 */
	public double getCell(int r,int c) {
        return table[r][c];
    }
	
	/**
	 * add the value val into table[r][c]
	 * @param row Row of the target cell to be assign the value
	 * @param col Column of the target cell to be assign the value
	 * @param val value to be assigned
	 * @return
	 */
	public boolean setCell(int row,int col,double val){
		Random rand = new Random();
    	double tmp = table[row][col];
    	if(val <= 0 || val > 9) return false;
    	table[row][col] = val;
    	while(isExcessiveVal(row,col) || isDuplicated(row,col)) {
    		table[row][col] = rand.nextInt(10-1)+1;
    		
    	}
    	return true;
    }
	
	/**
	 * replace all the value in the cells that related to the puzzle cell row,col
	 * it will be called in the operation crossover in Algorithm class
	 * @param row Row of the target puzzle cell
	 * @param col Column of the target puzzle cell
	 * @param indv the resource individual in which we pick the value from
	 */
	public void setCrossVal(int row,int col, KIndividual indv) {
    	int r,c;
    	double num = indv.getCell(row, col);
    	Random rand = new Random();
		
    	// if the puzzle cell has only it's down corresponding play cells
    	if(KUtility.isDownOnly(num)){
    		r = row + 1; c = col;
    		while(r < indv.size() && table[r][c] >= 0){
    			setCell(r,c,indv.getCell(r, c));
    			++r;
    		}
    	}
    	// if the puzzle cell has only it's right corresponding play cells
    	else if(KUtility.isRightOnly(num)){
    		r = row; c = col + 1;
    		while(c < indv.size() && table[r][c] >= 0){
    			setCell(r,c,indv.getCell(r, c));
    			++c;
    		}
    	}
    	// the puzzle cell has both down and right corresponding play cells
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
    	
    	// reset the fitness value because of some changes in the individual
    	fitness = -1;
        
    }
    
	/**
	 * Find the puzzle which has the minimum of the difference between it's value and it's
	 * corresponding sum of it's play cells
	 * @return Indexes of the puzzle cell in a format of [row.col] double value
	 */
    public double getBestTrait(){
    	int[] pX = KUtility.getPX();
    	int[] pY = KUtility.getPY();
    	
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
    	// make the format
    	double fullIdx = (1.0)*idX + (idY/10.0);
    	return fullIdx;
    }
    
    // There is a problem with the crossover which results in Duplicate value.
    // This is not a good solution that just re-adjust all the value in the play cells table.
//    public void adjust(){
//    	Random rand = new Random();
//    	for(int r = 0; r < size(); ++r){
//    		for(int c = 0; c < size(); ++c){
//    			if(table[r][c] == Double.NaN || table[r][c] < 0){
//    				continue;
//    			}
//    			while(isDuplicated(r,c)){
//    				int n = rand.nextInt(10-1)+1;
//    				if(n % 2 == 0 && table[r][c] < 9) table[r][c] += 1;
//    				else if(n % 2 == 1 && table[r][c] > 1) table[r][c] -= 1;
//    				else table[r][c] = n;
//    			}
//    		}
//    	}
//    }
    
    /**
     * get fitness
     * @return
     */
	public double getFitness(){
		
		if(fitness == -1){
			
			fitness = FitnessCalc.getFitness(this.table);
		}
		
		return fitness;
	}
	
	
}
