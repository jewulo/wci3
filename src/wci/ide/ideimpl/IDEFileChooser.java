package wci.ide.ideimpl;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
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


    /**
     * Choose a file
     * @param textField filename text field.
     * @param parent the parent of the file selection dialog
     * @return the file selected in the dialog
     */
    public File choose(JTextField textField, Component parent)
    {
        int option = this.showOpenDialog(parent);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = this.getSelectedFile();
            textField.setText(file.getPath());

            return file;
        }
        else {
            return null;
        }
    }

    /**
     * Choose a file
     * @param textField filename text field.
     * @param parent the parent of the file selection dialog
     * @param multiple true if multiple files can be selected, false otherwise
     * @return the file selected in the dialog
     */
    public File choose(JTextField textField, Component parent, boolean multiple)
    {
        if (!multiple) {
            return choose(textField, parent);
        }

        int option = this.showOpenDialog(parent);

        if (option == JFileChooser.APPROVE_OPTION) {
            File[] files = this.getSelectedFiles();
            StringBuffer buffer = new StringBuffer();

            for (int i = 0; i < files.length; ++i) {
                if (i > 0) {
                    buffer.append(";");
                }
                buffer.append(files[i].getPath());
            }

            textField.setText(buffer.toString());
            return files[0];
        }
        else {
            return null;
        }
    }
}
