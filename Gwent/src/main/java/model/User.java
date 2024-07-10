package model;

import java.util.ArrayList;
import java.util.Collections;

public class User {
    private String username;
    private String nickname;
    private String password;
    private String email;
    private boolean stayLoggedIn;
    private final ArrayList<String> answerOfQuestions = new ArrayList<>(3);
    public static User loggedInUser;
    public static final ArrayList<User> allUsers= new ArrayList<>();
    public static final ArrayList<User> usersBaseRanking = new ArrayList<User>();
    private final GameInformation gameInformation=new GameInformation();
    private final ArrayList<GameHistory> allGameHistories=new ArrayList<>();
    private Deck deck = new Deck();

    public static void addMockUser(String existingUser, String s) {
    }

    public static User getUser(String newUser) {
        return null;
    }

    public static void addUser(User user) {

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
        stayLoggedIn=false;
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

    public static User getUserByUsername(String username){
        for(User i: allUsers){
            if(i.username.equals(username)){
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
            if(user.username.equals(username)) {
                return true;
            }
        }
        return false;
    }
    public static User getUserForLogin(String username, String password) {
        for (User user : allUsers) {
            if(user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }
    public static void rankingUsers(){
        Collections.sort(usersBaseRanking,(user1, user2) -> user1.getNumWins() - user2.getNumWins());
    }
    public static ArrayList<User> getUsersBaseRanking(){
        rankingUsers();
        return usersBaseRanking;
    }
    public void increaseNumWins(){
        gameInformation.numberOfWins++;
    }
    public void increaseNumDraws(){
        gameInformation.numberOfDraws++;
    }
    public void increaseNumLoses(){
        gameInformation.numberOfLoses++;
    }
    public void increaseNumTotalGames(){
        gameInformation.numberOfTotalGames++;
    }
    public int getNumWins(){
        return gameInformation.numberOfWins;
    }
    public int getNumDraws(){
        return gameInformation.numberOfDraws;
    }
    public int getNumLoses(){
        return gameInformation.numberOfLoses;
    }
    public int getMaxScore(){
        return gameInformation.maxScore;
    }
    public void setMaxScore(int score){
        if (gameInformation.maxScore < score) gameInformation.maxScore=score;
    }
    public int getNumberOfTotalGames(){
        return gameInformation.numberOfTotalGames;
    }

    public ArrayList<GameHistory> getAllGameHistories() {
        return allGameHistories;
    }
    public void addGameHistory(GameHistory gameHistory){
        allGameHistories.add(gameHistory);
    }
}
class GameInformation {
    public int maxScore=0;
    public int numberOfTotalGames=0;
    public int numberOfWins=0;
    public int numberOfLoses=0;
    public int numberOfDraws=0;
}