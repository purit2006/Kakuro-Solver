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
        
        KakuroTable puzzle = new KakuroTable(3, 3);
        puzzle.addCell(0, 1, KakuroCell.LOCK, 5, null);
        puzzle.addCell(1, 0, KakuroCell.LOCK, 2, null);
        puzzle.addCell(1, 1, KakuroCell.PLAY, null, null);
        puzzle.addCell(2, 2, KakuroCell.PLAY, null, null);
        
        game.loadPuzzle(puzzle);
    }
}
