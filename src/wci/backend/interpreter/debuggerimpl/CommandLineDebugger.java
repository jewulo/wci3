package wci.backend.interpreter.debuggerimpl;

import java.util.ArrayList;

import wci.intermediate.*;
import wci.backend.*;
import wci.backend.interpreter.*;
import wci.message.Message;

/**
 * <h1>CommandLineDebugger</h1>
 *
 * <p>Command line version of the interactive source-level debugger.</p>
 */
public class CommandLineDebugger extends Debugger
{
    private CommandProcessor commandProcessor;

    /**
     * Constructor.
     * @param backend      the back end.
     * @param runtimeStack the runtime stack.
     */
    public CommandLineDebugger(Backend backend, RuntimeStack runtimeStack)
    {
        super(backend, runtimeStack);
        commandProcessor = new CommandProcessor(this);
    }

    /**
     * Process a message from the backend.
     * @param message the message.
     */
    @Override
    public void processMessage(Message message)
    {
        commandProcessor.processMessage(message);
    }

    /**
     * Display a prompt for a debugger command.
     */
    @Override
    public void promptForCommand()
    {
        System.out.print(">>> Command? ");
    }

    /**
     * Parse a debugger command.
     * @return true to parse another command immediately, else false.
     */
    @Override
    public boolean parseCommand()
    {
        return commandProcessor.parseCommand();
    }

    /**
     * Process a source statement.
     * @param lineNumber the statement line number.
     */
    @Override
    public void atStatement(Integer lineNumber)
    {
        System.out.println("\n>>> At line " + lineNumber);
    }

    /**
     * Process a breakpoint at a statement.
     * @param lineNumber the statement line number.
     */
    @Override
    public void atBreakpoint(Integer lineNumber)
    {
        System.out.println("\n>>> Breakpoint at line " + lineNumber);
    }

    /**
     * Process the current value of a watchpoint variable.
     * @param lineNumber the current statement line number.
     * @param name       the variable name.
     * @param value      the variable's value.
     */
    @Override
    public void atWatchpointValue(Integer lineNumber, String name, Object value)
    {
        System.out.println("\n>>> At line " + lineNumber + ": " + name + " := " + value.toString());    
    }

    /**
     * Process assigning a new value ot a watchpoint variable.
     * @param lineNumber the current statement line number.
     * @param name       the variable name.
     * @param value      the new value.
     */
    @Override
    public void atWatchpointAssignment(Integer lineNumber, String name, Object value)
    {
        System.out.println("\n>>> At line " + lineNumber + ": " + name + " := " + value.toString());
    }

    /**
     * Process calling a declared procedure or function.
     * @param lineNumber  the current statement line number.
     * @param routineName the routineName
     */
    @Override
    public void callRoutine(Integer lineNumber, String routineName) {}

    /**
     * Process returning from a declared procedure or function.
     * @param lineNumber  the current statement line number.
     * @param routineName the routineName
     */
    @Override
    public void returnRoutine(Integer lineNumber, String routineName) {}

    /**
     * Display a value.
     * @param valueString the value string.
     */
    @Override
    public void displayValue(String valueString)
    {
        System.out.println(valueString);
    }

    /**
     * Display the call stack.
     * @param stack the list of elements of the call stack.
     */
    @Override
    public void displayCallStack(ArrayList stack)
    {
        for (Object item : stack) {

            // Name of a procedure or function.
            if (item instanceof SymTabEntry) {
                SymTabEntry routineId = (SymTabEntry) item;
                String routineName = routineId.getName();
                int level = routineId.getSymTab().getNestingLevel();
                Definition definition = routineId.getDefinition();

                System.out.println(level + ": " +
                                   definition.getText().toUpperCase() + " " +
                                   routineName);
            }

            // Variable name-value pair.
            else if (item instanceof NameValuePair) {
                NameValuePair pair = (NameValuePair) item;
                System.out.print(" " + pair.getVariableName() + ": ");
                displayValue(pair.getValueString());
            }
        }
    }

    /**
     * Terminate execution of the source program.
     */
    @Override
    public void quit()
    {
        System.out.println("Program terminated.");
        System.exit(-1);
    }

    /**
     * Handle a debugger command error.
     * @param errorMessage the error message.
     */
    @Override
    public void commandError(String errorMessage)
    {
        System.out.println("!!! ERROR:  " + errorMessage);
    }

    /**
     * Handle a source program runtime error.
     * @param errorMessage the error message.
     * @param lineNumber   the source line number where the error occurred.
     */
    @Override
    public void runtimeError(String errorMessage, Integer lineNumber)
    {
        System.out.println("!!! RUNTIME ERROR:  ");
        if (lineNumber != null) {
            System.out.println(" at line " + String.format("%03d", lineNumber));
        }
        System.out.println(":  " + errorMessage);
    }
}
