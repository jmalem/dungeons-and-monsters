package Items;

import Game.Entity;
import java.awt.Point;
import Obstacles.Door;
import Obstacles.DoorState;

public class Key extends Item {
	int id;
	int uses;
	
	public Key(Point coords, int id){
        super(coords, "Key");
        this.id = id;
        this.uses = 1;
    }
	
	@Override
    public void useItem(Entity entity) {
        Door door = (Door) entity;
        if (door.getId() == this.id) {
	        door.setDoorState(DoorState.OPEN);
	        uses--;
        }
    }
	
	public int getId() {
		return this.id;
	}

    @Override
    public boolean isKey() {
        return true;
    }
}
