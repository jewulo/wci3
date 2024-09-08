package wci.frontend.pascal;

import wci.frontend.*;
import wci.intermediate.SymTabEntry;
import wci.message.Message;

import static wci.frontend.pascal.PascalErrorCode.IO_ERROR;
import static wci.frontend.pascal.PascalTokenType.ERROR;
import static wci.frontend.pascal.PascalTokenType.IDENTIFIER;
import static wci.message.MessageType.PARSER_SUMMARY;
import static wci.message.MessageType.TOKEN;



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

    protected static PascalErrorHandler errorHandler = new PascalErrorHandler();
    /**
     * Parse a Pascal source program and generate the symbol
     * and the intermediate code.
     *
     * @throws Exception
     */
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

    /**
     * Return the number of syntax errors found by the parser.
     * @return the error count.
     */
    @Override
    public int getErrorCount()
    {
        return errorHandler.getErrorCount();
    }
}
