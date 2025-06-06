import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import wci.frontend.*;
import wci.intermediate.*;
import wci.backend.*;
import wci.message.*;
import wci.util.CrossReferencer;
import wci.util.ParseTreePrinter;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.message.MessageType.*;
import static wci.ide.IDEControl.*;


/**
 * <h1>Pascal</h1>
 *
 * <p>Compile or interpret a Pascal source program.</p>
 */
public class Pascal
{
    private Parser parser;              // language-independent parser
    private Source source;              // language-independent scanner
    private ICode iCode;                // generated intermediate code
    private Backend backend;            // backend
    private SymTabStack symTabStack;    // symbol table stack

    private boolean intermediate;       // true to print intermediate code
    private boolean xref;               // true to print cross-reference listing
    private boolean lines;              // true to print source line tracing
    private boolean assign;             // true to print value assignment tracing
    private boolean fetch;              // true to print value fetch tracing
    private boolean call;               // true to print routine call tracing
    private boolean returnn;            // true to print routine return tracing

    /**
     * Compile or interpret a Pascal program.
     * @param operation either "compile" or "execute".
     * @param sourcePath the source file path.
     * @param inputPath the input file path.
     * @param flags the command line flags.
     */
    public Pascal(String operation, String sourcePath, String inputPath, String flags)
    {
        try {
            intermediate = flags.indexOf('i') > -1;
            xref         = flags.indexOf('x') > -1;
            lines        = flags.indexOf('l') > -1;
            assign       = flags.indexOf('a') > -1;
            fetch        = flags.indexOf('f') > -1;
            call         = flags.indexOf('c') > -1;;               // true to print routine call tracing
            returnn      = flags.indexOf('r') > -1;

            source = new Source(new BufferedReader(new FileReader(sourcePath)));
            source.addMessageListener(new SourceMessageListener());

            parser = FrontEndFactory.createParser("Pascal", "top-down", source);
            parser.addMessageListener(new ParserMessageListener());

            backend = BackendFactory.createBackend(operation, inputPath);
            backend.addMessageListener(new BackendMessageListener());

            parser.parse();
            source.close();

            if (parser.getErrorCount() == 0) {
                symTabStack = parser.getSymTabStack();

                SymTabEntry programId = symTabStack.getProgramId();
                iCode = (ICode) programId.getAttribute(ROUTINE_ICODE);

                //xref = true;            // HACKY CODE FLAGS NOT WORKING
                if (xref) {
                    CrossReferencer crossReferencer = new CrossReferencer();
                    crossReferencer.print(symTabStack);
                }

                //intermediate = true;    // HACKY CODE FLAGS NOT WORKING
                if (intermediate) {
                    ParseTreePrinter treePrinter =
                            new ParseTreePrinter(System.out);
                    treePrinter.print(symTabStack);
                }

                backend.process(iCode, symTabStack);
            }
        }
        catch (Exception ex) {
            System.out.println("***** Internal translator error. *****");
            ex.printStackTrace();
        }
    }

    private static final String FLAGS = "[-ixlafcr]";
    private static final String USAGE = "Usage: Pascal execute|compile " + FLAGS + " <source file path>";

    /**
     * The main method.
     * @param args command-line arguments: "compile" or "execute" followed by
     *             optional flags followed by the source file path followed by
     *             an optional runtime input data file path.
     */
    public static void main(String[] args)
    {
        try {
            String operation = args[0];

            // Operation.
            if (!(   operation.equalsIgnoreCase("compile")
                  || operation.equalsIgnoreCase("execute"))) {
                throw new Exception();
            }

            int i = 0;
            String flags = "";

            // Flags.
            while ((++i < args.length) && (args[i].charAt(0) == '-')) {
                flags += args[i].substring(1);
            }

            String sourcePath = null;
            String inputPath = null;

            // Source path.
            if (i < args.length) {
                sourcePath = args[i];
            }
            else {
                throw new Exception();
            }

            // Runtime input data file path.
            if (++i < args.length) {
                inputPath = args[i];

                File inputFile = new File(inputPath);
                if (! inputFile.exists()) {
                    System.out.println("Input file '" + inputPath + "' does not exist.");
                    throw new Exception();
                }
            }

            new Pascal(operation, sourcePath, inputPath, flags);
        } catch (Exception e) {
            System.out.println(USAGE);
        }
    }

    private static final String SOURCE_LINE_FORMAT = "%03d %s";

    /**
     * Listener for source messages.
     */
    private class SourceMessageListener implements MessageListener
    {
        /**
         * Called by the source whenever it produces a message.
         * @param message the message.
         */
        @Override
        public void messageReceived(Message message)
        {
            MessageType type = message.getType();
            Object[] body = (Object[]) message.getBody();

            switch (type) {
                case SOURCE_LINE: {
                    int lineNumber = (Integer) body[0];
                    String lineText = (String) body[1];

                    System.out.println(String.format(SOURCE_LINE_FORMAT,
                                                     lineNumber, lineText));
                    break;
                }
            }
        }
    }

    /*
    private static final String PARSER_SUMMARY_FORMAT =
            PARSER_TAG +
            "\n%,20d source lines." +
            "\n%,20d syntax errors." +
            "\n%,20f seconds total parsing time.\n";
    */

    private static final String PARSER_SUMMARY_FORMAT =
            PARSER_TAG +
            "\n%,d source lines, " +
            "\n%,d syntax errors, " +
            "\n%,.2f seconds total parsing time.\n";

    private static final String TOKEN_FORMAT =
            ">>> %-15s line=%03d, pos=%2d, text=\"%s\"";

    private static final String VALUE_FORMAT =
            ">>>                 value=%s";

    private static final int PREFIX_WIDTH = 5;

    /**
     * Listener for parser messages.
     */
    private class ParserMessageListener implements MessageListener
    {
        /**
         * Called by the parser whenever it produces a message.
         * @param message the message
         */
        public void messageReceived(Message message)
        {
            MessageType type = message.getType();

            switch (type) {
                case PARSER_SUMMARY: {
                    Number[] body = (Number[]) message.getBody();
                    int statementCount = (Integer) body[0];
                    int syntaxErrors = (Integer) body[1];
                    float elapsedTime = (Float) body[2];

                    System.out.printf(PARSER_SUMMARY_FORMAT,
                            statementCount, syntaxErrors,
                            elapsedTime);
                    break;
                }

                case SYNTAX_ERROR: {
                    Object[] body = (Object []) message.getBody();
                    int lineNumber = (Integer) body[0];
                    int position = (Integer) body[1];
                    String tokenText = (String) body[2];
                    String errorMessage = (String) body[3];

                    //int spaceCount = PREFIX_WIDTH + position;
                    StringBuilder flagBuffer = new StringBuilder();

                    // Spaces up to the error position.
/*
                    for (int i = 1; i < spaceCount; ++i) {
                        flagBuffer.append(' ');
                    }
*/

                    // A pointer to the error followed by the error message.
                    // flagBuffer.append("^\n*** ").append(errorMessage);
                    flagBuffer.append(String.format(SYNTAX_TAG + "%d: %s",
                                                    lineNumber, errorMessage));

                    // Text, if any, of the bad token.
                    if (tokenText != null) {
                        flagBuffer.append(" [at \"").append(tokenText)
                                .append("\"]");
                    }

                    System.out.println(flagBuffer.toString());
                    break;
                }
            }
        }
    }

//    private static final String INTERPRETER_SUMMARY_FORMAT =
//            "\n%,20d statements executed." +
//                    "\n%,20d runtime errors." +
//                    "\n%,20.2f seconds total execution time.\n";

    private static final String INTERPRETER_SUMMARY_FORMAT =
            INTERPRETER_TAG + "\n%,d statements executed, " +
                              "\n%,d runtime errors, " +
                    "\n%,20.2f seconds total execution time.\n";

    private static final String COMPILER_SUMMARY_FORMAT =
            "\n%,20d instructions generated." +
                    "\n%,20.2f seconds total code generation time.\n";

    private static final String LINE_FORMAT =
            ">>> AT LINE %03d\n";

    private static final String ASSIGN_FORMAT =
            ">>> AT LINE %03d: %s = %s\n";

    private static final String FETCH_FORMAT =
            ">>> AT LINE %03d: %s : %s\n";

    private static final String CALL_FORMAT =
            ">>> AT LINE %03d: CALL %s\n";

    private static final String RETURN_FORMAT =
            ">>> AT LINE %03d: RETURN FROM %s\n";

    /**
     * Listener for back end messages.
     */
    private class BackendMessageListener implements MessageListener
    {
        private boolean firstOutputMessage = true;

        /**
         * Called by the back end whenever it produces a message.
         * @param message the message
         */
        public void messageReceived(Message message)
        {
            MessageType type = message.getType();

            switch (type) {

                case SOURCE_LINE: {
                    if (lines) {
                        int lineNumber = (Integer) message.getBody();

                        System.out.printf(LINE_FORMAT, lineNumber);
                    }
                    break;
                }

                case ASSIGN: {
                    if (assign) {
                        Object[] body = (Object[]) message.getBody();
                        int lineNumber = (Integer) body[0];
                        String variableName = (String) body[1];
                        Object value = body[2];

                        System.out.printf(ASSIGN_FORMAT,
                                lineNumber, variableName, value);
                    }
                    break;
                }

                case FETCH: {
                    if (fetch) {
                        Object[] body = (Object[]) message.getBody();
                        int lineNumber = (Integer) body[0];
                        String variableName = (String) body[1];
                        Object value = body[2];

                        System.out.printf(FETCH_FORMAT,
                                lineNumber, variableName, value);
                    }
                    break;
                }

                case CALL: {
                    if (call) {
                        Object[] body = (Object[]) message.getBody();
                        int lineNumber = (Integer) body[0];
                        String routineName = (String) body[1];

                        System.out.printf(CALL_FORMAT,
                                lineNumber, routineName);
                    }
                    break;
                }

                case RETURN: {
                    if (returnn) {
                        Object[] body = (Object[]) message.getBody();
                        int lineNumber = (Integer) body[0];
                        String routineName = (String) body[1];

                        System.out.printf(RETURN_FORMAT,
                                lineNumber, routineName);
                    }
                    break;
                }

                case RUNTIME_ERROR: {
                    Object[] body = (Object []) message.getBody();
                    String errorMessage = (String) body[0];
                    Integer lineNumber = (Integer) body[1];

                    System.out.print("*** RUNTIME ERROR");
                    if (lineNumber != null) {
                        System.out.print(" AT LINE " +
                                String.format("%03d", lineNumber));
                    }
                    System.out.println(": " + errorMessage);
                    break;
                }

                case INTERPRETER_SUMMARY: {
                    Number[] body = (Number[]) message.getBody();
                    int executionCount = (Integer) body[0];
                    int runtimeErrors = (Integer) body[1];
                    float elapsedTime = (Float) body[2];

                    System.out.printf(INTERPRETER_SUMMARY_FORMAT,
                            executionCount, runtimeErrors,
                            elapsedTime);
                    break;
                }

                case COMPILER_SUMMARY: {
                    Number[] body = (Number[]) message.getBody();
                    int instructionCount = (Integer) body[0];
                    float elapsedTime = (Float) body[1];

                    System.out.printf(COMPILER_SUMMARY_FORMAT,
                            instructionCount, elapsedTime);
                    break;
                }
            }
        }
    }
}