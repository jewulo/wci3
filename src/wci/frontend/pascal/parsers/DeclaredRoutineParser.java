package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.TokenType;
import wci.frontend.pascal.PascalErrorCode;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.DefinitionImpl;
import wci.intermediate.symtabimpl.Predefined;
import wci.intermediate.typeimpl.TypeFormImpl;

import java.util.ArrayList;

import static wci.frontend.pascal.PascalErrorCode.*;
import static wci.frontend.pascal.PascalTokenType.IDENTIFIER;
import static wci.frontend.pascal.PascalTokenType.SEMICOLON;
import static wci.intermediate.symtabimpl.RoutineCodeImpl.DECLARED;
import static wci.intermediate.symtabimpl.RoutineCodeImpl.FORWARD;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;

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
                token = nextToken();    // consume PROGRAM
                routineDefn = DefinitionImpl.PROGRAM;
                dummyName = "DummyProgramName".toLowerCase();
                break;
            }

            case PROCEDURE: {
                token = nextToken();    // consume PROCEDURE
                routineDefn = DefinitionImpl.PROCEDURE;
                dummyName = "DummyProcedureName_".toLowerCase() +
                                String.format("%03d", ++dummyCounter);
                break;
            }

            case FUNCTION: {
                token = nextToken();    // consume FUNCTION
                routineDefn = DefinitionImpl.FUNCTION;
                dummyName = "DummyFunctionName_".toLowerCase() +
                                String.format("%03d", ++dummyCounter);
                break;
            }

            default: {
                routineDefn = DefinitionImpl.PROGRAM;
                dummyName = "DummyProgramName".toLowerCase();
                break;
            }
        }

        // Parse the routine name.
        routineId = parseRoutineName(token,dummyName);
        routineId.setDefinition(routineDefn);

        token = currentToken();

        // Create new intermediate code for the routine.
        ICode iCode = ICodeFactory.createICode();
        routineId.setAttribute(ROUTINE_ICODE, iCode);
        routineId.setAttribute(ROUTINE_ROUTINES, new ArrayList<SymTabEntry>());

        // Push the routine's new symbol unto the stack.
        // If it was forwarded push its existing symbol table.
        if (routineId.getAttribute(ROUTINE_CODE) == FORWARD) {
            SymTab symTab = (SymTab) routineId.getAttribute(ROUTINE_SYMTAB);
            symTabStack.push(symTab);
        }
        else {
            routineId.setAttribute(ROUTINE_SYMTAB, symTabStack.push());
        }

        // Program: Set the program identifier in the symbol table stack.
        if (routineDefn == DefinitionImpl.PROGRAM) {
            symTabStack.setProgramId(routineId);
        }
        // Non-forwarded procedure or function: Append to the parent's list of routines.
        else if (routineId.getAttribute(ROUTINE_CODE) != FORWARD) {
            ArrayList<SymTabEntry> subroutines = (ArrayList<SymTabEntry>)
                    parentId.getAttribute(ROUTINE_ROUTINES);
            subroutines.add(routineId);
        }

        // If the routine was forwarded, there should not be
        // any formal parameters or a function return type.
        // But parse anyway them if they're there.
        if (routineId.getAttribute(ROUTINE_CODE) == FORWARD) {
            if (token.getType() != SEMICOLON) {
                errorHandler.flag(token, ALREADY_FORWARDED, this);
                parseHeader(token, routineId);
            }
        }
        // Parse the routine's formal parameters and function return type.
        else {
            parseHeader(token, routineId);
        }

        // Look for the semicolon.
        token = currentToken();
        if (token.getType() == SEMICOLON) {
            do {
                token = nextToken();    // consume ;
            } while (token.getType() == SEMICOLON);
        }
        else {
            errorHandler.flag(token, MISSING_SEMICOLON, this);
        }

        // Parse the routine's block or forward declaration.
        if ((token.getType() == IDENTIFIER) &&
            (token.getText().equalsIgnoreCase("forward")))
        {
            token = nextToken();    // consume forward
            routineId.setAttribute(ROUTINE_CODE, FORWARD);
        }
        else {
            routineId.setAttribute(ROUTINE_CODE, DECLARED);

            BlockParser blockParser = new BlockParser(this);
            ICodeNode rootNode = blockParser.parse(token, routineId);
            iCode.setRoot(rootNode);
        }

        // Pop the routine's symbol table off the stack.
        symTabStack.pop();

        return routineId;
    }

    /**
     * Parse a routine's name.
     * @param token the current token.
     * @param dummyName a dummyName in case of parsing problem.
     * @return the symbol table entry of the declared routines name.
     * @throws Exception if an error occurred.
     */
    private SymTabEntry parseRoutineName(Token token, String dummyName)
        throws Exception
    {
        SymTabEntry routineId = null;

        // parse the routine name identifier
        if (token.getType() == IDENTIFIER) {
            String routineName = token.getText().toLowerCase();
            routineId = symTabStack.lookupLocal(routineName);

            // Not already defined locally: Enter into local symbol table.
            if (routineId == null) {
                routineId = symTabStack.enterLocal(routineName);
            }
            // If already defined, it should be a forward definition.
            else if (routineId.getAttribute(ROUTINE_CODE) != FORWARD) {
                routineId = null;
                errorHandler.flag(token, IDENTIFIER_REDEFINED, this);
            }

            token = nextToken();    // consume routine name identifier.
        }
        else {
            errorHandler.flag(token, MISSING_IDENTIFIER, this);
        }

        // If necessary, create a dummy routine name symbol table entry.
        if (routineId == null) {
            routineId = symTabStack.enterLocal(dummyName);
        }

        return routineId;
    }

    /**
     * Parse a routine's formal parameter list and the function return type.
     * @param token the current token.
     * @param routineId the symbol table entry of the declared routine's name.
     * @throws Exception if an error occurred.
     */
    private void parseHeader(Token token, SymTabEntry routineId)
        throws Exception
    {
        // Parse the routine's formal parameters.
        parseFormalParameters(token, routineId);
        token = currentToken();

        // If this is a function, parse and set its return type.
        if (routineId.getDefinition() == DefinitionImpl.FUNCTION) {
            VariableDeclarationsParser variableDeclarationsParser =
                new VariableDeclarationsParser(this);
            variableDeclarationsParser.setDefinition(DefinitionImpl.FUNCTION);
            TypeSpec type = variableDeclarationsParser.parseTypeSec(token);

            token = currentToken();

            // The return type cannot be an array or record.
            if (type != null) {
                TypeForm form = type.getForm();
                if ((form == TypeFormImpl.ARRAY) ||
                    (form == TypeFormImpl.RECORD))
                {
                    errorHandler.flag(token, INVALID_TYPE, this;;
                }
            }
            // Missing Return Type
            else {
                type = Predefined.undefinedType;
            }

            routineId.setTypeSpec(type);
            token = currentToken();
        }
    }

    /**
     *
     * @param token
     * @param routineId
     */
    private void parseFormalParameters(Token token, SymTabEntry routineId)
    {
    }

}
