package counters;
import geometry.Ball;
import collidable.Block;
import game.GameLevel;
// a BlockRemover is in charge of removing blocks from the game, as well as keeping count
// of the number of blocks that remain.

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 * @author Alon Levkovitch
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * create a new block remover based on saving the game we are in and the counter
     * of the blocks in the game.
     * @param game we need this to know how to remove the block.
     * @param removedBlocks the number of blocks in the game.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    // Blocks that are hit and reach 0 hit-points should be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.

    /**
     * let the remover know that a block has been hit an remove it from the game.
     * also changes the color of the block if needed.
     * @param beingHit the block that is being hit.
     * @param hitter the ball that hits the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // if al alien shot hits an alien we do nothing.
        if (hitter.isAlienShot() && beingHit.alienOrNot()) {
            return;
        }
        // if the block hit is a paddle we remove a life.
        if (beingHit.isInPaddle()) {
            this.game.removeLife();
        }
        if (beingHit.getHitPoints() == 0) { // we remove that block only if it has 0 hit points.
            beingHit.removeFromGame(this.game);
            beingHit.removeHitListener(this);
            if (beingHit.alienOrNot()) {
                this.remainingBlocks.decrease(1);
            }
        }
    }
}
