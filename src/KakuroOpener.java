import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p title="Text File Reader">
 * This object simply reads a file path and returns a string of all the contents
 * contained in the text file. The class will provide an access to the "Open"
 * dialog box and allows the user to select ONLY the text files. Texts in the
 * file will be read and displayed in the program's main text area.
 * </p>
 *
 * @author Purit Thong-On 1106
 * @author Marcus Vinicius Pereira Araujo 1106149
 * @author Piyapat Russamitinakornkul 1106291
 * @since Friday 30/06/2015
 */
public class KakuroOpener
{
    /*
     * Program texts are declared here. Displaying contents can be modified from
     * the class "program".
     */
    
    // String for storing information in the file
    private KakuroTable dataFromFile;
    // "Open" Dialog box when choosing file types
    final private static String READABLE_FILES = "Serializable File";
    final private static String READABLE_EXTENSION = "ser";
    // File reading error dialog box
    private final String FILE_ERROR_HEAD = "Invalid file";
    private final String FILE_ERROR_BODY = "The serializable loaded is not a Kakuro Table!";
    private final String INVALID_FILE = "The selected file is not a serializable file!";
    // The name of the file
    private String FILENAME;
    
    /*
     * Program components are decleared here.
     * Modifications of the program (Component properties) can be made in methods
     * Component variables are declared with small letters at the beginning and
     * no space between word, but with capitalizing instead.
     */
    
    private JFileChooser getFile = new JFileChooser();          // Object for getting files
    private FileNameExtensionFilter specifyFile;                // File Filter
    
    // Constructor
    /**
     * Reads the text to be displayed on labels and dialog boxes.
     */
    public KakuroOpener()
    { 
        // Allow only text file to be readable
        specifyFile = new FileNameExtensionFilter(READABLE_FILES, READABLE_EXTENSION);
        getFile.addChoosableFileFilter(specifyFile);
        getFile.setFileFilter(specifyFile);
    }
    
    /**
     * Reads all text input from the text file given by the "Open" Dialog box.
     * 
     * @return Texts read from the specified text file
     */
    public KakuroTable LoadFile() {
        // File chooser (Open Dialog)
        if (getFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            // Perform nothing if the file is not a serializable file
            if(!isValidFile(READABLE_EXTENSION)) {
                // Inform the user for invalid or corrupted files
                JOptionPane.showMessageDialog(
                    null,
                    INVALID_FILE,
                    FILE_ERROR_HEAD,
                    JOptionPane.ERROR_MESSAGE);
                return null;          // Give no string
            }

            // Attempt to get the input file to be read
            try(ObjectInputStream file = new ObjectInputStream(new FileInputStream(getFile.getSelectedFile()))) {
                // Read the object
                dataFromFile = (KakuroTable)(file.readObject());
                
                file.close();               // Close the data file
                
                // Set the filename
                FILENAME = getFile.getSelectedFile().getName();
                
                return dataFromFile;             // Return the fetched strings
            } 
            // Inform the user if the file is not a "KakuroTable" object!
            catch (Exception trashVariable) {
                // Inform the user for invalid or corrupted files
                JOptionPane.showMessageDialog(
                    null,
                    FILE_ERROR_BODY,
                    FILE_ERROR_HEAD,
                    JOptionPane.ERROR_MESSAGE);
                trashVariable.printStackTrace();
            }
        }
        
        return null;
    }
    
    /**
     * Checks whether the file received has the extension matched to the given 
     * function parameter.
     * 
     * @param fileExtension The extension which to file extension has to match
     * @return <code>true</code> if the file extension matches the parameter
     */
    public boolean isValidFile(String fileExtension) {
        String filename = getFile.getSelectedFile().getName();          // Get the file name
        return filename.substring(filename.length() - (fileExtension.length())).compareTo(fileExtension) == 0;
    }
    
    /**
     * Returns null if the file is not present.
     * @return The name of the loaded file
     */
    public final String getFileName() {
        return FILENAME;
    }
}
