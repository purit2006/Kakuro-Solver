/**
 * Javadoc first introduction paragraph here!
 * 
 * @author Purit
 * @author Marcus Vinicius Pereira Araujo 1106149
 * @author Piyapat Russamitinakornkul 1106291
 * 
 * @version 1
 * @since 16/06/2015
 */
public class Main {
    final private static int WIDTH = 8;
    final private static int HEIGHT = 8;
    /*****************
     * Main Function *
     ****************/
    /**
     * Main function of the program.
     * @param args Command-Line Arguments
     */
    public static void main(String[] args) {
        KakuroGUI game = new KakuroGUI();
        
        KakuroTable puzzle = new KakuroTable(WIDTH, HEIGHT);
        puzzle.addPlayCell(1, 1);
        puzzle.addPlayCell(1, 2);
        puzzle.addPlayCell(1, 5);
        puzzle.addPlayCell(1, 6);
        puzzle.addPlayCell(1, 7);
        puzzle.addPlayCell(2, 1);
        puzzle.addPlayCell(2, 2);
        puzzle.addPlayCell(2, 4);
        puzzle.addPlayCell(2, 5);
        puzzle.addPlayCell(2, 6);
        puzzle.addPlayCell(2, 7);
        puzzle.addPlayCell(3, 1);
        puzzle.addPlayCell(3, 2);
        puzzle.addPlayCell(3, 3);
        puzzle.addPlayCell(3, 4);
        puzzle.addPlayCell(3, 5);
        puzzle.addPlayCell(4, 2);
        puzzle.addPlayCell(4, 3);
        puzzle.addPlayCell(4, 5);
        puzzle.addPlayCell(4, 6);
        puzzle.addPlayCell(5, 3);
        puzzle.addPlayCell(5, 4);
        puzzle.addPlayCell(5, 5);
        puzzle.addPlayCell(5, 6);
        puzzle.addPlayCell(5, 7);
        puzzle.addPlayCell(6, 1);
        puzzle.addPlayCell(6, 2);
        puzzle.addPlayCell(6, 3);
        puzzle.addPlayCell(6, 4);
        puzzle.addPlayCell(6, 6);
        puzzle.addPlayCell(6, 7);
        puzzle.addPlayCell(7, 1);
        puzzle.addPlayCell(7, 2);
        puzzle.addPlayCell(7, 3);
        puzzle.addPlayCell(7, 6);
        puzzle.addPlayCell(7, 7);
        
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
        
        
        game.loadPuzzle(puzzle);
    }
}
