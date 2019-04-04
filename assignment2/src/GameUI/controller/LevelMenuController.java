package GameUI.controller;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import Enemies.*;
import Game.*;
import GameUI.scenes.DesignerScreen;
import GameUI.scenes.GameLevelScreen;
import GameUI.scenes.MainMenuScreen;
import Items.*;
import Obstacles.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LevelMenuController implements Controller {
    @FXML
    private GridPane levelStage;
    private VBox mainLayout;
    private VBox levelList;
    private Button backButton;

    private Stage stage;
    private GameSystem gameSystem;

    public LevelMenuController(Stage s, GameSystem system){
        this.stage = s;
        this.gameSystem = system;
        this.mainLayout = new VBox();
        this.levelList = new VBox();
        this.backButton = new Button("Back to Main Menu");
    }

    @FXML
    public void initialize() {
        // Loads files from .txt files stored in levels directory
        File levelDir = new File("levels");
        System.out.println(levelDir.getAbsolutePath());
        File[] levels = levelDir.listFiles();
        if (levels != null){
            // Adding buttons to the nested VBox for the button list
            for (File f : levels){
                // Adds a new Button for each Level
                String levelLabel = f.getName().replace(".txt", "");
                Button newLevelButton = new Button("Level " + levelLabel);
                newLevelButton.setPrefSize(150, 40);
                newLevelButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        handlePlayButton(f);
                    }
                });
                levelList.getChildren().add(newLevelButton);
            }
        }
        // Setting up layouts and controls
        setViewProps();
        populateViews();
        setButtonHandlers(null);
    }

    @Override
    public void setViewProps(){
        // Centering the elements in the grid
        levelStage.setAlignment(Pos.CENTER);
        GridPane.setHalignment(mainLayout, HPos.CENTER);
        mainLayout.setAlignment(Pos.CENTER);
        levelList.setAlignment(Pos.CENTER);
        levelList.setSpacing(15);
        backButton.setPrefSize(150, 40);
        mainLayout.setSpacing(60);
    }

    @Override
    public void setButtonHandlers(GameLevel game){
        // Setting action handlers for the buttons
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleBackButton();
            }
        });
    }

    @Override
    public void populateViews(){
        // Adding title and buttons to the main display layout
        mainLayout.getChildren().add(levelList);
        mainLayout.getChildren().add(backButton);
        levelStage.add(mainLayout, 1, 0, 1, 3);
    }

    public void handleBackButton(){
        MainMenuScreen mainMenu = new MainMenuScreen(stage, gameSystem);
        mainMenu.start();
    }

    public void handlePlayButton(File levelFile){
        GameLevelScreen gameLevelScreen = new GameLevelScreen(stage);
        gameLevelScreen.start(levelFile, gameSystem);
    }
}
