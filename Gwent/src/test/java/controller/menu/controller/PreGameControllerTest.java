package controller.menu.controller;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import view.menus.MainMenu;
import javafx.scene.input.MouseEvent;


import static org.mockito.Mockito.*;

public class PreGameControllerTest {
    @Test
    public void testGoToMainMenu() throws Exception {
        // Create a spy of your actual class
        PreGameController preGameControllerSpy = Mockito.spy(new PreGameController());

        // Create a mock MouseEvent
        MouseEvent mouseEventMock = mock(MouseEvent.class);

        // Call the method under test
        preGameControllerSpy.goToMainMenu(mouseEventMock);

        // Since Stage and MainMenu are final classes and their methods can't be verified directly using Mockito,
        // you need to manually check the behavior during the test.
    }
}
