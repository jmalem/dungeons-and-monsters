package Game;

import Items.Item;
import Obstacles.Obstacle;
import Obstacles.Pit;
import Obstacles.Switch;

import java.awt.*;

public interface Movable {
    /**
     * Determines if the coordinates are within the boundaries of the map
     * @param coords Point to check
     * @return Boolean if coordinates are within boundaries
     */
    default boolean withinBound(Point coords){
        return !(coords.x >= GameLevel.LEVEL_SIZE ||
                coords.x < 0 ||
                coords.y >= GameLevel.LEVEL_SIZE ||
                coords.y < 0);
    }

    /**
     * Moves entity's coordinates
     * @param direction Direction to move to
     * @param e Entity to move
     * @return true if move was successful
     */
    default boolean move(Direction direction, GameLevel level, Entity e){
        Point coordToMoveInto = nextPosition(direction, e.getPosition());
        Point entityPosition = e.getPosition();

        if (withinBound(coordToMoveInto) && isMovableTo(coordToMoveInto, direction, level)){
            // Move entity to new position
            level.popFromSquare(entityPosition);
            e.setPosition(coordToMoveInto);
            level.addEntityToSquare(e, coordToMoveInto);
            return true;
        }

        return false;
    }

    /**
     * Returns coordinate Entity will move into if it moves in the direction
     * @param d direction Entity is moving towards
     * @return Coordinate Entity will move into
     */
    default Point nextPosition (Direction d, Point src){
        switch (d){
            case UP:
                return new Point(src.x - 1, src.y);
            case DOWN:
                return new Point(src.x + 1, src.y);
            case LEFT:
                return new Point(src.x, src.y - 1);
            case RIGHT:
                return new Point(src.x, src.y + 1);
            default:
            	return src;
        }
    }


    /**
     * Checks if Entity can move to a pair of coordinates on the level map
     * @param coords Coordinate entity is moving to
     * @param direction Direction entity is moving towards
     * @param level Current GameLevel
     * @return If entity can move to given coordinates
     */
    default boolean isMovableTo(Point coords, Direction direction, GameLevel level){
        Entity e = level.hasEntity(coords);
        return (e == null ||
                e.isHero() ||
                e.isItem() ||
                (e.isObstacle() && (((Obstacle)e).isSwitch() || ((Obstacle)e).isPit())));
    }

}
