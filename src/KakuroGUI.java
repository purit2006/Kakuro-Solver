import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;

/**
 * Creates a GUI for playing Kakuro game.
 *
 * @author Purit Thong-On 1106
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
    // 1.1.1 GUI Values
    final private static int TABLE_GAP = 1;
    final private static String TITLE = "Kakuro Solver";
    final private static String PUZZLE_ERROR_BODY = "Please load a puzzle to be solved!";
    final private static String PUZZLE_ERROR_HEAD = "No Puzzle!";
    // 1.1.2 Image Paths
    final private static String IMAGE_EXTENSION = ".png";
    final private static String IMAGE_PATH = "buttons/";
    // 1.1.3 Cell size Properties
    final private static int CELL_SIZE = 48;
    // 1.1.4 Puzzle Cell Properties
    final private static int DOWN_X = 5;
    final private static int DOWN_Y = 40;
    final private static int RIGHT_X = 28;
    final private static int RIGHT_Y = 16;
    final private static int FONT_SIZE = 16;
    final private static String FONT_NAME = "Times New Roman";
    final private static String SOLVE = "Solving Attempt: ";
    
    // 1.2 Game Data
    // 1.2.1 Game Component table and maps
    private KakuroTable table;
    private GridLayout puzzle;
    private JComponent[][] playMap;
    // 1.2.2 Input mechanisms
    private int currentX;
    private int currentY;
    
    // 1.3 GUI Components
    final private JPanel puzzleRow = new JPanel();
    final private JPanel inputRow = new JPanel();
    final private JToolBar toolbar = new JToolBar(JToolBar.VERTICAL);
    final private JLabel solveStatus = new JLabel();
    // 1.3.1 Input Buttons
    final private JButton[] inputButtons = new JButton[9];
    final private ImageIcon[] buttonsIcon = new ImageIcon[9];
    final private ImageIcon[] buttonsPressed = new ImageIcon[9];
    final private ImageIcon[] buttonsHovered = new ImageIcon[9];
    
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
        setLayout(new FlowLayout());

        // Initiate GUI Components
        setup();
        
        // Add GUI Components to the main window
        add(toolbar);
        add(puzzleRow);
        add(inputRow);
        pack();
        
        // Open the program's main window to the user
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    /*********************
     * 3. Set-up Methods *
     *********************/
    private void setup() {
        createToolBar();
        createInputButtons();
    }
    
    private void createToolBar() {
        // Create buttons
        JButton openButton = new JButton(new ImageIcon(
                IMAGE_PATH + "open" + IMAGE_EXTENSION));
        JButton saveButton = new JButton(new ImageIcon(
                IMAGE_PATH + "save" + IMAGE_EXTENSION));
        JButton solveButton = new JButton(new ImageIcon(
                IMAGE_PATH + "solve" + IMAGE_EXTENSION));
        
        // Set Buttons' Properties
        openButton.setFocusPainted(false);
        saveButton.setFocusPainted(false);
        solveButton.setFocusPainted(false);
        
        // Assign button responsibilities
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an "OPEN" dialog box
                KakuroOpener puzzleLoader = new KakuroOpener();
                // Get a file data
                KakuroTable temp = puzzleLoader.LoadFile();
                // Load a kakuro puzzle from file only if the data is not null
                if(temp != null)
                    loadPuzzle(temp);
            }
        });
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a "SAVE" dialog box
                KakuroSaver fileWriter = new KakuroSaver(table);
                // Write a puzzle to the file
                fileWriter.saveFile();
            }
        });
        
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Only solve the puzzle when it exists!
                if(table == null) {
                    JOptionPane.showMessageDialog(
                    null,
                    PUZZLE_ERROR_BODY,
                    PUZZLE_ERROR_HEAD,
                    JOptionPane.ERROR_MESSAGE);
                    return ;
                }
                
                Thread solverThread = new Thread(new Solver());
                solverThread.start();
            }
        });
        
        // Set Toolbar's Properties
        toolbar.setRollover(true);
        toolbar.setFloatable(false);
        
        // Add all buttons to the toolbar
        toolbar.add(openButton);
        toolbar.add(saveButton);
        toolbar.addSeparator();
        toolbar.add(solveButton);
        toolbar.add(solveStatus);
    }
    
    private void createInputButtons() {
        // Create a layout for placing buttons
        inputRow.setLayout(new GridLayout(3 ,3 , TABLE_GAP, TABLE_GAP));
        
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
            // Set button operations after being pressed
            inputButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    // Apply a new number image to the selected cell
                    JButton temp = (JButton)playMap[currentX][currentY];
                    temp.setIcon(new ImageIcon(
                        IMAGE_PATH + "num" + (j + 1) + IMAGE_EXTENSION));
                    table.fillCell(currentX, currentY, j + 1);
                    // Disable all input numbers
                    disableInputs();
                }
            });
            
            inputRow.add(inputButtons[i]);
        }
    }

    /************************
     * 4. Operation Methods *
     ************************/
    /**
     * Generates a Kakuro table to play a game
     * @param game A Kakuro puzzle to be played
     */
    public void loadPuzzle(KakuroTable game) {
        // Configure puzzle data
        playMap = new JComponent[game.getWidth()][game.getHeight()];
        puzzle = new GridLayout(game.getWidth(),
                                game.getHeight(),
                                TABLE_GAP,
                                TABLE_GAP);
        table = game;
        
        // Clear out the old puzzle
        puzzleRow.removeAll();
        // Set Puzzle Layout to the program
        puzzleRow.setLayout(puzzle);
        
        // Create puzzle
        for(int i = 0 ; i < game.getWidth() ; ++i)
            for(int j = 0 ; j < game.getHeight() ; ++j) {
                // Create a black image for "null" cells
                if(game.isBlankCell(i, j)) {
                    // Create a new cell
                    JLabel temp = new JLabel(new ImageIcon(
                        IMAGE_PATH + "void" + IMAGE_EXTENSION));
                    // Add cells to the GUI Mechanism
                    playMap[i][j] = temp;
                    puzzleRow.add(playMap[i][j]);
                }
                // Create a fixed number image for "LOCK" cells
                else if(game.isLockCell(i, j)) {
                    // Create a new cell
                    JLabel temp = new JLabel(new ImageIcon(
                        IMAGE_PATH + "num" + game.getCell(i, j).getDownValue() + IMAGE_EXTENSION));
                    // Add cells to the GUI Mechanism
                    playMap[i][j] = temp;
                    puzzleRow.add(playMap[i][j]);
                }
                // Create a blank white image for "PLAY" cells
                else if(game.isPlayCell(i, j)) {
                    // Create a new JButton for a "PLAY" cell
                    JButton temp;
                    
                    if(game.isBlankPlayCell(i, j))
                        temp = new JButton(new ImageIcon(
                            IMAGE_PATH + "blank" + IMAGE_EXTENSION));
                    else {
                        temp = new JButton(new ImageIcon(
                            IMAGE_PATH + "num" + Integer.toString(game.getCell(i, j).getDownValue()) + IMAGE_EXTENSION));
                    }
                    temp.setBorder(null);
                    temp.setContentAreaFilled(false);
                    
                    // Add cells to the GUI Mechanism
                    playMap[i][j] = temp;
                    puzzleRow.add(playMap[i][j]);
                    
                    // Record current cell data when this "PLAY" cell is selected
                    final int x = i, y = j;
                    temp.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            int value = table.getCell(x, y).getDownValue();
                            JButton temp = (JButton)(playMap[x][y]);

                            enableInputs();
                            
                            try {
                                // Color the selected cell with yellow
                                if(value == 0)
                                    temp.setIcon(new ImageIcon(
                                    IMAGE_PATH + "blanky" + IMAGE_EXTENSION));
                                else
                                    temp.setIcon(new ImageIcon(
                                    IMAGE_PATH + "num" + Integer.toString(value) + "y" + IMAGE_EXTENSION));
                                
                                temp = (JButton)playMap[currentX][currentY];
                                value = table.getCell(currentX, currentY).getDownValue();
                                
                                // Change the color of previous cell back to white
                                if(currentX != x || currentY != y) {
                                    if(value == 0)
                                        temp.setIcon(new ImageIcon(
                                        IMAGE_PATH + "blank" + IMAGE_EXTENSION));
                                    else
                                        temp.setIcon(new ImageIcon(
                                        IMAGE_PATH + "num" + Integer.toString(value) + IMAGE_EXTENSION));
                                }
                            }
                            catch(Exception e) {}
                            
                            // Set current selected cell data for input operations
                            currentX = x;
                            currentY = y;
                        }
                    });
                }
                // Create a representation for "PUZZLE" cells
                else if(table.isPuzzleCell(i, j)) {
                    int down = table.getCell(i, j).getDownValue(),
                        right = table.getCell(i, j).getRightValue();
                    BufferedImage cell;
                    Graphics pen;
                    JLabel temp;
                    
                    try {
                        if(down == 0)
                            cell = ImageIO.read(new File(
                                    IMAGE_PATH + "puzzleR" + IMAGE_EXTENSION));
                        else if(right == 0)
                            cell = ImageIO.read(new File(
                                    IMAGE_PATH + "puzzleD" + IMAGE_EXTENSION));
                        else
                            cell = ImageIO.read(new File(
                                    IMAGE_PATH + "puzzle" + IMAGE_EXTENSION));
                        
                        // Write text to images
                        pen = cell.getGraphics();
                        pen.setColor(Color.black);
                        pen.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
                        if(right != 0)
                            pen.drawString(Integer.toString(right), RIGHT_X, RIGHT_Y);
                        if(down != 0)
                            pen.drawString(Integer.toString(down), DOWN_X, DOWN_Y);
                        pen.dispose();

                        // Add created images to some values
                        temp = new JLabel(new ImageIcon((Image)cell));
                        playMap[i][j] = temp;
                        puzzleRow.add(playMap[i][j]);
                    }
                    catch(Exception e) {}
                }
            }
        
        // Repaint the puzzle screen
        puzzleRow.validate();
    }
    
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
    
    public class Solver implements Runnable {
        @Override
        public final void run() {
            KUtility.init(table);
        
            // Create an initial population
               KPopulation myPop = new KPopulation(1000,true);
               double curFittestVal = myPop.getFittest().getFitness();
               double prevFittestVal;
            // Evolve our population until we reach an optimum solution
               int generationCount = 0;
               int newFittestGeneration = 0;
               while (curFittestVal > FitnessCalc.getMaxFitness()) {
                   ++generationCount;
                   solveStatus.setText(SOLVE + Integer.toString(generationCount));

                   System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
                   try {
                       for(int i = 0 ; i < table.getWidth() ; ++i)
                           for(int j = 0 ; j < table.getHeight() ; ++j)
                               if(table.isPlayCell(i, j))
                                   table.fillCell(i, j, (int)myPop.getFittest().getTable()[i][j]);
                       loadPuzzle(table);
                       Thread.sleep(100);
                   }
                   catch(Exception trash) {
                       trash.printStackTrace();
                   }

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
    }
}
