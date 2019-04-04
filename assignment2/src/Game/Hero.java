package Game;

import java.awt.Point;
import java.util.ArrayList;

import Enemies.Enemy;
import Items.*;
import Obstacles.*;
//import javafx.scene.image.Image;


public class Hero extends Entity implements Movable {
	private HeroState heroState;
	private ArrayList<Item> inventory;
	private int iCounter;
	
	public Hero() {
		super(new Point(1,1), "Hero");
    	this.heroState = HeroState.NORMAL;
    	this.inventory = new ArrayList<>();
    	this.iCounter=0;
	}

	@Override
	public boolean isMovableTo(Point coords, Direction direction, GameLevel level) {
		Entity e = level.hasEntity(coords);
		if (e instanceof Obstacle){
			Obstacle o = (Obstacle) e;

			if (o.isWall()){
				// Player cannot walk through walls
				return false;
			} else if (o.isDoor()){
				// Only movable if door is open or hero has key to open door
				Door d = (Door) e;
				Key k = level.getHero().getKey();
				if (d.getDoorState() == DoorState.OPEN) {
					return true;
				} else {
					if(k!=null) return true;
					return false;
				}
			} else if (o.isBoulder()){
				// Only movable if boulder can be moved
				Boulder b = (Boulder) e;
				System.out.println("Player is pushing " + b);
				return b.move(direction, level, b);
			}
		}
		return true;
	}
	
	@Override
	public boolean move(Direction direction, GameLevel level, Entity e){
        Point coordToMoveInto = nextPosition(direction, e.getPosition());
        Point entityPosition = e.getPosition();
        Square[][] s = level.getLevel();
        int heroIndex = s[entityPosition.x][entityPosition.y].indexOf(e);

        if (withinBound(coordToMoveInto) && isMovableTo(coordToMoveInto, direction, level)){
            // Move entity to new position
            level.popFromSquareIndex(entityPosition, heroIndex);
            e.setPosition(coordToMoveInto);
            level.addEntityToSquare(e, coordToMoveInto);
            //System.out.println("x = " + this.getPosition().x + " , y = " + this.getPosition().y);
            
            Arrow arrow = getArrow();
            if (useArrow(direction) && arrow != null) {
            	System.out.println("Using Arrow");
            	arrow.shoot(direction, entityPosition, level);
            	inventory.remove(arrow);
            }
            
            Bomb bomb = getBomb();
            if (useBomb(direction) && bomb != null) {
            	System.out.println("Using Bomb");
            	bomb.useItem(bomb);
            	System.out.println(bomb.getBombState().getCode());
            	bomb.setPosition(coordToMoveInto);
            	level.addEntity(bomb);;
            	inventory.remove(bomb);
            }
            
            return true;
        }

        return false;
    }
	
	@Override
	public boolean isMovable() {
		return true;
	}

	/**
	 * Check if the user is trying to shoot an arrow
	 * @param direction
	 * @return boolean
	 */
	public boolean useArrow(Direction direction) {
		switch (direction) {
			case ARROWUP:
				return true;
			case ARROWDOWN:
				return true;
			case ARROWLEFT:
				return true;
			case ARROWRIGHT:
				return true;
		}
		return false;
	}
	
	public boolean useBomb(Direction direction) {
		if (direction == Direction.PLACEBOMB) {
			return true;
		}
		return false;
	}
	

	/**
	 * Adds an item to the user's inventory
	 * however, user can only store 1 key and 1 sword at a time
	 * @param item Item on map to be picked up
	 */
	public void pickUpItem(Item item) {
		if (item.isSword() && this.getSword() != null) {
			// Restock sword with the new one and throw old one away
			inventory.remove(getSword());
			inventory.add(item);
		} else if (item.isKey() && this.getKey() != null) {
			// do nothing
		} else if (item.isBomb() && ((Bomb)item).getBombState().isUnlitState()) {
			// if bomb state is unlit pickup
			inventory.add(item);
		} else {
			inventory.add(item);
		}
	}

	/**
	 * Checks if there is a key in the inventory
	 * @return Key if it exists in the inventory
	 */
	public Key getKey() {
		for (Item item : inventory){
			if (item.isKey())
				return (Key)item;
		}
		return null;
	}

	/**
	 * Checks if there is a sword in the inventory
	 * @return Sword if it exists in inventory
	 */
	public Sword getSword(){
		for (Item item : inventory){
			if (item.isSword()){
				return (Sword)item;
			}
		}
		return null;
	}
	
	/**
	 * Check if there is an arrow in the inventory
	 * @return Arrow if it exist in inventory
	 */
	public Arrow getArrow(){
		for (Item item : inventory){
			if (item.isArrow()){
				return (Arrow)item;
			}
		}
		return null;
	}	
	
	public Bomb getBomb(){
		for (Item item : inventory){
			if (item.isBomb()){
				return (Bomb)item;
			}
		}
		return null;
	}

	/**
	 * Getter method for Hero's State
	 * @return Hero's State
	 */
	public HeroState getHeroState() {
		return heroState;
	}

	/**
	 * Setter method to change Hero's state for potion effects
	 * @param newState New State after potion has been used
	 */
	public void setHeroState(HeroState newState){
		this.heroState = newState;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}
    
	/**
	 * Method to use sword
	 * @param sword
	 */
	public void useSword(Sword sword){
		sword.useItem(sword);
		if (sword.getUses() == 0){
			// Weapon no longer has any use
			inventory.remove(sword);
		}
	}

	// Temporary function to make tests happy
	public void useWeapon(Weapon weapon){
		System.out.println(weapon.getUses());
	}
	
	/**
	 * Set the iCounter to 5
	 */
	public void initCounter() {
		this.iCounter = 5;
	}
	
	/**
	 * Decrement the iCounter
	 * and if it reaches 0, set hero state to normal
	 * only if heroState is Invincible
	 */
	public void updateCounter() {

		System.out.println(this.iCounter+" here");
		if((iCounter>0) && heroState==HeroState.INVINCIBLE) {
			this.iCounter--;
		} else if(iCounter == 0 && heroState==HeroState.INVINCIBLE) {
			heroState = HeroState.NORMAL;
		}
	}

	@Override
	public boolean isHero() {
		return true;
	}
}


