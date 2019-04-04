package Items;
import java.awt.Point;

public class Sword extends Weapon {
    public Sword(Point coords){
        super(coords, "Sword");
        this.uses = 5;
    }

    @Override
    public boolean isSword() {
        return true;
    }
}
