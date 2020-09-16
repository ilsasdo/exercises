package exercise;

/**
 * Responds to events occuring on connections
 */
public interface ConnectionEventListener {

    /**
     * Triggered when a message is received from the client connection
     *
     * @param clientConnection the connection that receives the message
     * @param message the message received
     */
    void messageReceived(ClientConnection clientConnection, String message);

    /**
     * Triggered when a connection gets closed.
     * @param clientConnection the connection closed.
     */
    void connectionClosed(ClientConnection clientConnection);
}
