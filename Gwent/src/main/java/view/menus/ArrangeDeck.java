package view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.util.Objects;

public class ArrangeDeck extends Application {
    public static Stage appStage;
    @Override
    public void start(Stage stage) throws Exception {
        appStage = stage;
        appStage.setResizable(false);
        stage.centerOnScreen();
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url2 = getClass().getResource("/FXML/ArrangeDeck.fxml");
        assert url2 != null;
        Pane pane = fxmlLoader.load(url2.openStream());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
