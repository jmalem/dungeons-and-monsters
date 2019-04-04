package GameUI.controller;

import Game.GameLevel;
import Game.GameSystem;
import GameUI.scenes.DesignerScreen;
import GameUI.scenes.GameLevelScreen;
import GameUI.scenes.HowToPlayScreen;
import GameUI.scenes.LevelMenuScreen;
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

public class MainMenuController implements Controller{
    @FXML
    private GridPane mainStage;
    private VBox mainLayout;
    private VBox buttonList;
    private Label gameTitle;
    private Button playButton;
    private Button designButton;
    private Button tutorialButton;
    
    private Stage stage;
    private GameSystem gameSystem;

    public MainMenuController(Stage s, GameSystem system){
        this.stage = s;
        this.gameSystem = system;
        this.mainLayout = new VBox();
        this.buttonList = new VBox();
        this.gameTitle = new Label("Dungeon Cantooth");
        this.playButton = new Button("Play Game");
        this.designButton = new Button("Design Mode");
        this.tutorialButton = new Button("How to Play");
    }
    @FXML
    public void initialize(){
        setViewProps();
        setButtonHandlers(null);
        populateViews();
    }

    @Override
    public void setViewProps(){
        // Centering the elements in the grid
        mainStage.setAlignment(Pos.CENTER);
        GridPane.setHalignment(mainLayout, HPos.CENTER);
        mainLayout.setAlignment(Pos.CENTER);
        buttonList.setAlignment(Pos.CENTER);

        // Adding buttons to the nested VBox for the button list
        playButton.setPrefSize(150, 40);
        designButton.setPrefSize(150, 40);
        tutorialButton.setPrefSize(150, 40);
        buttonList.setSpacing(15);

        gameTitle.setFont(new Font("Arial", 50));
        mainLayout.setSpacing(60);
    }

    @Override
    public void setButtonHandlers(GameLevel game){
        // Setting action handlers for the buttons
        playButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                handlePlayButton();
            }
        });

        designButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleDesignButton();
            }
        });
        
        tutorialButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleTutorialButton();
            }
        });
    }

    @Override
    public void populateViews(){
        buttonList.getChildren().add(playButton);
        buttonList.getChildren().add(designButton);
        buttonList.getChildren().add(tutorialButton);
        // Adding title and buttons to the main display layout
        mainLayout.getChildren().add(gameTitle);
        mainLayout.getChildren().add(buttonList);

        mainStage.add(mainLayout, 0, 1, 3, 1);
    }

    public void handlePlayButton(){
        LevelMenuScreen levelMenuScreen = new LevelMenuScreen(stage);
        levelMenuScreen.start(gameSystem);
    }

    public void handleDesignButton(){
        DesignerScreen designerScreen = new DesignerScreen(stage);
        designerScreen.start(gameSystem);
    }

    public void handleTutorialButton() {
    	HowToPlayScreen howToPlayScreen = new HowToPlayScreen(stage);
    	howToPlayScreen.start();
    }
}