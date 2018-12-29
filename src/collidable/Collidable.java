package collidable;
import geometry.Ball;
import geometry.Point;
import geometry.Velocity;
import geometry.Rectangle;
/**
 * this is an interface of all the classes that the ball can collide with. sets
 * the basic methods that are needed to create proper collisions.
 * @author Alon levkovitch
 */
public interface Collidable {
    /**
     * returns the rectangle of the object that we are going to collide with.
     * @return the rectangle of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * uses to point of collision and the current velocity of the colliding
     * object to calculate the new velocity of the object.
     *
     * @param collisionPoint the point were the collision is happening.
     * @param currenVelocity the current velocity of the colliding object.
     * @param hitter the ball that is hitting this collidable.
     * @return the new velocity of the colliding object.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currenVelocity);
}
