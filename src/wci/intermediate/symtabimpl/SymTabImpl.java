package wci.intermediate.symtabimpl;

import wci.intermediate.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * <h1>SymTabImpl</h1>
 *
 * <p>An implementation the symbol table.</p>
 */
public class SymTabImpl
    extends TreeMap<String, SymTabEntry>
    implements SymTab
{
    private int nestingLevel;

    /**
     * Constructor
     * @param nestingLevel the nesting level of this stack
     */
    public SymTabImpl(int nestingLevel)
    {
        this.nestingLevel = nestingLevel;
    }

    /**
     * Get the nesting level of the stack.
     * @return the nesting level of this stack.
     */
    @Override
    public int getNestingLevel()
    {
        return nestingLevel;
    }

    /**
     * Create and enter a new entry into the symbol table.
     * @param name the name of the entry.
     * @return the new entry
     */
    @Override
    public SymTabEntry enter(String name)
    {
        SymTabEntry entry = SymTabFactory.createSymTabEntry(name, this);
        put(name, entry);

        return entry;
    }

    /**
     * Look up an existing symbol table entry.
     * @param name the name of the entry.
     * @return the entry, or null if it does not exist.
     */
    @Override
    public SymTabEntry lookup(String name)
    {
        return get(name);
    }

    /**
     * @return a list of symbol entries sorted by name.
     */
    @Override
    public ArrayList<SymTabEntry> sortedEntries()
    {
        Collection<SymTabEntry> entries = values();
        Iterator<SymTabEntry> iter = entries.iterator();
        ArrayList<SymTabEntry> list = new ArrayList<SymTabEntry>(size());

        // Iterate over the sorted entries and append them to the list.
        while (iter.hasNext()) {
            list.add(iter.next());
        }

        // sorted list of entries. ???? how is this sorted ???? Is it the tree?
        return list;
    }
}
