package exercise;

import java.io.IOException;
import java.net.ServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This ConnectionManager implementation is backed by a socket connection.
 */
public class SocketConnectionManager implements ConnectionManager {

    private final Logger log = LoggerFactory.getLogger(SocketConnectionManager.class);

    private final ServerSocket socket;

    public SocketConnectionManager(int port) throws IOException {
        log.info("Ready To Listen on Port: " + port);
        this.socket = new ServerSocket(port);
    }

    @Override
    public ClientConnection accept(ConnectionEventListener listener) throws IOException {
        ClientConnection connection = new ClientConnection(this.socket.accept(), listener);
        log.info("New connection received: " + connection);
        return connection;
    }
}
