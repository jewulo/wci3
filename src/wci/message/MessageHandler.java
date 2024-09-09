package wci.message;

import java.util.ArrayList;

/**
 * <h1>MessageHandler</h1>
 *
 * <p>A helper class to which message producer classes delegate the task of
 * maintaining and notifying listeners.</p>
 */

public class MessageHandler
{
    private Message                     message;    // message
    private ArrayList<MessageListener>  listeners;  // listener list

    /**
     * Constructor
     */
    public MessageHandler()
    {
        this.listeners = new ArrayList<MessageListener>();
    }

    /**
     * Add a listener to the listener list.
     * @param listener the listener to add.
     */
    public void addListener(MessageListener listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Remove a listener from the listener list.
     * @param listener the listener to add.
     */
    public void removeListener(MessageListener listener)
    {
        this.listeners.remove(listener);
    }

    /**
     * Notify listeners after setting the message.
     * @param message to the message set.
     */
    public void sendMessage(Message message)
    {
        this.message = message;
        notifyListeners();
    }

    /**
     * Notify each listener in the listener list by calling the listener's
     * messageReceived() method.
     */
    public void notifyListeners()
    {
        for (MessageListener listener : this.listeners) {
            listener.messageReceived(message);
        }
    }
}
