package Items;

import Game.Entity;
import java.awt.Point;

abstract public class Weapon extends Item {
    int uses;

	public Weapon(Point coords, String code){
        super(coords, code);
    }

    /**
     * Using a weapon will kill an enemy entity
     * @param entity Enemy entity weapon is used against
     */
    @Override
    public void useItem(Entity entity) {
        /*
        Uncomment after Enemy has been implemented
        Using a weapon with one use kills the enemy
        Enemy enemy = (Enemy) entity;
        entity.die();
        */
        uses--;
    }
    
    public int getUses() {
    	return uses;
    }
}
