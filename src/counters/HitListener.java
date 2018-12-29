package counters;
import collidable.Block;
import geometry.Ball;

/**
 * this is the interface of all the objects that can "hear" other objects hits.
 * @author Alon Levkovitch
 */
public interface HitListener {
    /**
     * notifies the hit listener that an object has been hit.
     * @param beingHit the block that has been hit.
     * @param hitter the ball that hit the block.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
