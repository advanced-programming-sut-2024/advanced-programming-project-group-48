package model;

import java.util.ArrayList;

public class User {
    private String username;
    private String nickname;
    private String password;
    private String email;
    private boolean stayLoggedIn;
    private final ArrayList<String> answerOfTheQuestions = new ArrayList<>(3);
    public static User loggedInUser;
    private static final ArrayList<User> allUsers= new ArrayList<>();

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
        answerOfTheQuestions.add(answer1);
        answerOfTheQuestions.add(answer2);
        answerOfTheQuestions.add(answer3);
        allUsers.add(this);
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

    public ArrayList<String> getAnswerOfTheQuestions() {
        return answerOfTheQuestions;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
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
}
