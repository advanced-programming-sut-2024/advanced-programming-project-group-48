package controller.menu.controller;

import client.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.GameEnvironment;
import view.menus.*;

public class PreGameController {
    public Label ErrorText;

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
            sendCreateNewGameReq(usernameOfOpponent);
            String response = Client.currentClient.receiveResponse();

            if (response.equals("Game created successfully")) {
                Alert alert1 = new Alert(Alert.AlertType.NONE);
                alert1.setTitle("new Game🎴");
                alert1.setContentText("new game created.");
                alert1.getDialogPane().getButtonTypes().add(ButtonType.OK);
                alert1.showAndWait();
                Client.currentClient.sendMessage("getGameEnvironment");
                String gameEnvironmentJson = Client.currentClient.receiveResponse();
                GameEnvironment gameEnvironment = GameEnvironment.fromJson(gameEnvironmentJson);
                PreGameMenu.appStage.close();
                PreGameMenu.appStage = null;

                new GameEnvironmentMenu(gameEnvironment).start(new Stage());
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("invalid input!");
                alert1.setContentText(response);
                alert1.showAndWait();
            }

        } else {
            alert.close();
        }
    }

    private void sendCreateNewGameReq(String usernameOfOpponent) {
        Client.currentClient.sendMessage("createNewGame:" + usernameOfOpponent);
    }

    public void ArrangeDeckCards(MouseEvent mouseEvent) throws Exception {
        new ArrangeDeck().start(PreGameMenu.appStage);
        PreGameMenu.appStage = null;

    }

    public void goToMainMenu(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(PreGameMenu.appStage);
        PreGameMenu.appStage = null;
    }

    public void goToWaitingRoom(MouseEvent mouseEvent) throws Exception {
        ErrorText.setText("Wait till the opponent joins the game.");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Waiting Room");
        alert.setHeaderText("Waiting Room");
        alert.setContentText("Wait till the opponent joins the game.");
        alert.showAndWait();
        sendEnterWaitingRoomReq();
        String response = Client.currentClient.receiveResponse();
        if (response.equals("Game created successfully")) {
            ErrorText.setText("Opponent joined the game.");
            Client.currentClient.sendMessage("getGameEnvironment");
            String gameEnvironmentJson = Client.currentClient.receiveResponse();
            GameEnvironment gameEnvironment = GameEnvironment.fromJson(gameEnvironmentJson);
            PreGameMenu.appStage.close();
            PreGameMenu.appStage = null;
            new GameEnvironmentMenu(gameEnvironment).start(new Stage());
        } else {
            ErrorText.setText("Opponent didn't join the game.");
        }

    }

    private void sendEnterWaitingRoomReq() {
        Client.currentClient.sendMessage("enterWaitingRoom");
    }
}

