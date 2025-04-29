package wci.backend.compiler.generators;

import java.util.ArrayList;

import wci.intermediate.*;
import wci.backend.compiler.*;

import static wci.backend.compiler.Instruction.*;

/**
 * <h1>Compound Statement</h1>
 * <p>Generate code for a compound statement.</p>
 */
public class CompoundGenerator extends StatementGenerator {
    /**
     * Constructor.
     * @param parent the parent executor.
     */
    public CompoundGenerator(CodeGenerator parent)
    {
        super(parent);
    }

    /**
     * Generate code for a compound statement.
     * @param node the root node of the compound statement.
     * @throws PascalCompilerException when an error occurs.
     */
    public void generate(ICodeNode node)
        throws PascalCompilerException
    {
        ArrayList<ICodeNode> children = node.getChildren();

        // Loop over the statement children of the COMPOUND node and generate
        // code for each statement. Emit a NOP if there are no statements.
        //if (children.size() == 0) {
        //    emit(NOP);
        //}
        if (children.isEmpty()) {
            emit(NOP);
        }
        else {
            StatementGenerator statementGenerator = new StatementGenerator(this);

            for (ICodeNode child : children) {
                statementGenerator.generate(child);
            }
        }
    }
}
