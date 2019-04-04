package Game;

import java.util.ArrayList;

public class Square extends ArrayList<Entity>{
    @Override
    public String toString() {
        return String.valueOf(this.peek().getImage());
    }

    public Entity peek(){
        return this.get(this.size() - 1);
    }

    public Entity pop(){
        return this.remove(this.size() - 1);
    }
    
    public Entity popIndex(int index) {
    	return this.remove(index);
    }
}
