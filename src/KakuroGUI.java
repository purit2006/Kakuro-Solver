import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /**
 * Creates a GUI for playing Kakuro game.
 *
 * @author Purit
 * @author Marcus Vinicius Pereira Araujo 1106149
 * @author Piyapat Russamitinakornkul 1106291
 * 
 * @version 1
 * @since 30/06/2015
 */
public class KakuroGUI extends JFrame {
    /*******************
     * 1. Data Members *
     ******************/
    // 1.1 Constants
    final private static int TABLE_GAP = 1;
    final private static String TITLE = "Kakuro Solver";
    final private static String IMAGE_EXTENSION = ".png";
    final private static String IMAGE_PATH = "buttons/";
    final private static int CELL_SIZE = 48;
    
    // 1.2 Game Data
    private KakuroTable table;
    private int currentX;
    private int currentY;
    
    // 1.3 GUI Components
    private GridLayout puzzle;
    final private JPanel puzzleRow = new JPanel();
    final private JPanel inputRow = new JPanel();
    // 1.3.1 Input Buttons
    final private JButton[] inputButtons = new JButton[9];
    final private ImageIcon[] buttonsIcon = new ImageIcon[9];
    final private ImageIcon[] buttonsPressed = new ImageIcon[9];
    final private ImageIcon[] buttonsHovered = new ImageIcon[9];
    // 1.3.2 Game Buttons
    private JButton[][] playMap;
    
    /**************************
     * 2. Default Constructor *
     *************************/
    /**
     * Creates a GUI for game.
     */
    public KakuroGUI() {
        // Specify Main Window Data
        setTitle(TITLE);
        setIconImage(new ImageIcon(IMAGE_PATH + "icon" + IMAGE_EXTENSION).getImage());
        // Program Layout: Box layout from top to bottom
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Create GUI Components
        for(int i = 0 ; i < inputButtons.length ; ++i) {
            // Create image for buttons for entering numbers
            buttonsIcon[i] = new ImageIcon(
                    IMAGE_PATH + "b" + Integer.toString(i + 1) + IMAGE_EXTENSION);
            buttonsHovered[i] = new ImageIcon(
                    IMAGE_PATH + "b" + Integer.toString(i + 1) + "h" + IMAGE_EXTENSION);
            buttonsPressed[i] = new ImageIcon(
                    IMAGE_PATH + "b" + Integer.toString(i + 1) + "p" + IMAGE_EXTENSION);
            // Create input buttons for entering numbers
            inputButtons[i] = new JButton(buttonsIcon[i]);
            // Set button appearances
            inputButtons[i].setBorder(null);
            inputButtons[i].setContentAreaFilled(false);
            inputButtons[i].setFocusPainted(false);
            inputButtons[i].setEnabled(false);
            // Set button graphics
            final int j = i;
            inputButtons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    inputButtons[j].setIcon(buttonsHovered[j]);
                }
                @Override
                public void mousePressed(MouseEvent evt) {
                    inputButtons[j].setIcon(buttonsPressed[j]);
                }
                @Override
                public void mouseReleased(MouseEvent evt) {
                    inputButtons[j].setIcon(buttonsHovered[j]);
                }
                @Override
                public void mouseExited(MouseEvent evt) {
                    inputButtons[j].setIcon(buttonsIcon[j]);
                }
            });
            
            inputButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    // Apply new number image to the selected cell
                    playMap[currentX][currentY].setIcon(new ImageIcon(
                        IMAGE_PATH + "num" + (j + 1) + IMAGE_EXTENSION));
                    // Disable all input numbers
                    disableInputs();
                }
            });
            
            inputRow.add(inputButtons[i]);
        }
        
        add(puzzleRow);
        add(inputRow);
        pack();
        
        // Open the program window to the user
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**************
     * 3. Methods *
     *************/
    /**
     * Generates a Kakuro table to play a game
     * @param game A Kakuro puzzle to be played
     */
    public void loadPuzzle(KakuroTable game) {
        // Configure puzzle data
        playMap = new JButton[game.getWidth()][game.getHeight()];
        puzzle = new GridLayout(game.getWidth(),
                                game.getHeight(),
                                TABLE_GAP,
                                TABLE_GAP);
        
        // Create puzzle
        for(int i = 0 ; i < game.getWidth() ; ++i)
            for(int j = 0 ; j < game.getHeight() ; ++j) {
                // Create a black image for "null" cells
                if(game.getCell(i, j) == null)
                    puzzleRow.add(new JLabel(new ImageIcon(
                        IMAGE_PATH + "void" + IMAGE_EXTENSION)));
                // Create a fixed number image for "LOCK" cells
                else if(game.getCell(i, j).getCellType().
                        compareTo(KakuroCell.CELL_TYPE[KakuroCell.LOCK]) == 0)
                    puzzleRow.add(new JLabel(new ImageIcon(
                        IMAGE_PATH + "num" + game.getCell(i, j).clearDownValue() + IMAGE_EXTENSION)));
                // Create a blank white image for "PLAY" cells
                else if(game.getCell(i, j).getCellType().
                        compareTo(KakuroCell.CELL_TYPE[KakuroCell.PLAY]) == 0) {
                    JButton temp = new JButton(new ImageIcon(
                        IMAGE_PATH + "blank" + IMAGE_EXTENSION));
                    temp.setBorder(null);
                    temp.setContentAreaFilled(false);
                    
                    // Record current cell data when this "PLAY" cell is selected
                    final int x = i, y = j;
                    temp.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            enableInputs();
                            
                            currentX = x;
                            currentY = y;
                        }
                    });
                    
                    playMap[i][j] = temp;
                    puzzleRow.add(temp);
                }
            }
        
        // Set puzzle to the game
        puzzleRow.setLayout(puzzle);
    }
    
    /**
     * Kakuro table total columns getter.
     * @return Number of columns in current Kakuro puzzle
     *//*
    @Override
    public int getWidth() {
        return table.getWidth();
    }*/
    
    /**
     * Kakuro table total rows getter.
     * @return Number of rows in current Kakuro puzzle
     *//*
    @Override
    public int getHeight() {
        return table.getHeight();
    }*/
    
    /**
     * Allows all number buttons (game inputs) to be pressed
     */
    public void enableInputs() {
        for(int i = inputButtons.length - 1 ; i >= 0 ; --i)
            inputButtons[i].setEnabled(true);
    }
    
    /**
     * Forbids all number buttons (game inputs) to be pressed
     */
    public void disableInputs() {
        for(int i = inputButtons.length - 1 ; i >= 0 ; --i)
            inputButtons[i].setEnabled(false);
    }
}
