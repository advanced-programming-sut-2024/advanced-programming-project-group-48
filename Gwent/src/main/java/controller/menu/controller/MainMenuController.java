package controller.menu.controller;

import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import view.menus.MainMenu;
import view.menus.PreGameMenu;
import view.menus.ProfileMenu;
import view.menus.RegisterMenu;

public class MainMenuController {
    public void changeMenuToProfileMenu() throws Exception {
        if(MainMenu.appStage != null){
            new ProfileMenu().start(MainMenu.appStage);
            MainMenu.appStage = null;
        }

    }

    public void changeMenuToPreGame(MouseEvent mouseEvent) throws Exception {
        MainMenu.appStage.close();
        new PreGameMenu().start(new Stage());
    }

    public void changeMenuToRegisterMenu(MouseEvent mouseEvent) throws Exception {
        if(User.loggedInUser != null && MainMenu.appStage != null){
            User.loggedInUser = null;
            MainMenu.appStage.close();
            new RegisterMenu().start(new Stage());
        }

        //This may need some changes because we are making a long callstack
        //We can just change the scene of the current stage
    }

    public void scoreTable(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Score table:");
        alert.setContentText(scoreTableString());
        alert.showAndWait();
    }

    public String scoreTableString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (User i : User.getUsersBaseRanking()) {
            stringBuilder.append(i.getUsername()).append("\n").append("---------------");
        }
        return stringBuilder.toString();
    }
}
