package Items;

public class JustDroppedState implements BombState {
    @Override
    public void tick(Bomb bomb) {
    	bomb.setBombState(new FullLitState());
    }

    @Override
    public String getCode() {
        return "B-0";
    }
}
