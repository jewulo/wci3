package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.TokenType;
import wci.frontend.pascal.PascalErrorCode;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.SymTabEntry;
import wci.intermediate.TypeSpec;

import java.util.EnumSet;

import static wci.frontend.pascal.PascalErrorCode.*;
import static wci.frontend.pascal.PascalTokenType.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.CONSTANT;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.CONSTANT_VALUE;

/**
 * <h1>ConstantDefinitionsParser</h1>
 *
 * <p>Parse Pascal constant definitions.</p>
 */
public class ConstantDefinitionsParser extends DeclarationsParser
{
    /**
     * Constructor.
     * @param parent the initial token.
     */
    public ConstantDefinitionsParser(PascalParserTD parent)
    {
        super(parent);
    }

    // Synchronisation set for a constant identifier.
    private static final EnumSet<PascalTokenType> IDENTIFIER_SET =
        DeclarationsParser.TYPE_START_SET.clone();
    static {
        IDENTIFIER_SET.add(IDENTIFIER);
    }

    // Synchronisation set for a starting constant.
    static final EnumSet<PascalTokenType> CONSTANT_START_SET =
        EnumSet.of(IDENTIFIER, INTEGER, REAL, PLUS, MINUS, STRING, SEMICOLON);

    // Synchronisation set for the = token.
    private static final EnumSet<PascalTokenType> EQUALS_SET =
        CONSTANT_START_SET.clone();
    static {
        EQUALS_SET.add(EQUALS);
        EQUALS_SET.add(SEMICOLON);
    }

    // Synchronisation set for the start of the next definition or declaration.
    private static final EnumSet<PascalTokenType> NEXT_START_SET =
        DeclarationsParser.TYPE_START_SET.clone();
    static {
        NEXT_START_SET.add(SEMICOLON);
        NEXT_START_SET.add(IDENTIFIER);
    }

    /**
     * Parse constant definitions.
     * @param token the initial token.
     * @throws Exception if an error occurred.
     */
    public void parse(Token token)
        throws Exception
    {
        token = synchronize(IDENTIFIER_SET);

        // Loop to parse a sequence of constant definitions
        // separated by semicolons.
        while (token.getType() == IDENTIFIER) {
            String name = token.getText().toLowerCase();
            SymTabEntry constantId = symTabStack.lookupLocal(name);

            // Enter the new identifier into the symbol table
            // but don't set how it's defined yet.
            if (constantId == null) {
                constantId = symTabStack.enterLocal(name);
                constantId.appendLineNumber(token.getLineNumber());
            }
            else {
                errorHandler.flag(token, IDENTIFIER_REDEFINED, this);
                constantId = null;
            }

            token = nextToken();    // consume the identifier token

            // Synchronise on the = token
            token = synchronize(EQUALS_SET);
            if (token.getType() == EQUALS) {
                token = nextToken();    // consume the =
            }
            else {
                errorHandler.flag(token, MISSING_EQUALS, this);
            }

            // Parse the constant value.
            Token constantToken = token;
            Object value = parseConstant(token);

            // Set identifier to be a constant and set its value.
            if (constantId != null) {
                constantId.setDefinition(CONSTANT);
                constantId.setAttribute(CONSTANT_VALUE, value);

                // Set the constant's type.
                TypeSpec constantType =
                    constantToken.getType() == IDENTIFIER
                        ? getConstantType(constantToken)
                        : getConstantType(value);
                constantId.setTypeSpec(constantType);
            }

            token = currentToken();
            TokenType tokenType = token.getType();

            // Look for one or more semicolons after a definition.
            if (tokenType == SEMICOLON) {
                while (token.getType() == SEMICOLON) {
                    token = nextToken();    // consume the ;
                }
            }
            // If at the start of the next definition or declaration,
            // then missing a semicolon.
            else {
               errorHandler.flag(token, MISSING_SEMICOLON, this);
            }

            token = synchronize(IDENTIFIER_SET);
        }
    }
}
