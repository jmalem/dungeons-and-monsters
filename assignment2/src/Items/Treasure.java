package Items;

import Game.Entity;
import java.awt.Point;

public class Treasure extends Item {
    public Treasure(Point coords){
        super(coords, "Treasure");
    }

    @Override
    public void useItem(Entity entity) {
        //Using treasure does nothing
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}
