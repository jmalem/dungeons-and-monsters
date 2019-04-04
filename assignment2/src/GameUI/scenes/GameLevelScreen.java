package GameUI.scenes;

import Game.Direction;
import Game.GameLevel;
import Game.GameSystem;
import GameUI.controller.GameController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GameLevelScreen {
    private Stage stage;
    private String title;
    private FXMLLoader fxmlLoader;

    public GameLevelScreen(Stage stage) {
        this.stage = stage;
        this.title = "Dungeon Cantooth";
        this.fxmlLoader = new FXMLLoader(getClass().getResource("level.fxml"));
    }

    public void start(File levelFile, GameSystem gameSystem){
        stage.setTitle(title);
        // Attach controller to the scene
        fxmlLoader.setController(new GameController(stage, levelFile, gameSystem));

        try {
            // Load, Set and Show the Game Scene
            Parent root = fxmlLoader.load();
            Scene levelScene = new Scene(root, 1000, 800);
            stage.setScene(levelScene);
            levelScene.setOnKeyPressed(
            		new EventHandler<KeyEvent>() {
            			public void handle (KeyEvent e) {
            				String code = e.getCode().toString();
            			}
            		}
    		);
            levelScene.setOnKeyReleased(
            		new EventHandler<KeyEvent>() {
            			public void handle (KeyEvent e) {
            				String code = e.getCode().toString();

            				GameController g = fxmlLoader.getController();
   				
            				if(code.equals("LEFT")) {
            					g.handle(Direction.LEFT);
            				} else if(code.equals("RIGHT")) {
            					g.handle(Direction.RIGHT);
            				} else if(code.equals("UP")) {
            					g.handle(Direction.UP);
            				} else if(code.equals("DOWN")) {
            					g.handle(Direction.DOWN);
            				} else if(code.equals("A")) {
            					g.handle(Direction.ARROWLEFT);
            				} else if(code.equals("D")) {
            					g.handle(Direction.ARROWRIGHT);
            				} else if(code.equals("W")) {
            					g.handle(Direction.ARROWUP);
            				} else if(code.equals("S")) {
            					g.handle(Direction.ARROWDOWN);
            				} else if(code.equals("B")) {
            					g.handle(Direction.PLACEBOMB);
            				}
            			}
            		}
    		);
            
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
