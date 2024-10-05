package wci.frontend.pascal.parsers;

import java.util.EnumSet;

import wci.frontend.*;
import wci.frontend.pascal.*;
import wci.intermediate.*;

import static wci.frontend.pascal.PascalTokenType.*;
import static wci.frontend.pascal.PascalErrorCode.*;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.VARIABLE;

/**
 * <h1>DeclarationsParser</h1>
 *
 * <p>Parse Pascal declarations.</p>
 */
public class DeclarationsParser extends PascalParserTD
{
    /**
     * Constructor
     * @param parent the parent parser.
     */
    public DeclarationsParser(PascalParserTD parent)
    {
        super(parent);
    }

    static final EnumSet<PascalTokenType> DECLARATION_START_SET =
        EnumSet.of(CONST, TYPE, VAR, PROCEDURE, FUNCTION, BEGIN);

    static final EnumSet<PascalTokenType> TYPE_START_SET =
        DECLARATION_START_SET.clone();
    static {
        TYPE_START_SET.remove(CONST);
    }
    /* -- just for clarity of thought
    static final EnumSet<PascalTokenType> TYPE_START_SET =
        EnumSet.of(TYPE, VAR, PROCEDURE, FUNCTION, BEGIN);
     */

    static final EnumSet<PascalTokenType> VAR_START_SET =
        TYPE_START_SET.clone();
    static {
        VAR_START_SET.remove(TYPE);
    }
    /*  -- just for clarity of thought
    static final EnumSet<PascalTokenType> VAR_START_SET =
        EnumSet.of(VAR, PROCEDURE, FUNCTION, BEGIN);
     */

    static final EnumSet<PascalTokenType> ROUTINE_START_SET =
        VAR_START_SET.clone();
    static {
        ROUTINE_START_SET.remove(VAR);
    }
    /*  -- just for clarity of thought
    static final EnumSet<PascalTokenType> ROUTINE_START_SET =
        EnumSet.of(PROCEDURE, FUNCTION, BEGIN);
     */

    /**
     * Parse constant declarations.
     * To be overridden by the specialised declarations parser subclasses.
     * @param token the initial token.
     * @param parentId the symbol table entry of the parent routine's name.
     * @return null.
     * @throws Exception
     */
    public SymTabEntry parse(Token token, SymTabEntry parentId)
        throws Exception
    {
        token = synchronize(DECLARATION_START_SET);

        if (token.getType() == CONST) {
            token = nextToken();    // consume CONST

            ConstantDefinitionsParser constantDefinitionsParser =
                new ConstantDefinitionsParser(this);
            constantDefinitionsParser.parse(token);
        }

        token = synchronize(TYPE_START_SET);

        if (token.getType() == TYPE) {
            token = nextToken();    // consume TYPE

            TypeDefinitionsParser typeDefinitionsParser =
                new TypeDefinitionsParser(this);
            typeDefinitionsParser.parse(token);
        }

        token = synchronize(VAR_START_SET);

        if (token.getType() == VAR) {
            token = nextToken();    // consume VAR

            VariableDeclarationsParser variableDeclarationsParser =
                new VariableDeclarationsParser(this);
            variableDeclarationsParser.setDefinition(VARIABLE);
            variableDeclarationsParser.parse(token);
        }

        token = synchronize(ROUTINE_START_SET);
        TokenType tokenType = token.getType();

        // look for procedures and functions and parse them to create
        // types of class DeclaredRoutineParser.
        while ((tokenType == PROCEDURE) || (tokenType == FUNCTION)) {
            DeclaredRoutineParser routineParser =
                new DeclaredRoutineParser(this);
            routineParser.parse(token, parentId);

            // Look for one or more semicolons after a definition.
            token = currentToken();
            if (token.getType() == SEMICOLON) {
                while (token.getType() == SEMICOLON) {
                    token = nextToken();    // consume the ;
                }
            }

            token = synchronize(ROUTINE_START_SET);
            tokenType = tokenType = token.getType();
        }

        return null;
    }
}
