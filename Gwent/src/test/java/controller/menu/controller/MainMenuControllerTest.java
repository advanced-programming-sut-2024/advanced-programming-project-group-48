package controller.menu.controller;

import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

public class MainMenuControllerTest {

    @Test
    public void testGoToProfileMenu() throws Exception{
        MainMenuController mainMenuController = new MainMenuController();
        MouseEvent mouseEvent = mock(MouseEvent.class);
        mainMenuController.changeMenuToProfileMenu();

    }

    @Test
    public void testGoToRegisterMenu() throws Exception {
        MainMenuController mainMenuController = new MainMenuController();
        MouseEvent mouseEvent = mock(MouseEvent.class);
        mainMenuController.changeMenuToRegisterMenu(mouseEvent);
    }

}
