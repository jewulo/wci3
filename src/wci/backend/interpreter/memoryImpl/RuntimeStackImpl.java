package wci.backend.interpreter.memoryImpl;

import wci.backend.interpreter.ActivationRecord;
import wci.backend.interpreter.MemoryFactory;
import wci.backend.interpreter.RuntimeDisplay;
import wci.backend.interpreter.RuntimeStack;

import java.util.ArrayList;

/**
 * <h1>RuntimeStackImpl</h1>
 *
 * <p>The interpreters Runtime stack.</p>
 */
public class RuntimeStackImpl
    extends ArrayList<ActivationRecord>
    implements RuntimeStack
{
    private RuntimeDisplay display;     // runtime display

    /**
     * Constructor.
     */
    public RuntimeStackImpl()
    {
        display = MemoryFactory.createRuntimeDisplay();
    }

    /**
     * @return an array of activation records on the stack.
     */
    @Override
    public ArrayList<ActivationRecord> records()
    {
        return this;
    }

    /**
     * Get the topmost activation record at a given nesting level.
     * @param nestingLevel the nesting level.
     * @return the activation record.
     */
    @Override
    public ActivationRecord getTopmost(int nestingLevel)
    {
        return display.getActivationRecord(nestingLevel);
    }

    /**
     * Get the current nesting level.
     * @return the current nesting level.
     */
    @Override
    public int currentNestingLevel()
    {
        int topIndex = size() - 1;
        return topIndex >= 0 ? get(topIndex).getNestingLevel() : -1;
    }

    /**
     * Push an activation record onto the stack for a routine being called.
     * @param ar the activation record to push.
     */
    @Override
    public void push(ActivationRecord ar)
    {
        int nestingLevel = ar.getNestingLevel();

        add(ar);
        display.callUpdate(nestingLevel, ar);
    }

    /**
     * Pop an activation record of the stack of the stack for a returning routine.
     */
    @Override
    public void pop()
    {
        display.returnUpdate(currentNestingLevel());
        remove(size() - 1);
    }
}
