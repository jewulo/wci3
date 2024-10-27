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

                if (this.stepping) {
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
                String routineName = ((String) body[1]).toLowerCase();

                debugger.callRoutine(lineNumber, routineName);
                break;
            }

            case RETURN: {
                Object[] body = (Object[]) message.getBody();
                int lineNumber = (Integer) body[0];
                String routineName = ((String) body[1]).toLowerCase();

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

        throw new Exception("Invalid command: '" + command + "'.");
    }

    /**
     * Create the call stack display.
     */
    private void stack()
    {}

    /**
     * Show the current value of a variable.
     * @throws Exception if an error occurred.
     */
    private void show()
        throws Exception
    {

    }

    /**
     * Assign a new value to a variable.
     * @throws Exception if an error occurred.
     */
    private void assign()
        throws Exception
    {
    }

    private CellTypePair createCellTypePair()
        throws Exception
    {
    }
}
