package controller.menu.controller;

import controller.PreGame;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.GameHistory;
import model.User;
import view.menus.MainMenu;
import view.menus.ProfileMenu;

import java.util.Arrays;
import java.util.Date;

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

    public void showUserInfo() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setHeaderText("User Information : 👤");
        StringBuilder userInfo = getStringBuilder();
        alert.setContentText(userInfo.toString());
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    private static StringBuilder getStringBuilder() {
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("username: ").append(User.loggedInUser.getUsername()).append("\n")
                .append("nickname: ").append(User.loggedInUser.getNickname()).append("\n")
                .append("maxScore: ").append(User.loggedInUser.getMaxScore()).append("\n")
                .append("ranking: ").append(User.loggedInUser.getUsersBaseRanking().indexOf(User.loggedInUser)+1).append("\n")
                .append("number of games played: ").append(User.loggedInUser.getNumberOfTotalGames()).append("\n")
                .append("number of draws: ").append(User.loggedInUser.getNumDraws()).append("\n")
                .append("number of wins: ").append(User.loggedInUser.getNumWins()).append("\n")
                .append("number of loses: ").append(User.loggedInUser.getNumLoses());
        return userInfo;
    }

    public void showGameHistory() {
        String numberOfGameHistory = NumberOfGameHistory.getText();
        if(!numberOfGameHistory.matches("[0-9]+")){
            ErrorText.setText("invalid number gameHistory");
            return;
        }
        int numberOfGameHistories = Integer.parseInt(numberOfGameHistory);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setHeaderText("Game History : ");
        StringBuilder gameHistory = new StringBuilder();
        if(User.loggedInUser.getAllGameHistories().isEmpty()){
            ErrorText.setText("No game has played");
            return;
        }
        if(User.loggedInUser.getAllGameHistories().size()<numberOfGameHistories){
            numberOfGameHistories=User.loggedInUser.getAllGameHistories().size();
        }
        for(int i =User.loggedInUser.getAllGameHistories().size()-1;i>User.loggedInUser.getAllGameHistories().size()-1-numberOfGameHistories ;i--){
            gameHistory.append("opponent username: ").append(User.loggedInUser.getAllGameHistories().get(i).getOpponentUsername()).append("\n")
                    .append("date of game: ").append(User.loggedInUser.getAllGameHistories().get(i).getDateOfGame().toString()).append("\n")
                    .append("your score per round : ").append(Arrays.toString(User.loggedInUser.getAllGameHistories().get(i).getUserScorePerRound())).append("\n")
                    .append("opponent score per round : ").append(Arrays.toString(User.loggedInUser.getAllGameHistories().get(i).getOpponentScoresPerRound())).append("\n")
                    .append("total score of you in this game : ").append(User.loggedInUser.getAllGameHistories().get(i).getTotalScoreOfUser()).append("\n")
                    .append("total score of your opponent: ").append(User.loggedInUser.getAllGameHistories().get(i).getTotalScoreOfOpponent()).append("\n")
                    .append("winner🏆🏆: ").append(User.loggedInUser.getAllGameHistories().get(i).getUsernameOfWinner()).append("\n");
        }
        alert.setContentText(gameHistory.toString());
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();

    }

    public void changeMenuToMain() throws Exception {
        new MainMenu().start(ProfileMenu.appStage);
        ProfileMenu.appStage=null;
    }

}
