package Obstacles;

import Game.Entity;
import java.awt.Point;

abstract public class Obstacle extends Entity {
    
	public Obstacle(Point coords, String code){
        super(coords, code);
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    public boolean isPit(){
	    return false;
    }

    public boolean isSwitch(){
	    return false;
    }

    public boolean isBoulder(){
	    return false;
    }

    public boolean isWall(){
	    return false;
    }

    public boolean isDoor(){
	    return false;
    }
}


