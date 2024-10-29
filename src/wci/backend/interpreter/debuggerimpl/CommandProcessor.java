package wci.backend.interpreter.debuggerimpl;

import java.util.ArrayList;

import wci.intermediate.*;
import wci.intermediate.symtabimpl.*;
import wci.backend.interpreter.*;
import wci.message.*;

import static wci.frontend.pascal.PascalTokenType.SEMICOLON;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.ROUTINE_SYMTAB;

/**
 * <h1>CommandProcessor</h1>
 *
 * <p>Command Processor for the interactive source-level debugger.</p>
 */
public class CommandProcessor
{
    private Debugger debugger;  // the debugger
    private boolean stepping;   // true when single stepping

    /**
     * Constructor.
     * @param debugger the parent debugger.
     */
    protected CommandProcessor(Debugger debugger)
    {
        this.debugger = debugger;
        this.stepping = true;
    }

    /**
     * Process a message from the back end.
     * @param message the message.
     */
    protected void processMessage(Message message)
    {
        MessageType type = message.getType();

        switch (type) {

            case SOURCE_LINE: {
                int lineNumber = (Integer) message.getBody();

                if (stepping) {
                    debugger.atStatement(lineNumber);
                    debugger.readCommands();
                }
                else if (debugger.isBreakPoint(lineNumber)) {
                    debugger.atBreakpoint(lineNumber);
                    debugger.readCommands();
                }

                break;
            }

            case FETCH: {
                Object[] body = (Object[]) message.getBody();
                String variableName = ((String) body[1]).toLowerCase();

                if (debugger.isWatchPoint(variableName)) {
                    int lineNumber = (Integer) body[0];
                    Object value = body[2];

                    debugger.atWatchpointValue(lineNumber, variableName, value);
                }

                break;
            }

            case ASSIGN: {
                Object[] body = (Object[]) message.getBody();
                String variableName = ((String) body[1]).toLowerCase();

                if (debugger.isWatchPoint(variableName)) {
                    int lineNumber = (Integer) body[0];
                    Object value = body[2];

                    debugger.atWatchpointAssignment(lineNumber, variableName, value);
                }

                break;
            }

            case CALL: {
                Object[] body = (Object[]) message.getBody();
                int lineNumber = (Integer) body[0];
                String routineName = (String) body[1];

                debugger.callRoutine(lineNumber, routineName);
                break;
            }

            case RETURN: {
                Object[] body = (Object[]) message.getBody();
                int lineNumber = (Integer) body[0];
                String routineName = (String) body[1];

                debugger.returnRoutine(lineNumber, routineName);
                break;
            }

            case RUNTIME_ERROR: {
                Object[] body = (Object[]) message.getBody();
                String errorMessage = (String) body[0];
                Integer lineNumber = (Integer) body[1];

                debugger.runtimeError(errorMessage, lineNumber);
                break;
            }
        }
    }

    /**
     * Parse a debugger command.
     * @return true to parse another command immediately, else false.
     */
    protected boolean parseCommand()
    {
        boolean anotherCommand = true;

        // Parse a command.
        try {
            debugger.nextToken();
            String command = debugger.getWord("Command expected.");
            anotherCommand = executeCommand(command);
        }
        catch (Exception ex) {
            debugger.commandError(ex.getMessage());
        }

        // Skip to the next command.
        try {
            debugger.skipToNextCommand();
        }
        catch (Exception ex) {
            debugger.commandError(ex.getMessage());
        }

        return anotherCommand;
    }

    /**
     * Execute a debugger command.
     * @param command the command string.
     * @return true to parse another command immediately, else false.
     * @throws Exception if an error occurred.
     */
    private boolean executeCommand(String command)
        throws Exception
    {
        stepping = false;

        if (command.equals("step")) {
            stepping = true;
            checkForSemiColon();
            return false;
        }

        if (command.equals("break")) {
            Integer lineNumber = debugger.getInteger("Line number expected.");
            checkForSemiColon();
            debugger.setBreakPoint((Integer) lineNumber);
            return true;
        }

        if (command.equals("unbreak")) {
            Integer lineNumber = debugger.getInteger("Line number expected.");
            checkForSemiColon();
            debugger.unsetBreakPoint((Integer) lineNumber);
            return true;
        }

        if (command.equals("watch")) {
            String name = debugger.getWord("Variable name expected.");
            checkForSemiColon();
            debugger.setWatchPoint(name);
            return true;
        }

        if (command.equals("unwatch")) {
            String name = debugger.getWord("Variable name expected.");
            checkForSemiColon();
            debugger.unsetWatchPoint(name);
            return true;
        }

        if (command.equals("stack")) {
            checkForSemiColon();
            stack();
            return true;
        }

        if (command.equals("show")) {
            show();
            return true;
        }

        if (command.equals("assign")) {
            assign();
            return true;
        }

        if (command.equals("go")) {
            checkForSemiColon();
            return false;
        }

        if (command.equals("quit")) {
            checkForSemiColon();
            debugger.quit();
        }

        throw new Exception("Invalid command: '" + command + "'.");
    }

    /**
     * Create the call stack display.
     */
    private void stack()
    {
        ArrayList callStack = new ArrayList();      // What is in the Book and on GitHub...no <type variable>

        // Loop over the activation records on the runtime stack
        // starting at the top of stack.
        RuntimeStack runtimeStack = debugger.getRuntimeStack();
        ArrayList<ActivationRecord> arList = runtimeStack.records();
        for (int i = arList.size() - 1; i >= 0; --i) {
            ActivationRecord ar = arList.get(i);
            SymTabEntry routineId = ar.getRoutineId();

            // Add the symbol table entry of the procedure or function.
            callStack.add(routineId);   // ...this looks hacky

            // Create and add a name-value pair for each local variable.
            for (String name : ar.getAllNames()) {
                Object value = ar.getCell(name).getValue();
                callStack.add(new NameValuePair(name, value));  // ...this looks hacky
            }
        }

        // Display the call stack.
        debugger.displayCallStack(callStack);
    }

    /**
     * Show the current value of a variable.
     * @throws Exception if an error occurred.
     */
    private void show()
        throws Exception
    {
        CellTypePair pair = createCellTypePair();
        Cell cell = pair.getCell();

        checkForSemiColon();
        debugger.displayValue(NameValuePair.valueString(cell.getValue()));
    }

    /**
     * Assign a new value to a variable.
     * @throws Exception if an error occurred.
     */
    private void assign()
        throws Exception
    {
        CellTypePair pair = createCellTypePair();
        Object newValue = debugger.getValue("Invalid value.");

        checkForSemiColon();
        pair.setValue(newValue);
    }

    /**
     * Create a cell-data type pair.
     * @return the CellTypePair object.
     * @throws Exception if an error occurred.
     */
    private CellTypePair createCellTypePair()
        throws Exception
    {
        RuntimeStack runtimeStack = debugger.getRuntimeStack();
        int currentLevel = runtimeStack.currentNestingLevel();
        ActivationRecord ar = null;
        Cell cell = null;

        // Parse the variable name.
        String variableName = debugger.getWord("Variable name expected");

        // Find the variable's cell in the call stack.
        for (int level = currentLevel; (cell == null) && (level > 0); --level) {
            ar = runtimeStack.getTopmost(level);
            cell = ar.getCell(variableName);
        }

        if (cell == null) {
            throw new Exception("Undeclared variable name '" + variableName + "'.");
        }

        // VAR parameter.
        if (cell.getValue() instanceof Cell) {
            cell = (Cell) cell.getValue();
        }

        // Find the variable's symbol table entry.
        SymTab symTab = (SymTab) ar.getRoutineId().getAttribute(ROUTINE_SYMTAB);
        SymTabEntry id = symTab.lookup(variableName);

        return new CellTypePair(id.getTypeSpec(), cell, debugger);
    }

    /**
     * Verify that a command ends with a semicolon.
     * @throws Exception if an error occurred.
     */
    private void checkForSemiColon()
        throws Exception
    {
        if (debugger.currentToken().getType() != SEMICOLON) {
            throw new Exception("Invalid command syntax.");
        }
    }
}
