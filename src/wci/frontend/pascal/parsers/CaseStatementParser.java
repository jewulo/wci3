package wci.frontend.pascal.parsers;

import wci.frontend.pascal.PascalParserTD;

/**
 * <h1>CaseStatementParser</h1>
 *
 * <p>Parse a Pascal CASE statement.</p>
 */

public class CaseStatementParser extends StatementParser {
    /**
     * Constructor.
     *
     * @param parent the parent parser.
     */
    public CaseStatementParser(PascalParserTD parent) {
        super(parent);
    }
}
