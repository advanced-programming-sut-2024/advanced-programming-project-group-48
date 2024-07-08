package controller.menu.controller;

import client.Client;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;
import view.menus.ForgetPasswordMenu;
import view.menus.MainMenu;
import view.menus.RegisterMenu;

import java.util.Random;


public class RegisterMenuController {
    public static final String USERNAME_REGEX = "[a-zA-Z0-9-]+";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
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

    public void loginAction() throws Exception {
        if (LoginUserName.getText().isEmpty() || LoginPassword.getText().isEmpty()) {
            ErrorText.setText("Please fill all the fields");
            return;
        }
        sendLoginReq(LoginUserName.getText(), LoginPassword.getText(), StayLogged.isSelected());
        String response = Client.currentClient.receiveResponse();
        if(response.equals("login successful")) {
            RegisterMenu.appStage.close();
            RegisterMenu.appStage=null;
            new MainMenu().start(new Stage());
        } else {
            ErrorText.setText(response);
        }
    }

    private void sendLoginReq(String username, String password, boolean stayLoggedIn) {
        Client.currentClient.sendMessage("login:" + username + ":" + password + ":" + stayLoggedIn);
    }

    public void registerAction() {
        if (RegisterUsername.getText().isEmpty() || RegisterPassword.getText().isEmpty()||Nickname.getText().isEmpty()||Answer1.getText().isEmpty()
                ||Answer2.getText().isEmpty()||Answer3.getText().isEmpty()) {
            ErrorText.setText("Please fill all the fields");
            return;
        }
        if(!RegisterUsername.getText().matches(USERNAME_REGEX)){
            ErrorText.setText("Username can only contain letters, numbers, and underscores");
            return;
        }
        if(!Email.getText().matches(EMAIL_REGEX)){
            ErrorText.setText("Invalid Email");
            return;
        }

        if(!RegisterPassword.getText().matches(PASSWORD_REGEX)){
            ErrorText.setText("Invalid Password format");
            return;
        }

        sendRegisterReq(RegisterUsername.getText(), Nickname.getText(), RegisterPassword.getText(), Email.getText(), Answer1.getText(), Answer2.getText(), Answer3.getText());
        ErrorText.setText(Client.currentClient.receiveResponse());
    }

    private void sendRegisterReq(String username, String nickname, String password, String email, String answer1, String answer2, String answer3) {
        Client.currentClient.sendMessage("register:" + username + ":" + nickname + ":" + password + ":" + email + ":" + answer1 + ":" + answer2 + ":" + answer3);
    }

    public void generateRandomPassword() {
        String password=PasswordGenerator.generateRandomPassword(12);
        RandomPassword.setText(password);
        RegisterPassword.setText(password);
    }

    public void changeToForgetPassword() throws Exception {
        new ForgetPasswordMenu().start(RegisterMenu.appStage);
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
