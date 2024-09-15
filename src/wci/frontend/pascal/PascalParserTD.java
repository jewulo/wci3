package wci.frontend.pascal;

import wci.frontend.*;
import wci.frontend.pascal.parsers.StatementParser;
import wci.intermediate.ICodeFactory;
import wci.intermediate.ICodeNode;
import wci.message.Message;

import java.util.EnumSet;

import static wci.frontend.pascal.PascalErrorCode.*;
import static wci.frontend.pascal.PascalTokenType.*;
import static wci.message.MessageType.PARSER_SUMMARY;


/**
 * <h1>PascalParserTD</>
 *
 * <p>The top down Pascal parser.</>
 */
public class PascalParserTD extends Parser
{
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
        iCode = ICodeFactory.createICode();

        try {
            Token token = nextToken();
            ICodeNode rootNode = null;

            // Look for a BEGIN to parse a compound statement.
            if (token.getType() == BEGIN) {
                StatementParser statementParser = new StatementParser(this);
                rootNode = statementParser.parse(token);
                token = currentToken();
            }
            else  {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
            }

            // Look for the final period.
            if (token.getType() != DOT) {
                errorHandler.flag(token, MISSING_PERIOD, this);
            }
            token = currentToken();

            // Set the parse tree root node.
            if (rootNode != null) {
                iCode.setRoot(rootNode);
            }

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
/*


    /**
     * Parse a Pascal source program and generate the symbol
     * and the intermediate code.
     *
     * @throws Exception if an error occurred
     */
    /*

    @Override
    public void parse()
        throws Exception
    {
        Token   token;
        long    startTime = System.currentTimeMillis();

        try {
            // Loop over each token until the end of file.
            while (!((token = nextToken()) instanceof EofToken)) {
                TokenType tokenType = token.getType();

                // Cross-reference only the identifiers
                if (tokenType == IDENTIFIER) {
                    String name = token.getText().toLowerCase();

                    // If it is not already in the symbol table.
                    // create and enter a new entry for the identifier.
                    SymTabEntry entry = symTabStack.lookup(name);
                    if (entry == null) {
                        entry = symTabStack.enterLocal(name);
                    }

                    // Append the current line number to the entry
                    entry.appendLineNumber(token.getLineNumber());
                }
                else if (tokenType == ERROR) {
                    errorHandler.flag(token, (PascalErrorCode) token.getValue(),
                                this);

//                    // Format each token
//                    sendMessage(new Message(TOKEN,
//                                            new Object[] {token.getLineNumber(),
//                                                          token.getPosition(),
//                                                          tokenType,
//                                                          token.getText(),
//                                                          token.getValue()}));
                }
            }

            // Send the parser summary message.
            float elapsedTime = (System.currentTimeMillis() - startTime) / 1000.0f;
            sendMessage(new Message(PARSER_SUMMARY,
                                    new Number[] {token.getLineNumber(),
                                                  getErrorCount(),
                                                  elapsedTime}));
        } catch (java.io.IOException e) {
            errorHandler.abortTranslation(IO_ERROR, this);
        }

    }

*/
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
