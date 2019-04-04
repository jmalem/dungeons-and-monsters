package Items;

public class FullLitState implements BombState {
    @Override
    public void tick(Bomb bomb) {
        bomb.setBombState(new SemiLitState());
    }

    @Override
    public String getCode() {
        return "FullLitBomb";
    }

	@Override
	public boolean isFullLitState() {
		return true;
	}
    
    
}
