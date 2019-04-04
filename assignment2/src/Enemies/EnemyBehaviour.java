package Enemies;

import Game.Direction;
import Game.GameLevel;
import java.awt.Point;

public interface EnemyBehaviour {
    /**
     * Calculates move based on the enemy type and hero's location
     * Also considers environment (walls, etc.)
     * @param map Current state of the level
     * @param enemyLocation Coordinates of enemy's position
     * @param heroLocation Coordinates of hero's position
     * @return Direction for enemy to move to
     */
    Direction getDecision(GameLevel map, Point enemyLocation, Point heroLocation);
    String getCode();
}
