package GameUI.scenes;

import Game.GameSystem;
import GameUI.controller.LevelMenuController;
import GameUI.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LevelMenuScreen {
    private Stage stage;
    private String title;
    private FXMLLoader fxmlLoader;

    public LevelMenuScreen(Stage s){
        this.stage = s;
        this.title = "Level Menu";
        this.fxmlLoader = new FXMLLoader(getClass().getResource("levelMenu.fxml"));
    }

    public void start(GameSystem gameSystem){
        // Setting controller and fxml for screen
        stage.setTitle(title);
        fxmlLoader.setController(new LevelMenuController(stage, gameSystem));

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