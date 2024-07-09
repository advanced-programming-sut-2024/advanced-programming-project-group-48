package model;

import java.util.*;

public class User {
    private String username;
    private String nickname;
    private String password;
    private String email;
    private boolean stayLoggedIn;
    private final ArrayList<String> answerOfQuestions = new ArrayList<>(3);
    public static User loggedInUser;
    private static final ArrayList<User> allUsers = new ArrayList<>();
    private static final ArrayList<User> usersBaseRanking = new ArrayList<>();
    private final GameInformation gameInformation = new GameInformation();
    private final ArrayList<GameHistory> allGameHistories = new ArrayList<>();
    private final ArrayList<String> allFriends = new ArrayList<>();
    private final ArrayList<String> friendRequests = new ArrayList<>();


    private Deck deck = new Deck();

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public static String getGameHistory(User user, int numberOfGameHistories) {
        StringBuilder gameHistory = new StringBuilder();
        if (user.getAllGameHistories().isEmpty()) {
            return "No game has played";
        }
        int historySize = user.getAllGameHistories().size();
        int historiesToFetch = Math.min(numberOfGameHistories, historySize);
        for (int i = historySize - 1; i >= historySize - historiesToFetch; i--) {
            gameHistory.append("opponent username: ").append(user.getAllGameHistories().get(i).getOpponentUsername()).append("\n")
                    .append("date of game: ").append(user.getAllGameHistories().get(i).getDateOfGame().toString()).append("\n")
                    .append("your score per round : ").append(Arrays.toString(user.getAllGameHistories().get(i).getUserScorePerRound())).append("\n")
                    .append("opponent score per round : ").append(Arrays.toString(user.getAllGameHistories().get(i).getOpponentScoresPerRound())).append("\n")
                    .append("total score of you in this game : ").append(user.getAllGameHistories().get(i).getTotalScoreOfUser()).append("\n")
                    .append("total score of your opponent: ").append(user.getAllGameHistories().get(i).getTotalScoreOfOpponent()).append("\n")
                    .append("winner🏆🏆: ").append(user.getAllGameHistories().get(i).getUsernameOfWinner()).append("\n\n");
        }
        return gameHistory.toString();
    }

    // We can move this method to server side
    public static String getUserInfo(User user) {
        return "username: " + user.getUsername() + "\n" +
                "nickname: " + user.getNickname() + "\n" +
                "maxScore: " + user.getMaxScore() + "\n" +
                "ranking: " + (user.getUsersBaseRanking().indexOf(user) + 1) + "\n" +
                "number of games played: " + user.getNumberOfTotalGames() + "\n" +
                "number of draws: " + user.getNumDraws() + "\n" +
                "number of wins: " + user.getNumWins() + "\n" +
                "number of loses: " + user.getNumLoses();
    }

    public ArrayList<String> getAllFriends() {
        return allFriends;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public User(String username, String nickname, String password, String email, String answer1, String answer2, String answer3) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        stayLoggedIn = false;
        answerOfQuestions.add(answer1);
        answerOfQuestions.add(answer2);
        answerOfQuestions.add(answer3);
        allUsers.add(this);
        usersBaseRanking.add(this);
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getAnswerOfQuestions() {
        return answerOfQuestions;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User getUserByUsername(String username) {
        for (User i : allUsers) {
            if (i.username.equals(username)) {
                return i;
            }
        }
        return null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static boolean usernameExists(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static User getUserForLogin(String username, String password) {
        for (User user : allUsers) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void rankingUsers() {
        usersBaseRanking.sort(Comparator.comparingInt(User::getNumWins));
    }

    public ArrayList<User> getUsersBaseRanking() {
        rankingUsers();
        return usersBaseRanking;
    }

    public void increaseNumWins() {
        gameInformation.numberOfWins++;
    }

    public void increaseNumDraws() {
        gameInformation.numberOfDraws++;
    }

    public void increaseNumLoses() {
        gameInformation.numberOfLoses++;
    }

    public void increaseNumTotalGames() {
        gameInformation.numberOfTotalGames++;
    }

    public int getNumWins() {
        return gameInformation.numberOfWins;
    }

    public int getNumDraws() {
        return gameInformation.numberOfDraws;
    }

    public int getNumLoses() {
        return gameInformation.numberOfLoses;
    }

    public int getMaxScore() {
        return gameInformation.maxScore;
    }

    public void setMaxScore(int score) {
        if (gameInformation.maxScore < score) gameInformation.maxScore = score;
    }

    public int getNumberOfTotalGames() {
        return gameInformation.numberOfTotalGames;
    }

    public ArrayList<GameHistory> getAllGameHistories() {
        return allGameHistories;
    }

    public void addGameHistory(GameHistory gameHistory) {
        allGameHistories.add(gameHistory);
    }

    public boolean isFriend(String username) {
        return allFriends.contains(username);
    }
    public boolean isInFriendsRequests(String username) {
        return friendRequests.contains(username);
    }
    public void addFriendRequest(String username){
        friendRequests.add(username);
    }

    public void addFriend(String username) {
        if(!allFriends.contains(username)) allFriends.add(username);
    }
    public String showAllFriends(){
        StringBuilder friends = new StringBuilder();
        for (String friend : allFriends) {
            friends.append(friend).append("\n");
        }
        return friends.toString();
    }
    public String showAllFriendRequests(){
        StringBuilder friends = new StringBuilder();
        for (String friend : friendRequests) {
            friends.append(friend).append("\n");
        }
        return friends.toString();
    }
}


class GameInformation {
    public int maxScore = 0;
    public int numberOfTotalGames = 0;
    public int numberOfWins = 0;
    public int numberOfLoses = 0;
    public int numberOfDraws = 0;
}