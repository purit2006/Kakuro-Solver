/**
 * Javadoc first introduction paragraph here!
 *
 * @author Piyapat Russamitinakornkul 55090031
 */
public class KakuroPuzzleCell extends KakuroCell {
    /*******************
     * 1. Data Members *
     ******************/
    private final int value;
    private final int value2;

    /**************************
     * 2. Default Constructor *
     *************************/
    /**
     * Creates a cell as a component of a Kakuro table as a clue for other solutions.
     * @param id
     * @param id2
     * @param number
     * @param number2 
     */
    public KakuroPuzzleCell(String id, String id2, int number, int number2) {
        super(id, id2);
        value = number;
        value2 = number2;
        lockCell();
    }

    /**************
     * 3. Methods *
     *************/
    /**
     * Returns a first sum (Puzzle) of this cell
     * @return A first value of this puzzle cell
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Returns a second sum (Puzzle) of this cell
     * @return A second value of this puzzle cell
     */
    public int getValue2() {
        return value2;
    }
}
