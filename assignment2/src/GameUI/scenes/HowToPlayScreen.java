package GameUI.scenes;

import Game.GameLevel;
import Game.GameSystem;
import GameUI.controller.GameOverController;
import GameUI.controller.HowToPlayController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class HowToPlayScreen {
    private Stage stage;
    private String title;
    private FXMLLoader fxmlLoader;
    private GameSystem gameSystem;

    public HowToPlayScreen(Stage s){
        this.stage = s;
        this.title = "How to Play";
        this.fxmlLoader = new FXMLLoader(getClass().getResource("howToPlay.fxml"));
    }

    public void start(){
        stage.setTitle(title);
        fxmlLoader.setController(new HowToPlayController(stage));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
