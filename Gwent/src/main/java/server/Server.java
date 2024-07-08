package server;

import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    public static final int PORT = 1020; // Server port
    private static final int POOL_SIZE = 10; // Thread pool size
    private static final ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Store Server is listening on port " + PORT);

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + PORT);
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

}

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private User currentUser;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream())) {
            String command;
            while (true) {
                try {
                    command = dataInputStream.readUTF();
                } catch (EOFException e) {
                    System.out.println("Client disconnected");
                    break; // Exit the loop if client disconnects
                }
                // Read the command from the client
                Matcher matcher = Pattern.compile("register:(?<username>.+):(?<nickname>.+):(?<password>.+):(?<email>.+):(?<answer1>.+):(?<answer2>.+):(?<answer3>.+)").matcher(command);
                if (matcher.matches()) {
                    // Register the user
                    String username = matcher.group("username");
                    String nickname = matcher.group("nickname");
                    String password = matcher.group("password");
                    String email = matcher.group("email");
                    String answer1 = matcher.group("answer1");
                    String answer2 = matcher.group("answer2");
                    String answer3 = matcher.group("answer3");
                    // Register the user
                    if (User.usernameExists(username)) {
                        dataOutputStream.writeUTF("Username already exists");
                        continue;
                    }
                    new User(username, nickname, password, email, answer1, answer2, answer3);
                    dataOutputStream.writeUTF("User registered successfully");
                    continue;
                }
                matcher = Pattern.compile("login:(?<username>.+):(?<password>.+):(?<stayLoggedIn>.+)").matcher(command);
                if (matcher.matches()) {
                    // Login the user
                    String username = matcher.group("username");
                    String password = matcher.group("password");
                    boolean stayLoggedIn = Boolean.parseBoolean(matcher.group("stayLoggedIn"));
                    User user = User.getUserForLogin(username, password);
                    if (user == null) {
                        dataOutputStream.writeUTF("Username does not exist");
                        continue;
                    } else {
                        dataOutputStream.writeUTF("login successful");
                        currentUser = user;
                        continue;
                    }
                }
                matcher = Pattern.compile("changePassword:(?<username>.+):(?<answerOfTheQuestion>.+):(?<numberOfQuestion>.+):(?<newPassword>.+)").matcher(command);
                if (matcher.matches()) {
                    // Change the password
                    String username = matcher.group("username");
                    String answerOfTheQuestion = matcher.group("answerOfTheQuestion");
                    int numberOfQuestion = Integer.parseInt(matcher.group("numberOfQuestion"));
                    String newPassword = matcher.group("newPassword");
                    User user = User.getUserByUsername(username);
                    if (user == null) {
                        dataOutputStream.writeUTF("Username does not exist");
                        continue;
                    }
                    if (user.getAnswerOfQuestions().get(numberOfQuestion - 1).equals(answerOfTheQuestion)) {
                        user.setPassword(newPassword);
                        dataOutputStream.writeUTF("Password changed successfully");
                        continue;
                    } else {
                        dataOutputStream.writeUTF("Answer is incorrect");
                        continue;
                    }
                }

                dataOutputStream.writeUTF("invalid input");
            }
        } catch (IOException e) {
            System.err.println("Error handling client connection");
            e.printStackTrace();
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}