import java.util.ArrayList;

/**
 * KUtility is a class that contains many helper function
 * to support many other operations during the coputation
 * @author purit
 *
 */

public class KUtility {
	
	private static KakuroTable table;
	private static int[] pX;
	private static int[] pY;
	
	/**
	 * Extract the index of the puzzöe cells into pX an pY
	 */
	private static void initPuzzleXYs(){
		double[][] atable = CellsToDoubles(table);
		ArrayList<Integer> coords = new ArrayList<Integer>();
		for(int r = 0; r < table.getWidth(); ++r){
			for(int c = 0; c < table.getHeight(); ++c){
				if(table.getCell(r, c) != null && atable[r][c] < 0){
					coords.add(r);coords.add(c);
				}
			}
		}
		pX = new int[coords.size()/2]; int ix = 0;
		pY = new int[coords.size()/2]; int iy = 0;
		for(int i = 0; i < coords.size(); ++i){
			if(i % 2 == 0){
				pX[ix] = coords.get(i); ++ix;
			}
			else{
				pY[iy] = coords.get(i); ++iy;
			}
		}
	}
	
	
	/**
	 * initialize the table
	 * @param atable
	 */
	public static void init(KakuroTable atable){
		table = atable;
		initPuzzleXYs();
	}
	
	/**
	 * get Row indexes of the puzzle cells
	 * @return
	 */
	public static int[] getPX(){return pX;}
	
	/**
	 * get Column indexes of the puzzle cells
	 * @return
	 */
	public static int[] getPY(){return pY;}
	
	/**
	 * get an empty table with only the puzzle cells present
	 * @return
	 */
	public static KakuroTable getTable(){return table;}
	
	/**
	 * print the table which has the data type double[][]
	 * @param atable
	 */
	public static void printRawTable(double[][] atable){
		for(int i = 0; i < atable.length; ++i){
			for(int j = 0; j < atable[i].length; ++j){
				if(Double.isNaN(atable[i][j])){
					System.out.print("   ");
					System.out.print(atable[i][j]);
					
				}
				else
					System.out.format("%6.2f", atable[i][j]);
				System.out.print("|");
			}
			System.out.println();
			System.out.println();
		}
		
	}
	
	/**
	 * change from number format "A" to "0.A"
	 * @param val the value to be formatted
	 * @return
	 */
	public static double rightValueFormat(double val){
		if(val > 9) val /= 100;
		else val /= 10;
		return val;
	}
	
	/**
	 * create the value in the new table from right and down value in the cell 
	 * @param cell
	 * @return
	 */
	public static double convertPuzzleCellValue(KakuroCell cell){
		double val = 0.0;
		val += cell.getDownValue();
		if(cell.getRightValue() != 0){
			val += rightValueFormat(cell.getRightValue());
		}
		val *= -1;
		
		return val;
	}
	
	/**
	 * Compute the difference between the sum of the corresponding 
	 * (Down)play cells of the puzzle cells in r row and c column and the puzzle cell value
	 * @param table
	 * @param row
	 * @param col
	 * @param num
	 * @return
	 */
	public static int sumDiffDown(double[][] table,int row,int col,double num){
		int r = row + 1,c = col;
		String[] arr=String.valueOf(num).split("\\.");
		double val = num;
		
		double sum = 0.0;
		while(r < table.length && table[r][c] >= 0){
			sum += table[r][c];
			++r;
			
		}
		return (int)Math.abs(num - sum);
	}
	
	/**
	 * Compute the difference between the sum of the corresponding 
	 * (Right)play cells of the puzzle cells in r row and c column and the puzzle cell value
	 * @param table
	 * @param row
	 * @param col
	 * @param num
	 * @return
	 */
	public static int sumDiffRight(double[][] table,int row,int col,double num){
		int r = row,c = col + 1;
		
		String[] arr=String.valueOf(num).split("\\.");
		double sum = 0.0;
		while(c < table.length && table[r][c] >= 0){
			sum += table[r][c];
			++c;
			
		}
		return (int)Math.abs(num - sum);
	}
	
	
	/**
	 * Convert the value of the KakuroTable and change the format into double[][] 
	 * instead for easy in performing the algorithm
	 * @param atable
	 * @return
	 */
	public static double[][] CellsToDoubles(KakuroTable atable){
		double[][] newTable = new double[atable.getWidth()][atable.getHeight()];
		for(int r = 0; r < atable.getWidth(); ++r){
			for(int c = 0; c < atable.getWidth(); ++c){
				if(atable.getCell(r, c) == null)
					newTable[r][c] = Double.NaN;
				else if(atable.getCell(r, c).isPuzzleCell()){
					newTable[r][c] = convertPuzzleCellValue(atable.getCell(r,c));
					
				}
				
			}
		}
		
		
		return newTable;
	}
	
	
	/**
	 * is the puzzle cell "num" has only the down value or not
	 * @param num
	 * @return
	 */
	public static boolean isDownOnly(double num){
		String n = Double.toString(num);
		boolean PointFound = false; 
		for(int i = n.length()-1; i >= 0; --i){
			if(n.charAt(i) == '.') break;
			if(n.charAt(i) != '.' && n.charAt(i) != '0' && !PointFound) return false;
		}
		
		return true;
	}
	
	/**
	 * is the puzzle cell "num" has only the right value or not
	 * @param num
	 * @return
	 */
	public static boolean isRightOnly(double num){
		
		String n = Double.toString(num);
		boolean PointFound = false; 
		for(int i = 0; i < n.length(); ++i){
			if(n.charAt(i) == '.') break;
			if(n.charAt(i) != '.' && n.charAt(i) != '0' && !PointFound) return false;
		}
		
		return true;

	}
	
	/**
	 * is the puzzle cell "num" contains both right and down values or not
	 * @param num
	 * @return
	 */
	public static boolean isDownRight(double num){
		String n = Double.toString(num);
		boolean numFound = false;
		int i = 0;
		while(n.charAt(i) != '.'){
			if(n.charAt(i) != '0'){
				numFound = true;
				break;
			}
			++i;
		}
		if(!numFound) return false;
		numFound = false;
		i = n.length() - 1;
		while(n.charAt(i) != '.'){
			if(n.charAt(i) != '0'){
				numFound = true;
				break;
			}
			--i;
		}
		if(!numFound) return false;
		
		
		return true;
	}
	
	
	


}
