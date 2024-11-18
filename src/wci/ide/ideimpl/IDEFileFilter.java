package wci.ide.ideimpl;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * <h1>IDEFileFilter</h1>
 *
 * <p>The file filter for class IDEFileChooser.</p>
 *
 * <p>This class was not separate in the books GitHub.
 *    It was an internal private class in IDEFileChoose.java</p>
 */
public class IDEFileFilter extends FileFilter
{
    private String[] extensions;
    private String description;

    /**
     * Constructor
     * @param extensions the available file extensions
     * @param description the description
     */
    public IDEFileFilter(String[] extensions, String description)
    {
        this.extensions = new String[extensions.length];
        this.description = description;

        for (int i = 0; i < extensions.length; ++i) {
            this.extensions[i] = extensions[i].toLowerCase();
        }
    }

    /**
     * Tests whether the specified abstract pathname should be
     * included in a pathname list.
     * @param file The abstract pathname to be tested
     * @return true if and only if pathname should be included
     */
    @Override
    public boolean accept(File file)
    {
        if (file.isDirectory()) {
            return true;
        }

        String name = file.getName().toLowerCase();
        for (int i = 0; i < extensions.length; ++i) {
            if (name.endsWith(extensions[i])) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return the file description
     */
    public String getDescription()
    {
        return description;
    }
}
