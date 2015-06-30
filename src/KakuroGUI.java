import javax.swing.JFrame;
import java.awt.GridLayout;

public class KakuroGUI extends JFrame {
    /*******************
     * 1. Data Members *
     ******************/
    // 1.1 Constants
    final private int WIDTH;
    final private int HEIGHT;
    final private String TITLE = "Kakuro Solver";
    
    // 1.2 Game Data
    private KakuroTable table;
    
    // 1.3 GUI Components
    private GridLayout puzzle;
    
    /**************************
     * 2. Default Constructor *
     *************************/
    /**
     * Creates a GUI for game.
     * @param width The width of Kakuro table
     * @param height The height of Kakuro table
     */
    public KakuroGUI(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        table = new KakuroTable(WIDTH, HEIGHT);
        puzzle = new GridLayout(HEIGHT, WIDTH);
        
        setTitle(TITLE);
        
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**************
     * 3. Methods *
     *************/
}
