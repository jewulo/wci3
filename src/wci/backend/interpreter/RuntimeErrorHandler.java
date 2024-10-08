package wci.backend.interpreter;

import wci.backend.Backend;
import wci.intermediate.ICodeNode;
import wci.message.Message;
import wci.message.MessageType;

import static wci.intermediate.icodeimpl.ICodeKeyImpl.LINE;

/**
 * <h1>RuntimeErrorHandler</h1>
 *
 * <p>Runtime error handler for the backend interpreter.</p>
 */
public class RuntimeErrorHandler
{
    private static final int MAX_ERRORS = 5;

    private static int errorCount = 0;      // count of runtime errors

    /**
     * The error count.
     * @return the error count
     */
    public static int getErrorCount()
    {
        return errorCount;
    }

    /**
     * Flag a runtime error.
     * @param node the root node of the offending statement or expression.
     * @param errorCode the runtime error code.
     * @param backend the backend processor.
     */
    public void flag(ICodeNode node, RuntimeErrorCode errorCode, Backend backend)
    {
        String lineNumber = null;

        // Look for the ancestor statement node with a line number attribute.
        while ((node != null) && (node.getAttribute(LINE) == null)) {
            node = node.getParent();
        }

        // Notify the interpreters listeners
        //assert node != null;
        backend.sendMessage(
                new Message(MessageType.RUNTIME_ERROR,
                            new Object[]{errorCode.toString(),
                                        (Integer) node.getAttribute(LINE)}));

        if (++errorCount > MAX_ERRORS) {
            System.out.println("*** ABORTED AFTER TOO MANY RUNTIME ERRORS.");
            System.exit(-1);
        }
    }
}
