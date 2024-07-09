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
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Server.class);
    public static final int PORT = 1020; // Server port
    private static final int POOL_SIZE = 10; // Thread pool size
    private static final ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            logger.error("Could not listen on port " + PORT, e);
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
                    } else {
                        dataOutputStream.writeUTF("login successful");
                        currentUser = user;
                    }
                    continue;
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
                    } else {
                        dataOutputStream.writeUTF("Answer is incorrect");
                    }
                    continue;
                }
                if (command.equals("logout")) {
                    currentUser = null;
                    dataOutputStream.writeUTF("logout successful");
                    continue;
                }
                if (command.equals("showUserInfo")) {
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    dataOutputStream.writeUTF(User.getUserInfo(currentUser));
                    continue;
                }
                matcher = Pattern.compile("showGameHistory:(?<numberOfGameHistories>.+)").matcher(command);
                if (matcher.matches()) {
                    // Show the game history
                    int numberOfGameHistories = Integer.parseInt(matcher.group("numberOfGameHistories"));
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    if (currentUser.getAllGameHistories().isEmpty()) {
                        dataOutputStream.writeUTF("No game has played");
                        continue;
                    }
                    dataOutputStream.writeUTF(User.getGameHistory(currentUser, numberOfGameHistories));
                    continue;
                }
                matcher = Pattern.compile("saveProfileMenuChanges:(?<newUsername>.*):(?<newEmail>.*):(?<newNickname>.*):(?<newPassword>.*):(?<oldPassword>.*)").matcher(command);
                if (matcher.matches()) {
                    // Save the changes
                    String newUsername = matcher.group("newUsername");
                    String newEmail = matcher.group("newEmail");
                    String newNickname = matcher.group("newNickname");
                    String newPassword = matcher.group("newPassword");
                    String oldPassword = matcher.group("oldPassword");
                    boolean haveChanges = false;
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    if (!newPassword.isEmpty()) {
                        if(currentUser.getPassword().equals(oldPassword)){
                            if(!newPassword.equals(oldPassword)){
                                currentUser.setPassword(newPassword);
                                haveChanges = true;
                            }
                        } else {
                            dataOutputStream.writeUTF("Old password is incorrect");
                            continue;
                        }

                    }
                    if (!newUsername.isEmpty()) {
                        if(!currentUser.getUsername().equals(newUsername)){
                            currentUser.setUsername(newUsername);
                            haveChanges = true;
                        }
                    }
                    if (!newNickname.isEmpty()) {
                        if(!currentUser.getNickname().equals(newNickname)){
                            currentUser.setNickname(newNickname);
                            haveChanges = true;
                        }
                    }
                    if (!newEmail.isEmpty()) {
                        if(!currentUser.getEmail().equals(newEmail)){
                            currentUser.setEmail(newEmail);
                            haveChanges = true;
                        }
                    }
                    if (!haveChanges) {
                        dataOutputStream.writeUTF("No changes have been made dou to the fact that you have not entered any new information or the information you have entered is the same as the previous one.");
                        continue;
                    }

                    dataOutputStream.writeUTF("Changes saved");
                    continue;
                }
                matcher = Pattern.compile("addFriendSearch:(?<username>.+)").matcher(command);
                if (matcher.matches()) {
                    // Search for a friend
                    String username = matcher.group("username");
                    if (User.usernameExists(username)&&!username.equals(currentUser.getUsername())) {
                        User user = User.getUserByUsername(username);
                        assert user != null;
                        dataOutputStream.writeUTF(User.getUserInfo(user));
                        continue;
                    }
                    dataOutputStream.writeUTF("User with this username was not found");
                    continue;
                }
                matcher = Pattern.compile("addFriend:(?<username>.+)").matcher(command);
                if (matcher.matches()) {
                    // Add a friend
                    String username = matcher.group("username");
                    if (User.usernameExists(username)) {
                        User user = User.getUserByUsername(username);
                        assert user != null;
                        if(currentUser.isFriend(username)){
                            dataOutputStream.writeUTF("This user is already your friend");
                            continue;
                        }
                        if(user.isInFriendsRequests(currentUser.getUsername())){
                            dataOutputStream.writeUTF("You have already sent a friend request to this user");
                            continue;
                        }
                        user.addFriendRequest(currentUser.getUsername());
                        dataOutputStream.writeUTF("Friend request sent successfully");
                        continue;
                    }
                    dataOutputStream.writeUTF("User with this username was not found");
                    continue;
                }
                if (command.equals("showAllFriends")) {
                    // Show all friends
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    if (currentUser.getAllFriends().isEmpty()) {
                        dataOutputStream.writeUTF("You have no friends");
                        continue;
                    }
                    dataOutputStream.writeUTF(currentUser.showAllFriends());
                    continue;
                }
                if (command.equals("showAllFriendRequests")) {
                    // Show all friend requests
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    if (currentUser.showAllFriendRequests().isEmpty()) {
                        dataOutputStream.writeUTF("You have no friend requests");
                        continue;
                    }
                    dataOutputStream.writeUTF(currentUser.showAllFriendRequests());
                    continue;
                }
                matcher = Pattern.compile("acceptFriendRequest:(?<username>.+)").matcher(command);
                if (matcher.matches()) {
                    // Accept a friend request
                    String username = matcher.group("username");
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    if(!User.usernameExists(username)){
                        dataOutputStream.writeUTF("User with this username was not found");
                        continue;
                    }
                    if (currentUser.isInFriendsRequests(username)) {
                        currentUser.getFriendRequests().remove(username);
                        currentUser.addFriend(username);
                        User user = User.getUserByUsername(username);
                        assert user != null;
                        user.addFriend(currentUser.getUsername());
                        dataOutputStream.writeUTF("Friend request accepted successfully");
                        continue;
                    }
                    dataOutputStream.writeUTF("You have no friend request from this user");
                    continue;
                }
                matcher = Pattern.compile("rejectFriendRequest:(?<username>.+)").matcher(command);
                if (matcher.matches()) {
                    // Reject a friend request
                    String username = matcher.group("username");
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    if(!User.usernameExists(username)){
                        dataOutputStream.writeUTF("User with this username was not found");
                        continue;
                    }
                    if (currentUser.isInFriendsRequests(username)) {
                        currentUser.getFriendRequests().remove(username);
                        dataOutputStream.writeUTF("Friend request rejected successfully");
                        continue;
                    }
                    dataOutputStream.writeUTF("You have no friend request from this user");
                    continue;
                }


                dataOutputStream.writeUTF("invalid input");
            }
        } catch (IOException e) {
            Server.logger.error("Error handling client connection", e);
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    Server.logger.error("Error closing client socket", e);
                }
            }
        }
    }
}