package wci.backend.interpreter;

import java.util.ArrayList;

/**
 * <h1>MemoryMap</h1>
 *
 * <p> Interface for this interpreter's runtime memory map.</p>
 */
public interface MemoryMap
{
    /**
     * Return the memory cell with the given name.
     * @param name the name,
     * @return the cell.
     */
    public Cell getCell(String name);

    /**
     * Return all names.
     * @return the list of all names.
     */
    public ArrayList<String> getAllNames();
}
