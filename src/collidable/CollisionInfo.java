package collidable;
import geometry.Point;
/**
 * used to hold the information about collisions. holds the collision point and
 * what was the collision with.
 *
 * @author Alon Levkovitch
 */
public class CollisionInfo {
        private Point collisionPoint;
        private Collidable collisionObject;

    /**
     * creates a new collision info.
     * @param p the collision point.
     * @param c the object we collided with.
     */
    public CollisionInfo(Point p, Collidable c) {
        this.collisionPoint = p;
        this.collisionObject = c;
    }

    /**
     * return the collision point.
     * @return the collision point.
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * return the object we had collided with.
     * @return the object we had collided with.
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}
