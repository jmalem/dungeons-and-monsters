package GameUI.scenes;

import java.util.ArrayList;

import Game.CompletionCondition;
import Game.GameLevel;
import Game.GameSystem;
import GameUI.controller.DesignerController;
import GameUI.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DesignerScreen {
    private Stage stage;
    private String title;
    private FXMLLoader fxmlLoader;

    public DesignerScreen(Stage s){
        this.stage = s;
        this.title = "Design Mode";
        this.fxmlLoader = new FXMLLoader(getClass().getResource("designer.fxml"));
    }

    public void start(GameSystem gameSystem){
        // Setting controller and fxml for screen
        stage.setTitle(title);
        fxmlLoader.setController(new DesignerController(stage, gameSystem));

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
