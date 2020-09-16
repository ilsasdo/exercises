package exercise

import spock.lang.Specification

class ChatServerSpec extends Specification {
    void "when chat server receives a message it broadcast the message to other clients"() {
        setup:
        ByteArrayInputStream inputStream = new ByteArrayInputStream("message".bytes)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()

        Socket socket = Mock(Socket)
        socket.isConnected() >> true
        socket.getInputStream() >> inputStream
        socket.getOutputStream() >> outputStream

        ConnectionManager connectionManager = Mock(ConnectionManager)
        ConnectionEventListener chatServer = Mock(ConnectionEventListener)

        ClientConnection connection = new ClientConnection(socket, chatServer)
        connectionManager.accept() >> connection

        when: "chat server receive a message"
        connection.run()

        then: "the message should be broadcasted"
        1 * chatServer.messageReceived(connection, "message")
    }

    void "every registered clients should receive the broadcasted message"() {
        setup:
        ConnectionManager connectionManager = Mock(ConnectionManager)
        ChatServer chatServer = new ChatServer(connectionManager)

        ByteArrayInputStream inputStream = new ByteArrayInputStream("message".bytes)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        Socket socket = Mock(Socket)
        socket.isConnected() >> true
        socket.getInputStream() >> inputStream
        socket.getOutputStream() >> outputStream

        ClientConnection connection1 = Mock(ClientConnection, constructorArgs: [socket, chatServer])
        ClientConnection connection2 = Mock(ClientConnection, constructorArgs: [socket, chatServer])
        ClientConnection connection3 = Mock(ClientConnection, constructorArgs: [socket, chatServer])
        ClientConnection connection4 = Mock(ClientConnection, constructorArgs: [socket, chatServer])
        connectionManager.accept(_) >>> [connection1, connection2, connection3, connection4] >> {throw new IOException("interrupt")}

        try {
            chatServer.run()
        } catch(IOException e) {
            // ignored
        }

        when:
        chatServer.messageReceived(connection1, "message received")

        then:
        0 * connection1.sendMessage("message received")
        1 * connection2.sendMessage("message received")
        1 * connection3.sendMessage("message received")
        1 * connection4.sendMessage("message received")
    }

    void "write message should append a carriage return"() {
        setup:
        ByteArrayInputStream inputStream = new ByteArrayInputStream("message".bytes)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()

        Socket socket = Mock(Socket)
        socket.isConnected() >> true
        socket.getInputStream() >> inputStream
        socket.getOutputStream() >> outputStream

        ClientConnection connection = new ClientConnection(socket, Mock(ConnectionEventListener))

        when:
        connection.sendMessage("message to write")
        then:
        outputStream.toString() == "message to write"+System.getProperty("line.separator")
    }
}
