package Enemies;

import Game.Direction;
import Game.GameLevel;

import java.awt.*;

import com.sun.javafx.geom.Point2D;

public class Coward implements EnemyBehaviour {
    @Override
    public Direction getDecision(GameLevel map, Point enemyLocation, Point heroLocation) {
        Hunter h = new Hunter();
        Direction hunterDecision = h.getDecision(map, enemyLocation, heroLocation);
        
        if (enemyLocation.distance(heroLocation) <= 1){
            // If within a 1-square radius of player, proceeds to run away
            switch (hunterDecision){
                case RIGHT:
                    return Direction.LEFT;
                case LEFT:
                    return Direction.RIGHT;
                case DOWN:
                    return Direction.UP;
                case UP:
                    return Direction.DOWN;
			default:
				break;
            }
        }


        // Else behaves like a hunter
        return hunterDecision;
    }

    @Override
    public String getCode() {
        return "Coward";
    }
}
