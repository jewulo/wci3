package wci.backend.interpreter;

import wci.intermediate.SymTabEntry;

import java.util.ArrayList;

/**
 * <h1>ActivationRecord</h1>
 *
 * <p>Interface for the interpreter's runtime activation record.</p>
 */
public interface ActivationRecord
{
    /**
     * Getter.
     * @return the symbol table entry of the routine's name.
     */
    public SymTabEntry getRoutineId();

    /**
     * Return the memory cell for a given name from the memory map.
     * @param name the name.
     * @return the cell.
     */
    public Cell getCell(String name);

    /**
     * @return the list of all the names in a memory map.
     */
    public ArrayList<String> getAllNames();

    /**
     * Getter.
     * @return the scope nesting level.
     */
    public int getNestingLevel();

    /**
     * @return the activation record to which this record is dynamically linked.
     */
    ActivationRecord linkedTo();

    /**
     * Make a dynamic link from this activation record to another one.
     * @param ar the activation record to link to.
     * @return this activation record.
     */
    ActivationRecord makeLinkTo(ActivationRecord ar);

}
