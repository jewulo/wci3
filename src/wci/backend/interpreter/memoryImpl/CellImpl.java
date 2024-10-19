package wci.backend.interpreter.memoryImpl;

import wci.backend.interpreter.Cell;

/**
 * <h1>CellImpl</h1>
 *
 * <p>The interpreter's runtime memory cell.</p>
 */
public class CellImpl implements Cell
{
    private Object value = null;    // value contained in the memory cell.

    /**
     * Constructor
     * @param value the value for the cell.
     */
    public CellImpl(Object value)
    {
        this.value = value;
    }

    /**
     * Set a new value into the cell.
     * @param newValue the new value,
     */
    @Override
    public void setValue(Object newValue)
    {
        value = newValue;
    }

    /**
     * Get value in the cell.
     * @return the value in the cell.
     */
    @Override
    public Object getValue()
    {
        return this.value;
    }
}
