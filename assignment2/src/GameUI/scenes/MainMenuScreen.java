package GameUI.scenes;

import Game.GameSystem;
import GameUI.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuScreen {
    private Stage stage;
    private String title;
    private FXMLLoader fxmlLoader;
    private GameSystem gameSystem;

    public MainMenuScreen(Stage s){
        this.stage = s;
        this.title = "Main Menu";
        this.fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        this.gameSystem = new GameSystem();
    }

    /**
     * Copy constructor for when a game system has already been initialised
     * @param s JavaFX Stage to display contents
     * @param system Pre-existing Game System so existing levels are preserved
     */
    public MainMenuScreen(Stage s, GameSystem system){
        this.stage = s;
        this.title = "Main Menu";
        this.fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        this.gameSystem = system;
    }

    public void start(){
        // Setting controller and fxml for screen
        stage.setTitle(title);
        fxmlLoader.setController(new MainMenuController(stage, gameSystem));

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
