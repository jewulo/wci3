package wci.ide;

import wci.ide.ideimpl.CallStackFrame;
import wci.ide.ideimpl.ConsoleFrame;
import wci.ide.ideimpl.DebugFrame;
import wci.ide.ideimpl.EditFrame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <h1>IDEFrame</h1>
 *
 * <p>The main window of the Pascal IDE.</p>
 */
public class IDEFrame
    extends JFrame
    implements IDEControl
{
    private EditFrame editFrame;
    private DebugFrame debugFrame;
    private ConsoleFrame consoleFrame;
    private CallStackFrame stackFrame;

    private DebuggerProcess debuggerProcess;

    /**
     * Set the path of the source file.
     *
     * @param sourcePath the path.
     */
    @Override
    public void setSourcePath(String sourcePath) {

    }

    /**
     * @return the path of the source file.
     */
    @Override
    public String getSourcePath() {
        return "";
    }

    /**
     * Set the path of the runtime input data file.
     *
     * @param inputPath the path.
     */
    @Override
    public void setInputPath(String inputPath) {

    }

    /**
     * @return the path of the runtime input data file.
     */
    @Override
    public String getInputPath() {
        return "";
    }

    /**
     * Start the debugger process.
     *
     * @param sourceName the source file name.
     */
    @Override
    public void startDebuggerProcess(String sourceName) {

    }

    /**
     * Stop the debugger process.
     */
    @Override
    public void stopDebuggerProcess() {

    }

    /**
     * Send a command or runtime input text to the debugger process.
     *
     * @param text the command string or input text.
     */
    @Override
    public void sendToDebuggerProcess(String text)
    {
        debuggerProcess.writeToDebuggerStandardInput(text);
    }

    /**
     * Set the editor window's message.
     *
     * @param message the message.
     * @param color   the message color.
     */
    @Override
    public void setEditWindowMessage(String message, Color color) {

    }

    /**
     * Clear the editor window's syntax errors.
     */
    @Override
    public void clearEditWindowErrors() {

    }

    /**
     * Add a syntax error message to the editor window's syntax errors.
     *
     * @param line the error message.
     */
    @Override
    public void addToEditWindowErrors(String line) {

    }

    /**
     * Show the debugger window.
     *
     * @param sourceName the source file name.
     */
    @Override
    public void showDebugWindow(String sourceName) {

    }

    /**
     * Clear the debugger window's listing.
     */
    @Override
    public void clearDebugWindowListing() {

    }

    /**
     * Add a line to the debugger window's listing.
     *
     * @param line the listing line.
     */
    @Override
    public void addToDebugWindowListing(String line) {

    }

    /**
     * Select a listing line in the debugger window.
     *
     * @param lineNumber the line number.
     */
    @Override
    public void selectDebugWindowListingLine(int lineNumber) {

    }

    /**
     * Set the debugger to a listing line.
     *
     * @param lineNumber the line number.
     */
    @Override
    public void setDebugWindowAtListingLine(int lineNumber) {

    }

    /**
     * Set the debugger to break at a listing line.
     *
     * @param lineNumber the line number.
     */
    @Override
    public void breakDebugWindowAtListingLine(int lineNumber) {

    }

    /**
     * Set the debugger window's message.
     *
     * @param message the message.
     * @param color   the message color.
     */
    @Override
    public void setDebugWindowMessage(String message, Color color) {

    }

    /**
     * Stop the debugger.
     */
    @Override
    public void stopDebugWindow() {

    }

    /**
     * Show the call stack window.
     *
     * @param sourceName the source file name.
     */
    @Override
    public void showCallStackWindow(String sourceName) {

    }

    /**
     * Initialize the call stack display.
     */
    @Override
    public void initializeCallStackWindow() {

    }

    /**
     * Add an invoked routine to the call stack display.
     *
     * @param level  the routine's nesting level.
     * @param header the routine's header.
     */
    @Override
    public void addRoutineToCallStackWindow(String level, String header) {

    }

    /**
     * Add a local variable to the call stack display.
     *
     * @param name  the variable's name.
     * @param value the variable's value.
     */
    @Override
    public void addVariableToCallStackWindow(String name, String value) {

    }

    /**
     * Complete the call stack display.
     */
    @Override
    public void completeCallStackWindow() {

    }

    /**
     * Show the console window.
     *
     * @param sourceName the source file name.
     */
    @Override
    public void showConsoleWindow(String sourceName) {

    }

    /**
     * Clear the console window's output.
     */
    @Override
    public void clearConsoleWindowOutput() {

    }

    /**
     * Add output text to the console window.
     *
     * @param text the output text.
     */
    @Override
    public void addToConsoleWindowOutput(String text) {

    }

    /**
     * Enable runtime input from the console window.
     */
    @Override
    public void enableConsoleWindowInput()
    {
        consoleFrame.enableInput();
    }

    /**
     * Disable runtime input from the console window.
     */
    @Override
    public void disableConsoleWindowInput() {

    }
}
