package Game;

import java.util.ArrayList;

import Enemies.Enemy;

public class EnemyCondition implements CompletionCondition {

	@Override
	public boolean checkCondition(GameLevel level) {
		// Checks if any enemies left in the map
		ArrayList<Enemy> enemies = level.getEnemies();
		if (enemies.isEmpty()) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Enemy";
	}
}
