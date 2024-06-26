package controller;

import javafx.scene.control.*;
import model.User;

import java.util.Random;


public class RegisterMenuController {
    public Label ErrorText;
    public TextField Nickname;
    public TextField RegisterUsername;
    public PasswordField RegisterPassword;
    public TextField Answer1;
    public TextField Answer2;
    public TextField Answer3;
    public TextField LoginUserName;
    public PasswordField LoginPassword;
    public TextField Email;
    public CheckBox StayLogged;
    public Label RandomPassword;

    public void loginAction() {
        if (LoginUserName.getText().isEmpty() || LoginPassword.getText().isEmpty()) {
            ErrorText.setText("Please fill all the fields");
            return;
        }
        User u= User.getUserForLogin(LoginUserName.getText(), LoginPassword.getText());
        if(u == null) {
            ErrorText.setText("username or password is incorrect");
            return;
        }

        User.loggedInUser=u;
        u.setStayLoggedIn(StayLogged.isSelected());

//        new ProfileMenu().start(RegisterMenu.appStage);
//        TODO: set the next stage and scene

    }

    public void registerAction() {
        if (RegisterUsername.getText().isEmpty() || RegisterPassword.getText().isEmpty()||Nickname.getText().isEmpty()||Answer1.getText().isEmpty()
                ||Answer2.getText().isEmpty()||Answer3.getText().isEmpty()) {
            ErrorText.setText("Please fill all the fields");
            return;
        }
        if(User.usernameExists(RegisterUsername.getText())){
            ErrorText.setText("User Exist");
            return;
        }
        new User(RegisterUsername.getText(),Nickname.getText(), RegisterPassword.getText(),Email.getText(),Answer1.getText(),Answer2.getText(),Answer3.getText());
        ErrorText.setText("New User successfully created");
    }

    public void generateRandomPassword() {
        String password=PasswordGenerator.generateRandomPassword(12);
        RandomPassword.setText(password);
        RegisterPassword.setText(password);
    }
}

class PasswordGenerator {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+-=[]{};:'\"\\|,.<>/?";
    private static final String PASSWORD_ALLOW = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARACTERS;

    private static final Random random = new Random();

    public static String generateRandomPassword(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(PASSWORD_ALLOW.length());
            char rndChar = PASSWORD_ALLOW.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }
}
