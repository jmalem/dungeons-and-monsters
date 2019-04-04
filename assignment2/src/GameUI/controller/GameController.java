package GameUI.controller;

import GameUI.scenes.GameOverScreen;
import GameUI.scenes.LevelMenuScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.awt.Point;
import java.io.File;
import Game.*;

public class GameController implements Controller {
    public static final int LEVEL_SIZE = 15;

    // FXML Attributes
    @FXML
    private GridPane levelGrid;
    @FXML
    private AnchorPane levelStage;
    @FXML
    private Label statusLabel;
    @FXML
    private AnchorPane statStage;
    @FXML
    private TextField text1;
    @FXML
    private Button backButton;
    
    // System class Attributes
    private GameLevel game;
    private Stage stage;
    private ImageLoader imageData;
    private GameSystem gameSystem;
    private File levelFile;

    public GameController(Stage stage, File levelFile, GameSystem system){
        this.stage = stage;
        // Initiate the game level
        this.levelFile = levelFile;
        this.game = system.loadLevel(levelFile);
        this.imageData = new ImageLoader();
        this.gameSystem = system;
    }

    @FXML
    public void initialize(){
        setViewProps();
        updateGrid();
        setButtonHandlers(null);
    }

    /**
     * Sets the properties of the elements of the view
     */
    @Override
    public void setViewProps(){
        // Setting the sizes of the Game Window and Game Level prior to game start
        statStage.setMaxSize(800, 200);
        statStage.setMinSize(800, 200);
        
        levelStage.setMaxSize(560, 560);
        levelStage.setMinSize(560, 560);
        levelGrid.setMinSize(560,560);
        levelGrid.setMaxSize(560,560);
        //levelGrid.setAlignment(Pos.CENTER);
    }

    /**
     * Set up the back button handler
     */
    @Override
    public void setButtonHandlers(GameLevel game){
        // Setting action handlers for the buttons
        backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				handleBackButton();
			}
        });
    }

    @Override
    public void populateViews() { }

    /**
     * Handle the back button,
     * Switch back to level menu screen
     */
    public void handleBackButton() {
    	LevelMenuScreen levelMenuScreen = new LevelMenuScreen(stage);
        levelMenuScreen.start(gameSystem);
        
    }
    /**
     * Handles the input provided by the user
     * @param d Directional input provided by user
     */
    public void handle(Direction d) {
    	game.updateGameState(d);
    	if (game.isComplete()){
    	    // Game has finished - handle later
            GameOverScreen gameOverScreen = new GameOverScreen(stage);
            gameOverScreen.start(levelFile, game, gameSystem);
        }
    	updateGrid();
    }

    /**
     * Loads the front end of the entire game state
     */
    public void updateGrid() {
        // Setup the floor of the map
        for (int i = 0; i < LEVEL_SIZE; i++){
            for (int j = 0; j < LEVEL_SIZE; j++){
                ImageView imageView = new ImageView(imageData.getImage("Floor"));
                levelGrid.add(imageView, i, j);
                GridPane.setHalignment(imageView, HPos.CENTER);
            }
        }

        // Places Entities on the map
        for (Entity e : game.getEntitites()){
            Point p = e.getPosition();
            ImageView imageView = new ImageView(imageData.getImage(e.getImage()));
            levelGrid.add(imageView, p.y, p.x);
            GridPane.setHalignment(imageView, HPos.CENTER);
        }

        // Places the hero on the map
        Hero h = game.getHero();
        ImageView hero = new ImageView(imageData.getImage("Hero"));
        levelGrid.add(hero , h.getPosition().y, h.getPosition().x);
        GridPane.setHalignment(hero, HPos.CENTER);
        GridPane.setValignment(hero, VPos.CENTER);
        statusLabel.setText(game.printHeroStats(h));
    }
}
