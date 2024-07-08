package client;

import javafx.application.Application;
import javafx.stage.Stage;
import view.menus.RegisterMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Application {
    private Socket socket;
    private DataInputStream receiveBuffer;
    private DataOutputStream sendBuffer;
    public static Client currentClient;

    public void start(Stage stage) throws Exception{
        new RegisterMenu().start(stage);
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
        currentClient = client;
        if (!client.establishConnection("127.0.0.1", 1020)) {
            System.err.println("Failed to establish connection!");
            System.exit(1);
        }
        launch(args);
    }

}
