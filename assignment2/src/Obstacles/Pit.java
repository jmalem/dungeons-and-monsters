package Obstacles;

import java.awt.Point;
import Game.Entity;
import Game.GameStatus;
import Game.Hero;
import Game.HeroState;

public class Pit extends Obstacle {
	
	public Pit(Point coords) {
		super(coords, "Pit");
	}

	@Override
	public GameStatus heroInteract(Hero hero) {
		if (hero.getHeroState() != HeroState.HOVER){
			return GameStatus.LOSE;
		}
		return GameStatus.NOT_DONE;
	}

	@Override
	public boolean isPit() {
		return true;
	}
}
