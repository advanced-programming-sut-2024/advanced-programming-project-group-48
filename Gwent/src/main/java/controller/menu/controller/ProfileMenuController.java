package controller.menu.controller;

import client.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import view.menus.MainMenu;
import view.menus.ProfileMenu;


public class ProfileMenuController {
    public Label ErrorText;
    public TextField NewUsername;
    public TextField NewNickname;
    public TextField NewEmail;
    public TextField OldPassword;
    public TextField NewPassword;
    public TextField NumberOfGameHistory;

    public void saveChanges(MouseEvent mouseEvent) {
        String newUsername = NewUsername.getText();
        String newEmail = NewEmail.getText();
        String newPassword = NewPassword.getText();
        String newNickname = NewNickname.getText();
        String oldPassword = OldPassword.getText();
        if (!newUsername.isEmpty() &&
                (!newUsername.matches(RegisterMenuController.USERNAME_REGEX))) {
            ErrorText.setText("Invalid UserName");
            return;
        }

        if (!newEmail.isEmpty() &&
                (!newEmail.matches(RegisterMenuController.EMAIL_REGEX))) {
            ErrorText.setText("Invalid Email");
            return;
        }

        if (!newPassword.isEmpty() &&
                (!newPassword.matches(RegisterMenuController.PASSWORD_REGEX))) {
            ErrorText.setText("Invalid Password");
            return;
        }
        sendSaveChangesReq(newUsername, newEmail, newPassword, newNickname, oldPassword);
        String response = Client.currentClient.receiveResponse();
        ErrorText.setText(response);
    }

    private void sendSaveChangesReq(String newUsername, String newEmail, String newPassword, String newNickname, String oldPassword) {
        Client.currentClient.sendMessage("saveProfileMenuChanges:" + newUsername + ":" + newEmail + ":" + newNickname + ":" + newPassword+":" + oldPassword);
    }

    public void showUserInfo() {
        sendShowUserInfoReq();
        String response = Client.currentClient.receiveResponse();
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setHeaderText("User Information : 👤");
        alert.setContentText(response);
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    private void sendShowUserInfoReq() {
        Client.currentClient.sendMessage("showUserInfo");
    }

    public void showGameHistory() {
        String numberOfGameHistory = NumberOfGameHistory.getText();
        if (!numberOfGameHistory.matches("[0-9]+")) {
            ErrorText.setText("invalid number gameHistory");
            return;
        }
        int numberOfGameHistories = Integer.parseInt(numberOfGameHistory);
        sendShowGameHistoryReq(numberOfGameHistory);
        String response = Client.currentClient.receiveResponse();
        if(response == null || response.equals("No game has played")){
            ErrorText.setText(response == null ? "Error receiving response" : "No game has played");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Game History : ");
        alert.setContentText(response);
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();

    }

    private void sendShowGameHistoryReq(String numberOfGameHistory) {
        Client.currentClient.sendMessage("showGameHistory:" + numberOfGameHistory);
    }

    public void changeMenuToMain() throws Exception {
        new MainMenu().start(ProfileMenu.appStage);
        ProfileMenu.appStage = null;
    }


}
