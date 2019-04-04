package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Game.CompletionCondition;
import Game.GameLevel;

import Game.*;
import Items.*;
import Obstacles.*;
import Enemies.*;

class US3 {
	/**
	 * US 3.1 Keys can unlock specific doors
	 */
	@Test
	void testUS3_1() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		
		Entity key = new Key(new Point(2,1),0);
		Entity door = new Door(new Point(3,2),0);
		Entity door2 = new Door(new Point(3,1),1);
		
		level.addEntity(key);
		level.addEntity(door);
		level.addEntity(door2);
		
		// Pick up the key
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getInventory().size()==1);
		
		// Can open and go through door because hero doesnt have the correct key
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getPosition().equals(new Point(2,1)));
		assertTrue(level.getHero().getInventory().size()==1);
		
		// Now use the door to the right which has the same ID
		level.updateGameState(Direction.RIGHT);
		level.updateGameState(Direction.DOWN);
		
		// Hero can get through
		assertTrue(level.getHero().getPosition().equals(new Point(3,2)));
		assertTrue(level.getHero().getInventory().size()==0);
		
	}
	
	/**
	 * US 3.2 Place a bomb to destroy obstacles
	 */
	@Test
	void testUS3_2() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		
		Entity boulder = new Boulder(new Point(2,2));
		Entity boulder1 = new Boulder(new Point(2,3));
		Entity bomb = new Bomb(new Point(2,1));
		
		level.addEntity(boulder);
		level.addEntity(boulder1);
		level.addEntity(bomb);
		
		
		// Pick the bomb at (2,1)
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getInventory().size()==1);
		
		// Place the bomb
		// wait till it explodes
		// boulder(2,2) will get destroyed but boulder1(2,3) will not
		level.updateGameState(Direction.PLACEBOMB);
		assertTrue(level.getHero().getInventory().size()==0);
		Square[][] sq = level.getLevel();
		level.updateGameState(Direction.DOWN);
		// Bomb has not exploded
		assertTrue(sq[2][1].size() == 1);
		level.updateGameState(Direction.DOWN);
		level.updateGameState(Direction.DOWN);
		level.updateGameState(Direction.DOWN);
		sq = level.getLevel();
		
		// Bomb exploded, removed from square[2][1]
		// Destroys bolder at (2,2) but not (2,3)
		assertTrue(sq[2][1].size() == 0);
		assertTrue(sq[2][2].size() == 0);
		assertTrue(sq[2][3].size() == 1);
		
	}
	
	/**
	 * US 3.3 Consume Hover potion
	 */
	@Test
	void testUS3_3() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		
		Entity hover = new Potion(new Point(2,1), new Hover());
		Entity pit = new Pit(new Point(3,1));
		Entity exit = new Exit(new Point(6,1));
		
		level.addEntity(hover);
		level.addEntity(pit);
		level.addEntity(exit);
		
		// Pick the hover potion at (2,1)
		// It is consumed immediately
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getInventory().size()==0);
		
		// Walk over the pit
		level.updateGameState(Direction.DOWN);
		
		assertTrue(level.getHero().getHeroState().equals(HeroState.HOVER));
		assertTrue(level.getGameStatus().equals(GameStatus.NOT_DONE));
		
		// Still hover until the game ends
		level.updateGameState(Direction.DOWN);
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getHeroState().equals(HeroState.HOVER));
		
		// Walk to exit
		// Hover potion still in effect
		level.updateGameState(Direction.DOWN); 
		assertTrue(level.getGameStatus().equals(GameStatus.WIN_THROUGH_EXIT));
		assertTrue(level.getHero().getHeroState().equals(HeroState.HOVER));
	}
	
	
	/**
	 * US 3.4 Consume invincibility potion
	 */
	@Test
	void testUS3_4() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		
		Entity invi = new Potion(new Point(2,1), new Invincible());
		Entity enemy = new Enemy(new Point(5,1), new Hunter());
		Entity exit = new Exit(new Point(6,1));
		
		level.addEntity(invi);
		level.addEntity(enemy);
		level.addEntity(exit);
		
		// Pick the hover potion at (2,1)
		// It is consumed immediately
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getHeroState().equals(HeroState.INVINCIBLE));
		assertTrue(level.getHero().getInventory().size()==0);
		
		// Walk towards enemy
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getGameStatus() == GameStatus.NOT_DONE);
		assertTrue(level.getHero().getHeroState().equals(HeroState.INVINCIBLE));
		
		// Still Invinvibility until the some period
		level.updateGameState(Direction.DOWN);
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getHeroState().equals(HeroState.INVINCIBLE));
		
		// Walk to exit
		// Invincibility potion still in effect
		level.updateGameState(Direction.RIGHT);
		level.updateGameState(Direction.LEFT);
		level.updateGameState(Direction.RIGHT);
		level.updateGameState(Direction.LEFT);
		
		
		assertTrue(level.getHero().getHeroState().equals(HeroState.NORMAL));
	}
	/**
	 * US 3.5 Use Arrow
	 */
	@Test
	void testUS3_5() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		Entity arrow = new Arrow(new Point(2,1));
		Entity enemy = new Enemy(new Point(5,1), new Hunter());
		level.addEntity(arrow);
		level.addEntity(enemy);
		
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		assertTrue(level.getEnemies().get(0).getPosition().equals(new Point (5,1)));
		
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getPosition().equals(new Point(2,1)));
		assertTrue(level.getEnemies().get(0).getPosition().equals(new Point (4,1)));
		assertTrue(level.getHero().getInventory().size()==1);
		
		// Shoot the arrow
		level.updateGameState(Direction.ARROWDOWN);
		assertTrue(level.getHero().getPosition().equals(new Point(2,1)));
		
		// Enemy removed from map and the gameLevel
		assertTrue(level.getEnemies().size()==0);
		Square[][] map = level.getLevel();
		assertTrue(map[4][1].size()==0);
		assertTrue(level.getHero().getInventory().size()==0);
		
		
	}
	
	/**
	 * US 3.6 Use Sword
	 */
	@Test
	void testUS3_6() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		Entity sword = new Sword(new Point(2,1));
		Entity enemy = new Enemy(new Point(5,1), new Hunter());
		level.addEntity(sword);
		level.addEntity(enemy);
		
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		assertTrue(level.getEnemies().get(0).getPosition().equals(new Point (5,1)));
		
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getPosition().equals(new Point(2,1)));
		assertTrue(level.getEnemies().get(0).getPosition().equals(new Point (4,1)));
		assertTrue(level.getHero().getInventory().size()==1);
		
		// Move down to point(3,1) which is the same position with enemy
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getPosition().equals(new Point(3,1)));
		assertTrue(level.getEnemies().size()==0);
		// Sword does not break
		assertTrue(level.getHero().getInventory().size()==1);
		Square[][] map = level.getLevel();
		
		// Square[3][1] now only contains hero, enemy is killed
		assertTrue(map[3][1].size()==1);
		
	}
}
