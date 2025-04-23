package wci.backend.compiler.generators;

import wci.backend.compiler.CodeGenerator;
import wci.backend.compiler.PascalCompilerException;
import wci.intermediate.ICodeNode;
import wci.intermediate.SymTabEntry;

/**
 * <h1>StructuredDataGenerator</h1>
 *
 * <p>Generate code to allocate arrays, records, and strings.</p>
 */
public class StructuredDataGenerator extends CodeGenerator
{
    /**
     * Constructor.
     * @param parent the parent generator.
     */
    public StructuredDataGenerator(CodeGenerator parent)
    {
        super(parent);
    }

    /**
     * Generate code to allocate the structured data of a program,
     * procedure, or function.
     * @param routineId the routine's symbol table entry.
     * //@throws PascalCompilerException
     */
    @Override
    public void generate(SymTabEntry routineId) //throws PascalCompilerException
    {
        //super.generate(node);
    }
}
