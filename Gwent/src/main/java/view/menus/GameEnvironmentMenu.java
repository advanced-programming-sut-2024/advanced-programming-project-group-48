package view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.util.Objects;

public class GameEnvironmentMenu extends Application {
    public static Stage appStage;
//    public final User opponentUser;

    public static GameEnvironmentMenu currentGame;

    @Override
    public void start(Stage stage) throws Exception {
        appStage=stage;
        appStage.setResizable(false);
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/GameEnvironment.fxml")));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
//    public GameEnvironmentMenu(User opponentUser){
//        this.opponentUser=opponentUser;
//        currentGame=this;
//    }
}
