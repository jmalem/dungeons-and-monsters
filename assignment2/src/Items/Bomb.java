package Items;

import Game.Entity;

import java.awt.Point;

public class Bomb extends Item {
    private BombState bombState;

    public Bomb(Point coords){
        super(coords, new UnlitState().getCode());
        this.bombState = new UnlitState();
    }

    
    /**
     * Places the bomb onto the map and triggers it
     * @param coords Hero's current location (where to place bomb)
     */
    /*public void placeBomb(Point coords){
        setPosition(coords);
        useItem(this);
    }*/

    /**
     * Triggers bomb by lighting it up.
     * Has 3 turns before it explodes
     * @param entity Bomb to light up
     */
    @Override
    public void useItem(Entity entity) {
        Bomb bomb = (Bomb) entity;
        bombState.tick(bomb);
    }
    

    @Override
	public boolean isBomb() {
		return true;
	}


	public void setBombState(BombState newState){
        this.bombState = newState;
    }
    
    public BombState getBombState() {
    	return this.bombState;
    }
    
    @Override
    public String getImage() {
    	return this.getBombState().getCode();
    }
}
