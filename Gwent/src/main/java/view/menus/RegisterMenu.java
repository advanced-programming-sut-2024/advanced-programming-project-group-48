package view.menus;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class RegisterMenu extends Application {

    public static Stage appStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        appStage = stage;
        appStage.setResizable(false);
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/RegisterMenu.fxml")));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }
}
