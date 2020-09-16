package exercise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The chat server implementation
 */
public class ChatServer implements ConnectionEventListener {

    private final Logger log = LoggerFactory.getLogger(ChatServer.class);

    private final List<ClientConnection> connectedClients = Collections.synchronizedList(new ArrayList<>());
    private final ConnectionManager connectionManager;

    public static void main(String args[]) throws IOException {
        new ChatServer(new SocketConnectionManager(10000)).run();
    }

    public ChatServer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    void run() throws IOException {
        while (true) {
            ClientConnection connection = connectionManager.accept(this);
            connectedClients.add(connection);
            connection.start();
        }
    }

    @Override
    public void messageReceived(ClientConnection clientConnection, String message) {
        broadcastMessage(clientConnection, message);
    }

    @Override
    public void connectionClosed(ClientConnection connection) {
        connectedClients.remove(connection);
        log.info("Connection " + connection + " Removed");
    }

    private void broadcastMessage(ClientConnection sender, String message) {
        log.info("Broadcast Message: " + message);
        for (ClientConnection client : connectedClients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}
