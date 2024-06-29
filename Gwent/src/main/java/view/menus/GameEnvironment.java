package view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Deck;

import java.util.Objects;

public class GameEnvironment extends Application {
    Deck deck;
    public static Stage appStage;
    @Override
    public void start(Stage stage) throws Exception {
        appStage=stage;
        appStage.setResizable(false);
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/GameEnvironment.fxml")));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
