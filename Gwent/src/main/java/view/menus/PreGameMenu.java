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
//        pane.setBackground(new Background(createBackgroundImage( pane.getWidth(), pane.getHeight())));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private BackgroundImage createBackgroundImage(double width , double height) {
        Image image = new Image(PreGameMenu.class.getResource("/images/BackGrounds/OIG1.mtRXIGDdZq_IdGR.jpg").toExternalForm() ,500 , 400, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }
}
