package Game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.awt.Point;
import Obstacles.*;
import Items.*;
import Enemies.*;
import java.util.ArrayList;

public class GameSystem {
	private User user;
	private ArrayList<GameLevel> levels;
	private int id;
	ArrayList<CompletionCondition> winConditions;
	public static final int LEVEL_SIZE = 15;
	
	public GameSystem() {
		this.user = new Player();
		this.levels = new ArrayList<>();
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
	}

	public static void main(String[] args) {
		GameSystem g = new GameSystem();

		// Main Game Loop
		g.playGame(g.getLevelByID(1));
	}

	/**
	 * Builds a game level given a list of entities to place on it
	 * @param level GameLevel instance to populate with entities
	 * @param entities List of entities to place on the map
	 * @return Level populated with the entities
	 */
	public GameLevel buildLevel(GameLevel level, ArrayList<Entity> entities){
        // Setup the map by adding entities for testing

		// Add wall border
		for (int i = 0; i < LEVEL_SIZE; i++) {
			for (int j = 0; j < LEVEL_SIZE; j++) {
				if (i == 0 || i == LEVEL_SIZE - 1 || j == 0 || j == LEVEL_SIZE - 1) {
					level.addEntity(new Wall(new Point(i, j)));
				}
			}
		}
		for (Entity e : entities) level.addEntity(e);

        return level;
    }

	/**
	 * Plays the game level instance passed in
	 * @param level Game Level instance to be played
	 */
	public void playGame(GameLevel level){
		level.drawLevel();
		// Get User Input
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();

		while (!input.equals("end")) {
			level.updateGameState(inputToDirection(input));
			if (level.isComplete()){
				sc.close();
				return;
			}
			// Executes processing required for the turn
			input = sc.nextLine();
		}
		System.out.println("Game Finished");
	}

	/**
	 * Converts Movement string to a Direction enum
	 * @param input User's Input
	 * @return Direction user wants hero to move to
	 */
	public Direction inputToDirection (String input){
		switch (input){
			case "U":
				return Direction.UP;
			case "D":
				return Direction.DOWN;
			case "L":
				return Direction.LEFT;
			case "R":
				return Direction.RIGHT;
			case "SU":
				return Direction.ARROWUP;
			case "SD":
				return Direction.ARROWDOWN;
			case "SL":
				return Direction.ARROWLEFT;
			case "SR":
				return Direction.ARROWRIGHT;
			case "B":
				return Direction.PLACEBOMB;
		}
		return null;
	}

	public ArrayList<GameLevel> getLevels(){
		return levels;
	}

	public void addLevel(GameLevel g) {
		g.setGameID(assignGameID());
		levels.add(g);
	}

	/**
	 * Gets the appropriate game instance
	 * Precondition: id has to be a value greater than 0
	 * @param id Game's id that is requested
	 */
	public GameLevel getLevelByID(int id){
		return levels.get(id - 1);
	}

	/**
	 * Assigns a unique gameID based on the number of levels stored
	 * @return Unique gameID as an integer
	 */
	public int assignGameID(){
		File levelDir = new File("assignment2/levels");
		File[] levels = levelDir.listFiles();
		return (levels == null) ? 0 : levels.length + 1;
	}

	/**
	 * Builds level based on entities listed in file f
	 * @param f File where level details are stored
	 * @return Level with entities specified in f
	 */
	public GameLevel loadLevel(File f){
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		ArrayList<Entity> entities = new ArrayList<>();

		// Reading level details from the file
		try {
			// Referenced from https://alvinalexander.com/blog/post/java/how-open-read-file-java-string-array-list
			BufferedReader scanner = new BufferedReader(new FileReader(f));
			String line;
			while ((line = scanner.readLine()) != null){
				String[] entityLine = line.split(" ");
				if (entityLine.length == 1){
					// Line is specifying a win condition
					winConditions.add(convertToCondition(entityLine[0]));
				} else if (entityLine.length == 3){
					// Line is specifying an entity
					entities.add(convertToEntity(entityLine[0],
								  Integer.parseInt(entityLine[1]),
								  Integer.parseInt(entityLine[2])));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return new GameLevel(winConditions, entities);
	}

	/**
	 * Converts a condition as a String to a CompletionCondition implementation
	 * @param condition String that is either "Enemy", "Switch", "Treasure"
	 * @return Completion Condition implmentation based on String parameter
	 */
	public CompletionCondition convertToCondition(String condition){
		switch(condition){
			case "Enemy":
				return new EnemyCondition();
			case "Switch":
				return new SwitchCondition();
			case "Treasure":
				return new TreasureCondition();
		}
		return null;
	}

	/**
	 * Converts parameters into corresponding Entity object
	 * @param entity String representing the entity
	 * @param xCoord X-Coordinate of the Entity
	 * @param yCoord Y-Coordinate of the Entity
	 * @return Entity object formed based on the parameters
	 */
	public Entity convertToEntity(String entity, int xCoord, int yCoord){
		Point coord = new Point(xCoord, yCoord);

		switch(entity){
			case "Strategist":
				return new Enemy(coord, new Strategist());
			case "Hunter":
				return new Enemy(coord, new Hunter());
			case "Coward":
				return new Enemy(coord, new Coward());
			case "Hound":
				return new Enemy(coord, new Hound());
			case "Door":
				this.id++;
				new Key(coord,this.id);
				return new Door(coord,this.id);
			case "Switch":
				return new Switch(coord);
			case "Boulder":
				return new Boulder(coord);
			case "Pit":
				return new Pit(coord);
			case "Wall":
				return new Wall(coord);
			case "Exit":
				return new Exit(coord);
			case "Treasure":
				return new Treasure(coord);
			case "Key":
				this.id++;
				new Door(coord,this.id);
				return new Key(coord,this.id);
			case "Hover":
				return new Potion(coord, new Hover());
			case "Invincible":
				return new Potion(coord, new Invincible());
			case "Sword":
				return new Sword(coord);
			case "Arrow":
				return new Arrow(coord);
			case "Bomb":
				return new Bomb(coord);
		}
		return null;
	}
}