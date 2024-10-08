package wci.message;

import java.awt.*;

/**
 * <h1>Message</h1>
 *
 * <p>Message format.</p>
 */
public class Message
{
    private MessageType type;
    private Object body;

    /**
     * Constructor.
     * @param type the message type.
     * @param body the message body.
     */
    public Message(MessageType type, Object body)
    {
        this.type = type;
        this.body = body;
    }

    /**
     * Getter.
     * @return the message body.
     */
    public Object getBody()
    {
        return body;
    }

    /**
     * Getter.
     * @return the message type.
     */
    public MessageType getType()
    {
        return type;
    }
}
