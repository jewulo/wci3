package wci.backend.interpreter.executors;

import wci.frontend.pascal.parsers.CallDeclaredParser;
import wci.frontend.pascal.parsers.CallStandardParser;
import wci.intermediate.*;
import wci.backend.interpreter.*;

import static wci.intermediate.icodeimpl.ICodeKeyImpl.ID;
import static wci.intermediate.symtabimpl.RoutineCodeImpl.DECLARED;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.ROUTINE_CODE;

/**
 * <h1>CallExecutor</h1>
 *
 * <p>Execute a call to a procedure or function.</p>
 */
public class CallExecutor extends StatementExecutor
{
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
        SymTabEntry routineId = (SymTabEntry) node.getAttribute(ID);
        RoutineCode routineCode = (RoutineCode) routineId.getAttribute(ROUTINE_CODE);
        CallExecutor callExecutor = routineCode == DECLARED
                                                   ? new CallDeclaredExecutor(this)
                                                   : new CallStandardExecutor(this);

        ++executionCount;   // count the call statement
        return callExecutor.execute(node);
    }
}
