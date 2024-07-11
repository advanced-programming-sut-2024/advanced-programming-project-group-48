package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static server.Server.logger;

public class ChatHandler implements Runnable {
    private final Socket chatSocket;
    private DataInputStream chatInput;
    private DataOutputStream chatOutput;

    public ChatHandler(Socket chatSocket) {
        this.chatSocket = chatSocket;
        try {
            chatInput = new DataInputStream(chatSocket.getInputStream());
            chatOutput = new DataOutputStream(chatSocket.getOutputStream());
        } catch (IOException e) {
            logger.error("Error creating chat input/output streams", e);
        }
    }

    @Override
    public void run() {
        try {
            while (!chatSocket.isClosed()) {
                String message = chatInput.readUTF();
                // Process and forward the message as needed
                System.out.println("Received chat message: " + message);
                // Example: Echo the message back to the sender
                chatOutput.writeUTF("Echo: " + message);
            }
        } catch (IOException e) {
            logger.error("Error reading chat message", e);
        } finally {
            try {
                chatSocket.close();
            } catch (IOException e) {
                logger.error("Error closing chat socket", e);
            }
        }
    }
}