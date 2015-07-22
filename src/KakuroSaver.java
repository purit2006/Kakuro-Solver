import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p title="Text File Writer">
 * This object simply writes a string to a text file contained in the text area
 * of the program. The "Save" dialog box will allow the user to save all the texts
 * in the text area of the program to the text files. This object will act like
 * ordinary save dialog box in other general programs.
 * </p>
 * 
 * @author Piyapat Russamitinakornkul 55090031
 * @author Pongrawee Jutadhammakorn 55090033
 * @since Friday 1/11/2013
 */
public class KakuroSaver
{
    /*
     * Program texts are declared here. Displaying contents can be modified from
     * the class "program".
     */
    
    // String for storing information from the program to the text file
    final private KakuroTable dataFromProgram;
    // "Open" Dialog box when choosing file types
    final private static String READABLE_FILES = "Serializable File";
    final private static String READABLE_EXTENSION = ".ser";
    // File reading error dialog box
    final private static String FILE_ERROR_HEAD = "The file could not be saved.";
    final private static String FILE_ERROR_BODY = "Save Error";
    // The name of the file
    private String FILENAME;
    
    /*
     * Program components are decleared here.
     * Modifications of the program (Component properties) can be made in methods
     * Component variables are declared with small letters at the beginning and
     * no space between word, but with capitalizing instead.
     */
    
    private JFileChooser saveFile = new JFileChooser();         // Object for getting files
    private FileNameExtensionFilter specifyFile;                // File Filter
    
    // Constructor
    /**
     * Reads the text to be displayed on labels and dialog boxes.
     * 
     * @param contents A list of text labels to be displayed
     */
    public KakuroSaver(KakuroTable contents)
    {
        dataFromProgram = contents;
        
        //Write only text files
        specifyFile = new FileNameExtensionFilter(READABLE_FILES, READABLE_EXTENSION);
        saveFile.addChoosableFileFilter(specifyFile);
        saveFile.setFileFilter(specifyFile);
    }
    
    /**
     * Writes all text from the program's main interface to the specified text 
     * file provided by the "Save" Dialog box.
     */
    public void saveFile() {
        if(saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try(ObjectOutputStream file = new ObjectOutputStream(
                    new FileOutputStream(saveFile.getSelectedFile().getCanonicalFile()))) {

                file.writeObject(dataFromProgram);     // Serialize this object to a given filePath.
                file.close();                          // Close the pointer to this file

                // Set the filename
                FILENAME = saveFile.getSelectedFile().getName();
            }
            catch (Exception e) {
                // Inform the user for invalid or corrupted files
                JOptionPane.showMessageDialog(
                        null,
                        FILE_ERROR_BODY,
                        FILE_ERROR_HEAD,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Returns null if the file is not present.
     * @return The name of the loaded file
     */
    public final String getFileName() {
        return FILENAME;
    }
}
