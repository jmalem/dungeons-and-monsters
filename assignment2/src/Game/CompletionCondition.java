package Game;

public interface CompletionCondition {
	abstract public boolean checkCondition(GameLevel level);

	@Override
	public String toString();

}
