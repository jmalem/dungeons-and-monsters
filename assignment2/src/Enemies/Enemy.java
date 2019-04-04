package Enemies;

import Game.*;
import Items.Sword;
import Items.Weapon;

import java.awt.Point;

public class Enemy extends Entity implements Movable {
    private EnemyBehaviour enemyType;

    public Enemy(Point coords, EnemyBehaviour type){
        super(coords, type.getCode());
        enemyType = type;
    }

	@Override
	public GameStatus heroInteract(Hero hero) {
		Sword sword = hero.getSword();
    	if (sword != null){
			// Hero kills the enemy
    		hero.useSword(sword);
    		return GameStatus.NOT_DONE;
		} else if (hero.getHeroState().equals(HeroState.INVINCIBLE)) {
			return GameStatus.NOT_DONE;
		}
		// Hero does not have sword and is killed by enemy
		
		return GameStatus.LOSE;
	}

	/**
	 * Logic for enemy making decisions based on hero's location
	 * @param map Map of the level
	 * @param heroLocation Current coordinates of the Hero in the map
	 */
	public void makeMove(GameLevel map, Point heroLocation) {
    	Direction d = enemyType.getDecision(map, this.getPosition(), heroLocation);
    	move(d, map, this);
    }
	
	public EnemyBehaviour getBehaviour() {
		return enemyType;
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public boolean isEnemy() {
		return true;
	}
}
