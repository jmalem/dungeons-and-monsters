package Items;

import Game.Entity;
import Game.GameStatus;
import Game.Hero;

import java.awt.Point;

abstract public class Item extends Entity {

    public Item(Point coords, String code){
        super(coords, code);
    }

	abstract public void useItem(Entity entity);

    @Override
    public GameStatus heroInteract(Hero hero) {
        hero.pickUpItem(this);
        return GameStatus.NOT_DONE;
    }

    @Override
    public boolean isItem() {
        return true;
    }

    public boolean isBomb() {
    	return false;
    }

    public boolean isKey(){
        return false;
    }

    public boolean isSword(){
        return false;
    }

    public boolean isArrow(){
        return false;
    }

    public boolean isTreasure() {
        return false;
    }

}
