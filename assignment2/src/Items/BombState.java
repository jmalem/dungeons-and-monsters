package Items;

public interface BombState {
    /**
     * Advance the bomb's state by one turn
     * @param bomb Bomb to be lit and exploded
     */
    void tick(Bomb bomb);

    /**
     * Returns the proper image corresponding to the state of the bomb
     * @return Code corresponding to the state of the bomb
     */
    String getCode();
    
    default boolean isExplodeState() {
    	return false;
    }
    
    default boolean isFullLitState() {
    	return false;
    }
    
    default boolean isSemiLitState() {
    	return false;
    }
    
    default boolean isAlmostLitState() {
    	return false;
    }
    
    default boolean isUnlitState() {
    	return false;
    }
    
}
