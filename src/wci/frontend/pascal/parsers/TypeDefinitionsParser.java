package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.pascal.PascalErrorCode;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.SymTabEntry;

import java.util.EnumSet;

import static wci.frontend.pascal.PascalTokenType.*;

/**
 * <h1>TypeDefinitionsParser</h1>
 *
 * <p>Parse Pascal Type Definitions.</p>
 */
public class TypeDefinitionsParser extends DeclarationsParser
{
    /**
     * <h1>TypeDefinitionsParser</h1>
     *
     * <p>Parse Pascal type definitions.</p>
     * @param parent
     */
    public TypeDefinitionsParser(PascalParserTD parent)
    {
        super(parent);
    }

    // Synchronization set for a type identifier.
    private static final EnumSet<PascalTokenType> IDENTIFIER_SET =
        DeclarationsParser.VAR_START_SET.clone();
    static {
        IDENTIFIER_SET.add(IDENTIFIER);
    }

    // Synchronisation set for the = token.
    private static final EnumSet<PascalTokenType> EQUALS_SET =
        ConstantDefinitionsParser.CONSTANT_START_SET.clone();
    static {
        EQUALS_SET.add(EQUALS);
        EQUALS_SET.add(SEMICOLON);
    }

    // Synchronisation set for what follows a definition or declaration.
    private static final EnumSet<PascalTokenType> FOLLOW_SET =
        EnumSet.of(SEMICOLON);

    // Synchronization set for the start of the next definition or declaration.
    private static final EnumSet<PascalTokenType> NEXT_START_SET =
        DeclarationsParser.VAR_START_SET.clone();
    static {
        NEXT_START_SET.add(SEMICOLON);
        NEXT_START_SET.add(IDENTIFIER);
    }

    /**
     * Parse type definition.
     * @param token the initial token.
     * @throws Exception if an error occurred.
     */
    public void parse(Token token)
        throws Exception
    {
        token = synchronize(IDENTIFIER_SET);

        // Loop to parse a sequence of type definitions
        // separated by semicolons.
        while (token.getType() == IDENTIFIER) {
            String name = token.getText().toLowerCase();
            SymTabEntry typeId = getSymTabStack().lookupLocal(name);

            // Enter new identifier into the symbol table
            // but don't set how it's defined yet.
            if (typeId == null) {
                typeId = symTabStack.enterLocal(name);
                typeId.appendLineNumber(token.getLineNumber());
            }
            else {
                errorHandler.flag(token, PascalErrorCode.IDENTIFIER_REDEFINED, this);
                typeId = null;
            }

            token = nextToken();    // consume the identifier token

            // Synchronize on the = token.
        }
    }
}
