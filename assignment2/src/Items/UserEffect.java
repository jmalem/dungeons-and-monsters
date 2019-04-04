package Items;

import Game.Hero;

public interface UserEffect {
    /**
     * Use the potion and apply its effects on the hero
     * @param hero Hero that uses the potion
     */
    void use(Hero hero);

    /**
     * Returns the correct image for the potion
     * @return Image corresponding to the specific potion
     */
    String getCode();
}
