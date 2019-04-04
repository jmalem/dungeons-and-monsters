package Enemies;

import Game.Direction;
import Game.GameLevel;
import java.awt.*;

import com.sun.javafx.geom.Point2D;

public class Hunter implements EnemyBehaviour {

    // Finds the closest direction to Hero
    @Override
    public Direction getDecision(GameLevel map, Point enemyLocation, Point heroLocation) {
    	// matrix coords are inverted
    	int distH = enemyLocation.y - heroLocation.y;
    	int distV = enemyLocation.x - heroLocation.x;

    	// determine whether to move vertically or horizontally
    	if (distV == 0) {
    		if (distH > 0) {
    			// enemy is right of point
    			return Direction.LEFT;
    		} else {
    			return Direction.RIGHT;
    		}
    	} else if (distH == 0) {
    		if (distV > 0) {
    			// enemy is below point
    			return Direction.UP;
    		} else {
    			return Direction.DOWN;
    		}
    	} else {
	    	if (Math.abs(distV) < Math.abs(distH)) {
	    		// move vertical
	    		if (distV > 0) {
	    			// enemy is below point
	    			return Direction.UP;
	    		} else {
	    			return Direction.DOWN;
	    		}
	    	} else {
	    		// move horizontal
	    		if (distH > 0) {
	    			// enemy is right of point
	    			return Direction.LEFT;
	    		} else {
	    			return Direction.RIGHT;
	    		}
	    	}
    	}
    }

    @Override
    public String getCode() {
        return "Hunter";
    }
}
