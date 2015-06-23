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
    // General Signals
    public static final int PUZZLE = 0;
    public static final int PLAY = 1;
    public static final int LOCK = 2;
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
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT))
            return false;
        else {
            String forID = Integer.toString(x) + Integer.toString(y);
            if(cellType == PUZZLE)
                table[x][y] = new KakuroPuzzleCell("D" + forID,
                                                   "R" + forID,
                                                    x,
                                                    y);
            else if(cellType == PLAY)
                table[x][y] = new KakuroPlayCell("D" + forID, "R" + forID);
            else if(cellType == LOCK)
                table[x][y] = new KakuroPlayCell("D" + forID, "R" + forID);
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
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT))
            return null;
        else {
            KakuroCell results = table[x][y];
            table[x][y] = null;
            return results;
        }
    }
    
    public boolean fillCell(int x, int y, int value) {
        if((x < 0 || x >= WIDTH) || (y < 0 || y >= HEIGHT))
            return false;
        else if(table[x][y] instanceof KakuroPlayCell)
            ;
        return true;
    }
}
