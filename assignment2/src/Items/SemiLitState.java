package Items;

public class SemiLitState implements BombState {
    @Override
    public void tick(Bomb bomb) {
        bomb.setBombState(new AlmostLitState());
    }

    @Override
    public String getCode() {
        return "SemiLitBomb";
    }

	@Override
	public boolean isSemiLitState() {
		return true;
	}
}
