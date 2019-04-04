package Obstacles;

import Game.Entity;
import Game.GameStatus;
import Game.Hero;

import java.awt.Point;

public class Switch extends Obstacle {
	private SwitchState switchState;

    public Switch(Point coords) {
        super(coords, "Switch");
        this.switchState = SwitchState.UNSWITCHED;
    }

    public void setSwitchState(SwitchState newState){
    	if (newState == SwitchState.SWITCHED) {
    		this.setImage("Switched");
    	} else {
    		this.setImage("Switch");
    	}
        this.switchState = newState;
    }

    public boolean isTriggered(){ return (switchState == SwitchState.SWITCHED); }

    @Override
    public boolean isSwitch() {
        return true;
    }
}
