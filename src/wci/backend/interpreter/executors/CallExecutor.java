package wci.backend.interpreter.executors;

import wci.intermediate.*;
import wci.backend.interpreter.*;

/**
 * <h1>CallExecutor</h1>
 *
 * <p>Execute a call to a procedure or function.</p>
 */
public class CallExecutor extends StatementExecutor {
    /**
     * Constructor
     * @param parent the parent executor.
     */
    public CallExecutor(Executor parent)
    {
        super(parent);
    }

    /**
     * Execute procedure or function call statement.
     * @param node the root node of the call.
     * @return the value of the call.
     */
    public Object execute(ICodeNode node)
    {
        return null;
    }
}
