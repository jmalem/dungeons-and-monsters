package GameUI.scenes;

import Game.GameLevel;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public abstract class Screen {
    private Stage stage;
    private String title;
    private FXMLLoader fxmlLoader;

    public Screen(Stage s, String title, String fxmlFileName){
        this.stage = s;
        this.title = title;
        this.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
    }

    public abstract void start(GameLevel level);
}
