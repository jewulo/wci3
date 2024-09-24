package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.TypeSpec;

import java.util.EnumSet;

import static wci.frontend.pascal.PascalTokenType.*;

/**
 * <h1>SimpleTypeParser</h1>
 *
 * <p>Parse a simple Pascal type (identifier, subrange, enumeration)</p>
 */
public class SimpleTypeParser extends TypeSpecificationParser {

    // Synchronisation set for starting a simple type specification.
    static final EnumSet<PascalTokenType> SIMPLE_TYPE_START_SET =
        ConstantDefinitionsParser.CONSTANT_START_SET.clone();
    static {
        SIMPLE_TYPE_START_SET.add(LEFT_PAREN);
        SIMPLE_TYPE_START_SET.add(COMMA);
        SIMPLE_TYPE_START_SET.add(SEMICOLON);
    }

    /**
     * Parse
     * @param parent
     */
    public SimpleTypeParser(PascalParserTD parent) {
    }

    public TypeSpec parse(Token token) {
    }
}
