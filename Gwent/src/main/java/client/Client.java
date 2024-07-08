package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataInputStream receiveBuffer;
    private DataOutputStream sendBuffer;

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!this.establishConnection("127.0.0.1", 1020)) {
                System.err.println("Failed to establish connection!");
                return;
            }
            System.out.println("Enter command (type 'exit' to quit): ");
            while (true) {
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input)) break;
                if (this.sendMessage(input)) {
                    String responseOfServer = this.receiveResponse();
                    if (responseOfServer != null) {
                        System.out.println("Server response: " + responseOfServer);
                    } else {
                        System.err.println("Failed to receive a response from the server.");
                    }
                } else {
                    System.err.println("Failed to send message to the server.");
                }
            }
        } finally {
            this.endConnection();
        }
    }

    public boolean establishConnection(String address, int port) {
        try {
            socket = new Socket(address, port);
            sendBuffer = new DataOutputStream(socket.getOutputStream());
            receiveBuffer = new DataInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            System.err.println("Unable to initialize socket: " + e.getMessage());
            return false;
        }
    }

    public void endConnection() {
        try {
            if (socket != null) socket.close();
            if (receiveBuffer != null) receiveBuffer.close();
            if (sendBuffer != null) sendBuffer.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

    public boolean sendMessage(String message) {
        try {
            sendBuffer.writeUTF(message);
            return true;
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
            return false;
        }
    }

    public String receiveResponse() {
        try {
            return receiveBuffer.readUTF();
        } catch (IOException e) {
            System.err.println("Error receiving response: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
