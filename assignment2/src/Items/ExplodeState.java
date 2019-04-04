package Items;

public class ExplodeState implements BombState {
    @Override
    public void tick(Bomb bomb) {
        // TODO
        // Add code to make the bomb explode
    }

    @Override
    public String getCode() {
        return "ExplodeBomb";
    }

	@Override
	public boolean isExplodeState() {
		return true;
	}
    
    
}
