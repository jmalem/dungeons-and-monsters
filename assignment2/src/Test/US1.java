package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Game.*;
import Items.*;
import Obstacles.*;
import Enemies.*;

class US1 {
	// Test for User Story 1
	
	/**
	 * US 1.1 Player can move around
	 */
	@Test
	void testUS1_1() {
		
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		
		// Add pit
		Entity pit = new Pit(new Point(4,3));
		level.addEntity(pit);
		
		// Add an unlocked Door
		Door door = new Door(new Point(3,2),0);
		door.setDoorState(DoorState.OPEN);
		level.addEntity((Entity) door);
		
		// Add a boulder
		Entity boulder = new Boulder(new Point(1,3));
		level.addEntity(boulder);
		// Add a wall to the right of the boulder
		level.addEntity(new Wall(new Point(1,4)));

		// Add an enemy
		level.addEntity(new Enemy(new Point(6,6),new Hunter()));
		
		// Block the enemy with wall
		level.addEntity(new Wall(new Point(6,5)));
		level.addEntity(new Wall(new Point(5,6)));
		
		
		// Player can walk to adjacent block
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getPosition().equals(new Point(1,2)));
		
		level.updateGameState(Direction.DOWN);
		
		assertTrue(level.getHero().getPosition().equals(new Point(2,2)));
		
		level.updateGameState(Direction.LEFT);
		assertTrue(level.getHero().getPosition().equals(new Point(2,1)));
		
		level.updateGameState(Direction.UP);
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		
		// Walls Should block players
		level.updateGameState(Direction.UP);
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		
		// Walls should also block enemies
		assertTrue(level.getEnemies().get(0).getPosition().equals(new Point(6,6)));
		
		// Try to push the boulder that is blocked by the walls
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getPosition().equals(new Point(1,2)));
		
		// Boulder does not move
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getPosition().equals(new Point(1,2)));
		assertTrue(boulder.getPosition().equals(new Point(1,3)));
		
		// Hero is able to walk through open door
		level.updateGameState(Direction.DOWN);
		System.out.println(level.getHero().getPosition());
		System.out.println(level.getGameStatus());
		level.updateGameState(Direction.DOWN);
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getPosition().equals(new Point(4,2)));
		
		// Hero died and game status equals lose when hero walks to Pit
		assertTrue(level.getGameStatus().equals(GameStatus.NOT_DONE));
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getPosition().equals(pit.getPosition()));
		assertTrue(level.getGameStatus().equals(GameStatus.LOSE));
		
		// Hero also dies when hero walks tp an enemy
		level.updateGameState(Direction.RIGHT);
		level.addEntity(new Enemy(new Point(4,6), new Coward()));
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getGameStatus().equals(GameStatus.LOSE));
		
	}
	/**
	 * US 1.2 Can pick up item from the dungeon
	 */
	@Test
	void testUS1_2() {

		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		
		Entity treasure = new Treasure(new Point(1,2));
		Entity sword = new Sword(new Point(1,3));
		Entity arrow = new Arrow(new Point(1,4));
		Entity potionInvi = new Potion(new Point(1,5), new Invincible());
		Entity potionHover = new Potion(new Point(1,6), new Hover());
		Entity bomb = new Bomb(new Point(3,5));
		
		level.addEntity(treasure);
		level.addEntity(sword);
		level.addEntity(arrow);
		level.addEntity(potionInvi);
		level.addEntity(potionHover);
		level.addEntity(bomb);
		
		// Pick all the item
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getInventory().contains(treasure));
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getInventory().contains(sword));
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getInventory().contains(arrow));
		assertTrue(level.getHero().getInventory().size()==3);
		
		// Item is immediately picked by hero if hero is on the same block
		assertTrue(level.getHero().getHeroState().equals(HeroState.NORMAL));
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getHeroState().equals(HeroState.INVINCIBLE));
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getHeroState().equals(HeroState.HOVER));
		
		
		// Walk to bomb
		level.updateGameState(Direction.DOWN);
		level.updateGameState(Direction.LEFT);
		level.updateGameState(Direction.DOWN);
		level.updateGameState(Direction.DOWN);
		level.updateGameState(Direction.DOWN);
		
		// picked the bomb, bomb disappears from the map
		assertTrue(level.getHero().getInventory().contains(bomb));
		assertTrue(level.getHero().getInventory().size()==4);
		Square[][] sq = level.getLevel();
		
		assertTrue(sq[3][5].size()==0);
		
		Entity enemy = new Enemy(new Point(4,1), new Hunter());
		Entity treasure1 = new Treasure(new Point(4,3));
		level.addEntity(enemy);
		level.addEntity(treasure1);
		// Move the hero randomly as long as the hunter steps over the treasure1
		level.updateGameState(Direction.LEFT);
		level.updateGameState(Direction.RIGHT);
		level.updateGameState(Direction.DOWN);
		
		// Treasure is not picked by the enemy
		sq = level.getLevel();
		assertTrue(sq[4][3].peek().equals(treasure1));
		
	}
	/**
	 * US 1.3 Can move boulders
	 */
	@Test
	void testUS1_3() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		level.addWinConditions(new SwitchCondition());
		Entity boulder = new Boulder(new Point(1,2));
		Entity boulder1 = new Boulder(new Point(1,3));
		Entity boulder2 = new Boulder(new Point(2,1));
		Entity boulder3 = new Boulder(new Point(2,2));
		Entity swtch = new Switch(new Point(2,3));
		Entity wall = new Wall(new Point(4,1));
		Entity pit = new Pit(new Point(2,4));
		
		level.addEntity(boulder);
		level.addEntity(boulder1);
		level.addEntity(boulder2);
		level.addEntity(boulder3);
		level.addEntity(swtch);
		level.addEntity(wall);
		level.addEntity(pit);
		// Hero position before pushing boulder
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		// cannot go to the right because there are 2 consecutive boulders
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getPosition().equals(new Point(1,1)));
		
		// Able to push the boulder down because there is nothing blocking the boulder
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getPosition().equals(new Point(2,1)));
		
		// Boulder is blocked by Wall
		level.updateGameState(Direction.DOWN);
		assertTrue(level.getHero().getPosition().equals(new Point(2,1)));
		
		// Can push boulder to the switch
		assertTrue(level.getGameStatus().equals(GameStatus.NOT_DONE));
		level.updateGameState(Direction.RIGHT);
		assertTrue(level.getHero().getPosition().equals(new Point(2,2)));
		assertTrue(level.isComplete());
		
		// Push boulder to pit
		level.updateGameState(Direction.RIGHT);
		Square [][]sq = level.getLevel();
		
		// Boulder is not found at the top of the stack
		assertTrue(sq[2][4].peek().equals(pit));
		
	}
	
	/**
	 * Can win by completing hte puzzle
	 */
	@Test
	void testUS1_4() {
		ArrayList<CompletionCondition> winConditions = new ArrayList<>();
		GameLevel level = new GameLevel(winConditions, new ArrayList<>());
		level.addWinConditions(new SwitchCondition());
		level.addWinConditions(new EnemyCondition());
		level.addWinConditions(new TreasureCondition());
		
		Entity sword = new Sword(new Point(2,1));
		Entity boulder = new Boulder(new Point(1,2));
		Entity swtch = new Switch(new Point(1,3));
		Entity hunter = new Enemy(new Point(3,1), new Hunter());
		Entity treasure = new Treasure (new Point(2,2));
		
		level.addEntity(sword);
		level.addEntity(boulder);
		level.addEntity(swtch);
		level.addEntity(hunter);
		level.addEntity(treasure);
		
		// Try kiling the hunter with sword and check whether game is complete
		assertFalse(level.isComplete());
		level.updateGameState(Direction.DOWN);
		level.updateGameState(Direction.DOWN);
		// Game is not complete yet
		assertFalse(level.isComplete());
	
		// Go up 2 times to initial position
		level.updateGameState(Direction.UP);
		level.updateGameState(Direction.UP);
		
		// Push the boulder to the switch and check whether game is complete
		assertFalse(level.isComplete());
		level.updateGameState(Direction.RIGHT);
		// Game is not complete yet
		assertFalse(level.isComplete());
		
		// Pick the last treasure and complete game
		level.updateGameState(Direction.DOWN);
		// Game is complete now
		assertTrue(level.isComplete());
		
	
	}
}
