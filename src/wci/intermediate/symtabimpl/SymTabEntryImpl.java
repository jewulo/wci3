package wci.intermediate.symtabimpl;

import java.util.ArrayList;
import java.util.HashMap;

import wci.intermediate.*;

/**
 * <h1>SymTabEntryImpl</h1>
 *
 * <p>An implementation the symbol table entry.</p>
 */
public class SymTabEntryImpl
    extends HashMap<SymTabKey, Object>
    implements SymTabEntry
{
    private String name;                        // entry name
    private SymTab symTab;                      // parent symbol table
    private ArrayList<Integer> lineNumbers;     // source line numbers
    private Definition definition;              // how the identifier is defined
    private TypeSpec typeSpec;                  // type specification

    /**
     * Constructor
     * @param name the name of the entry.
     * @param symTab the symbol table that contains this entry.
     */
    public SymTabEntryImpl(String name, SymTab symTab)
    {
        this.name = name;
        this.symTab = symTab;
        this.lineNumbers = new ArrayList<Integer>();
    }

    /**
     * @return the name
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * @return the symbol table
     */
    @Override
    public SymTab getSymTab()
    {
        return symTab;
    }

    /**
     * Append a source line number to the entry.
     * @param lineNumber the line number to append.
     */
    @Override
    public void appendLineNumber(int lineNumber)
    {
        lineNumbers.add(lineNumber);
    }

    /**
     * @return the line numbers.
     */
    @Override
    public ArrayList<Integer> getLineNumbers()
    {
        return lineNumbers;
    }

    /**
     * Set an attribute of the entry.
     * @param key   the attribute key.
     * @param value the attribute value.
     */
    @Override
    public void setAttribute(SymTabKey key, Object value)
    {
        put(key, value);
    }

    /**
     * Get the value of an attribute of the entry.
     * @param key the attribute key.
     * @return the attribute value
     */
    @Override
    public Object getAttribute(SymTabKey key)
    {
        return get(key);
    }

    /**
     * Setter.
     * @param definition the definition to set.
     */
    @Override
    public void setDefinition(Definition definition)
    {
        this.definition = definition;
    }

    /**
     * Getter
     * @return the current definition.
     */
    @Override
    public Definition getDefinition()
    {
        return this.definition;
    }

    /**
     * Setter
     * @param typeSpec the type specification to set.
     */
    @Override
    public void setTypeSpec(TypeSpec typeSpec)
    {
        this.typeSpec = typeSpec;
    }

    /**
     * Return the typeSpec.
     * @return the typeSpec.
     */
    @Override
    public TypeSpec getTypeSpec()
    {
        return this.typeSpec;
    }
}
