package Obstacles;

import java.awt.Point;

import Game.Entity;
import Game.GameStatus;
import Game.Hero;
import Game.HeroState;

public class Exit extends Obstacle {

	public Exit(Point coords) {
		super(coords, "Exit");
	}

	@Override
	public GameStatus heroInteract(Hero hero) {
		// Hero wins when they pass through exit
		return GameStatus.WIN_THROUGH_EXIT;
	}

}
