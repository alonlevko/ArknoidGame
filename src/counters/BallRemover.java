package counters;
import geometry.Ball;
import collidable.Block;
import game.GameLevel;

/**
 * a hit listner that is added to the bottom edge of the screen to notice that
 * it was hit an remove the ball from the game.
 */
public class BallRemover implements HitListener {
    private GameLevel game;

    /**
     * create a new ball remover based on saving the number of balls and the level.
     * @param game the game we are in.
     */
    public BallRemover(GameLevel game) {
        this.game = game;
    }

    // Blocks that are hit and reach 0 hit-points should be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.

    /**
     * let the listner know that a hit event accured.
     * @param beingHit the block that is being hit.
     * @param hitter the ball that is hitting the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        // if the level/game ends we want to remove this hit listner.
        if (this.game.getLives() == 0) {
            beingHit.removeHitListener(this);
        }
    }
}
