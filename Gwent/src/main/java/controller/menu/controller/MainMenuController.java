package controller.menu.controller;

import client.Client;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.menus.MainMenu;
import view.menus.PreGameMenu;
import view.menus.ProfileMenu;
import view.menus.RegisterMenu;

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
}
