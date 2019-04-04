package GameUI;

import Game.GameLevel;
import GameUI.scenes.GameLevelScreen;
import GameUI.scenes.MainMenuScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setWidth(1000);
        primaryStage.setHeight(800);

        MainMenuScreen mainMenu = new MainMenuScreen(primaryStage);
        mainMenu.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
