package model;

import java.util.ArrayList;

public class User {
    private String username;
    private String nickname;
    private String password;
    private String email;
    private ArrayList<String> answerOfQuestions = new ArrayList<>(3);
    private ArrayList<User> allUsers;

    public User(String username, String nickname, String password, String email, ArrayList<String> answerOfQuestions) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.answerOfQuestions = answerOfQuestions;
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

    public ArrayList<String> getAnswerOfQuestions() {
        return answerOfQuestions;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUserByUsername(String username){
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

    public void setAnswerOfQuestions(ArrayList<String> answerOfQuestions) {
        this.answerOfQuestions = answerOfQuestions;
    }

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }
}
