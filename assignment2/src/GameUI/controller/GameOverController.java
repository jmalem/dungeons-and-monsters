package GameUI.controller;

import Game.GameLevel;
import Game.GameStatus;
import Game.GameSystem;
import GameUI.scenes.GameLevelScreen;
import GameUI.scenes.LevelMenuScreen;
import GameUI.scenes.MainMenuScreen;
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
import java.io.File;

public class GameOverController implements Controller {
    // Stage and View Elements
    @FXML
    private GridPane displayStage;
    private VBox results;
    private VBox buttonList;
    private Label gameResult;
    private Button restartButton;
    private Button selectButton;
    private Button quitButton;

    // Backend variables
    private Stage stage;
    private GameLevel level;
    private GameSystem gameSystem;
    private File levelFile;

    public GameOverController(Stage s, File levelFile, GameLevel level, GameSystem system) {
        this.stage = s;
        this.level = level;
        this.gameSystem = system;
        this.levelFile = levelFile;

        // Initialising UI elements
        this.results = new VBox();
        this.buttonList = new VBox();
        this.gameResult = new Label(getResults());
        this.restartButton = new Button("Replay Level");
        this.selectButton = new Button("Back to Level Select");
        this.quitButton = new Button("Back to Main Menu");
    }

    @FXML
    public void initialize(){
        setViewProps();
        setButtonHandlers(level);
        populateViews();
    }

    /**
     * Outputs the results label for the level
     * @return Level Results Outcome as a String
     */
    public String getResults(){
        return (level.getGameStatus() == GameStatus.LOSE) ? "Game Over" : "Level Completed";
    }

    /**
     * Settings for the Properties of the JavaFX Elements
     */
    @Override
    public void setViewProps(){
        // Setting alignment of layouts
        displayStage.setAlignment(Pos.CENTER);
        GridPane.setHalignment(results, HPos.CENTER);
        results.setAlignment(Pos.CENTER);
        buttonList.setAlignment(Pos.CENTER);

        //Setting Spacing
        results.setSpacing(60);
        buttonList.setSpacing(15);

        // Changing look of JavaFX elements
        gameResult.setFont(new Font("Arial", 50));
        restartButton.setPrefSize(150, 40);
        selectButton.setPrefSize(150, 40);
        quitButton.setPrefSize(150, 40);
    }

    /**
     * Add Event Handlers to buttons in the view
     */
    @Override
    public void setButtonHandlers(GameLevel game){
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Restarting Game");
                GameLevelScreen gameLevelScreen = new GameLevelScreen(stage);
                gameLevelScreen.start(levelFile, gameSystem);
            }
        });

        selectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LevelMenuScreen levelMenuScreen = new LevelMenuScreen(stage);
                levelMenuScreen.start(gameSystem);
            }
        });

        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainMenuScreen mainMenuScreen = new MainMenuScreen(stage);
                mainMenuScreen.start();
            }
        });
    }

    /**
     * Adds elements to the JavaFX Containers
     */
    @Override
    public void populateViews(){
        // Adding elements to the layout and stage
        buttonList.getChildren().add(restartButton);
        buttonList.getChildren().add(selectButton);
        buttonList.getChildren().add(quitButton);
        results.getChildren().add(gameResult);
        results.getChildren().add(buttonList);
        displayStage.add(results, 0, 1, 3, 1);
    }
}
