package controller.menu.controller;

import client.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;
import view.menus.MainMenu;
import view.menus.ProfileMenu;

import java.util.Arrays;

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
                (!newUsername.matches(RegisterMenuController.USERNAME_REGEX) /*|| newUsername.equals(oldUsername)*/)) {
            ErrorText.setText("Invalid UserName");
            return;
        }

        if (!newEmail.isEmpty() &&
                (!newEmail.matches(RegisterMenuController.EMAIL_REGEX) /*|| newEmail.equals(oldEmail)*/)) {
            ErrorText.setText("Invalid Email");
            return;
        }

        if (!newPassword.isEmpty() &&
                (!newPassword.matches(RegisterMenuController.PASSWORD_REGEX) /*|| newPassword.equals(oldPassword) || !oldPassword.equals(OldPassword.getText())*/)) {
            ErrorText.setText("Invalid Password");
            return;
        }
//        if (!newNickname.isEmpty() && newNickname.equals(oldNickname)) {
//            ErrorText.setText("Invalid NickName");
//            return;
//        }
//        if (!newUsername.isEmpty()) {
//            User.loggedInUser.setUsername(newUsername);
//        }
//        if (!newNickname.isEmpty()) {
//            User.loggedInUser.setNickname(newNickname);
//        }
//        if (!newEmail.isEmpty()) {
//            User.loggedInUser.setEmail(newEmail);
//        }
//        if (!newPassword.isEmpty()) {
//            User.loggedInUser.setPassword(newPassword);
//        }
//        ErrorText.setText("Changes saved");
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

    public void showGameHistory() {
        String numberOfGameHistory = NumberOfGameHistory.getText();
        if (!numberOfGameHistory.matches("[0-9]+")) {
            ErrorText.setText("invalid number gameHistory");
            return;
        }
        int numberOfGameHistories = Integer.parseInt(numberOfGameHistory);
        sendShowGameHistoryReq(numberOfGameHistory);
        String response = Client.currentClient.receiveResponse();
        if(response.equals("No game has played")){
            ErrorText.setText("No game has played");
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

}
