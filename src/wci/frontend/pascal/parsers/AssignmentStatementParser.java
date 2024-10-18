package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.ICodeFactory;
import wci.intermediate.ICodeNode;
import wci.intermediate.TypeSpec;
import wci.intermediate.symtabimpl.Predefined;
import wci.intermediate.typeimpl.TypeChecker;

import java.util.EnumSet;

import static wci.frontend.pascal.PascalErrorCode.INCOMPATIBLE_TYPES;
import static wci.frontend.pascal.PascalErrorCode.MISSING_COLON_EQUALS;
import static wci.frontend.pascal.PascalTokenType.COLON_EQUALS;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.ASSIGN;

/**
 * <h1>AssignmentStatementParser</h1>
 *
 * <p>Parse a Pascal ASSIGNMENT statement.</p>
 */

public class AssignmentStatementParser extends StatementParser
{
    /**
     * Constructor.
     *
     * @param parent the parent parser.
     */
    public AssignmentStatementParser(PascalParserTD parent)
    {
        super(parent);
    }

    // Synchronisation set for the := token.
    private static final EnumSet<PascalTokenType> COLON_EQUALS_SET =
        ExpressionParser.EXPR_START_SET.clone();
    static {
        COLON_EQUALS_SET.add(COLON_EQUALS);
        COLON_EQUALS_SET.addAll(StatementParser.STMT_FOLLOW_SET);
    }

    /**
     * Parse an assignment statement.
     * @param token the initial token.
     * @return the root node of the generated parse tree.
     * @return intermediate info containing the root node of the
     * generated parse tree and type specification.
     * @throws Exception if an error occurred.
     */
    public ICodeNode parse(Token token)
            throws Exception
    {
        // Create the ASSIGN node.
        ICodeNode assignNode = ICodeFactory.createICodeNode(ASSIGN);

        // Parse the target variable.
        VariableParser variableParser = new VariableParser(this);
        ICodeNode targetNode = isFunctionTarget
                                ? variableParser.parseFunctionNameTarget(token)
                                : variableParser.parse(token);
        TypeSpec targetType = targetNode != null ? targetNode.getTypeSpec()
                                                 : Predefined.undefinedType;

        // The ASSIGN node adopts the variable node as its first child.
        assignNode.addChild(targetNode);

        // Synchronise on the := token.
        token = synchronize(COLON_EQUALS_SET);
        if (token.getType() == COLON_EQUALS) {
            token = nextToken();    // consume the :=
        }
        else {
            errorHandler.flag(token, MISSING_COLON_EQUALS, this);
        }

        // Parse the expression. The ASSIGN node adopts the expression's
        // node as its second child.
        ExpressionParser expressionParser = new ExpressionParser(this);
        ICodeNode exprNode = expressionParser.parse(token);
        assignNode.addChild(exprNode);

        // Type check: Assignment compatible?
        TypeSpec exprType = exprNode != null ? exprNode.getTypeSpec()
                                             : Predefined.undefinedType;
        if (!TypeChecker.areAssignmentCompatible(targetType, exprType)) {
            errorHandler.flag(token, INCOMPATIBLE_TYPES, this);
        }

        assignNode.setTypeSpec(targetType);
        return assignNode;
    }

    // Set to true to parse a function name
    // as the target of an assignment.
    private boolean isFunctionTarget = false;

    /**
     * Parse an assignment to a function name.
     * @param token the Token
     * @return ICodeNode
     * @throws Exception
     */
    public ICodeNode parseFunctionNameAssignment(Token token)
        throws Exception
    {
        isFunctionTarget = true;
        return parse(token);
    }
/*
    public ICodeNode parse(Token token)
        throws Exception
    {
        // create the ASSIGN node.
        ICodeNode assignNode = ICodeFactory.createICodeNode(ASSIGN);

        // Look up the target identifier in the symbol table stack.
        // Enter the identifier into the table if it's not found.
        String targetName = token.getText().toLowerCase();
        SymTabEntry targetId = symTabStack.lookup(targetName);
        if (targetId == null) {
            targetId = symTabStack.enterLocal(targetName);
        }
        targetId.appendLineNumber(token.getLineNumber());

        token = nextToken();    // consume the identifier token

        // Create the variable node and set its name attribute.
        ICodeNode variableNode = ICodeFactory.createICodeNode(VARIABLE);
        variableNode.setAttribute(ID, targetId);

        // The ASSIGN node adopts the variable node as its first child.
        assignNode.addChild(variableNode);

        // Synchronise on the := token.
        token = synchronize(COLON_EQUALS_SET);
        if (token.getType() == COLON_EQUALS) {
            token = nextToken();    // consume the :=
        }
        else {
            errorHandler.flag(token, MISSING_COLON_EQUALS, this);
        }

        // Parse the expression. The ASSIGN node adopts the expression's
        // node as its second child.
        ExpressionParser expressionParser = new ExpressionParser(this);
        assignNode.addChild(expressionParser.parse(token));

        return assignNode;
    }
*/
}
