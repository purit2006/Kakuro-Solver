/**
 * Javadoc first introduction paragraph here!
 *
 * @author Piyapat Russamitinakornkul 55090031
 */
public abstract class KakuroCell {
    /*******************
     * 1. Data Members *
     ******************/
    final private String ID;
    final private String ID_2;
    private boolean isLocked;

    /*******************
     * 2. Constructors *
     *******************/
    /**
     * Creates a cell as a component of a Kakuro table.
     * @param id The identification of this cell
     * @param id2 The second identification of this cell
     */
    public KakuroCell(String id, String id2) {
        ID = id;
        ID_2 = id2;
    }
    
    /**************
     * 3. Methods *
     *************/
    /**
     * Getter method for obtaining a first identification of this cell.
     * @return The first ID of this cell
     */
    public String getID() {
        return ID;
    }
    
    /**
     * Getter method for obtaining a second identification of this cell.
     * @return The second ID of this cell 
     */
    public String getID2() {
        return ID_2;
    }
    
    /**
     * Locks the cell to allow no modifications
     */
    public void lockCell() {
        isLocked = true;
    }
    
    /**
     * Unlocks the cell to allow modifications
     */
    public void unlockCell() {
        isLocked = false;
    }
    
    /**
     * Tells whether values in this cell could be modified.
     * @return <code>true</code>, if the cell could not be modified
     */
    public boolean isLockedCell() {
        return isLocked;
    }
}
