package wci.backend.interpreter;

import wci.backend.Backend;
import wci.backend.BackendFactory;
import wci.backend.interpreter.executors.CallDeclaredExecutor;
import wci.frontend.Scanner;
import wci.frontend.Source;
import wci.frontend.pascal.PascalScanner;
import wci.intermediate.*;
import wci.message.Message;

import java.io.*;

import static wci.backend.interpreter.DebuggerType.COMMAND_LINE;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.ID;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.CALL;
import static wci.message.MessageType.INTERPRETER_SUMMARY;

public class Executor extends Backend
{
    protected static int executionCount;
    protected static RuntimeStack runtimeStack;
    protected static RuntimeErrorHandler errorHandler;

    protected static Scanner standardIn;        // standard input
    protected static PrintWriter standardOut;   // standard output

    protected Debugger debugger;    // interactive source-level debugger.

    static {
        executionCount = 0;
        runtimeStack = MemoryFactory.createRuntimeStack();
        errorHandler = new RuntimeErrorHandler();
        standardOut = new PrintWriter(new PrintStream(System.out));
    }

    /**
     * Constructor.
     */
    public Executor(String inputPath)
    {
        try {
            standardIn = inputPath != null
                            ? new PascalScanner(
                                new Source(
                                    new BufferedReader(
                                        new FileReader(inputPath))))

                            : new PascalScanner(
                                new Source(
                                    new BufferedReader(
                                        new InputStreamReader(System.in))));
        }
        catch (IOException ignored) {}

        debugger = BackendFactory.createDebugger(COMMAND_LINE, this, runtimeStack);
    }

    /**
     * Constructor for subclasses.
     * @param parent the parent executor.
     */
    public Executor(Executor parent)
    {
        super();
    }

    /**
     * Getter.
     * @return the error handler.
     */
    public RuntimeErrorHandler getErrorHandler()
    {
        return errorHandler;
    }

    /**
     * Execute the source program by processing the intermediate code
     * and the symbol table.
     * Process the intermediate code and the symbol table generated by the
     * parser to execute the source program.
     * @param iCode the intermediate code.
     * @param symTabStack the symbol table.
     * @throws Exception if an error occurred.
     */
    @Override
    public void process(ICode iCode, SymTabStack symTabStack)
        throws Exception
    {
        this.symTabStack = symTabStack;
        long startTime = System.currentTimeMillis();

        SymTabEntry programId = symTabStack.getProgramId();

        // Construct an artificial CALL node to the main program.
        ICodeNode callNode = ICodeFactory.createICodeNode(CALL);
        callNode.setAttribute(ID, programId);

        // Execute the main program.
        CallDeclaredExecutor callExecutor = new CallDeclaredExecutor(this);
        callExecutor.execute(callNode);

        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        int runtimeErrors = errorHandler.getErrorCount();

        // Send the interpreter summary message.
        sendMessage(new Message(INTERPRETER_SUMMARY,
                                new Number[] {executionCount,
                                              runtimeErrors,
                                              elapsedTime}));
    }

    /**
     * @param listener the listener to add.
     */
    //@Override
  //  public void addMessageListener(MessageListener listener) {}

    /**
     * @param listener the listener to add.
     */
   // @Override
    //public void removeMessageListener(MessageListener listener) {}

    /**
     * @param message the message to set.
     */
    //@Override
    //public void sendMessage(Message message) {}
}
