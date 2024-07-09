package controller.menu.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import view.menus.*;

public class PreGameController {
    public User opponent;

    public void createNewGame(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Create new game🎮");
        alert.setHeaderText("Please enter the opponent's username here.👇");
        TextField textField = new TextField();
        textField.setPromptText("Opponent's username");
        alert.getDialogPane().setContent(new VBox(10, textField));
        alert.showAndWait();
        if (alert.getResult().getButtonData().isDefaultButton()) {
            String usernameOfOpponent = textField.getText();
            if (!User.usernameExists(usernameOfOpponent)) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("invalid input!");
                alert1.setContentText("User with this username was not found");
                alert1.showAndWait();
            } else if (User.loggedInUser.getUsername().equals(usernameOfOpponent)) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("invalid input!");
                alert1.setContentText("The username you entered is your own.🙅‍♀️");
                alert1.showAndWait();
            } else {
                opponent = User.getUserByUsername(usernameOfOpponent);
                Alert alert1 = new Alert(Alert.AlertType.NONE);
                alert1.setTitle("new Game🎴");
                alert1.setContentText("new game created.");
                alert1.getDialogPane().getButtonTypes().add(ButtonType.OK);
                alert1.showAndWait();
                PreGameMenu.appStage.close();
                PreGameMenu.appStage = null;
                new GameEnvironmentMenu(opponent).start(new Stage());
            }
        } else {
            alert.close();
        }
    }

    public void ArrangeDeckCards(MouseEvent mouseEvent) throws Exception {
        new ArrangeDeck().start(PreGameMenu.appStage);
        PreGameMenu.appStage = null;

    }

    public void goToMainMenu(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(PreGameMenu.appStage);
        PreGameMenu.appStage = null;
    }
}
