package exercise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a Connection to a socket
 * Each connection is a Thread listening for messages coming from the socket.
 */
public class ClientConnection extends Thread {

    private final Logger log = LoggerFactory.getLogger(ClientConnection.class);

    private final Socket socket;
    private final BufferedWriter writer;
    private final BufferedReader reader;
    private final ConnectionEventListener listener;

    public ClientConnection(Socket socket, ConnectionEventListener listener) throws IOException {
        this.socket = socket;
        this.listener = listener;
        this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    /**
     * Sends a message through the socket
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            try {
                this.socket.close();
            } catch (IOException e1) {
                // ignored
            }
        }
    }

    /**
     * While the socket is connected, waits for new messages to read.
     */
    public void run() {
        try {
            while (socket.isConnected()) {
                String message = this.reader.readLine();

                // connection is closed
                if (message == null) {
                    listener.connectionClosed(this);
                    return;
                }

                listener.messageReceived(this, message);
            }
        } catch (IOException e) {
            log.error("Error on reading message.", e);
            listener.connectionClosed(this);
        }
    }
}
