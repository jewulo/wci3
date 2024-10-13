package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.TokenType;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.Definition;
import wci.intermediate.SymTabEntry;

/**
 * <h1>DeclaredRoutineParser</h1>
 *
 * <p>Parse a declared Pascal Routine</p>
 */
public class DeclaredRoutineParser extends DeclarationsParser
{
    /**
     * <h1>Constructor</h1>
     * @param parent the parent parser.
     */
    public DeclaredRoutineParser(PascalParserTD parent)
    {
        super(parent);
    }

    private static int dummyCounter = 0;    // counter for dummy routine names.
    private SymTabEntry parentId;           // entry of parents routine's name.

    /**
     * Parse a standard subroutine declaration.
     * @param token the initial token.
     * @param parentId the symbol table entry of the parent routine's name.
     * @return the symbol table entry of the declared routine's name.
     * @throws Exception if an error occurred.
     */
    public SymTabEntry parse(Token token, SymTabEntry parentId)
        throws Exception
    {
        Definition routineDefn = null;
        String dummyName = null;
        SymTabEntry routineId = null;
        TokenType routineType = token.getType();

        // Initialize.
        switch ((PascalTokenType) routineType) {

            case PROGRAM: {
                break;
            }

            case PROCEDURE: {
                break;
            }

            case FUNCTION: {
                break;
            }

            default: {
                break;
            }


        }
        return parentId;
    }
}
