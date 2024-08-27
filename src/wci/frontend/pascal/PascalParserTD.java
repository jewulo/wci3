package wci.frontend.pascal;

import wci.frontend.*;
import wci.message.Message;

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

        while (!((token = nextToken()) instanceof EofToken)) {}

        // Send the parser summary message.
        float elapsedTime = (System.currentTimeMillis() - startTime) / 1000.0f;
        sendMessage(new Message(PARSER_SUMMARY,
                new Number[] {token.getLineNumber(),
                              getErrorCount(),
                              elapsedTime}));

    }

    /**
     * Return the number of syntax errors found by the parser.
     * @return the error count.
     */
    @Override
    public int getErrorCount()
    {
        return 0;
    }
}
