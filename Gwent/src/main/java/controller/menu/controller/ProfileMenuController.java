package controller.menu.controller;

import controller.PreGame;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;

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
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("username: ").append(User.loggedInUser.getUsername()).append("\n")
                .append("nickname: ").append(User.loggedInUser.getNickname()).append("\n")
                .append("maxScore: ").append(User.loggedInUser.getMaxScore()).append("\n")
                .append("ranking: ").append(User.loggedInUser.getUsersBaseRanking().indexOf(User.loggedInUser)+1).append("\n")
                .append("number of games played: ").append(User.loggedInUser.getNumberOfTotalGames()).append("\n")
                .append("number of draws: ").append(User.loggedInUser.getNumDraws()).append("\n")
                .append("number of wins: ").append(User.loggedInUser.getNumWins()).append("\n")
                .append("number of loses: ").append(User.loggedInUser.getNumLoses());
        alert.setContentText(userInfo.toString());
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    public void showGameHistory(MouseEvent mouseEvent) {
        String numberOfGameHistory = NumberOfGameHistory.toString();
        if(!numberOfGameHistory.matches("[0-9]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("invalid input!!");
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);

        }
        else{
            int numberOfGameHistoris = Integer.parseInt(numberOfGameHistory);
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setHeaderText("Game History : ");
            StringBuilder gameHistory = null;
            for(int i = 0; i < numberOfGameHistoris; i++){
                gameHistory.append("opponent username: ").append(User.loggedInUser.getAllGameHistories().get(i).getOpponentUsername()).append("\n")
                        .append("date of game: ").append(User.loggedInUser.getAllGameHistories().get(i).getDateOfGame()).append("\n")
                        .append("score in roun 1️⃣: ").append("\n").append("score of you: ").append(User.loggedInUser.getAllGameHistories().get(i).getUserScorePerRound().toString()).append("\n")
                        .append("score of your opponent: ").append(User.loggedInUser.getAllGameHistories().get(i).getOpponentScoresPerRound().toString()).append("\n")
                        .append("score of you in this game: ").append(User.loggedInUser.getAllGameHistories().get(i).getTotalScoreOfOpponent()).append("\n")
                        .append("score of your your opponent: ").append(User.loggedInUser.getAllGameHistories().get(i).getTotalScoreOfOpponent()).append("\n")
                        .append("winner🏆🏆: ").append(User.loggedInUser.getAllGameHistories().get(i).getUsernameOfWinner());
        }
            alert.setContentText(gameHistory.toString());
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            alert.showAndWait();

        }



    }
    //  TODO: Add back to main menu button
}
