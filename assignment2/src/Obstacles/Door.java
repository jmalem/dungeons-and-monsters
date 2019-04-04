package Obstacles;

import Game.GameStatus;
import Game.Hero;
import Items.Key;

import java.awt.Point;

public class Door extends Obstacle {
	int id;
	private DoorState doorState;

    public Door(Point coords, int id) {
        super(coords, "ClosedDoor");
        this.id = id;
        this.doorState = DoorState.CLOSED;
    }

    @Override
    public GameStatus heroInteract(Hero hero) {
        Key key = hero.getKey();
        if (key != null){
        	//if (key.getId() == this.id) {
	            // Opens the door by using up a key
            setDoorState(DoorState.OPEN);
            hero.getInventory().remove(key);
        	//}
        }
        return GameStatus.NOT_DONE;
    }

    public void setDoorState(DoorState newState){
    	if (newState == DoorState.OPEN) {
    		this.setImage("OpenDoor");
    	}
        this.doorState = newState;
    }

    public DoorState getDoorState() {
    	return doorState;
    }

    @Override
    public boolean isDoor() {
        return true;
    }

    public int getId() {
    	return this.id;
    }
}

