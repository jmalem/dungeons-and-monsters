package GameUI.controller;

import GameUI.scenes.LevelMenuScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import Game.*;

public class DesignerController implements Controller {
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
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button addConditionButton;
    @FXML
    private Button removeConditionButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button backButton;
    @FXML
    private ListView listView;
    @FXML
    private ListView listView1;
    @FXML
    private TextField text1;
    @FXML
    private TextField text2;

    // System class Attributes
    private GameSystem gameSystem;
    private Stage stage;
    private ImageLoader imageData;
    public static final ObservableList names = FXCollections.observableArrayList();
    public static final ObservableList winCon = FXCollections.observableArrayList();
    public static final int LEVEL_SIZE = 15;

    public DesignerController(Stage stage, GameSystem system){
        this.stage = stage;
        // Initiate the game level
        this.gameSystem = system;
        this.imageData = new ImageLoader();
    }

    @FXML
    public void initialize(){
        // Create a new Game Level Instance
        GameLevel game = new GameLevel(new ArrayList<>(), -1);
        // Adds walls to the game instance
        game.populateLevel(new ArrayList<>());

        // Add all entities to an observable Array List
        names.addAll(
                "Arrow", "Boulder", "Bomb", "Coward", "Door", "Exit",
                "Hero", "Hound", "Hover", "Hunter", "Invincible",
                "Key", "Pit", "Switch", "Sword", "Treasure", "Wall", "Witch"
        );
        // Add win conditions to observable Array List
        winCon.addAll("Switch", "Treasure", "Enemy");
        
        // Set the array list into the list view
        listView1.setItems(winCon);
        listView.setItems(names);

        setViewProps();
        setClickHandlers();
        setButtonHandlers(game);
        updateGrid(game);
    }

    @Override
    public void populateViews() {
    }

    /**
     * Loads the front end of the entire game state
     * @param game Game level instance being designed
     */
    public void updateGrid(GameLevel game) {
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
        levelGrid.add(hero, h.getPosition().y, h.getPosition().x);
        GridPane.setHalignment(hero, HPos.CENTER);
        GridPane.setValignment(hero, VPos.CENTER);
    }

    /**
     * Change properties of JavaFX Elements
     */
    @Override
    public void setViewProps(){
        statStage.setMaxSize(800, 200);
        statStage.setMinSize(800, 200);
        levelStage.setMaxSize(560, 560);
        levelStage.setMinSize(560, 560);
        levelGrid.setMinSize(560,560);
        levelGrid.setMaxSize(560,560);
        listView.setPrefSize(200, 100);
        listView.setLayoutX(53);
        listView.setLayoutY(82);
    }

    /**
     * Set event handlers for when elements are clicked
     */
    public void setClickHandlers(){
        // Handle mouse click on list view
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(listView.getSelectionModel().getSelectedItem()+ " is selected");
                statusLabel.setText(listView.getSelectionModel().getSelectedItem()+ " is selected");
            }
        });
        
        listView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(listView1.getSelectionModel().getSelectedItem()+ " win condition is selected");
                statusLabel.setText(listView1.getSelectionModel().getSelectedItem()+ " win condition is selected");
            }
        });
    }

    /**
     * Add event handlers to the buttons
     * @param game Game level instance being designed
     */
    public void setButtonHandlers(GameLevel game){
        // Handle add button
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAddButton(game);
            }
        });

        // Handles removing entities
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleRemoveButton(game);
            }
        });
        
        addConditionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAddConditionButton(game);
            }
        });

        // Handles removing entities
        removeConditionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleRemoveConditionButton(game);
            }
        });

        // Handles saving the map
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSaveButton(game);

            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleBackButton();
            }
        });
    }

    /**
     * Adds entities onto the game level instance in designer mode
     * @param game Game level instance being designed
     */
    public void handleAddButton(GameLevel game){
        int x = Integer.parseInt(text1.getText());
        int y = Integer.parseInt(text2.getText());

        // Error checking when adding entities
        if (x >= LEVEL_SIZE || y >= LEVEL_SIZE || x < 0 || y < 0) {
            statusLabel.setText("Invalid coordinate");
            return;
        } else if (game.hasEntity(new Point(y, x)) != null){
            statusLabel.setText("Cannot place new entity over another entity");
            return;
        }

        Object o = listView.getSelectionModel().getSelectedItem();
        statusLabel.setText(o.toString() + " added at (" + x + "," + y + ")");

        if (o.toString().equalsIgnoreCase("HERO")) {
            game.getHero().setPosition(new Point(y, x));
        } else {
            game.addEntity(makeEntity(o.toString(), x, y));
        }

        updateGrid(game);
    }

    /**
     * Removes entities from the game level instance
     * @param game Game level instance being designed
     */
    public void handleRemoveButton(GameLevel game){
        int x = Integer.parseInt(text1.getText());
        int y = Integer.parseInt(text2.getText());
        game.removeEntity(new Point(y,x));
        updateGrid(game);
    }
    
    public void handleAddConditionButton(GameLevel game) {
    	Object o = listView1.getSelectionModel().getSelectedItem();
    	CompletionCondition c = null;
        if(o.toString().equals("Enemy")) {
        	c = new EnemyCondition();
        } else if (o.toString().equals("Treasure")) {
        	c = new TreasureCondition();
        } else if (o.toString().equals("Switch")) {
        	c = new SwitchCondition();
        }
        game.addWinConditions(c);
        statusLabel.setText("Win condition: \"" + o.toString() + "\" is added");
    }
    
    public void handleRemoveConditionButton(GameLevel game) {
    	Object o = listView1.getSelectionModel().getSelectedItem();
    	CompletionCondition c = null;
        if(o.toString().equals("Enemy")) {
        	c = new EnemyCondition();
        } else if (o.toString().equals("Treasure")) {
        	c = new TreasureCondition();
        } else if (o.toString().equals("Switch")) {
        	c = new SwitchCondition();
        }
        game.removeWinConditions(c);
        statusLabel.setText("Win condition: \"" + o.toString() + "\" is remove");
    }
    
    /**
     * Saves game level instance and entities onto a text file
     * @param game Game level instance being designed
     */
    public void handleSaveButton(GameLevel game){
        // Writes to a new file that stores level details
        String newFile = "levels/" +
                            String.valueOf(gameSystem.assignGameID()) +
                            ".txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
            for (Entity e : game.getEntitites()){
                String entityLine = e.getImage() + " " +
                                    e.getPosition().x + " " +
                                    e.getPosition().y;
                writer.write(entityLine);
                writer.newLine();
            }
            for (CompletionCondition c : game.getWinConditions()) {
            	String conditionLine = c.toString();
            	writer.write(conditionLine);
            	writer.newLine();
            }
            
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        LevelMenuScreen levelMenuScreen = new LevelMenuScreen(stage);
        levelMenuScreen.start(gameSystem);
    }

    /**
     * Goes back to the level selection screen
     */
    public void handleBackButton(){
        LevelMenuScreen levelMenuScreen = new LevelMenuScreen(stage);
        levelMenuScreen.start(gameSystem);
    }

    /**
     * Converts string to an entity
     * @param name String representing the entity
     * @param x X-Coordinate of entity
     * @param y Y-Coordinate of entity
     * @return Entity formed by parameters
     */
    public Entity makeEntity(String name, int x, int y) {
		return gameSystem.convertToEntity(name, x, y);
    }
    
}