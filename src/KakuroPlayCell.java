/**
 * Javadoc first introduction paragraph here!
 *
 * @author Piyapat Russamitinakornkul 55090031
 */
public class KakuroPlayCell extends KakuroCell {
    /*******************
     * 1. Data Members *
     ******************/
    private Integer value;

    /**************************
     * 2. Default Constructor *
     *************************/
    /**
     * Creates a cell as a component of a Kakuro table for filling a solution.
     * @param id The identification of this cell
     * @param id2 The second identification of this cell
     */
    public KakuroPlayCell(String id, String id2) {
        super(id, id2);
    }
    
    /**
     * Creates a cell as a component of a Kakuro table for filling a solution, 
     * given a number.
     * @param id The identification of this cell
     * @param id2 The second identification of this cell
     * @param number An initial given solution for a cell
     */
    public KakuroPlayCell(String id, String id2, int number) {
        super(id, id2);
        value = number;
    }

    /**************
     * 3. Methods *
     *************/
    /**
     * Fills a number into the cell, overwrites the old value+.
     * @param number A given number to be filled
     * @return <code>true</code>, if the <code>number</code> is filled into the cell
     */
    public boolean fillValue(int number) {
        // Fill the cell only if the cell is modifiable
        if(!isLockedCell()) {
            value = number;
            return true;
        }
        
        return false;
    }
    
    /**
     * Cleans the value of the cell, but not the cell identification!.
     * @return A value in this cell
     */
    public Integer clearValue() {
        // Remove the number only if exists, and the cell is modifiable
        if(!isLockedCell() && value != null) {
            int results = value;        // Retrieve the cell value
            value = null;               // Blank the value in the cell
            return results;             // Return results
        }
        
        return null;
    }
    
    /**
     * Retrieves a value from the cell
     * @return A number in the cell
     */
    public Integer getValue() {
        return value;
    }
    
    /**
     * Checks whether the cell is empty.
     * @return <code>true</code>, if the cell is empty
     */
    public boolean isBlank() {
        return value == null;
    }
}
