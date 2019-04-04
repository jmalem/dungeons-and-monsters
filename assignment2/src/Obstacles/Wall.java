package Obstacles;

import java.awt.Point;
import Game.Entity;

public class Wall extends Obstacle {
	
	public Wall(Point coords) {
		super(coords, "Wall");
	}

	@Override
	public boolean isWall() {
		return true;
	}
}
