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
        
        game.loadPuzzle(puzzle);
    }
}
