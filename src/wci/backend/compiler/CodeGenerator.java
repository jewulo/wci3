package wci.backend.compiler;

import wci.backend.Backend;
import wci.intermediate.ICode;
import wci.intermediate.SymTab;
import wci.message.Message;
import wci.message.MessageListener;
import wci.message.MessageType;

public class CodeGenerator extends Backend {
    /**
     * Process the intermediate code and the symbol table generated by the
     * parser to generate machine-language instructions.
     * @param iCode  the intermediate code.
     * @param symTab the symbol table.
     * @throws Exception if an error occurred.
     */
    @Override
    public void process(ICode iCode, SymTab symTab)
        throws Exception
    {
        long startTime = System.currentTimeMillis();
        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        int instructionCount = 0;

        // Send the compiler summary message.
        sendMessage(new Message(MessageType.COMPILER_SUMMARY,
                                new Number[] {instructionCount,
                                              elapsedTime}));
    }

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