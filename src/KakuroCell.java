/**
 * A class of all types of cells. Cannot be created. Please create subclasses of 
 * this class instead.
 *
 * @author Purit
 * @author Marcus
 * @author Piyapat Russamitinakornkul 1106291
 * 
 * @version 1
 * @since 16/06/2015
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
     * @param downID  An identification of this cell and others in a down direction
     * @param rightID  A second identification of this cell and others to the right
     */
    public KakuroCell(String downID, String rightID) {
        ID = downID;
        ID_2 = rightID;
    }
    
    /**************
     * 3. Methods *
     *************/
    /**
     * Getter method for obtaining a first identification of this cell.
     * @return The first ID of this cell
     */
    public String getDownID() {
        return ID;
    }
    
    /**
     * Getter method for obtaining a second identification of this cell.
     * @return The second ID of this cell 
     */
    public String getRightID() {
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
