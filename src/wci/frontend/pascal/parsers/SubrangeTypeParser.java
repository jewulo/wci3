package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.pascal.PascalParserTD;
import wci.intermediate.TypeSpec;

public class SubrangeTypeParser extends TypeSpecificationParser {

    /**
     * Constructor.
     *
     * @param parent the parent parser
     */
    public SubrangeTypeParser(PascalParserTD parent) {
        super(parent);
    }

    @Override
    public TypeSpec parse(Token token)
        throws Exception
    {
        return super.parse(token);
    }
}
