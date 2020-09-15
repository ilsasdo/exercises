package exercise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatClient extends Thread {

    private final Socket socket;
    private final BufferedWriter writer;
    private final BufferedReader reader;
    private final ChatServer chatServer;

    public ChatClient(Socket socket, ChatServer chatServer) throws IOException {
        this.chatServer = chatServer;
        this.socket = socket;
        this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            chatServer.removeClient(this);
            try {
                this.socket.close();
            } catch (IOException e1) {
                // ignored
            }
        }
    }

    public void run() {
        while (socket.isConnected()) {
            try {
                String message = this.reader.readLine();

                // connection is closed
                if (message == null) {
                    chatServer.removeClient(this);
                    return;
                }
                chatServer.broadcastMessage(this, message);
            } catch (IOException e) {
                chatServer.removeClient(this);
                return;
            }
        }
    }
}
