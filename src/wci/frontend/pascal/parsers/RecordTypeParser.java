package wci.frontend.pascal.parsers;

import java.util.ArrayList;
import java.util.EnumSet;

import wci.frontend.Token;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.TypeFactory;
import wci.intermediate.TypeSpec;

import static wci.frontend.pascal.PascalTokenType.*;
import static wci.frontend.pascal.PascalErrorCode.*;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.*;
import static wci.intermediate.typeimpl.TypeFormImpl.RECORD;
import static wci.intermediate.typeimpl.TypeKeyImpl.*;

/**
 * <h1>RecordTypeParser</h1>
 *
 * <p>Parse a Pascal record type specification.</p>
 */
public class RecordTypeParser extends TypeSpecificationParser
{
    /**
     * Constructor.
     * @param parent the parent parser.
     */
    public RecordTypeParser(PascalParserTD parent)
    {
        super(parent);
    }

    // Synchronization set for END.
    private static final EnumSet<PascalTokenType> END_SET =
        DeclarationsParser.VAR_START_SET.clone();
    static {
        END_SET.add(END);
        END_SET.add(SEMICOLON);
    }

    /**
     * Parse a Pascal record type specification.
     * @param token the current token.
     * @return the record type specification.
     * @throws Exception if an error occurred.
     */
    public TypeSpec parse(Token token)
        throws Exception
    {
        TypeSpec recordType = TypeFactory.createType(RECORD);
        token = nextToken();    // consume RECORD

        // Push a symbol table for the RECORD type specification.
            // Record definitions require a symbol table because each
            // record definition forms a new variable scope.
        recordType.setAttribute(RECORD_SYMTAB, symTabStack.push());

        // Parse the field declarations.
        VariableDeclarationsParser variableDeclarationsParser =
            new VariableDeclarationsParser(this);
        variableDeclarationsParser.setDefinition(FIELD);
        variableDeclarationsParser.parse(token);

        // Pop off the record's symbol table.
        symTabStack.pop();

        // Synchronize at the END.
        token = synchronize(END_SET);

        // Look for the END.
        if (token.getType() == END) {
            token = nextToken();    // consume END
        }
        else {
            errorHandler.flag(token, MISSING_END, this);
        }

        return recordType;
    }
}
