package controller.menu.controller;

import client.Client;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.menus.*;

public class MainMenuController {
    public void changeMenuToProfileMenu() throws Exception {
        new ProfileMenu().start(MainMenu.appStage);
        MainMenu.appStage=null;
    }

    public void changeMenuToPreGame(MouseEvent mouseEvent) throws Exception {
        new PreGameMenu().start(MainMenu.appStage);
        MainMenu.appStage=null;
    }

    public void changeMenuToRegisterMenu(MouseEvent mouseEvent) throws Exception {
        sendLogoutReq();
        String response = Client.currentClient.receiveResponse();
        if(response.equals("logout successful")) {
            MainMenu.appStage.close();
            new RegisterMenu().start(new Stage());
        } else {
            System.err.println(response);
        }
    }

    private void sendLogoutReq() {
        Client.currentClient.sendMessage("logout");
    }

    public void changeMenuToFriendsMenu(MouseEvent mouseEvent) throws Exception {
        new FriendsMenu().start(MainMenu.appStage);
        MainMenu.appStage=null;
    }

    public void showScoreBoard(MouseEvent mouseEvent) {
        sendShowScoreBoardReq();
        String response = Client.currentClient.receiveResponse();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Score Board");
        alert.setHeaderText("Score Board");
        alert.setContentText(response);
        alert.showAndWait();
    }

    private void sendShowScoreBoardReq() {
        Client.currentClient.sendMessage("showScoreBoard");
    }
}
