package view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;



public class PreGameMenu extends Application {
    public static Stage appStage;
    @Override
    public void start(Stage stage) throws Exception {
        appStage = stage;
        appStage.setResizable(false);
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/PreGame.fxml")));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Objects.requireNonNull(PreGameMenu.class.getResource("/images/BackGrounds/backGround.jpg")).toExternalForm() ,500 , 400, false, false);
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }
}
