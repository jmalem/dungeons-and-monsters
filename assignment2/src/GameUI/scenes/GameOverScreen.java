package GameUI.scenes;

import Game.GameLevel;
import Game.GameSystem;
import GameUI.controller.GameOverController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class GameOverScreen {
    private Stage stage;
    private String title;
    private FXMLLoader fxmlLoader;

    public GameOverScreen(Stage s){
        this.stage = s;
        this.title = "Game Over";
        this.fxmlLoader = new FXMLLoader(getClass().getResource("gameOver.fxml"));
    }

    public void start(File levelFile, GameLevel level, GameSystem gameSystem){
        stage.setTitle(title);
        fxmlLoader.setController(new GameOverController(stage, levelFile, level, gameSystem));
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
