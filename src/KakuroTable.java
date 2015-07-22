import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Creates a Kakuro table for playing game.</br />
 * Version 1 since 16/06/2015<br />
 * Version 2 since 30/06/2015<br />
 * Version 2.1 since 07/07/2015<br />
 *
 * @author Purit
 * @author Marcus Vinicius Pereira Araujo 1106149
 * @author Piyapat Russamitinakornkul 1106291
 * 
 * @version 2.2
 * @since 07/07/2015
 */
public class KakuroTable implements Serializable {
    /*******************
     * 1. Data Members *
     *******************/
    // Object data
    private static final int MAX_VALUE = 9;
    private static final int MIN_VALUE = 1;
    private final int WIDTH;
    private final int HEIGHT;
    private KakuroCell[][] table;

    /*******************
     * 2. Constructors *
     *******************/
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

    /******************************
     * 3. Table Operation Methods *
     ******************************/
    /**
     * Creates a <code>KakuroCell</code> and install it to a table at a 
     * specified slot with a <code>PUZZLE</code> type. Overwrites an old cell, 
     * if there exists any type of cell before.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @param downNum A value for solutions below (Can be null)
     * @param rightNum A second for solutions to the right (Can be null)
     * @return <code>true</code>, if the cell has been successfully added
     */
    public boolean addPuzzleCell(int x, int y, Integer downNum, Integer rightNum) {
        // Check cell boundaries
        if(!isInBound(x, y))
            return false;

        // Generate a cell ID
        String ID = Integer.toString(x) + Integer.toString(y);
        
        // Create a puzzle cell
        table[x][y] = new KakuroCell("D" + ID, "R" + ID, KakuroCell.PUZZLE);
        // Initialize cell with values
        if(downNum != null)
            table[x][y].setDownValue(downNum);
        if(rightNum != null)
            table[x][y].setRightValue(rightNum);
        table[x][y].fixCell();
        return true;
    }
    
    /**
     * Creates a <code>KakuroCell</code> and install it to a table at a 
     * specified slot with a <code>LOCK</code> type. Overwrites an old cell, 
     * if there exists any type of cell before.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @param num A value for the cell
     * @return <code>true</code>, if the cell has been successfully added
     */
    public boolean addLockCell(int x, int y, int num) {
        // Check nearest puzzle cells for ID
        String dID = findDownID(x, y), rID = findRightID(x, y);
        // Check cell boundaries and nearest puzzle cells
        if(!isInBound(x, y) ||
           !inputInBound(num) ||
           dID == null ||
           rID == null)
            return false;

        // Create a lock cell
        table[x][y] = new KakuroCell(rID, dID, KakuroCell.LOCK);
        // Initialize cell with values
        table[x][y].setDownValue(num);
        table[x][y].fixCell();
        return true;
    }
    
    /**
     * Creates a <code>KakuroCell</code> and install it to a table at a 
     * specified slot with a <code>PLAY</code> type. Overwrites an old cell, 
     * if there exists any type of cell before.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return <code>true</code>, if the cell has been successfully added
     */
    public boolean addPlayCell(int x, int y) {
        // Check nearest puzzle cells for ID
        String dID = findDownID(x, y), rID = findRightID(x, y);
        
        // Check cell boundaries
        if(!isInBound(x, y) || dID == null || rID == null)
            return false;
        
        // Create a play cell
        table[x][y] = new KakuroCell(dID, rID, KakuroCell.PLAY);
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
        if(!isInBound(x, y))
            return null;
        else {
            KakuroCell results = getCell(x, y);
            table[x][y] = null;
            return results;
        }
    }
    
    /**
     * Fills a specified cell with given values. Only available if the cell is 
     * a <code>PLAY</code> type.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @param value A first <code>value</code> of the cell
     * @return 
     */
    public boolean fillCell(int x, int y, int value) {
        // Check Boundaries and values
        if(!isInBound(x, y) ||
           table[x][y] == null ||
           !table[x][y].isPlayCell() ||
           !inputInBound(value))
            return false;
        // Fill the cell with a given value
        table[x][y].setDownValue(value);
        return true;
    }
    
    /**
     * Clears a value only in a <code>PLAY</code> cell.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return An integer received from the cell or <code>null</code>
     */
    public Integer clearCell(int x, int y) {
        // Check Boundaries
        if(!isInBound(x, y) || table[x][y] == null || !table[x][y].isPlayCell())
            return null;
        // Remove the value from the cell
        return table[x][y].clearDownValue();
    }
    
    /**
     * Performs a deep the copy of the <code>KakuroCell</code> object at a 
     * given spot on a table.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return a copy of <code>KakuroCell</code> at a given table slot
     */
    public KakuroCell getCell(int x, int y) {
        // Check Boundaries
        if(!isInBound(x, y) || table[x][y] == null)
            return null;
        
        // Perform a deep copy of the cell
        KakuroCell results;
        int cellType;
        
        if(table[x][y].isLockCell())
            cellType = KakuroCell.LOCK;
        else if(table[x][y].isPlayCell())
            cellType = KakuroCell.PLAY;
        else
            cellType = KakuroCell.PUZZLE;
        
        results = new KakuroCell(table[x][y].getDownID(),
                                 table[x][y].getRightID(),
                                 cellType);
        results.setDownValue(table[x][y].getDownValue());
        results.setRightValue(table[x][y].getRightValue());
        if(table[x][y].isFixedCell())
            results.fixCell();
        
        return results;
    }
    
    /**
     * Gets the the type of <code>KakuroCell</code> at a given spot on a table.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return a <code>KakuroCell</code>'s type
     */
    public String getCellType(int x, int y) {
        // Check Boundaries
        if(!isInBound(x, y) || table[x][y] == null)
            return null;
        return table[x][y].getCellType();
    }
    
    /**
     * Tells whether the cell at a given position in this table is a fixed cell. 
     * Fixed cells does not allow modifying values.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return <code>true</code>, if the cell is a fixed cell
     */
    public boolean isFixedCell(int x, int y) {
        if(!isInBound(x, y) || table[x][y] == null)
            return false;
        return table[x][y].isFixedCell();
    }
    
    /**
     * Tells whether the cell at a given position in this table is blank. 
     * Blank cells contains no <code>KakuroCell</code>, but null value.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return <code>true</code>, if the cell is a blank cell
     */
    public boolean isBlankCell(int x, int y) {
        if(!isInBound(x, y))
            return false;
        return table[x][y] == null;
    }
    
    /**
     * Tells whether the cell at a given position in this table is a <code>PLAY</code> 
     * cell, but has no values within a cell.
     * Blank cells contains no <code>KakuroCell</code>, but null value.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return <code>true</code>, if the cell is a play cell with no values
     */
    public boolean isBlankPlayCell(int x, int y) {
        if(!isInBound(x, y) || table[x][y] == null)
            return false;
        return table[x][y].isPlayCell() && (table[x][y].getDownValue() == 0);
    }
    
    /**
     * Tells whether the cell at a given position in this table is a type of 
     * <code>LOCK</code>.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return <code>true</code>, if the cell is a <code>LOCK</code> type
     */
    public boolean isLockCell(int x, int y) {
        if(!isInBound(x, y) || table[x][y] == null)
            return false;
        return table[x][y].isLockCell();
    }
    
    /**
     * Tells whether the cell at a given position in this table is a type of 
     * <code>PLAY</code>.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return <code>true</code>, if the cell is a <code>PLAY</code> type
     */
    public boolean isPlayCell(int x, int y) {
        if(!isInBound(x, y) || table[x][y] == null)
            return false;
        return table[x][y].isPlayCell();
    }
    
    /**
     * Tells whether the cell at a given position in this table is a type of 
     * <code>PUZZLE</code>.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return <code>true</code>, if the cell is a <code>PUZZLE</code> type
     */
    public boolean isPuzzleCell(int x, int y) {
        if(!isInBound(x, y) || table[x][y] == null)
            return false;
        return table[x][y].isPuzzleCell();
    }
    
    /**************
     * 4. Getters *
     **************/
    /**
     * Kakuro table total columns getter.
     * @return Number of columns in current Kakuro puzzle
     */
    public int getWidth() {
        return WIDTH;
    }
    
    /**
     * Kakuro table total rows getter.
     * @return Number of rows in current Kakuro puzzle
     */
    public int getHeight() {
        return HEIGHT;
    }
    
    /**
     * Records the implemented puzzle to a memory storage.
     * @param filePath A path to the location of storing this <code>KakuroTable</code>
     * @return <code>true</code>, if the file successfully saved
     */
    public boolean saveFile(String filePath) {
        try(ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(filePath))) {

            file.writeObject(this);     // Serialize this object to a given filePath.
            file.close();               // Close the pointer to this file
            
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /*************************
     * 5. Private Operations *
     *************************/
    /**
     * Determines whether given positions exists on this <code>KakuroTable</code>.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return <code>true</code>, if the given position is in a table range
     */
    private boolean isInBound(int x, int y) {
        return (x >= 0 && x < WIDTH) && (y >= 0 && y < HEIGHT);
    }
    
    /**
     * Determines whether given input is in range specified by this <code>KakuroTable</code>.
     * @param value A value to be examined
     * @return <code>true</code>, if the given <code>value</code> is in a table range
     */
    private boolean inputInBound(int value) {
        return value >= MIN_VALUE && value <= MAX_VALUE;
    }
    
    /**
     * Finds an ID of the <code>KakuroCell</code> with a <code>PUZZLE</code> 
     * type to the nearest upper direction.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return A <code>String</code> of a nearest puzzle cell ID to the up direction
     */
    private String findDownID(int x, int y) {
        // Check boundaries
        if(!isInBound(x, y))
            return null;
        
        // Find a related puzzle cell to this cell
        for(int i = y; i >= 0 ; --i)
            if(table[x][i] != null && table[x][i].isPuzzleCell())
                return "D" + Integer.toString(x) + Integer.toString(i);
                
        return null;
    }
    
    /**
     * Finds an ID of the <code>KakuroCell</code> with a <code>PUZZLE</code> 
     * type to the nearest puzzle cell on the left.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return A <code>String</code> of a nearest puzzle cell ID to the left
     */
    private String findRightID(int x, int y) {
        // Check boundaries
        if(!isInBound(x, y))
            return null;
        
        // Find a related puzzle cell to this cell
        for(int i = x; i >= 0 ; --i)
            if(table[i][y] != null && table[i][y].isPuzzleCell())
                return "R" + Integer.toString(i) + Integer.toString(y);
                
        return null;
    }
}
