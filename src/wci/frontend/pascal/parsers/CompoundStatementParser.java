package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.pascal.*;
import wci.intermediate.*;

import static wci.frontend.pascal.PascalTokenType.*;
import static wci.frontend.pascal.PascalErrorCode.*;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;

/**
 * <h1>CompoundStatementParser<h1/>
 *
 * <p>Parse a Pascal compound statement.</p>
 */
public class CompoundStatementParser extends StatementParser
{
    /**
     * Constructor.
     *
     * @param parent
     */
    public CompoundStatementParser(PascalParserTD parent)
    {
        super(parent);
    }

    /**
     * Parse a compound statement.
     * @param token the initial token.
     * @return the root node of the generated parse tree.
     * @throws Exception if an error occurred.
     */
    public ICodeNode parse(Token token)
        throws Exception
    {
        token = nextToken();        // consume the BEGIN

        // Create the COMPOUND node.
        ICodeNode compoundNode = ICodeFactory.createICodeNode(COMPOUND);

        // Parse the statement list generated by the END token.
        StatementParser statementParser = new StatementParser(this);
        statementParser.parseList(token, compoundNode, END, MISSING_END);

        return compoundNode;
    }
}
