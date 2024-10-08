package wci.backend.interpreter.executors;

import wci.backend.interpreter.Executor;
import wci.intermediate.ICodeNode;

import java.util.ArrayList;

/**
 * <h1>CompoundExecutor</h1>
 *
 * <p>Execute a compound statement.</p>
 */
public class CompoundExecutor extends Executor
{
    /**
     * Constructor
     * @param parent the parent executor.
     */
    public CompoundExecutor(Executor parent)
    {
        super(parent);
    }

    /**
     * Execute a compound statement.
     * @param node the root node of the compound statement.
     * @return null. Statements have no return value.
     */
    public Object execute(ICodeNode node)
    {
        // Loop over the children of the COMPOUND node and execute each child.
        StatementExecutor statementExecutor = new StatementExecutor(this);
        ArrayList<ICodeNode> children = node.getChildren();
        for (ICodeNode child : children) {
            statementExecutor.execute(child);
        }

        return null;
    }
}
