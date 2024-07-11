package controller.menu.controller;

import org.junit.jupiter.api.Test;
import view.menus.RegisterMenu;
import javafx.application.Application;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Card;
import model.Deck;
import model.GameEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxToolkit;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.*;

import static controller.menu.controller.GameEnvironmentController.drawRandomCards;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterMenuTest {
    @Test
    void generateRandomPassword(){
        RegisterMenuController registerMenuController = new RegisterMenuController();
        registerMenuController.generateRandomPassword();
        assertEquals (registerMenuController.RegisterPassword , null);

    }
}
