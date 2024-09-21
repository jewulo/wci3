package wci.frontend.pascal;

import wci.frontend.*;
import wci.frontend.pascal.parsers.BlockParser;
import wci.frontend.pascal.parsers.StatementParser;
import wci.intermediate.ICode;
import wci.intermediate.ICodeFactory;
import wci.intermediate.ICodeNode;
import wci.intermediate.SymTabEntry;
import wci.intermediate.symtabimpl.DefinitionImpl;
import wci.intermediate.symtabimpl.Predefined;
import wci.message.Message;

import java.util.EnumSet;

import static wci.frontend.pascal.PascalErrorCode.*;
import static wci.frontend.pascal.PascalTokenType.*;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.ROUTINE_ICODE;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.ROUTINE_SYMTAB;
import static wci.message.MessageType.PARSER_SUMMARY;


/**
 * <h1>PascalParserTD</>
 *
 * <p>The top down Pascal parser.</>
 */
public class PascalParserTD extends Parser
{
    private SymTabEntry routineId;

    /**
     * Constructor.
     *
     * @param scanner the scanner to be used by this parser.
     */
    public PascalParserTD(Scanner scanner)
    {
        super(scanner);
    }

    /**
     * Constructor for subclasses.
     *
     * @param parent the parent parser.
     */
    public PascalParserTD(PascalParserTD parent)
    {
        super(parent.getScanner());
    }

    protected static PascalErrorHandler errorHandler = new PascalErrorHandler();

    /**
     * Parse a Pascal source program and generate the symbol
     * and the intermediate code.
     *
     * @throws Exception if an error occurred
     */
    @Override
    public void parse()
            throws Exception
    {
        long startTime = System.currentTimeMillis();

        ICode iCode = ICodeFactory.createICode();
        Predefined.initialize(symTabStack);

        // Create a dummy program identifier symbol table entry.
        routineId = symTabStack.enterLocal("DummyProgramName".toLowerCase());
        routineId.setDefinition(DefinitionImpl.PROGRAM);
        symTabStack.setProgramId(this.routineId);

        // Push a new symbol table onto the symbol table stack and set
        // the routine's symbol table and intermediate code.
        routineId.setAttribute(ROUTINE_SYMTAB, symTabStack.push());
        routineId.setAttribute(ROUTINE_ICODE, iCode);

        BlockParser blockParser = new BlockParser(this);

        try {
            Token token = nextToken();

            // Parse a block.
            ICodeNode rootNode = blockParser.parse(token, routineId);
            iCode.setRoot(rootNode);
            symTabStack.pop();

            /*
            // Look for a BEGIN to parse a compound statement.
            if (token.getType() == BEGIN) {
                StatementParser statementParser = new StatementParser(this);
                rootNode = statementParser.parse(token);
                token = currentToken();
            }
            else  {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
            }

            // Set the parse tree root node.
            if (rootNode != null) {
                iCode.setRoot(rootNode);
            }

            */

            // Look for the final period.
            token = currentToken();
            if (token.getType() != DOT) {
                errorHandler.flag(token, MISSING_PERIOD, this);
            }
            token = currentToken();


            // Send the parse summary message.
            float elaspedTime = (System.currentTimeMillis() - startTime) / 1000f;
            sendMessage(new Message(PARSER_SUMMARY,
                                    new Number[] {token.getLineNumber(),
                                                    getErrorCount(),
                                                    elaspedTime}));
        } catch (java.io.IOException e) {
            errorHandler.abortTranslation(IO_ERROR, this);
        }
    }

    /**
     * Return the number of syntax errors found by the parser.
     * @return the error count.
     */
    @Override
    public int getErrorCount()
    {
        return errorHandler.getErrorCount();
    }

    /**
     * Synchronize the parser.
     * @param syncSet the set of token types for synchronizing the parser.
     * @return the token where the parser has synchronised.
     * @throws Exception if an error occurred.
     */
    public Token synchronize(EnumSet syncSet)
        throws Exception
    {
        Token token = currentToken();

        // If the current token is not in the synchronization set,
        // then it is unexpected and the parser must recover.
        if (!syncSet.contains(token.getType())) {
            // flag the unexpected token.
            errorHandler.flag(token, UNEXPECTED_TOKEN, this);

            // Recover by skipping tokens that are not
            // in the synchronisation set.
            do {
                token = nextToken();
            } while (!(token instanceof EofToken) &&
                     !syncSet.contains(token.getType()));
        }

        return token;
    }
}
