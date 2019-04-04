package Enemies;

import Game.Direction;
import Game.GameLevel;

import java.awt.*;

public class Hound implements EnemyBehaviour {
    @Override
    public Direction getDecision(GameLevel map, Point enemyLocation, Point heroLocation) {
        Hunter h = new Hunter();
        Direction hunterDecision = h.getDecision(map, enemyLocation, heroLocation);
        
        // Position itself such that the hero is between it and the hunter
        switch (hunterDecision) {
            case RIGHT:
            	Point heroRight = new Point(heroLocation.x, heroLocation.y+1);
                return h.getDecision(map, enemyLocation, heroRight);
            case LEFT:
            	Point heroLeft = new Point(heroLocation.x, heroLocation.y-1);
                return h.getDecision(map, enemyLocation, heroLeft);
            case DOWN:
            	Point heroDown = new Point(heroLocation.x+1, heroLocation.y);
                return h.getDecision(map, enemyLocation, heroDown);
            case UP:
            	Point heroUp = new Point(heroLocation.x-1, heroLocation.y);
                return h.getDecision(map, enemyLocation, heroUp);
		default:
			break;
        }

        // Else behaves like a hunter
        return hunterDecision;
    }

    @Override
    public String getCode() {
        return "Hound";
    }
}
