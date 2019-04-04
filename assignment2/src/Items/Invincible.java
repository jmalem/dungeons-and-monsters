package Items;

import Game.Hero;
import Game.HeroState;

public class Invincible implements UserEffect {
    @Override
    public void use(Hero hero) {
        hero.setHeroState(HeroState.INVINCIBLE);
        hero.initCounter();
    }

    @Override
    public String getCode() {
        return "Invincible";
    }
}
