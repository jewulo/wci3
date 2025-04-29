package wci.backend.compiler.generators;

import java.util.ArrayList;

import wci.intermediate.*;
import wci.intermediate.icodeimpl.*;
import wci.intermediate.symtabimpl.*;
import wci.backend.compiler.*;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.*;
import static wci.intermediate.typeimpl.TypeFormImpl.*;
import static wci.intermediate.typeimpl.TypeKeyImpl.*;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;
import static wci.backend.compiler.Instruction.*;


/**
 * <h1>Assignment Statement</h1>
 * <p>Generate code for an assignment statement.</p>
 */
public class AssignmentGenerator extends StatementGenerator
{
    /**
     * Constructor.
     * @param parent the parent executor.
     */
    public AssignmentGenerator(StatementGenerator parent)
    {
        super(parent);
    }

    /**
     * Generate code for an assignment statement.
     * @param node the root node of the statement.
     */
    public void generate(ICodeNode node)
    {
    }

    /**
     * Generate code to assign a scalar value
     * @param targetType the data type of the target.
     * @param targetId the symbol table entry of the target variable.
     * @param index the index of the target variable.
     * @param nestingLevel the nesting level of the target variable.
     * @param exprNode the expression tree node.
     * @param exprType the expression data type.
     * @param exprGenerator the expression generator.
     */
    private void generateScalarAssignment(TypeSpec targetType,
                                          SymTabEntry targetId,
                                          int index, int nestingLevel,
                                          ICodeNode exprNode,
                                          TypeSpec exprType,
                                          ExpressionGenerator exprGenerator)
    {}
}
