import java.io.Serializable;

/**
 * A class of Kakuro cells, with full specification in one class.<br />
 * This class contains 6 categories of usable methods:<br />
 * <ol>
 * <li>ID Getter Methods</li>
 * <li>Methods for filling cell values</li>
 * <li>Methods for getting cell values</li>
 * <li>Getters for determining cell types</li>
 * <li>Overridden methods</li>
 * <li>Methods for locking / unlocking cells</li>
 * </ol>
 * Version 1 since 16/06/2015
 *
 * @author Purit
 * @author Marcus Vinicius Pereira Araujo 1106149
 * @author Piyapat Russamitinakornkul 1106291
 * 
 * @version 2
 * @since 30/06/2015
 */
public class KakuroCell implements Comparable<KakuroCell>, Serializable {
    /*******************
     * 1. Data Members *
     ******************/
    // General Signals
    public static final int PUZZLE = 0;
    public static final int PLAY = 1;
    public static final int LOCK = 2;
    public static final String[] CELL_TYPE = {"PUZZLE", "PLAY", "LOCK"};
    
    private static final String COMPARE_ERROR =
            "Only cells with either \"PLAY\" or \"LOCK\" type can be compared.";
    
    final private int TYPE;
    final private String DOWN_ID;
    final private String RIGHT_ID;
    private int downValue;
    private int rightValue;
    private boolean isFixed;

    /*******************
     * 2. Constructors *
     *******************/
    /**
     * Creates a cell as a component of a Kakuro table.
     * @param downID  An identification of this cell and others in a down direction
     * @param rightID  A second identification of this cell and others to the right
     * @param flag Specifies a type of cell
     */
    public KakuroCell(String downID, String rightID, int flag) {
        DOWN_ID = downID;
        RIGHT_ID = rightID;
        TYPE = flag;
        downValue = 0;
        rightValue = 0;
        
        unfixCell();
    }
    
    /************************
     * 3. ID Getter Methods *
     ************************/
    /**
     * Getter method for obtaining a first identification of this cell.
     * @return The first ID of this cell
     */
    public String getDownID() {
        return DOWN_ID;
    }
    
    /**
     * Getter method for obtaining a second identification of this cell.
     * @return The second ID of this cell 
     */
    public String getRightID() {
        return RIGHT_ID;
    }
    
    /**************************************
     * 4. Methods for filling cell values *
     **************************************/
    /**
     * Fills a puzzle value for cells in a vertical direction.
     * @param value A number to be filled into the cell
     */
    public void setDownValue(int value) {
        if(!isFixedCell())
            downValue = value;
    }
    
    /**
     * Fills a puzzle value for cells in a horizontal direction. Only allow for 
     * puzzle cells, can only be called once. A second time will always result 
     * in returning <code>false</code> value.
     * @param value A number to be filled into the cell
     */
    public void setRightValue(int value) {
        if(!isFixedCell())
            rightValue = value;
    }
    
    /**
     * Clears a first value in a cell
     * @return <code>null</code>, if the cell does not contain a value
     */
    public int clearDownValue() {
        if(!isFixedCell()) {
            int results = downValue;
            downValue = 0;
            return results;
        }
        return 0;
    }
    
    /**
     * Clears a second value in a cell
     * @return <code>null</code>, if the cell does not contain a value
     */
    public int clearRightValue() {
        if(!isFixedCell()) {
            int results = rightValue;
            rightValue = 0;
            return results;
        }
        return 0;
    }
    
    /**************************************
     * 5. Methods for getting cell values *
     **************************************/
    /**
     * Gives a puzzle value for cells in a vertical direction.
     * @return A value in a cell
     */
    public int getDownValue() {
        return downValue;
    }
    
    /**
     * Gives a puzzle value for cells in a horizontal direction.
     * @return A value in a cell
     */
    public int getRightValue() {
        return rightValue;
    }
    
    /******************************************
     * 6. Methods for Locking/Unlocking Cells *
     ******************************************/
    /**
     * Unlocks the cell to allow modifications. Not allowed for <code>PLAY</code> 
     * cell type.
     */
    public void fixCell() {
        isFixed = true;
    }
    
    /**
     * Unlocks the cell to allow modifications. Not allowed for <code>LOCK</code> 
     * and <code>PUZZLE</code> cell type.
     */
    public void unfixCell() {
        isFixed = false;
    }
    
    /**
     * Tells whether values in this cell could be modified.
     * @return <code>true</code>, if the cell could not be modified
     */
    public boolean isFixedCell() {
        return isFixed;
    }
    
    /*****************************************
     * 7. Getters for determining cell types *
     *****************************************/
    /**
     * Tells whether this cell is a type of <code>LOCK</code>.
     * @return <code>true</code>, if the cell is a <code>LOCK</code> type
     */
    public boolean isLockCell() {
        return TYPE == LOCK;
    }
    
    /**
     * Tells whether this cell is a type of <code>PLAY</code>.
     * @return <code>true</code>, if the cell is a <code>PLAY</code> type
     */
    public boolean isPlayCell() {
        return TYPE == PLAY;
    }
    
    /**
     * Tells whether this cell is a type of <code>PUZZLE</code>.
     * @return <code>true</code>, if the cell is a <code>PUZZLE</code> type
     */
    public boolean isPuzzleCell() {
        return TYPE == PUZZLE;
    }
    
    /**
     * Tells the type of this Kakuro cell.
     * @return A type of cell in a <code>String</code> format
     */
    public String getCellType() {
        return CELL_TYPE[TYPE];
    }
    
    /*************************
     * 8. Overridden methods *
     *************************/
    /**
     * A <code>String</code> format for a cell type.
     * @return A type of Kakuro cell in a word
     */
    @Override
    public String toString() {
        return getCellType();
    }

    /**
     * Compares a value in a cell of two cells.
     * @param cell A cell to compare value
     * @return A negative value, if the comparing cell has a greater value.
     */
    @Override
    public int compareTo(KakuroCell cell) {
        if((isPlayCell() || isLockCell()) && (cell.isPlayCell() || cell.isLockCell()))
            return getDownValue() - cell.getDownValue();
        throw new UnsupportedOperationException(COMPARE_ERROR);
    }
}
