package wci.backend.interpreter.executors;

import wci.backend.interpreter.Executor;
import wci.intermediate.ICodeNode;

import java.util.ArrayList;
import java.util.HashMap;

import static wci.intermediate.icodeimpl.ICodeKeyImpl.VALUE;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.STRING_CONSTANT;

/**
 * <h1>SelectExecutorOptimized</h1>
 *
 * <p>Execute a Select (A CASE Statement) using HASH TABLES.</p>
 */
public class SelectExecutorOptimized extends StatementExecutor
{
    /**
     * Constructor
     *
     * @param parent the parent executor.
     */
    public SelectExecutorOptimized(Executor parent)
    {
        super(parent);
    }

    // Jump table cache: entry key is a SELECT node.
    //                   entry value is the jump table.
    // Jump table: entry key is a selection value
    //             entry value is the branch statement.
    private static HashMap<ICodeNode, HashMap<Object, ICodeNode>> jumpCache =
            new HashMap<ICodeNode, HashMap<Object, ICodeNode>>();

    /**
     * Execute SELECT statement.
     * @param node the root node of the statement.
     * @return null.
     */
    @Override
    public Object execute(ICodeNode node)
    {
        // Is there already an entry for this SELECT node in the
        // jump table cache? If not, create a jump table entry.
        HashMap<Object, ICodeNode> jumpTable = jumpCache.get(node);
        if (jumpTable == null) {
            jumpTable = createJumpTable(node);
            jumpCache.put(node, jumpTable);
        }

        // Get the SELECT node's children.
        ArrayList<ICodeNode> selectChildren = node.getChildren();
        ICodeNode exprNode = selectChildren.get(0);

        // Evaluate the SELECT expression.
        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        Object selectValue = expressionExecutor.execute(exprNode);

        // If there is a selection, execute the SELECT_BRANCH's statement.
        ICodeNode statementNode = jumpTable.get(selectValue);
        if (statementNode != null) {
            StatementExecutor statementExecutor = new StatementExecutor(this);
            statementExecutor.execute(statementNode);
        }

        ++executionCount;   // count the SELECT statement itself
        return null;
    }

    /**
     * Create a jump table for a SELECT node.
     * @param node the SELECT node.
     * @return the jump table.
     */
    private HashMap<Object, ICodeNode> createJumpTable(ICodeNode node)
    {
        HashMap<Object, ICodeNode> jumpTable = new HashMap<Object, ICodeNode>();

        // Loop over children that are SELECT_BRANCH nodes.
        ArrayList<ICodeNode> selectChildren = node.getChildren();
        for (int i = 1; i < selectChildren.size(); ++i) {
            ICodeNode branchNode = selectChildren.get(i);
            ICodeNode constantsNode = branchNode.getChildren().get(0);
            ICodeNode statementNode = branchNode.getChildren().get(1);

            // Loop over the constants children of the branch's CONSTANT_NODE.
            ArrayList<ICodeNode> constantsList = constantsNode.getChildren();
            for (ICodeNode constantNode : constantsList) {

                // Create a jump table entry.
                // Convert a single-character string constant to a character.
                Object value = constantNode.getAttribute(VALUE);
                if (constantNode.getType() == STRING_CONSTANT) {
                    value = ((String) value).charAt(0);
                }
                jumpTable.put(value, statementNode);
            }
        }

        return jumpTable;
    }
}
