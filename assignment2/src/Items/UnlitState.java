package Items;

public class UnlitState implements BombState {
    @Override
    public void tick(Bomb bomb) {
        bomb.setBombState(new JustDroppedState());
    }

    @Override
    public String getCode() {
        return "Bomb";
    }

	@Override
	public boolean isUnlitState() {
		return true;
	}
    
    
}
