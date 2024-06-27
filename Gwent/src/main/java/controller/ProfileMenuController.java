package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;
import view.RegisterMenu;

public class ProfileMenuController {
    public Label ErrorText;
    public TextField NewUsername;
    public TextField NewNickname;
    public TextField NewEmail;
    public TextField OldPassword;
    public TextField NewPassword;
    public TextField NumberOfGameHistory;

    public void saveChanges(MouseEvent mouseEvent) {
        String oldUsername= User.loggedInUser.getUsername();
        String oldPassword= User.loggedInUser.getPassword();
        String oldEmail= User.loggedInUser.getEmail();
        String oldNickname= User.loggedInUser.getNickname();
        String newUsername = NewUsername.getText();
        String newEmail = NewEmail.getText();
        String newPassword = NewPassword.getText();
        String newNickname = NewNickname.getText();
        if(!newUsername.isEmpty()&&
                (!newUsername.matches(RegisterMenuController.USERNAME_REGEX)||newUsername.equals(oldUsername))){
            ErrorText.setText("Invalid UserName");
            return;
        }

        if(!newEmail.isEmpty()&&
                (!newEmail.matches(RegisterMenuController.EMAIL_REGEX)||newEmail.equals(oldEmail))){
            ErrorText.setText("Invalid Email");
            return;
        }

        if(!newPassword.isEmpty()&&
                (!newPassword.matches(RegisterMenuController.PASSWORD_REGEX)||newPassword.equals(oldPassword)||!oldPassword.equals(OldPassword.getText()))){
            ErrorText.setText("Invalid Password");
            return;
        }
        if(!newNickname.isEmpty()&& newNickname.equals(oldNickname)){
            ErrorText.setText("Invalid NickName");
            return;
        }
        if(!newUsername.isEmpty()){
            User.loggedInUser.setUsername(newUsername);
        }
        if(!newNickname.isEmpty()){
            User.loggedInUser.setNickname(newNickname);
        }
        if(!newEmail.isEmpty()){
            User.loggedInUser.setEmail(newEmail);
        }
        if(!newPassword.isEmpty()){
            User.loggedInUser.setPassword(newPassword);
        }
        ErrorText.setText("Changes saved");
    }

    public void showUserInfo(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setHeaderText("User Information : 👤");
        String userInfo = PreGame.showInformationCurrentUser();
        alert.setContentText(userInfo);
        alert.showAndWait();
    }

    public void showGameHistory(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setHeaderText("Game History : ");
        String gameHistory = null;
//        gameHistory =        //todo
        alert.setContentText(gameHistory);
        alert.showAndWait();

    }
    //  TODO: Add back to main menu button
}
