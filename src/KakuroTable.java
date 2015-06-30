/**
 * Creates a Kakuro table for playing game.
 *
 * @author Purit
 * @author Marcus
 * @author Piyapat Russamitinakornkul 1106291
 * 
 * @version 1
 * @since 23/06/2015
 */
public class KakuroTable {
    /*******************
     * 1. Data Members *
     ******************/
    // Object data
    private final int WIDTH;
    private final int HEIGHT;
    private KakuroCell[][] table;

    /**************************
     * 2. Default Constructor *
     *************************/
    /**
     * Creates a Kakuro table with a specified <code>width</code> and <code>height</code>.
     * @param width
     * @param height 
     */
    public KakuroTable(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        table = new KakuroCell[width][height];
    }

    /**************
     * 3. Methods *
     *************/
    /**
     * Creates a Kakuro cell and install it to a table at a specified slot.
     * Overwrites an old cell.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @param cellType A type of cell for that certain slot
     * @param num1 A value for a either puzzle cell or required for locked cell
     * @param num2 A value for a puzzle cell only (Can be null)
     * @return <code>true</code>, if the cell has been successfully added
     */
    public boolean addCell(int x, int y, int cellType, Integer num1, Integer num2) {
        // Check cell boundaries
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT))
            return false;

        // Generate a cell ID
        String ID = Integer.toString(x) + Integer.toString(y);
        if(cellType == KakuroCell.PUZZLE) {
            // Create a cell table
            table[x][y] = new KakuroCell("D" + ID, "R" + ID, KakuroCell.PUZZLE);
            // Initialize cell with values
            if(num1 != null)
                table[x][y].setDownValue(num1);
            if(num2 != null)
                table[x][y].setRightValue(num2);
        }
        else if(cellType == KakuroCell.PLAY)
            // Create a cell table
            table[x][y] = new KakuroCell("D" + ID, "R" + ID, KakuroCell.PLAY);
        else if(cellType == KakuroCell.LOCK) {
            // Create a cell table
            table[x][y] = new KakuroCell("D" + ID, "R" + ID, KakuroCell.LOCK);
            // Initialize cell with values
            if(num1 != null)
                table[x][y].setDownValue(num1);
        }

        return true;
    }
    
    /**
     * Removes cell in the table and turn it to null.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return A cell of the specified slot that was removed
     */
    public KakuroCell removeCell(int x, int y) {
        // Check Boundaries
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT))
            return null;
        else {
            KakuroCell results = table[x][y];
            table[x][y] = null;
            return results;
        }
    }
    
    /**
     * Fills a specified cell with given values. Can be null
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @param value A first <code>value</code> of the cell
     * @param value2 A first <code>value</code> of the cell (For puzzle cells only)
     * @return 
     */
    public boolean fillCell(int x, int y, Integer value, Integer value2) {
        // Check Boundaries and values
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT) || value == null)
            return false;
        // Fill the cell with a given value
        else if(table[x][y].isPlayCell() || table[x][y].isLockCell())
            return table[x][y].setDownValue(value);
        else if(table[x][y].isPuzzleCell() && value2 != null) {
            return table[x][y].setDownValue(value) && table[x][y].setRightValue(value);
        }
        
        return false;
    }
    
    public Integer clearCell(int x, int y) {
        // Check Boundaries
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT) || !table[x][y].isPlayCell())
            return null;
        // Remove the value from the cell
        return table[x][y].clearCell();
        
    }
}
