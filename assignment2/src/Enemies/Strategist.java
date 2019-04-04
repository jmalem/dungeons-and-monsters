package Enemies;

import Game.Direction;
import Game.GameLevel;

import java.awt.*;

public class Strategist implements EnemyBehaviour {
    @Override
    public Direction getDecision(GameLevel map, Point enemyLocation, Point heroLocation) {
        // For now behaves the same way as a hunter
        Hunter h = new Hunter();
        return h.getDecision(map, enemyLocation, heroLocation);
    }

    @Override
    public String getCode() {
        return "Strategist";
    }
}
