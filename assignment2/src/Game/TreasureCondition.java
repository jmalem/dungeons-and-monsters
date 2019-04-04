package Game;

import java.util.ArrayList;

import Items.Item;
import Items.Treasure;

public class TreasureCondition implements CompletionCondition {

	@Override
	public boolean checkCondition(GameLevel level) {
		// Checks if all treasure has been collected
		ArrayList<Item> items = level.getItems();
		for (Item item : items){
			if (item.isTreasure()){
				return false;
			}
		}

		return true;
	}
	
	@Override
	public String toString() {
		return "Treasure";
	}
}
