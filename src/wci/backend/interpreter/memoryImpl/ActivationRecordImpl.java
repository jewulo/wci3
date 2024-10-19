package wci.backend.interpreter.memoryImpl;

import java.util.ArrayList;

import wci.intermediate.*;
import wci.backend.interpreter.*;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;

/**
 * <h1>ActivationRecordImpl</h1>
 *
 * <p>The runtime activation record.</p>
 */
public class ActivationRecordImpl implements ActivationRecord
{
    private SymTabEntry routineId;  // symbol table entry of the routine's name.
    private ActivationRecord link;  // dynamic link to previous record.
    private int nestingLevel;       // scope nesting level of this record.
    private MemoryMap memoryMap;    // memory map of this stack record.

    /**
     * Constructor.
     * @param routineId the symbol table entry of the routine's name.
     */
    public ActivationRecordImpl(SymTabEntry routineId)
    {
        SymTab symTab = (SymTab) routineId.getAttribute(ROUTINE_SYMTAB);

        this.routineId = routineId;
        this.nestingLevel = symTab.getNestingLevel();
        this.memoryMap = MemoryFactory.createMemoryMap(symTab);
    }

    /**
     * Getter.
     * @return the symbol table entry of the routine's name.
     */
    @Override
    public SymTabEntry getRoutineId()
    {
        return this.routineId;
    }

    /**
     * Return the memory cell for a given name from the memory map.
     * @param name the name.
     * @return the cell.
     */
    @Override
    public Cell getCell(String name)
    {
        return memoryMap.getCell(name);
    }

    /**
     * @return the list of all the names in a memory map.
     */
    @Override
    public ArrayList<String> getAllNames()
    {
        return memoryMap.getAllNames();
    }

    /**
     * Getter.
     * @return the scope nesting level.
     */
    @Override
    public int getNestingLevel()
    {
        return this.nestingLevel;
    }

    /**
     * @return the activation record to which this record is dynamically linked.
     */
    @Override
    public ActivationRecord linkedTo()
    {
        return this.link;
    }

    /**
     * Make a dynamic link from this activation record to another one.
     * @param ar the activation record to link to.
     * @return this activation record.
     */
    @Override
    public ActivationRecord makeLinkTo(ActivationRecord ar)
    {
        this.link = ar;
        return this;
    }
}
