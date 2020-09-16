package exercise;

import java.io.IOException;

/**
 * Manages the upcoming connections by creating the corresponding ClientConnection
 */
public interface ConnectionManager {

    /**
     * Returns a new ClientConnection
     *
     * @param listener the connection event listener
     * @return a new connection bounded to a client
     * @throws IOException
     */
    ClientConnection accept(ConnectionEventListener listener) throws IOException;

}
