import java.util.Random;

public class RandomTest {
	final private static int WIDTH = 8;
    final private static int HEIGHT = 8;
    
	public static boolean isDuplicatedM(int r,int c,double[][] table){
		int i = r-1,j = c;
		while(i >= 0 && (table[i][j] != Double.NaN && table[i][j] >= 0)){
			if(table[i][j] == table[r][c]) return true;
			--i;
		}
		i = r+1;j = c;
		while(i < table.length && (table[i][j] != Double.NaN && table[i][j] >= 0)){
			if(table[i][j] == table[r][c]) return true;
			++i;
		}
		
		i = r; j = c-1;
		while(j >= 0 && (table[i][j] != Double.NaN && table[i][j] >= 0)){
			if(table[i][j] == table[r][c]) return true;
			--j;
		}
		i = r; j = c+1;
		while(j < table.length && (table[i][j] != Double.NaN && table[i][j] >= 0)){
			if(table[i][j] == table[r][c]) return true;
			++j;
		}
		return false;
	}
	
	
	public static KIndividual crossoverV3M(KIndividual indiv1, KIndividual indiv2){
    	KIndividual newSol = new KIndividual(KUtility.getTable());
        double idiv1BestTraits = indiv1.getBestTrait();
        
        double idiv2BestTraits = indiv2.getBestTrait();
        System.out.println(idiv1BestTraits);
        System.out.println(idiv2BestTraits);
        System.out.println();
        
        String[] arr=String.valueOf(idiv1BestTraits).split("\\.");
        newSol.setCrossVal(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), indiv1);
        
        arr=String.valueOf(idiv2BestTraits).split("\\.");
        newSol.setCrossVal(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), indiv2);
        
    	newSol.generateSolution();
    	return newSol;
    }
	
	public static void adjustM(double[][] table){
    	Random rand = new Random();
    	for(int r = 0; r < table.length; ++r){
    		for(int c = 0; c < table.length; ++c){
    			if(table[r][c] == Double.NaN || table[r][c] < 0){
    				continue;
    			}
    			while(isDuplicatedM(r,c,table)){
    				int n = rand.nextInt(10-1)+1;
    				if(n % 2 == 0 && table[r][c] <= 9) table[r][c] += 1;
    				else if(n % 2 == 1 && table[r][c] > 1) table[r][c] -= 1;
    				else table[r][c] = n;
    			}
    		}
    	}
    }
//	public static void main(String[] args){
//		KakuroGUI game = new KakuroGUI();
//        
//        KakuroTable puzzle = new KakuroTable(WIDTH, HEIGHT);
//        
//        /****************
//         * Puzzle Cells *
//         ****************/
//        // first row
//        puzzle.addPuzzleCell(0, 1, 23, 0);
//        puzzle.addPuzzleCell(0, 2, 30, 0);  
//        puzzle.addPuzzleCell(0, 5, 27, 0);
//        puzzle.addPuzzleCell(0, 6, 12, 0);
//        puzzle.addPuzzleCell(0, 7, 16, 0);
//        // second row
//        puzzle.addPuzzleCell(1, 0, 0, 16);
//        puzzle.addPuzzleCell(1, 4, 17, 24);  
//        // third row
//        puzzle.addPuzzleCell(2, 0, 0, 17);
//        puzzle.addPuzzleCell(2, 3, 15, 29);  
//        // fourth row
//        puzzle.addPuzzleCell(3, 0, 0, 35);
//        puzzle.addPuzzleCell(3, 6, 12, 0);  
//        // fifth row
//        puzzle.addPuzzleCell(4, 1, 0, 7);
//        puzzle.addPuzzleCell(4, 4, 7, 8);  
//        puzzle.addPuzzleCell(4, 7, 7, 0);
//        // sixth row
//        puzzle.addPuzzleCell(5, 1, 11, 0);  
//        puzzle.addPuzzleCell(5, 2, 10, 16);
//        // seventh row
//        puzzle.addPuzzleCell(6, 0, 0, 21);  
//        puzzle.addPuzzleCell(6, 5, 0, 5);
//        // eighth row
//        puzzle.addPuzzleCell(7, 0, 0, 6);  
//        puzzle.addPuzzleCell(7, 5, 0, 3);
//        
//        /**************
//         * Play Cells *
//         **************/
//        // first row
//        puzzle.addPlayCell(1, 1);
//        puzzle.addPlayCell(1, 2);
//        puzzle.addPlayCell(1, 5);
//        puzzle.addPlayCell(1, 6);
//        puzzle.addPlayCell(1, 7);
//        // second row
//        puzzle.addPlayCell(2, 1);
//        puzzle.addPlayCell(2, 2);
//        puzzle.addPlayCell(2, 4);
//        puzzle.addPlayCell(2, 5);
//        puzzle.addPlayCell(2, 6);
//        puzzle.addPlayCell(2, 7);
//        // third row
//        puzzle.addPlayCell(3, 1);
//        puzzle.addPlayCell(3, 2);
//        puzzle.addPlayCell(3, 3);
//        puzzle.addPlayCell(3, 4);
//        puzzle.addPlayCell(3, 5);
//        // forth row
//        puzzle.addPlayCell(4, 2);
//        puzzle.addPlayCell(4, 3);
//        puzzle.addPlayCell(4, 5);
//        puzzle.addPlayCell(4, 6);
//        // fifth row
//        puzzle.addPlayCell(5, 3);
//        puzzle.addPlayCell(5, 4);
//        puzzle.addPlayCell(5, 5);
//        puzzle.addPlayCell(5, 6);
//        puzzle.addPlayCell(5, 7);
//        // sixth row
//        puzzle.addPlayCell(6, 1);
//        puzzle.addPlayCell(6, 2);
//        puzzle.addPlayCell(6, 3);
//        puzzle.addPlayCell(6, 4);
//        puzzle.addPlayCell(6, 6);
//        puzzle.addPlayCell(6, 7);
//        // seventh row
//        puzzle.addPlayCell(7, 1);
//        puzzle.addPlayCell(7, 2);
//        puzzle.addPlayCell(7, 3);
//        puzzle.addPlayCell(7, 6);
//        puzzle.addPlayCell(7, 7);
//        
//        game.loadPuzzle(puzzle);
//        KUtility.init(puzzle);
//        KIndividual indv = new KIndividual(KUtility.getTable());
//        indv.generateSolution();
//        double[][] table = indv.getTable();
//        KUtility.printRawTable(table);
//        
//        // Evaluate
//        String[][] result = new String[table.length][table.length];
//        for(int i = 0; i < table.length; ++i){
//        	for(int j = 0; j < table.length; ++j){
//        		if(table[i][j] == Double.NaN || table[i][j] < 0) result[i][j] = new String("______");
//        		else if(isDuplicatedM(i,j,table)){
//        			result[i][j] = new String("__true");
//        		}
//        		else if(!isDuplicatedM(i,j,table)){
//        			result[i][j] = new String("_false");
//        		}
//        	}
//        }
//        // Print
////        for(int i = 0; i < result.length; ++i){
////        	for(int j = 0; j < result.length; ++j){
////        		System.out.print(result[i][j] + "|");
////        	}
////        	System.out.println();
////        }
//        // Begin testing adjust function
//        System.out.println("USING ADJUST");
//        indv.adjust();
//        KUtility.printRawTable(indv.getTable());
//        result = new String[table.length][table.length];
//        for(int i = 0; i < table.length; ++i){
//        	for(int j = 0; j < table.length; ++j){
//        		if(table[i][j] == Double.NaN || table[i][j] < 0) result[i][j] = new String("______");
//        		else if(isDuplicatedM(i,j,table)){
//        			result[i][j] = new String("__true");
//        		}
//        		else if(!isDuplicatedM(i,j,table)){
//        			result[i][j] = new String("_false");
//        		}
//        	}
//        }
//        // Print
////        for(int i = 0; i < result.length; ++i){
////        	for(int j = 0; j < result.length; ++j){
////        		System.out.print(result[i][j] + "|");
////        	}
////        	System.out.println();
////        }
//        
//        // Begin testing cross over function
//        System.out.println("BEGIN TESTING CROSSOVER FUNCTION");
//        
//        // Creating another individual
//        KIndividual anotherIndv = new KIndividual(KUtility.getTable());
//        anotherIndv.generateSolution();
//        anotherIndv.adjust();
//        
//        
//        // result indicidual from cross over indv and anotherIndv
//        KIndividual newIndv = crossoverV3M(indv, anotherIndv);
//        System.out.println("THE RESULT OF THE CROSSOVER: newIndv");
//        KUtility.printRawTable(newIndv.getTable());
//        table = newIndv.getTable();
//        result = new String[table.length][table.length];
//        for(int i = 0; i < table.length; ++i){
//        	for(int j = 0; j < table.length; ++j){
//        		if(table[i][j] == Double.NaN || table[i][j] < 0) result[i][j] = new String("______");
//        		else if(isDuplicatedM(i,j,table)){
//        			result[i][j] = new String("__true");
//        		}
//        		else if(!isDuplicatedM(i,j,table)){
//        			result[i][j] = new String("_false");
//        		}
//        	}
//        }
//        for(int i = 0; i < result.length; ++i){
//        	for(int j = 0; j < result.length; ++j){
//        		System.out.print(result[i][j] + "|");
//        	}
//        	System.out.println();
//        }
//
//        System.out.println("USING ADJUST ON newIndv");
//        newIndv.adjust();
//        table = newIndv.getTable();
//        result = new String[table.length][table.length];
//        for(int i = 0; i < table.length; ++i){
//        	for(int j = 0; j < table.length; ++j){
//        		if(table[i][j] == Double.NaN || table[i][j] < 0) result[i][j] = new String("______");
//        		else if(isDuplicatedM(i,j,table)){
//        			result[i][j] = new String("__true");
//        		}
//        		else if(!isDuplicatedM(i,j,table)){
//        			result[i][j] = new String("_false");
//        		}
//        	}
//        }
//        for(int i = 0; i < result.length; ++i){
//        	for(int j = 0; j < result.length; ++j){
//        		System.out.print(result[i][j] + "|");
//        	}
//        	System.out.println();
//        }
//	}
}
