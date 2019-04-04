package Obstacles;

import Game.*;
import Items.Item;
import Items.Key;

import java.awt.Point;

public class Boulder extends Obstacle implements Movable {

	public Boulder(Point coords){
		super(coords, "Boulder");
    }
	
	@Override
	public GameStatus heroInteract (Hero hero) {
	    return GameStatus.NOT_DONE;
	}

	@Override
	public Entity makeCopy() {
		return new Boulder(new Point(getPosition()));
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public boolean isBoulder() {
		return true;
	}
}


