package wci.backend.compiler.generators;

import java.util.ArrayList;

import wci.intermediate.*;
import wci.intermediate.symtabimpl.*;
import wci.backend.compiler.*;

import static wci.intermediate.symtabimpl.DefinitionImpl.*;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;
import static wci.backend.compiler.Instruction.*;

/**
 *
 */
public class CallDeclaredGenerator extends CallGenerator
{
    /**
     * Constructor.
     * @param parent the parent executor.
     */
    public CallDeclaredGenerator(CallGenerator parent)
    {
        super(parent);
    }

    /**
     * Generate code to call to a declared procedure or function.
     * @param node the CALL node.
     */
    @Override
    public void generate(ICodeNode node)
    {
        // Generate code for any actual parameters
        if (node.getChildren().size() > 0) {
            generateActualParms(node);
        }
        
        // Generate code to make the call.
        generateCall(node);
        
        // Generate code for the epilogue.
        if  (node.getChildren().size() > 0) {
            generateCallEpilogue(node);
        }
        
        // A function call leaves a value on the operand stack.
        SymTabEntry routineId = (SymTabEntry) node.getAttribute(ID);
        if (routineId.getDefinition() == DefinitionImpl.FUNCTION) {
            localStack.increase(1);
        }
    }

    /**
     * Generate code for the actual parameters of a call.
     * @param callNode the CALL parse tree node.
     */
    private void generateActualParms(ICodeNode callNode)
    {
        SymTabEntry routineId = (SymTabEntry) callNode.getAttribute(ID);
        ICodeNode paramNode = callNode.getChildren().get(0);
        ArrayList<ICodeNode> actualNodes = paramNode.getChildren();
        ArrayList<SymTabEntry> formalIds = (ArrayList<SymTabEntry>) routineId.getAttribute(ROUTINE_PARAMS);
        ExpressionGenerator exprGenerator = new ExpressionGenerator(this);

        // Iterate over the formal parameters.
        for (int i = 0; i < formalIds.size(); ++i) {
            SymTabEntry formalId = formalIds.get(i);
            ICodeNode actualNode = actualNodes.get(i);
            SymTabEntry actualId = (SymTabEntry) actualNode.getAttribute(ID);
            TypeSpec formalType = formalId.getTypeSpec();
            TypeSpec actualType = actualNode.getTypeSpec();

            // VAR parameter: An actual parameter that is not structured
            //                needs to be wrapped.
            if (isWrapped(formalId)) {
                Integer wrapSlot = (Integer) actualId.getAttribute(WRAP_SLOT);

                // Already wrapped: Load the wrapper.
                if (wrapSlot != null) {
                    emitLoadLocal(null, wrapSlot);
                    localStack.increase(1);
                }

                // Actual parameter is itself a VAR parameter: No further wrapping.
                else if (actualId.getDefinition() == VAR_PARM) {
                    int actualSlot = (Integer) actualId.getAttribute(SLOT);
                    emitLoadLocal(null, actualSlot);
                    localStack.increase(1);
                }

                // Need to wrap: Reserve a temporary variable to hold the wrapper's address.
                else {
                    wrapSlot = localVariables.reserve();
                    actualId.setAttribute(WRAP_SLOT, wrapSlot);
                    generateWrap(actualNode, formalType, wrapSlot, exprGenerator);
                }
            }

            // Value parameter: Actual parameter is a constant string.
            else if ((formalType == Predefined.charType) && (actualNode.getType() == STRING_CONSTANT)) {
                int value = ((String) actualNode.getAttribute(VALUE)).charAt(0);
                emitLoadConstant(value);
                localStack.increase(1);
            }
            
            // Value parameter: All other types.
            else {
                exprGenerator.generate(actualNode);
                emitRangeCheck(formalType);
                
                // real formal := integer actual
                if ((formalType == Predefined.realType) && (actualType.baseType() == Predefined.integerType)) {
                    emit(I2F);
                }
                
                // Structured data needs to be cloned
                else if (needsCloning(formalId)) {
                    cloneActualParameter(formalType);
                }
            }
        }
    }

    private void generateWrap(ICodeNode actualNode, TypeSpec formalType, Integer wrapSlot, ExpressionGenerator exprGenerator) {
    }

    private void cloneActualParameter(TypeSpec formalType) {
    }

    private void generateCall(ICodeNode callNode)
    {
    }
    private void generateCallEpilogue(ICodeNode callNode)
    {
    }


}
