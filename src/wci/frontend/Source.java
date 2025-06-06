package wci.frontend;

import java.io.BufferedReader;
import java.io.IOException;

import wci.message.*;
import static wci.message.MessageType.SOURCE_LINE;

/**
 * <h1>Source</h1>
 *
 * <p>The framework class that represents the source program.</>
 */

public class Source implements MessageProducer
{
//    protected static MessageHandler messageHandler; // message handler delegate
//
//    static {
//        messageHandler = new MessageHandler();
//    }

    public static final char EOL = '\n';            // end-of-line character
    public static final char EOF = (char) 0;        // end-of-file character

    private BufferedReader reader;                  // reader for the source program
    private String line;                            // source line
    private int lineNum;                            // current source line number
    private int currentPos;                         // current source line position

    private MessageHandler messageHandler;          // message handler delegate


    /**
     * Constructor.
     * @param reader the reader for the source program
     * @throws IOException if an I/O error occurred
     */
    public Source(BufferedReader reader)
            throws IOException
    {
        this.lineNum = 0;
        this.currentPos = -2; // set to -2 to read the first source line
        this.reader = reader;
        this.messageHandler = new MessageHandler();
    }

    /**
     * Getter.
     * @return the current source line number.
     */
    public int getLineNum()
    {
        return lineNum;
    }

    /**
     * Getter.
     * @return the position of the next source character in the
     * current source line.
     */
    public int getPosition()
    {
        return currentPos;
    }

    /**
     * Return the source character at the current position.
     * @return the source character at the current position.
     * @throws Exception if an error occurred
     */
    public char currentChar()
            throws Exception
    {
        // First time?
        if (currentPos == -2) {
            readLine();
            return nextChar();
        }
        // At end of file?
        else if (line == null) {
            return EOF;
        }
        // At end of line?
        else if ((currentPos == -1) || (currentPos == line.length())) {
            return EOL;
        }
        // Need to read the next line?
        else if (currentPos > line.length()) {
            readLine();
            return nextChar();
        }
        // Return the character at the current position.
        else {
            return line.charAt(currentPos);
        }
    }

    /**
     * Consume the current source character and return the next character
     * @return the next source character.
     * @throws Exception if an error occurred.
     */
    public char nextChar()
            throws Exception
    {
        ++currentPos;
        return currentChar();
    }

    /**
     * Return the source character following the current character without
     * consuming the current character.
     * @return the following character.
     * @throws Exception if an error occurred.
     */
    public char peekChar()
            throws Exception
    {
        currentChar();
        if (line == null) {
            return EOF;
        }

        int nextPos = currentPos + 1;
        return nextPos < line.length() ? line.charAt(nextPos) : EOL;
    }

    /**
     * @return true if at the end of the line, else return false.
     * @throws Exception if an error occurred.
     */
    public boolean atEol()
        throws Exception
    {
        return (line != null) && (currentPos == line.length());
    }

    /**
     * @return true if at the end of the file, else return false.
     * @throws Exception if an error occurred.
     */
    public boolean atEof()
        throws Exception
    {
        // First time?
        if (currentPos == -2) {
            readLine();
        }

        return line == null;
    }

    /**
     * Skip the rest of the current input line
     * by forcing the next read to read a new line.
     * @throws Exception if an error occurred.
     */
    public void skipToNextLine()
        throws Exception
    {
        if (line != null) {
            currentPos = line.length() + 1;
        }
    }

    /**
     * Read the next source line.
     * @throws IOException if an I/O error occurred
     */
    private void readLine()
            throws IOException
    {
        line = reader.readLine();   // null when at the end of the source
        currentPos = -1;

        if (line != null) {
            ++lineNum;
        }

        // Send a source line message containing the line number
        // and the line text to all listeners.
        if (line != null) {
            sendMessage(new Message(SOURCE_LINE,
                                    new Object[] {lineNum, line}));
        }
    }

    /**
     * Close the source.
     * @throws Exception if an error occurred.
     */
    public void close()
            throws Exception
    {
        if (reader != null) {
            try {
                reader.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    /**
     * Add a parser message listener.
     * @param listener the message listener to add.
     */
    @Override
    public void addMessageListener(MessageListener listener)
    {
        messageHandler.addListener(listener);
    }

    /**
     * Remove a parser messagelistener.
     * @param listener the message listener to remove.
     */
    @Override
    public void removeMessageListener(MessageListener listener)
    {
        messageHandler.removeListener(listener);
    }

    /**
     * Notify listeners after setting the message.
     * @param message to set.
     */
    @Override
    public void sendMessage(Message message)
    {
        messageHandler.sendMessage(message);
    }
}
