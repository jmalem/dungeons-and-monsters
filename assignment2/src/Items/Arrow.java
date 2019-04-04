package Items;
import java.awt.Point;
import java.util.ArrayList;

import Enemies.Enemy;
import Game.Direction;
import Game.Entity;
import Game.GameLevel;
import Game.Square;
import Obstacles.*;

public class Arrow extends Weapon {

	public Arrow(Point coords){
        super(coords, "Arrow");
        this.uses = 1;
    }

	/**
	 * Shoot the arrow towards a certain direction
	 * @param d Direction to shoot the Arrow
	 * @param p Point from which arrow is shot
	 * @param game Game level instance
	 * @return Whether or not an arrow has been successfully shot
	 */
	public boolean shoot(Direction d, Point p, GameLevel game) {
    	Point x = new Point(p.x,p.y);
    	switch (d) {
			case ARROWUP:
				x.setLocation(x.x - 1, x.y);
				while (withinBound(x)) {
					if (checkImpact(x, game)) {
						return true;
					}
					x.setLocation(x.x - 1, x.y);
				}
				break;
			case ARROWDOWN:
				x.setLocation(x.x + 1, x.y);
				while (withinBound(x)) {
					if (checkImpact(x, game)) {
						return true;
					}
					x.setLocation(x.x + 1, x.y);
				}
				break;
			case ARROWLEFT:
				x.setLocation(x.x, x.y - 1);
				while (withinBound(x)) {
					if (checkImpact(x, game)) {
						return true;
					}
					x.setLocation(x.x, x.y - 1);
				}
				break;
			case ARROWRIGHT:
				x.setLocation(x.x, x.y + 1);
				while (withinBound(x)) {
					if (checkImpact(x, game)) {
						return true;
					}
					x.setLocation(x.x, x.y + 1);
				}
				break;
    	}
    	return false;
    }

	/**
	 * Checks whether or not an arrow has impacted another entity when fired
	 * @param p Point to check for impact
	 * @param game Game level instance
	 * @return Whether or not arrow impacted
	 */
	public boolean checkImpact(Point p, GameLevel game) {
    	Square[][] level = game.getLevel();
    	Square cell = level[p.x][p.y];

		// Impact only happens if there is an entity on the cell
    	if (cell.size() > 0) {
    		for (int i = 0; i < cell.size(); i++) {
				Entity entityOnCell = cell.get(i);
				if (entityOnCell.isObstacle()) {
					Obstacle o = (Obstacle) entityOnCell;
					if (!o.isPit() || !o.isSwitch()) {
						// Hits a Boulder, Door, Wall or Exit
						return true;
					}
				} else if (entityOnCell.isEnemy()) {
					// Enemy is killed
					level[p.x][p.y].remove(entityOnCell);
					game.getEnemies().remove(entityOnCell);
					return true;
				}
			}
		}
    	return false;
    }

	/**
	 * Checks whether a coordinate is within the map
	 * @param coords Coordinate to check
	 * @return Whether coordinate is within bounds of the map
	 */
	public boolean withinBound(Point coords){
        return !(coords.x >= GameLevel.LEVEL_SIZE ||
                coords.x < 0 ||
                coords.y >= GameLevel.LEVEL_SIZE ||
                coords.y < 0);
    }

	@Override
	public boolean isArrow() {
		return true;
	}
}
