package wci.backend.compiler;

import wci.backend.*;
import wci.backend.compiler.generators.ProgramGenerator;
import wci.intermediate.*;
import wci.message.*;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;

import static wci.message.MessageType.COMPILER_SUMMARY;

/**
 * <h1>CodeGenerator</h1>
 * <p>The code generator for the compiler back end.</p>
 */
public class CodeGenerator extends Backend
{
    private static PrintWriter assemblyFile;
    private static int instructionCount = 0;

    protected static String programName;

    protected LocalVariables localVariables;
    protected LocalStack localStack;

    /**
     * Constructor.
     */
    public CodeGenerator() {}

    /**
     * Constructor for subclasses.
     * @param parent the parent code generator.
     */
    public CodeGenerator(CodeGenerator parent)
    {
        super();
        this.localVariables = parent.localVariables;
        this.localStack = parent.localStack;
    }

    /**
     * Process the intermediate code and the symbol table generated by the
     * parser to generate machine-language instructions.
     * @param iCode  the intermediate code.
     * @param symTabStack the symbol table.
     * @throws Exception if an error occurred.
     */
    @Override
    public void process(ICode iCode, SymTabStack symTabStack)
        throws Exception
    {
        System.out.println("*** ***");
        System.out.println("*** CodeGenerator::process ***");
        System.out.println("*** ***");

        this.symTabStack = symTabStack;
        long startTime = System.currentTimeMillis();

        SymTabEntry programId = symTabStack.getProgramId();
        programName = programId.getName();
        String assemblyFileName = programName + ".j";

        // Open a new assembly file for writing.
        assemblyFile = new PrintWriter(
                            new PrintStream(
                                    new File(assemblyFileName)));

        // Generate code for the main program.
        CodeGenerator programGenerator = new ProgramGenerator(this);
        programGenerator.generate(iCode.getRoot());
        assemblyFile.close();

        // Send compiler summary message.
        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        int instructionCount = 0;

        // Send the compiler summary message.
        sendMessage(new Message(COMPILER_SUMMARY,
                                new Number[] {instructionCount,
                                              elapsedTime}));
    }

    /**
     * Generate code for a statement.
     * To be overridden by the code generator subclasses.
     * @param node the root node of the statement.
     * @throws PascalCompilerException when an error occurs.
     */
    public void generate(ICodeNode node)
        throws PascalCompilerException
    {
    }

    /**
     * Generate code for a routine.
     * To be overridden by the code generator subclasses.
     * @param routineId the routine's symbol-table entry.
     * @throws PascalCompilerException when an error occurs.
     */
    public void generate(SymTabEntry routineId)
        throws PascalCompilerException
    {
    }

    // =====================
    // General Code Emitters
    // =====================

    // =====
    // Loads
    // =====

    // ======
    // Stores
    // ======

    // ======================
    // Miscellaneous Emitters
    // ======================

    // =========
    // Utilities
    // =========
    /**
     * @param listener the listener to add.
     */
    @Override
    public void addMessageListener(MessageListener listener) {

    }

    /**
     * @param listener the listener to add.
     */
    @Override
    public void removeMessageListener(MessageListener listener) {

    }

    /**
     * @param message the message to set.
     */
    @Override
    public void sendMessage(Message message) {

    }
}
