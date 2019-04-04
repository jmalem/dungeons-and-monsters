package Items;

import Game.Entity;
import Game.GameLevel;
import Game.GameStatus;
import Game.Hero;
import java.awt.Point;

public class Potion extends Item {
    private UserEffect userEffect;

    public Potion(Point coords, UserEffect effect){
        super(coords, effect.getCode());
        this.userEffect = effect;
    }

    @Override
    public GameStatus heroInteract(Hero hero) {
        userEffect.use(hero);
        return GameStatus.NOT_DONE;
    }

    /**
     *
     * @param entity Hero (class) character who uses the potion
     */
    @Override
    public void useItem(Entity entity){
        // Places the potion in effect
        Hero hero = (Hero) entity;
        userEffect.use(hero);
    }
    
    public String getImage() {
    	return userEffect.getCode();
    }
}
