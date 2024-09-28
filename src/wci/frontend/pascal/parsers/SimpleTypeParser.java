package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.pascal.PascalErrorCode;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.Definition;
import wci.intermediate.SymTabEntry;
import wci.intermediate.TypeSpec;
import wci.intermediate.symtabimpl.DefinitionImpl;

import java.util.EnumSet;

import static wci.frontend.pascal.PascalTokenType.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.CONSTANT;
import static wci.intermediate.symtabimpl.DefinitionImpl.ENUMERATION_CONSTANT;

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
     * Constructor
     * @param parent the parent parser.
     */
    public SimpleTypeParser(PascalParserTD parent) {
        super(parent);
    }

    /**
     * Parse a simple Pascal type specification.
     * @param token the token to parse.
     * @return TypeSpec the simple type specification.
     * @throws Exception if an error occurred.
     */
    public TypeSpec parse(Token token)
        throws Exception
    {
        // Synchronise at the start of a simple type specification.
        token = synchronize(SIMPLE_TYPE_START_SET);

        switch ((PascalTokenType) token.getType()) {
            case IDENTIFIER: {
                String name = token.getText().toLowerCase();
                SymTabEntry id = symTabStack.lookup(name);

                if (id != null) {
                    Definition definition = id.getDefinition();

                    // It is either a type identifier
                    // or the start of a subrange type.
                    if (definition == DefinitionImpl.TYPE) {
                        id.appendLineNumber(token.getLineNumber());
                        token = nextToken();    // consume the identifier.

                        // Return the type of the referent tip.
                        return id.getTypeSpec();
                    }
                    else if ((definition != CONSTANT) &&
                             (definition != ENUMERATION_CONSTANT)) {
                        errorHandler.flag(token, PascalErrorCode.NOT_TYPE_IDENTIFIER, this);
                        token = nextToken();    // consume the identifier.
                        return null;
                    }
                    else {
                        SubrangeTypeParser subrangeTypeParser =
                            new SubrangeTypeParser(this);
                        return subrangeTypeParser.parse(token);
                    }
                }
                else {
                    errorHandler.flag(token, PascalErrorCode.IDENTIFIER_UNDEFINED, this);
                    token = nextToken();    // consume the identifier.
                    return null;
                }
            }

            case LEFT_PAREN: {
                EnumerationTypeParser enumerationTypeParser =
                    new EnumerationTypeParser(this);
                return enumerationTypeParser.parse(token);
            }

            case COMMA:
            case SEMICOLON: {
                errorHandler.flag(token, PascalErrorCode.INVALID_TYPE, this);
                return null;
            }

            default: {
                SubrangeTypeParser subrangeTypeParser =
                    new SubrangeTypeParser(this);
                return subrangeTypeParser.parse(token);
            }
        }
    }
}
