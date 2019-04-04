package Items;

public class AlmostLitState implements BombState {
    @Override
    public void tick(Bomb bomb) {
        bomb.setBombState(new ExplodeState());
    }

    @Override
    public String getCode() {
        return "AlmostLitBomb";
    }

	@Override
	public boolean isAlmostLitState() {
		return true;
	}   
}
