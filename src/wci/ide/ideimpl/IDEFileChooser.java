package wci.ide.ideimpl;

import javax.swing.*;
import java.io.File;

/**
 * <h1>IDEFileChooser</h1>
 * <p>The IDE file chooser.</p>
 *
 */
public class IDEFileChooser
    extends JFileChooser
{
    /**
     * Constructor
     * @param directoryPath the directory path.
     * @param filePath the file path.
     * @param filter the file extension filter.
     * @param directories flag to show directories
     * @param multiple flag to select multiple files. (I think?)
     */
    public IDEFileChooser(String directoryPath, String filePath, IDEFileFilter filter,
                          boolean directories, boolean multiple)
    {
        super();
        this.setMultiSelectionEnabled(multiple);
        this.setFileSelectionMode(directories ? DIRECTORIES_ONLY
                                              : FILES_ONLY);

        if (filePath != null) {
            this.setSelectedFile(new File(filePath));
        }
        else {
            if (directoryPath == null) {
                directoryPath = System.getProperties().
                                    getProperty("file separator").equals("/")
                                    ? System.getProperties().getProperty("user.home")
                                    : "c:\\";
            }

            this.setCurrentDirectory(new File(directoryPath));
        }

        if (filter != null) {
            this.addChoosableFileFilter(filter);
        }
    }

    /**
     * Constructor
     * @param directoryPath the directory path.
     * @param filePath the file path.
     * @param filter the file extension filter.
     */
    public IDEFileChooser(String directoryPath, String filePath, IDEFileFilter filter)
    {
        this(directoryPath, filePath, filter, false, false);
    }


    public File choose(JTextField sourcePathText, EditFrame editFrame) {
        return null;
    }
}
