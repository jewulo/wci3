package wci.backend.compiler.generators;

import wci.backend.interpreter.executors.AssignmentExecutor;
import wci.intermediate.*;
import wci.intermediate.icodeimpl.*;
import wci.backend.compiler.*;
import wci.message.*;

import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;
import static wci.intermediate.typeimpl.TypeKeyImpl.*;
import static wci.intermediate.typeimpl.TypeFormImpl.*;
import static wci.backend.compiler.Directive.*;
import static wci.message.MessageType.*;

/**
 * <h1>StatementGenerator</h1>
 *
 * <p>Generate code for a statement.<p/>
 */
public class StatementGenerator extends CodeGenerator
{
    /**
     * Constructor
     * @param parent the parent executor
     */
    public StatementGenerator(CodeGenerator parent)
    {
        super(parent);
    }

    /**
     * Generate code for a statement.
     * To be overridden by the specialized statement executor subclasses.
     * @param node the root node of the statement.
     */
    public void generate(ICodeNode node)
        throws PascalCompilerException
    {
        ICodeNodeTypeImpl nodeType = (ICodeNodeTypeImpl) node.getType();
        int line = 0;

        if (nodeType != COMPOUND) {
            line = getLineNumber(node);
            emitDirective(Directive.LINE, line);
        }

        // Generate code for a statement according to the type of statement.
        switch (nodeType) {

            case COMPOUND: {
                CompoundGenerator compoundGenerator = new CompoundGenerator(this);
                compoundGenerator.generate(node);
                break;
            }

            case ASSIGN: {
                AssignmentGenerator assignmentGenerator = new AssignmentGenerator(this);
                assignmentGenerator.generate(node);
                break;
            }

            case CALL: {
                CallGenerator callGenerator = new CallGenerator(this);
                callGenerator.generate(node);
                break;
            }
        }

        // Verify that the stack height after each statement is 0.
        if (localStack.getSize() != 0) {
            throw new PascalCompilerException(
                    String.format("Stack size error: size = %d after line %d",
                            localStack.getSize(), line));
        }
    }

    /**
     * Get the source line number of the parse tree node.
     * @param node the parse three node.
     * @return the line number.
     */
    private int getLineNumber(ICodeNode node)
    {
        Object lineNumber = null;

        // Go up the parent links to look for a line number.
        while ((node != null) && ((lineNumber = node.getAttribute(ICodeKeyImpl.LINE)) == null)) {
            node = node.getParent();
        }

        return (Integer) lineNumber;
    }
}
