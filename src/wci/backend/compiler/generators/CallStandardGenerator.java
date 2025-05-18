package wci.backend.compiler.generators;

import java.util.ArrayList;

import wci.frontend.*;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.*;
import wci.backend.compiler.*;

import static wci.frontend.pascal.PascalTokenType.*;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.symtabimpl.RoutineCodeImpl.*;
import static wci.intermediate.typeimpl.TypeKeyImpl.*;
import static wci.intermediate.typeimpl.TypeFormImpl.*;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;
import static wci.backend.compiler.Instruction.*;

/**
 * <h1>CallStandardGenerator</h1>
 * <p>Generate code to call a standard procedure or function.</p>
 */
public class CallStandardGenerator extends CallGenerator {
    private ExpressionGenerator exprGenerator;

    public CallStandardGenerator(CodeGenerator parent)
    {
        super(parent);
    }

    /**
     * Generate code to call a standard procedure or function.
     * @param node the root node of the statement.
     */
    @Override
    public void generate(ICodeNode node)
    {
        SymTabEntry routineId = (SymTabEntry) node.getAttribute(ID);
        RoutineCode routineCode = (RoutineCode) routineId.getAttribute(ROUTINE_CODE);
        exprGenerator = new ExpressionGenerator(this);
        ICodeNode actualNode = null;

        // Get the actual parameters of the call.
        if (node.getChildren().size() > 0) {
            ICodeNode parmsNode = node.getChildren().get(0);
            actualNode = parmsNode.getChildren().get(0);
        }

        switch ((RoutineCodeImpl) routineCode) {
            case READ:
            case READLN:    generateReadReadln(node, routineCode); break;

            case WRITE:
            case WRITELN:   generateWriteWriteln(node, routineCode); break;

            case EOF:
            case EOLN:      generateEofEoln(node, routineCode); break;

            case ABS:
            case SQR:       generateAbsSqr(routineCode, actualNode); break;

            case ARCTAN:
            case COS:
            case EXP:
            case LN:
            case SIN:
            case SQRT:      generateArctanCosExpLnSinSqrt(routineCode, actualNode); break;

            case PRED:
            case SUCC:      generatePredSucc(routineCode, actualNode); break;

            case CHR:       generateChr(actualNode); break;
            case ODD:       generateOdd(actualNode); break;

            case ORD:       generateOrd(actualNode); break;

            case ROUND:
            case TRUNC:     generateRoundTrunc(routineCode, actualNode); break;
        }
    }

    /**
     * Generate code to call to a read or readln.
     * @param callNode the CALL node.
     * @param routineCode the routine code.
     */
    private void generateReadReadln(ICodeNode callNode, RoutineCode routineCode) {
    }

    /**
     * Generate code for a call to write or writeln.
     * @param callNode the CALL node.
     * @param routineCode the routine code.
     */
    private void generateWriteWriteln(ICodeNode callNode, RoutineCode routineCode) {
    }

    /**
     * Generate code for a call to eof or eofln.
     * @param callNode the CALL node.
     * @param routineCode the routine code.
     */
    private void generateEofEoln(ICodeNode callNode, RoutineCode routineCode) {
    }

    /**
     * Generate code for a call to abs or sqr.
     * @param routineCode the routine code.
     * @param actualNode the actual parameter node.
     */
    private void generateAbsSqr(RoutineCode routineCode, ICodeNode actualNode) {
    }

    /**
     * Generate code for a call to arctan, cos, exp, ln, sin, or sqrt.
     * @param routineCode the routine code.
     * @param actualNode the actual parameter node.
     */
    private void generateArctanCosExpLnSinSqrt(RoutineCode routineCode, ICodeNode actualNode) {
    }

    /**
     * Generate code for a call to pred or succ.
     * @param routineCode the routine code.
     * @param actualNode the actual parameter node.
     */
    private void generatePredSucc(RoutineCode routineCode, ICodeNode actualNode) {
    }

    /**
     * Generate code for a call to chr.
     * @param actualNode the actual parameter node.
     */
    private void generateChr(ICodeNode actualNode) {
    }

    /**
     * Generate code for a call to odd.
     * @param actualNode the actual parameter node.
     */
    private void generateOdd(ICodeNode actualNode) {
    }

    /**
     * Generate code for a call to ord.
     * @param actualNode the actual parameter node.
     */
    private void generateOrd(ICodeNode actualNode) {
    }

    /**
     * Generate code for a call to round or trunc.
     * @param routineCode the routine code.
     * @param actualNode the actual parameter node.
     */
    private void generateRoundTrunc(RoutineCode routineCode, ICodeNode actualNode) {
    }


}
