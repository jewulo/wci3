package wci.backend.interpreter.memoryImpl;

import wci.backend.interpreter.ActivationRecord;
import wci.backend.interpreter.RuntimeDisplay;

import java.util.ArrayList;

public class RuntimeDisplayImpl
        extends ArrayList<ActivationRecord>
        implements RuntimeDisplay
{
    /**
     * Constructor.
     */
    public RuntimeDisplayImpl()
    {
        add(null);  // dummy element 0 (never used)
    }

    /**
     * Get the activation record at a given nesting level.
     * @param nestingLevel the nesting level.
     * @return the activation record.
     */
    @Override
    public ActivationRecord getActivationRecord(int nestingLevel)
    {
        return get(nestingLevel);
    }

    /**
     * Update the display for a call to a routine at a given nesting level.
     * @param nestingLevel the nesting level.
     * @param ar           the activation record for the routine.
     */
    @Override
    public void callUpdate(int nestingLevel, ActivationRecord ar)
    {
        // Next higher nesting level: Append a new element at the top.
        if (nestingLevel >= size()) {
            add(ar);
        }
        // Existing nesting level: Set at specified level.
        else {
            ActivationRecord prevAr = get(nestingLevel);
            set(nestingLevel, ar.makeLinkTo(prevAr));
        }
    }

    /**
     * Update the display for a return from a routine at the given nesting level.
     * @param nestingLevel the nesting level.
     */
    @Override
    public void returnUpdate(int nestingLevel)
    {
        int topIndex = size() - 1;
        ActivationRecord ar = get(nestingLevel);    // AR about to be popped off.
        ActivationRecord prevAr = ar.linkedTo();    // previous AR it points to.

        // Point the element at that nesting level to the
        // previous activation record.
        if (prevAr != null) {
            set(nestingLevel, prevAr);
        }
        // The top element has become null, so remove it.
        else if (nestingLevel == topIndex) {
            remove(topIndex);
        }
    }
}
