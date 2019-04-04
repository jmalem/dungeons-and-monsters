package Game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import Enemies.*;
import Items.*;
import Obstacles.*;


public class GameLevel {

	public static final int LEVEL_SIZE = 15;
	private Square[][] level;
	private int turn;
	private int gameID;
	private GameStatus gameStatus;
	private Hero hero;
	private ArrayList<Item> items;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Enemy> enemies;
	private ArrayList<CompletionCondition> winConditions;
	
	/**
	 * Initialize a new GameLevel object
	 * with hero at Point(0,0)
	 * @param conditions Completion Conditions to satisfy to win the level
	 */
    public GameLevel(ArrayList<CompletionCondition> conditions, int gameID) {
    	this.level = new Square[LEVEL_SIZE][LEVEL_SIZE];
    	for (int i = 0; i < LEVEL_SIZE; i++){
    		for (int j = 0; j < LEVEL_SIZE; j++){
    			level[i][j] = new Square();
			}
		}
    	this.turn = 0;
    	this.gameID = gameID;
    	this.gameStatus = GameStatus.NOT_DONE;
		this.hero = new Hero();
		this.items = new ArrayList<>();
		this.obstacles = new ArrayList<>();
		this.enemies = new ArrayList<>();
		this.winConditions = new ArrayList<>(conditions); 	// Tracks conditions required to win the game

		// Player starts at (1, 1)
		level[1][1].add(hero);
	}

    public GameLevel(ArrayList<CompletionCondition> conditions, ArrayList<Entity> entities) {
        this.level = new Square[LEVEL_SIZE][LEVEL_SIZE];
        for (int i = 0; i < LEVEL_SIZE; i++){
            for (int j = 0; j < LEVEL_SIZE; j++){
                level[i][j] = new Square();
            }
        }
        this.turn = 0;
        this.gameID = -1;
        this.gameStatus = GameStatus.NOT_DONE;
        this.hero = new Hero();
        this.items = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.winConditions = new ArrayList<>(conditions); 	// Tracks conditions required to win the game

        populateLevel(entities);
        // Player starts at (1, 1)
        level[1][1].add(hero);
    }

    /**
     * Builds a game level given a list of entities to place on it
     * @param entities List of entities to place on the map
     * @return Level populated with the entities
     */
	public void populateLevel(ArrayList<Entity> entities){
        // Setup the map by adding entities for testing
        // Add wall border
        for (int i = 0; i < LEVEL_SIZE; i++) {
            for (int j = 0; j < LEVEL_SIZE; j++) {
                if (i == 0 || i == LEVEL_SIZE - 1 || j == 0 || j == LEVEL_SIZE - 1) {
                    addEntity(new Wall(new Point(i, j)));
                }
            }
        }
        for (Entity e : entities) addEntity(e);
    }

    /**
     * Updates the state of the game
     * @param d Direction for moving the hero
     */
	public void updateGameState(Direction d) {
		// Hero moves based on user input
    	hero.move(d, this, hero);
		updateLevel();

    	// Enemies make a move based on hero's position
		if (!enemies.isEmpty()){
			moveEnemies();
			updateLevel();
		}
		
		// Ticking all bombs
		tickBombs();
		
		// Update turn and draw the map
		turn++;

		hero.updateCounter();
		drawLevel();
	}

	/**
	 * Method checks for any interactions between entities on the map
	 */
	public void updateLevel() {
		Point heroCoord = hero.getPosition();
		//System.out.println("x = " + heroCoord.x + " , y = " + heroCoord.y);
		Square heroCoordSquare = level[heroCoord.x][heroCoord.y];
		int heroIndexInSquare = heroCoordSquare.indexOf(hero);

		System.out.println("Index of hero in Square: " + heroIndexInSquare);

		if (heroCoordSquare.size() > 1){
			heroInteractWithEntity(heroCoordSquare);
		}

		// Check if any enemies or boulders fell into any pits
		checkOtherPitFalls();
		
		// Check and update the states of switches on map
		checkSwitches();
				
	}
	
	/**
	 * Tick all the existing bombs in the map
	 */
	public void tickBombs() {
		Entity e = null;
		Point p = null;
		for (Item i : items) {
			if (i.isBomb()) {
				if (((Bomb) i).getBombState().isUnlitState()) {
					// do nothing
				} else if (((Bomb) i).getBombState().isAlmostLitState()) {
					explodeBomb(i.getPosition());
					i.useItem(i);
				} else if (((Bomb) i).getBombState().isExplodeState()) {
					p = i.getPosition();
					e = i;
				} else {
					i.useItem(i);
				}
			}
		} if (e != null) {
			level[p.x][p.y].remove(e);
			items.remove(e);
		}
		
	}
	/**
	 * Destroy a bomb and anything that is adjacent to it
	 * @param p
	 */
	public void explodeBomb(Point p) {
		destroyPoint(p.x,p.y);
		if (withinBound(p.x+1, p.y)) {
			destroyPoint(p.x+1,p.y);
		}
		if (withinBound(p.x-1, p.y)) {
			destroyPoint(p.x-1,p.y);
		}
		if (withinBound(p.x, p.y+1)) {
			destroyPoint(p.x,p.y+1);
		}
		if (withinBound(p.x, p.y-1)) {
			destroyPoint(p.x,p.y-1);
		}
	}
	
	/**
	 * Destroys certain entity at coordinate (x,y)
	 * @param x X-Coordinate of point to destroy
	 * @param y Y-Coordinate of point to destroy
	 */
	public void destroyPoint(int x, int y) {
		Point p = new Point(x,y);

		// No entity destroyed
		if (level[p.x][p.y].size() == 0) return;

		// Enemies, Boulders and Hero will be affect the level state if destroyed
		for (int i = 0; i < level[p.x][p.y].size(); i++) {
			Entity entityOnSquare = level[p.x][p.y].get(i);
			if (entityOnSquare.isEnemy()){
				level[p.x][p.y].remove(entityOnSquare);
				enemies.remove(entityOnSquare);

			} else if (entityOnSquare.isObstacle() && ((Obstacle)entityOnSquare).isBoulder()){
				level[p.x][p.y].remove(entityOnSquare);
				obstacles.remove(entityOnSquare);

			} else if (entityOnSquare.isHero()) {
				this.gameStatus = GameStatus.LOSE;

			}
		}
	}

	/**
	 * Every enemy makes a move based on their respective algorithms
	 */
	public void moveEnemies() {
		System.out.println(enemies.size());
		for (int i = 0; i < enemies.size(); i++){
			System.out.println("Moving Enemy: " + enemies.get(i).getImage());
			enemies.get(i).makeMove(this, hero.getPosition());
		}
	}

	/**
	 * This method draws the array according to the value of each array
	 * and will not update the value in it
	 */
	public void drawLevel() {
		System.out.println("Entities in the level");
		for (Entity e : getEntitites()){
			System.out.println(e + " is in position (" + e.getPosition().y + ", " + e.getPosition().x + ")");
		}

		System.out.println(printHeroStats(hero));
		String printString;

		for (int i = 0; i < LEVEL_SIZE; i++) {
			for (int j = 0; j < LEVEL_SIZE; j++) {
				if (level[i][j].isEmpty()){
					printString = String.format("%-13s", "Floor");
					System.out.print(printString);
				} else {
					printString = String.format("%-13s", level[i][j].toString());
					System.out.print(printString);
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Adds an entity onto the square specified by coords
	 * Used when entities move around
	 * @param e Entity to add to the square
	 * @param coords Coordinates on the level to place entity
	 */
	public void addEntityToSquare(Entity e, Point coords){
		level[coords.x][coords.y].add(e);
	}

	/**
	 * Adds an entity onto the map from designer point of view
	 * 	 * Has not included designer check where an entity
	 * 	 * cannot be placed on top of another entity
	 * @param e Entity with assumed unique coordinates on the map
	 */
    public void addEntity(Entity e) {
    	Point p = e.getPosition();

    	if (!withinBound(p.x, p.y)) {
			// Entity trying to be placed outside the map
    		System.out.println("Entity must be place within the map boundary, map size: " + LEVEL_SIZE);
			return;
		}

		// Entity can be placed onto the map
		level[p.x][p.y].add(e);

		// Add entity to the correct list
		if (e.isObstacle()) obstacles.add((Obstacle) e);
		else if (e.isItem()) items.add((Item) e);
		else if (e.isEnemy()) enemies.add((Enemy) e);
    }

    /**
     * To check whether a certain coordinate is within the map
     * @param x The x coordinate to check
     * @param y The y coordinate to check
     * @return 1 if inside the map 0 otherwise
     */
    public boolean withinBound(int x, int y) {
		return !(x >= GameLevel.LEVEL_SIZE || x<0 || y>=GameLevel.LEVEL_SIZE || y<0);
	}

	/**
	 * Prints out Hero Related Stats before the map is drawn
	 * @param hero Hero entity in the game
	 */
	public String printHeroStats(Hero hero){
		StringBuilder str = new StringBuilder();
		str.append("Turn: "+ turn+"\n");
		//System.out.println("Turn: "+ turn);

		int key = 0;
    	int sword = 0;
    	int arrow = 0;
    	int bomb = 0;
    	int treasure = 0;
    	for (Item item : hero.getInventory()){
			if (item instanceof Key) {
				key++;
			} else if (item instanceof Sword) {
				Sword s = (Sword)item;
				sword = s.getUses();
			} else if (item instanceof Bomb) {
				bomb++;
			} else if (item instanceof Arrow) {
				arrow++;
			} else if (item instanceof Treasure) {
				treasure++;
			}
    	}/*
		System.out.println("Key: "+ key + " | Sword: " + sword);
		System.out.print("Arrow: "+ arrow + " | Bomb: " + bomb);
		System.out.println(" | Treasure: " + treasure);
		System.out.println("Hero State: " + hero.getHeroState());
		System.out.println();
		*/
    	str.append("Key: "+ key + " | Sword: " + sword+"\n");
    	str.append("Arrow: "+ arrow + " | Bomb: " + bomb);
    	str.append(" | Treasure: " + treasure+"\n");
    	str.append("Hero State: " + hero.getHeroState()+"\n\n");
		return str.toString();
	}

	/**
	 * Checks if there is another entity in that coordinate in the map
	 * @param coords (x,y) Coordinate to check
	 * @return Whether or not there is an entity on that square
	 */
	public Entity hasEntity(Point coords){
		if (level[coords.x][coords.y].isEmpty()) return null;
		return level[coords.x][coords.y].peek();
	}

	/**
	 * Performs interaction between hero and an entity in the same square
	 * @param heroCoordSquare Square hero entity is on
	 */
	public void heroInteractWithEntity(Square heroCoordSquare){
		int heroIndexInSquare = heroCoordSquare.indexOf(hero);
		Entity e;

		// Hero is in the same square as an entity and interacts with it
		if (heroIndexInSquare < heroCoordSquare.size() - 1){
			// Interacts with entity above the Hero in the Square
			e = heroCoordSquare.get(heroIndexInSquare + 1);
		} else {
			// Interacts with entity below the Hero in the Square
			e = heroCoordSquare.get(heroIndexInSquare - 1);
		}

		System.out.println("Hero interacting with " + e.toString());
		gameStatus = e.heroInteract(hero);
		checkStateChanges(e);
	}

	/**
	 * Changes the state of the level after hero interacts with the entity
	 * @param e Entity hero interacts with
	 */
	public void checkStateChanges(Entity e){
		Point heroCoord = hero.getPosition();

		// Handling item pickups
		if (e.isItem()){
			if (((Item)e).isBomb() && !((Bomb)e).getBombState().isUnlitState()){
				// If bomb is lit, not picked up
				return;
			}
			// Remove the item from the map as picked up by hero
			level[heroCoord.x][heroCoord.y].remove(e);
			items.remove(e);
		}

		// Handling enemy deaths
		if (e.isEnemy()){
			if (gameStatus == GameStatus.NOT_DONE || hero.getHeroState() == HeroState.INVINCIBLE){
				// Hero has killed Enemy e
				level[heroCoord.x][heroCoord.y].remove(e);
				enemies.remove(e);
			}
		}
	}

	/**
	 * Checks all pits on the map and removes any entity that is not the
	 * hero from the level
	 */
	public void checkOtherPitFalls(){
		ArrayList<Obstacle> obstaclesToRemove = new ArrayList<>();
		ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
		for (Obstacle o : obstacles){
			if (o.isPit()){
				Point pitCoord = o.getPosition();
				Square cell = level[pitCoord.x][pitCoord.y];
				if (cell.size() > 1 && !(cell.get(1).isHero())){
					if (cell.get(1).isEnemy()){
						// Remove enemy from the level
						enemiesToRemove.add((Enemy)level[pitCoord.x][pitCoord.y].pop());
					} else {
						// to avoid ConcurrentModificationException
						// Remove obstacle from the level
						obstaclesToRemove.add((Obstacle)level[pitCoord.x][pitCoord.y].pop());
					}
				}
			}
		}
		for (Enemy e : enemiesToRemove){
			enemies.remove(e);
		}
		for (Obstacle o : obstaclesToRemove){
			obstacles.remove(o);
		}
	}
	
	/**
	 * Checks all switches on the map and triggers it on if a hero or boulder is on top of it
	 * switches them off if nothing is on top
	 */
	public void checkSwitches(){
		for (Obstacle o : obstacles){
			if (o.isSwitch()){
				Point switchCoord = o.getPosition();
				Square cell = level[switchCoord.x][switchCoord.y];
				((Switch) o).setSwitchState(SwitchState.UNSWITCHED);
				
				if (cell.size() > 1 && cell.get(1).isObstacle()) {
					// Switches can only be triggered by a boulder on top of them
					((Switch) o).setSwitchState(SwitchState.SWITCHED);
				}
			}
		}
	}

	/**
	 * Checks if all the conditions to win have been satisfied
	 * @return Whether level has been completed
	 */
	public boolean isComplete() {
		
		// If user go through exit
		if (gameStatus == GameStatus.WIN_THROUGH_EXIT){
	   		System.out.println("YAY!!! You found the exit in " + getTurn() + " turns.");
	   		return true;
		} 
		
		// If user loses
		if (gameStatus == GameStatus.LOSE) {
			System.out.println("Game Over! YOU DIED!");
			return true;
		}
		
		// If winCondition is not empty
		if (!winConditions.isEmpty()) {
			for (CompletionCondition cond : winConditions) {
				// If winCondtion is not satisfied
				if (!cond.checkCondition(this)) {
					return false;
				}
			}
	   		gameStatus = GameStatus.WIN;
			System.out.println("YAY!!! You beat the game in " + getTurn() + " turns.");
    		return true;
		}
		
		return false;
	}
	
	
	/**
	 * Get the entity from the specified coordinates of the level
	 * @param coords
	 * @return Entity
	 */
	public Entity popFromSquare(Point coords){
		return level[coords.x][coords.y].pop();
	}
	
	/**
	 * Popping a specific index from the level square
	 * @param coords coordinate of the square
	 * @param index index of the entity
	 * @return Entity on that square index
	 */
	public Entity popFromSquareIndex(Point coords, int index){
		return level[coords.x][coords.y].popIndex(index);
	}
	
	/**
	 * Get the current game turn
	 * @return int
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Get the game's id
	 * @return gameId
	 */
	public int getGameID(){
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	/**
	 * Get the hero in the current GameLevel
	 * @return Hero
	 */
	public Hero getHero(){
		return hero;
	}
	/**
	 * get the level
	 * @return Square[][]
	 */
	public Square[][] getLevel() {
		return level;
	}
	/**
	 * Get the status of the game
	 * @return GameStatus
	 */
	public GameStatus getGameStatus() {
		return gameStatus;
	}

	/**
	 * Get all the collections of items on the map
	 * @return ArrayList<Item>
	 */
	public ArrayList<Item> getItems() {
		return items;
	}
	/**
	 * Get all the collections of obstacles on the map
	 * @return ArrayList<Obstacle>
	 */
	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}
	/**
	 * Get the list of enemies on the map
	 * @return ArrayList<Enemy>
	 */
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	/**
	 * Get the list of entities on the game level
	 * @return ArrayList<Entity>
	 */
	public ArrayList<Entity> getEntitites() {
		ArrayList<Entity> entities = new ArrayList<>(getItems());
		entities.addAll(getObstacles());
		entities.addAll(getEnemies());
		return entities;
	}

	/**
	 * Get the list of completion/winning condition of the game level
	 * @return ArrayList<Completioncondition>
	 */
	public ArrayList<CompletionCondition> getWinConditions() {
		return winConditions;
	}

	/**
	 * Add a new completion/winning conditions on the current game level
	 * @param c
	 */
	public void addWinConditions(CompletionCondition c) {
		if(winConditions.contains(c) || c==null) return;
		winConditions.add(c);
	}
	
	/**
	 * Remove the completion conditions from the current game level
	 * @param c
	 */
	public void removeWinConditions(CompletionCondition c) {
		if(c==null) return;
		winConditions.remove(c);
	}
	
	/**
	 * Remove an entity from the current game level
	 * @param p
	 */
	public void removeEntity(Point p) {
		int index = -1;
		int index1 = -1;
		int index2 = -1;
		// remove elements from the array list of entities
		for(Entity e : items) {
			if(e.getPosition().equals(p)) {
				index=items.indexOf(e);
				break;
			}
		}
		if(index != -1) items.remove(index);
		for(Entity e : enemies) {
			if(e.getPosition().equals(p)) {
				index1=enemies.indexOf(e);
				break;
			}
		}
		if(index1 != -1) enemies.remove(index1);
		for(Entity e : obstacles) {
			if(e.getPosition().equals(p)) {
				index2=obstacles.indexOf(e);
				break;
			}
		}
		if(index2 != -1) obstacles.remove(index2);
		// Remove elements from the 2-D square matrix as well
		for (int i = 0; i < LEVEL_SIZE; i++){
    		for (int j = 0; j < LEVEL_SIZE; j++){
    			if(i==p.x && j==p.y) {
    				level[i][j].pop();
    			}
			}
		}
	}
	
}

