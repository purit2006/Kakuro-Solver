import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Creates a Kakuro table for playing game.</br />
 * Version 1 since 16/06/2015<br />
 * Version 2 since 30/06/2015
 *
 * @author Purit
 * @author Marcus Vinicius Pereira Araujo 1106149
 * @author Piyapat Russamitinakornkul 1106291
 * 
 * @version 2.1
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
        if(cellType == KakuroCell.PUZZLE && num1 != null && num2 != null) {
            // Create a puzzle cell
            table[x][y] = new KakuroCell("D" + ID, "R" + ID, KakuroCell.PUZZLE);
            // Initialize cell with values
            table[x][y].setDownValue(num1);
            table[x][y].setRightValue(num2);
            table[x][y].fixCell();
            return true;
        }
        else if(cellType == KakuroCell.PLAY) {
            // Create a play cell
            table[x][y] = new KakuroCell("D" + ID, "R" + ID, KakuroCell.PLAY);
            return true;
        }
        else if(cellType == KakuroCell.LOCK && num1 != null && num1 > 0 && num1 <= 9) {
            // Create a lock cell
            table[x][y] = new KakuroCell("D" + ID, "R" + ID, KakuroCell.LOCK);
            // Initialize cell with values
            table[x][y].setDownValue(num1);
            table[x][y].fixCell();
            return true;
        }

        return false;
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
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT) || !table[x][y].isPlayCell())
            return null;
        // Remove the value from the cell
        return table[x][y].clearDownValue();
    }
    
    /**
     * Fetches the <code>KakuroCell</code> object at a given spot on a table.
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
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT))
            return null;
        return table[x][y].getCellType();
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

            file.writeObject(this);
            file.close();
            
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
