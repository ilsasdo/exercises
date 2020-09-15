package exercise;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatServer {

    private final List<ChatClient> connectedClients = Collections.synchronizedList(new ArrayList<>());
    private final int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public static void main(String args[]) throws IOException {
        try {
            new ChatServer(10000).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void run() throws IOException {
        // listen on port 10000 for connnections
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket socket = serverSocket.accept();

            // on receiving connection, store the connection
            ChatClient client = new ChatClient(socket, this);
            connectedClients.add(client);
            client.start();
        }
    }

    void broadcastMessage(ChatClient sender, String message) {
        for (ChatClient client : connectedClients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ChatClient client) {
        connectedClients.remove(client);
    }
}
