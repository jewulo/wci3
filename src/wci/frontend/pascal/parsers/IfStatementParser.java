package wci.frontend.pascal.parsers;

import wci.frontend.pascal.PascalParserTD;

/**
 * <h1>IfStatementParser</h1>
 *
 * <p>Parse a Pascal IF statement.</p>
 */

public class IfStatementParser extends StatementParser {
    /**
     * Constructor.
     *
     * @param parent the parent parser.
     */
    public IfStatementParser(PascalParserTD parent) {
        super(parent);
    }
}
