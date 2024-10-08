package wci.message;

public interface MessageProducer
{
    /**
     * Add a listener to the listener list.
     * @param listener the listener to add.
     */
    public void addMessageListener(MessageListener listener);

    /**
     * Remove a listener to the listener list.
     * @param listener the listener to add.
     */
    public void removeMessageListener(MessageListener listener);

    /**
     * Notify listeners after setting the message.
     * @param message the message to set.
     */
    public void sendMessage(Message message);
}
