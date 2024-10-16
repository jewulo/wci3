package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.pascal.PascalParserTD;
import wci.intermediate.ICodeNode;
import wci.intermediate.RoutineCode;
import wci.intermediate.SymTabEntry;

import static wci.intermediate.symtabimpl.RoutineCodeImpl.DECLARED;
import static wci.intermediate.symtabimpl.RoutineCodeImpl.FORWARD;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.ROUTINE_CODE;

/**
 * <h1>CallParser</h1>
 *
 * <p>Parse a call to a parse or function.</p>
 */
public class CallParser extends StatementParser {
    /**
     * Constructor.
     * @param parent the parent parser.
     */
    public CallParser(PascalParserTD parent)
    {
        super(parent);
    }

    /**
     * Parse a call to a declared procedure or function.
     * @param token the initial token.
     * @return the root node of the generated parse tree.
     * @throws Exception if an error occurred.
     */
    public ICodeNode parse(Token token)
        throws Exception
    {
        SymTabEntry pfId = symTabStack.lookup(token.getText().toLowerCase());
        RoutineCode routineCode = (RoutineCode) pfId.getAttribute(ROUTINE_CODE);
        StatementParser callParser = (routineCode == DECLARED) ||
                                     (routineCode == FORWARD)
                                            ? new CallDeclaredParser(this)
                                            : new CallStandardParser(this);

        return callParser.parse(token);
    }

    /**
     * Parse the actual parameters of a procedure or function call.
     * @param token the current token.
     * @param pfId the symbol table entry of the procedure or function name.
     * @param isDeclared true if parsing actual parms of declared routine.
     * @param isReadReadln true if parsing actual parms of read or readln.
     * @param isWriteWriteLn true if parsing actual parms of read or writeln.
     * @return the PARAMETERS node, or null if there are actual parameters.
     * @throws Exception if an error occurred.
     */
    protected ICodeNode parseActualParameters(Token token, SymTabEntry pfId,
                                              boolean isDeclared,
                                              boolean isReadReadln,
                                              boolean isWriteWriteLn)
        throws Exception
    {
        ICodeNode dummy = null;
        return dummy;
    }
}
