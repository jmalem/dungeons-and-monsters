package Items;

import Game.Hero;
import Game.HeroState;

public class Hover implements UserEffect {
    @Override
    public void use(Hero hero) {
        hero.setHeroState(HeroState.HOVER);
    }

    @Override
    public String getCode() {
        return "Hover";
    }
}
