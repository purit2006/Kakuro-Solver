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
     ******************/
    // Object data
    private final int WIDTH;
    private final int HEIGHT;
    private KakuroCell[][] table;

    /*******************
     * 2. Constructors *
     ******************/
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
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT))
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
        // Check cell boundaries
        if(((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT)) &&
           num < 1 && num > 9)
            return false;
        
        // Generate a cell ID
        String ID = Integer.toString(x) + Integer.toString(y);
        
        // Create a lock cell
        table[x][y] = new KakuroCell("D" + ID, "R" + ID, KakuroCell.LOCK);
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
        // Check cell boundaries
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT))
            return false;

        // Generate a cell ID
        String ID = Integer.toString(x) + Integer.toString(y);
        
        // Create a play cell
        table[x][y] = new KakuroCell("D" + ID, "R" + ID, KakuroCell.PLAY);
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
     * @return 
     */
    public boolean fillCell(int x, int y, int value) {
        // Check Boundaries and values
        if((x < 0 || x >= WIDTH) ||
           (y < 0 || y >= HEIGHT) ||
           table[x][y] == null ||
           value < 1 ||
           value > 9 ||
           !table[x][y].isPlayCell())
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
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT) ||
           table[x][y] == null || !table[x][y].isPlayCell())
            return null;
        // Remove the value from the cell
        return table[x][y].clearDownValue();
    }
    
    /**
     * Fetches the copy of the <code>KakuroCell</code> object at a given spot 
     * on a table.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return a <code>KakuroCell</code>
     */
    public KakuroCell getCell(int x, int y) {
        // Check Boundaries
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT))
            return null;
        
        return table[x][y];
    }
    
    /**
     * Gets the the type of <code>KakuroCell</code> at a given spot on a table.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return a <code>KakuroCell</code>'s type
     */
    public String getCellType(int x, int y) {
        // Check Boundaries
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT) || table[x][y] == null)
            return null;
        return table[x][y].getCellType();
    }
    
    /**
     * Tells whether the cell at a given position in this table is a type of 
     * <code>LOCK</code>.
     * @param x Table's "X" position
     * @param y Table's "Y" position
     * @return <code>true</code>, if the cell is a <code>LOCK</code> type
     */
    public boolean isLockCell(int x, int y) {
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT) || table[x][y] == null)
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
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT) || table[x][y] == null)
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
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT) || table[x][y] == null)
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
}
