package GameUI.controller;

import Game.GameLevel;
import Game.GameStatus;
import Game.GameSystem;
import GameUI.scenes.GameLevelScreen;
import GameUI.scenes.HowToPlayScreen;
import GameUI.scenes.LevelMenuScreen;
import GameUI.scenes.MainMenuScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;

public class HowToPlayController {
    // Stage and View Elements
	@FXML
	private Button back;
	@FXML
	private GridPane grid;
    private String title;
    private Stage stage;
    public HowToPlayController(Stage s) {
        this.stage = s;
    }

    @FXML
    public void initialize(){
    	grid.setAlignment(Pos.CENTER);
    	Label l = new Label("HOW TO PLAY");
    	Label l1 = new Label("      MOVEMENT: \n\n      PLACING BOMB: \n\n      SHOOT ARROW: ");
    	Label l2 = new Label("ARROW KEYS\n\nPRESS \"B\"\n\nW A S D KEY");
    	l.setFont(Font.font(40));
    	l.setPrefSize(800, 60);
    	l1.setFont(Font.font(25));
    	l1.setPrefSize(800, 400);

    	l2.setFont(Font.font(25));
    	l2.setPrefSize(800, 400);
    	grid.add(l, 1, 0);
    	grid.add(l1, 0, 1);
    	grid.add(l2, 1, 1);
    	
    	back.setPrefSize(150, 40);
        setButtonHandlers();
   
    }
   
    
    public void setButtonHandlers(){
        // Setting action handlers for the buttons
        back.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                handleBackButton();
            }
        });
    }
    
   
    public void handleBackButton() {
    	MainMenuScreen main = new MainMenuScreen(stage);
    	main.start();
    }
  
}
