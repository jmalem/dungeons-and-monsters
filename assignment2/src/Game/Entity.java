package Game;

import Enemies.Enemy;
//import javafx.scene.image.Image;

import java.awt.Point;

public abstract class Entity {
    
	private Point position;
    private String image;

	public Entity(Point coords, String image){
        this.position = coords;
        this.image = image;
    }

    public GameStatus heroInteract(Hero hero){
	    return GameStatus.NOT_DONE;
    }

    public void setPosition(Point coords){
        this.position = coords;
    }
    
    public Point getPosition(){ 
    	return position; 
	}
    
    public void setImage(String image) {
		this.image = image;
	}
    
    public String getImage() {
    	return image;
    }

    public Entity makeCopy() {
	    return this;
    }
    
    public boolean isMovable() {
    	return false;
    }

    public boolean isObstacle() {
	    return false;
    }

    public boolean isEnemy() {
	    return false;
    }

    public boolean isItem() {
	    return false;
    }

    public boolean isHero() {
	    return false;
    }
}
