package controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import view.MainMenu;
import view.RegisterMenu;

public class MainMenuController {
    public void changeMenuToProfileMenu(MouseEvent mouseEvent) {
    }

    public void changeMenuToPreGame(MouseEvent mouseEvent) {

    }

    public void changeMenuToRegisterMenu(MouseEvent mouseEvent) throws Exception {
        User.loggedInUser=null;
        MainMenu.appStage.close();
        new RegisterMenu().start(new Stage());
        //This may need some changes because we are making a long callstack
        //We can just change the scene of the current stage
    }
}
