package Game;

import java.util.ArrayList;

import Obstacles.Obstacle;
import Obstacles.Switch;

public class SwitchCondition implements CompletionCondition {

	@Override
	public boolean checkCondition(GameLevel level) {
		// Checks if all switches have been triggered
		ArrayList<Obstacle> obstacles = level.getObstacles();
		for (Obstacle obstacle : obstacles){
			if (obstacle.isSwitch() && !((Switch) obstacle).isTriggered()){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Switch";
	}
}
