package server;

import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static server.Server.logger;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private User currentUser;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private final CountDownLatch gameEndLatch; // Added latch to wait for game end

    public User getCurrentUser() {
        return currentUser;
    }

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.gameEndLatch = new CountDownLatch(1); // Initialize the latch with count 1
    }


    public void startGameSession(ClientHandler opponent) {
        GameSession gameSession = new GameSession(this, opponent, gameEndLatch);
        Thread gameThread = new Thread(gameSession);
        gameThread.start();
        try {
            gameEndLatch.await(); // Wait for the game to end
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Game session interrupted");
        }
    }

    @Override
    public void run() {

        try (DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream())) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            String command;
            while (true) {
                System.out.println("Running");

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
                        User.addOnlineUser(currentUser);
                        Server.getOnlineUsers().put(currentUser.getUsername(), this);
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
                    if (currentUser != null) {
                        User.removeOnlineUser(currentUser);
                        Server.getOnlineUsers().remove(currentUser.getUsername());
                        dataOutputStream.writeUTF("logout successful");
                        // Reset currentUser or handle it as needed
                        currentUser = null;
                    } else {
                        dataOutputStream.writeUTF("You are not logged in");
                    }
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
                    String gameHistory = User.getGameHistory(currentUser, numberOfGameHistories);
                    logger.debug("Sending game history: {}", gameHistory);
                    dataOutputStream.writeUTF(gameHistory);
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
                        if (currentUser.getPassword().equals(oldPassword)) {
                            if (!newPassword.equals(oldPassword)) {
                                currentUser.setPassword(newPassword);
                                haveChanges = true;
                            }
                        } else {
                            dataOutputStream.writeUTF("Old password is incorrect");
                            continue;
                        }

                    }
                    if (!newUsername.isEmpty()) {
                        if (!currentUser.getUsername().equals(newUsername)) {
                            currentUser.setUsername(newUsername);
                            haveChanges = true;
                        }
                    }
                    if (!newNickname.isEmpty()) {
                        if (!currentUser.getNickname().equals(newNickname)) {
                            currentUser.setNickname(newNickname);
                            haveChanges = true;
                        }
                    }
                    if (!newEmail.isEmpty()) {
                        if (!currentUser.getEmail().equals(newEmail)) {
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
                    if (User.usernameExists(username) && !username.equals(currentUser.getUsername())) {
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
                        if (currentUser.isFriend(username)) {
                            dataOutputStream.writeUTF("This user is already your friend");
                            continue;
                        }
                        if (user.isInFriendsRequests(currentUser.getUsername())) {
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
                    if (!User.usernameExists(username)) {
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
                    if (!User.usernameExists(username)) {
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
                matcher = Pattern.compile("saveLeaderFaction:(?<faction>.+):(?<leader>.+)").matcher(command);
                if (matcher.matches()) {
                    // Save the leader and faction
                    String faction = matcher.group("faction");
                    String leader = matcher.group("leader");
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    currentUser.getDeck().setFaction(faction);
                    currentUser.getDeck().setCommander(leader);
                    dataOutputStream.writeUTF("Leader and faction saved successfully");
                    continue;
                }
                matcher = Pattern.compile("addCardToDeck:(?<cardName>.+):(?<cardCount>.+)").matcher(command);
                if (matcher.matches()) {
                    // Add a card to the deck
                    String cardName = matcher.group("cardName");
                    int cardCount = Integer.parseInt(matcher.group("cardCount"));
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    if (currentUser.getDeck().addCard(cardName, cardCount))
                        dataOutputStream.writeUTF("Card added to deck successfully");

                    else
                        dataOutputStream.writeUTF("Card could not be added to deck");

                    continue;
                }
                if (command.equals("showDeck")) {
                    // Show the deck
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    dataOutputStream.writeUTF(currentUser.getDeck().showDeck());
                    continue;
                }
                matcher = Pattern.compile("removeCardFromDeck:(?<cardName>.+):(?<cardCount>.+)").matcher(command);
                if (matcher.matches()) {
                    // Remove a card from the deck
                    String cardName = matcher.group("cardName");
                    int cardCount = Integer.parseInt(matcher.group("cardCount"));
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    if (currentUser.getDeck().removeCard(cardName, cardCount))
                        dataOutputStream.writeUTF("Card removed from deck successfully");

                    else
                        dataOutputStream.writeUTF("Card could not be removed from deck");

                    continue;
                }
                if (command.equals("showScoreBoard")) {
                    // Show the score board
                    dataOutputStream.writeUTF(User.GetScoreBoardString());
                    continue;
                }
                matcher = Pattern.compile("createNewGame:(?<opponentUsername>.+)").matcher(command);
                if (matcher.matches()) {
                    // Create a new game
                    String opponentUsername = matcher.group("opponentUsername");
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    if (!User.usernameExists(opponentUsername)) {
                        dataOutputStream.writeUTF("User with this username was not found");
                        continue;
                    }
                    if (currentUser.getUsername().equals(opponentUsername)) {
                        dataOutputStream.writeUTF("The username you entered is your own");
                        continue;
                    }
                    User opponent = User.getUserByUsername(opponentUsername);
                    assert opponent != null;
                    if (opponent.isInGame()) {
                        dataOutputStream.writeUTF("The user is already in a game");
                        continue;
                    }
                    if (currentUser.isInGame()) {
                        dataOutputStream.writeUTF("You are already in a game");
                        continue;
                    }
                    if (!opponent.isInWaitingRoom()) {
                        dataOutputStream.writeUTF("The opponent is not Ready for a Game");
                        continue;
                    }

                    ClientHandler opponentClientHandler = Server.getOnlineUsers().get(opponentUsername);
                    if (opponentClientHandler == null) {
                        dataOutputStream.writeUTF("The opponent is not online");
                        continue;
                    }
                    currentUser.setInGame(true);
                    opponent.setInGame(true);
                    dataOutputStream.writeUTF("Game created successfully");
                    opponentClientHandler.dataOutputStream.writeUTF("Game created successfully");
                    new GameSession(this, opponentClientHandler, gameEndLatch);
                    continue;
                }
                if (command.equals("enterWaitingRoom")) {
                    // Enter the waiting room
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    currentUser.setInWaitingRoom(true);
                    continue;
                }
                if (command.equals("getGameEnvironment")) {
                    if (currentUser == null) {
                        dataOutputStream.writeUTF("You are not logged in");
                        continue;
                    }
                    GameSession gameSession = GameSession.allGameSessions.get(GameSession.allGameSessions.size() - 1);
                    if (!gameSession.isRunning) {
                        gameSession.run();
                    }
                    gameSession.gameEndLatch.await();
                    continue;
                }


                dataOutputStream.writeUTF("invalid input");
            }
        } catch (IOException e) {
            logger.error("Error handling client connection", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (currentUser != null) {
                Server.getOnlineUsers().remove(currentUser.getUsername());
                User.removeOnlineUser(currentUser);
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    logger.error("Error closing client socket", e);
                }
            }
        }
    }

    public void sendMessage(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            logger.error("Error sending message to client", e);
        }
    }

    public String receiveMessage() {
        try {
            return dataInputStream.readUTF();
        } catch (IOException e) {
            logger.error("Error receiving message from client", e);
            return null;
        }
    }
}

