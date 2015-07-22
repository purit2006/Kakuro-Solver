import java.util.Scanner;


/**
 * Javadoc first introduction paragraph here!
 * 
 * @author Purit Thong-On 1106
 * @author Marcus Vinicius Pereira Araujo 1106149
 * @author Piyapat Russamitinakornkul 1106291
 * 
 * @version 1
 * @since 16/06/2015
 */
public class Main {
    final private static int WIDTH = 8;
    final private static int HEIGHT = 8;
    private static KakuroTable puzzle;
    /*****************
     * Main Function *
     ****************/
    /**
     * Main function of the program.
     * @param args Command-Line Arguments
     */
    public static void main(String[] args) {
        KakuroGUI game = new KakuroGUI();
        
        
        puzzleHard();
        game.loadPuzzle(puzzle);
        KUtility.init(puzzle);
        
     // Create an initial population
        KPopulation myPop = new KPopulation(1000,true);
        double curFittestVal = myPop.getFittest().getFitness();
        double prevFittestVal;
     // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        int newFittestGeneration = 0;
        while (curFittestVal > FitnessCalc.getMaxFitness()) {
            generationCount++;
            
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            //if(generationCount % 10 == 0) {
            try {
                game.setTitle(game.TITLE + " - " + Integer.toString(generationCount));
                for(int i = 0 ; i < puzzle.getWidth() ; ++i)
                    for(int j = 0 ; j < puzzle.getHeight() ; ++j)
                        if(puzzle.isPlayCell(i, j))
                            puzzle.fillCell(i, j, (int)myPop.getFittest().getTable()[i][j]);
                game.loadPuzzle(puzzle);
                Thread.sleep(100);
            }
            catch(Exception e) {e.printStackTrace();}
            //}
            
            // record the current fittest value of the generation before evolving
            prevFittestVal = curFittestVal;
            // evolve : Crossover and Mutate individuals
            myPop = Algorithm.evolvePopulation(myPop);
            
            // update the current fittest value
            curFittestVal = myPop.getFittest().getFitness();
            
            // if the new fitness is found
            if(curFittestVal != prevFittestVal){
            	newFittestGeneration = generationCount;
            }
            
            // if the value of the fitness not changing over some generation
            else if((generationCount - newFittestGeneration) > 100 && curFittestVal == prevFittestVal){
            	System.out.println("BEFORE RESET ELITE: " + myPop.getFittest().getFitness());
            	myPop.resetElite();
            	System.out.println("AFTER RESET ELITE: "+ myPop.getFittest().getFitness());
            	
            	curFittestVal = myPop.getFittest().getFitness();
            	newFittestGeneration = generationCount;
            }
            
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        KUtility.printRawTable(myPop.getFittest().getTable());
        System.out.println(myPop.getFittest().getFitness());
    }
    
    /**
     * Contains easy data for creating a hard Kakuro game.
     */
    public static void puzzleHard(){
    	puzzle = new KakuroTable(WIDTH, HEIGHT);
    	/****************
         * Puzzle Cells *
         ****************/
        
    	// first row
        puzzle.addPuzzleCell(0, 1, 23, 0);
        puzzle.addPuzzleCell(0, 2, 30, 0);  
        puzzle.addPuzzleCell(0, 5, 27, 0);
        puzzle.addPuzzleCell(0, 6, 12, 0);
        puzzle.addPuzzleCell(0, 7, 16, 0);
        // second row
        puzzle.addPuzzleCell(1, 0, 0, 16);
        puzzle.addPuzzleCell(1, 4, 17, 24);  
        // third row
        puzzle.addPuzzleCell(2, 0, 0, 17);
        puzzle.addPuzzleCell(2, 3, 15, 29);  
        // fourth row
        puzzle.addPuzzleCell(3, 0, 0, 35);
        puzzle.addPuzzleCell(3, 6, 12, 0);  
        // fifth row
        puzzle.addPuzzleCell(4, 1, 0, 7);
        puzzle.addPuzzleCell(4, 4, 7, 8);  
        puzzle.addPuzzleCell(4, 7, 7, 0);
        // sixth row
        puzzle.addPuzzleCell(5, 1, 11, 0);  
        puzzle.addPuzzleCell(5, 2, 10, 16);
        // seventh row
        puzzle.addPuzzleCell(6, 0, 0, 21);  
        puzzle.addPuzzleCell(6, 5, 0, 5);
        // eighth row
        puzzle.addPuzzleCell(7, 0, 0, 6);  
        puzzle.addPuzzleCell(7, 5, 0, 3);
        
        /**************
         * Play Cells *
         **************/
        // first row
        puzzle.addPlayCell(1, 1);
        puzzle.addPlayCell(1, 2);
        puzzle.addPlayCell(1, 5);
        puzzle.addPlayCell(1, 6);
        puzzle.addPlayCell(1, 7);
        // second row
        puzzle.addPlayCell(2, 1);
        puzzle.addPlayCell(2, 2);
        puzzle.addPlayCell(2, 4);
        puzzle.addPlayCell(2, 5);
        puzzle.addPlayCell(2, 6);
        puzzle.addPlayCell(2, 7);
        // third row
        puzzle.addPlayCell(3, 1);
        puzzle.addPlayCell(3, 2);
        puzzle.addPlayCell(3, 3);
        puzzle.addPlayCell(3, 4);
        puzzle.addPlayCell(3, 5);
        // forth row
        puzzle.addPlayCell(4, 2);
        puzzle.addPlayCell(4, 3);
        puzzle.addPlayCell(4, 5);
        puzzle.addPlayCell(4, 6);
        // fifth row
        puzzle.addPlayCell(5, 3);
        puzzle.addPlayCell(5, 4);
        puzzle.addPlayCell(5, 5);
        puzzle.addPlayCell(5, 6);
        puzzle.addPlayCell(5, 7);
        // sixth row
        puzzle.addPlayCell(6, 1);
        puzzle.addPlayCell(6, 2);
        puzzle.addPlayCell(6, 3);
        puzzle.addPlayCell(6, 4);
        puzzle.addPlayCell(6, 6);
        puzzle.addPlayCell(6, 7);
        // seventh row
        puzzle.addPlayCell(7, 1);
        puzzle.addPlayCell(7, 2);
        puzzle.addPlayCell(7, 3);
        puzzle.addPlayCell(7, 6);
        puzzle.addPlayCell(7, 7);
    }
    
    /**
     * Contains easy data for creating a medium difficulty Kakuro game.
     */
    public static void puzzleMedium(){
    	
    	puzzle = new KakuroTable(WIDTH, HEIGHT);
    	/****************
         * Puzzle Cells *
         ****************/
        
    	// 1st row
        puzzle.addPuzzleCell(0, 3, 9, 0);
        puzzle.addPuzzleCell(0, 4, 9, 0);
        // 2nd row
        puzzle.addPuzzleCell(1, 2, 0, 11);
        puzzle.addPuzzleCell(1, 5, 9, 0);
        puzzle.addPuzzleCell(1, 6, 22, 0);
        // 3rd row
        puzzle.addPuzzleCell(2, 2, 15, 12);
        puzzle.addPuzzleCell(2, 7, 7, 0);
        // fourth row
        puzzle.addPuzzleCell(3, 1, 17, 8);
        puzzle.addPuzzleCell(3, 4, 0, 9);
        // fifth row
        puzzle.addPuzzleCell(4, 0, 0, 10);
        puzzle.addPuzzleCell(4, 3, 8, 0);
        puzzle.addPuzzleCell(4, 5, 21, 15);
        // sixth row
        puzzle.addPuzzleCell(5, 0, 0, 12);
        puzzle.addPuzzleCell(5, 4, 11, 12);
        // seventh row
        puzzle.addPuzzleCell(6, 1, 0, 29);
        // eight row
        puzzle.addPuzzleCell(7, 3, 0, 10);
        
        /**************
         * Play Cells *
         **************/
        // 2nd row
        puzzle.addPlayCell(1, 3);
        puzzle.addPlayCell(1, 4);
        // 3rd row
        puzzle.addPlayCell(2, 3);
        puzzle.addPlayCell(2, 4);
        puzzle.addPlayCell(2, 5);
        puzzle.addPlayCell(2, 6);
        //4th row
        puzzle.addPlayCell(3, 2);
        puzzle.addPlayCell(3, 3);
        puzzle.addPlayCell(3, 5);
        puzzle.addPlayCell(3, 6);
        puzzle.addPlayCell(3, 7);
        // 5th row
        puzzle.addPlayCell(4, 1);
        puzzle.addPlayCell(4, 2);
        puzzle.addPlayCell(4, 6);
        puzzle.addPlayCell(4, 7);
        // 6th
        puzzle.addPlayCell(5, 1);
        puzzle.addPlayCell(5, 2);
        puzzle.addPlayCell(5, 3);
        puzzle.addPlayCell(5, 5);
        puzzle.addPlayCell(5, 6);
        // 7th 
        puzzle.addPlayCell(6, 2);
        puzzle.addPlayCell(6, 3);
        puzzle.addPlayCell(6, 4);
        puzzle.addPlayCell(6, 5);
        // 8th
        puzzle.addPlayCell(7, 4);
        puzzle.addPlayCell(7, 5);
        
        
        
    }
    
    /**
     * Contains easy data for creating an easy Kakuro game.
     */
    public static void puzzleEasy(){
    	puzzle = new KakuroTable(5, 5);
    	/****************
         * Puzzle Cells *
         ****************/
        
    	//1st row
    	puzzle.addPuzzleCell(0, 3, 6, 0);
    	puzzle.addPuzzleCell(0, 4, 3, 0);
    	// 2nd row
    	puzzle.addPuzzleCell(1, 1, 4, 0);
    	puzzle.addPuzzleCell(1, 2, 3, 3);
    	// 3rd row
    	puzzle.addPuzzleCell(2, 0, 0, 10);
    	// 4th row
    	puzzle.addPuzzleCell(3, 0, 0, 3);
    	
    	 /**************
         * Play Cells *
         **************/
    	// 2nd row
    	puzzle.addPlayCell(1, 3);
    	puzzle.addPlayCell(1, 4);
    	// 3rd
    	puzzle.addPlayCell(2, 1);
    	puzzle.addPlayCell(2, 2);
    	puzzle.addPlayCell(2, 3);
    	puzzle.addPlayCell(2, 4);
    	// 4th
    	puzzle.addPlayCell(3, 1);
    	puzzle.addPlayCell(3, 2);
    	
    }
}
