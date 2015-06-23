/**
 * Creates a cell with a fixed number to act as a problem to be solved.
 *
 * @author Purit
 * @author Marcus
 * @author Piyapat Russamitinakornkul 1106291
 * 
 * @version 1
 * @since 23/06/2015
 */
public class KakuroPuzzleCell extends KakuroCell {
    /*******************
     * 1. Data Members *
     ******************/
    private final int downValue;
    private final int rightValue;

    /**************************
     * 2. Default Constructor *
     *************************/
    /**
     * Creates a cell as a component of a Kakuro table as a clue for other solutions.
     * @param downID An identification of this cell and others in a down direction
     * @param rightID A second identification of this cell and others to the right
     * @param number The value for cells in a down direction (Can be null, but not both)
     * @param number2 The value for cells to the right (Can be null, but not both)
     */
    public KakuroPuzzleCell(String downID, String rightID, Integer number, Integer number2) {
        super(downID, rightID);
        downValue = number;
        rightValue = number2;
        lockCell();
    }

    /**************
     * 3. Methods *
     *************/
    /**
     * Returns a first sum (Puzzle) of this cell
     * @return A first value of this puzzle cell
     */
    public int getDownValue() {
        return downValue;
    }
    
    /**
     * Returns a second sum (Puzzle) of this cell
     * @return A second value of this puzzle cell
     */
    public int getRightValue() {
        return rightValue;
    }
}
