package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Game.*;
import Items.*;
import Obstacles.*;
import Enemies.*;
class US2 {
	/**
	 * US 2.1 Interact with Coward
	 */
	@Test
	void testUS2_1() {

		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		Entity e = new Enemy(new Point(4,4), new Coward());
		Entity sword = new Sword(new Point(1,2));
		level.addEntity(e);
		level.addEntity(sword);
		ArrayList<Enemy> array = level.getEnemies();
		
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		assertTrue(array.get(0).getPosition().equals(new Point(4,4)));
		
		
		level.updateGameState(Direction.RIGHT);
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,2)));
		assertTrue(array.get(0).getPosition().equals(new Point(4,3)));
		assertTrue(level.getHero().getInventory().size()==1);
		
		level.updateGameState(Direction.RIGHT);
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,3)));
		assertTrue(array.get(0).getPosition().equals(new Point(3,3)));
		assertTrue(level.getHero().getInventory().size()==1);
		
		// Hero didnt kill the coward at 1,4
		level.updateGameState(Direction.RIGHT);
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,4)));
		assertTrue(array.get(0).getPosition().equals(new Point(3,4)));// atau coordinate lainnya
		
	}
	
	/**
	 * US 2.2 Interact with Hunter
	 */
	@Test
	void testUS2_2() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		Entity e = new Enemy(new Point(4,4), new Hunter());
		Entity sword = new Sword(new Point(1,2));
		level.addEntity(e);
		level.addEntity(sword);
		ArrayList<Enemy> array = level.getEnemies();
		
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		assertTrue(array.get(0).getPosition().equals(new Point(4,4)));
		
		
		level.updateGameState(Direction.RIGHT);
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,2)));
		assertTrue(array.get(0).getPosition().equals(new Point(4,3)));
		assertTrue(level.getHero().getInventory().size()==1);
		
		level.updateGameState(Direction.RIGHT);
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,3)));
		assertTrue(array.get(0).getPosition().equals(new Point(3,3)));
		assertTrue(level.getHero().getInventory().size()==1);
		
		// Hero killed the hunter at 1,4
		level.updateGameState(Direction.DOWN);
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(2,3)));
		assertTrue(array.size()==0);
		assertTrue(level.getHero().getInventory().size()==1);
		assertTrue(level.getHero().getSword().getUses()==4);
		
		
		
	}
	/**
	 * US 2.3 Interact with Hound
	 */
	@Test
	void testUS2_3() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		Entity e = new Enemy(new Point(4,4), new Hound());
		
		level.addEntity(e);
		ArrayList<Enemy> array = level.getEnemies();
		
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		assertTrue(array.get(0).getPosition().equals(new Point(4,4)));

		level.updateGameState(Direction.RIGHT);		
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,2)));
		assertTrue(array.get(0).getPosition().equals(new Point(4,3)));
		
		level.updateGameState(Direction.RIGHT);		
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,3)));
		assertTrue(array.get(0).getPosition().equals(new Point(3,3)));
		
		level.updateGameState(Direction.RIGHT);		
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,4)));
		assertTrue(array.get(0).getPosition().equals(new Point(3,4)));
		
		level.updateGameState(Direction.DOWN);		
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(2,4)));
		assertTrue(array.get(0).getPosition().equals(new Point(2,4)));
		
		// This case the Hound killed the hero
		assertTrue(level.getGameStatus().equals(GameStatus.LOSE));
	}
	
	/**
	 * US 2.4 Interact with Strategist
	 */
	@Test
	void testUS2_4() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		Entity e = new Enemy(new Point(4,4), new Strategist());
		
		level.addEntity(e);
		ArrayList<Enemy> array = level.getEnemies();
		
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		assertTrue(array.get(0).getPosition().equals(new Point(4,4)));

		level.updateGameState(Direction.RIGHT);		
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,2)));
		assertTrue(array.get(0).getPosition().equals(new Point(4,3)));
		
		level.updateGameState(Direction.RIGHT);		
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,3)));
		assertTrue(array.get(0).getPosition().equals(new Point(3,3)));
		
		level.updateGameState(Direction.RIGHT);		
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(1,4)));
		assertTrue(array.get(0).getPosition().equals(new Point(3,4)));
		
		level.updateGameState(Direction.DOWN);		
		array = level.getEnemies();
		assertTrue(level.getHero().getPosition().equals(new Point(2,4)));
		assertTrue(array.get(0).getPosition().equals(new Point(2,4)));
		
		// This case the Strategist killed the hero
		assertTrue(level.getGameStatus().equals(GameStatus.LOSE));
	}
}
