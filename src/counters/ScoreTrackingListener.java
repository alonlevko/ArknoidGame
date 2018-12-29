package counters;
import collidable.Block;
import geometry.Ball;

/**
 * a hit listener that sees that blocks are removed and adds numbers to the score counter.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * create a score tracking listner and get the counter of the score.
     * @param scoreCounter the counter of the score we will add score to.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * let the score tracker know about the hit and add update the score accordingly.
     * @param beingHit the block that has been hit.
     * @param hitter the ball that hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.alienOrNot() && !hitter.isAlienShot()) {
            if (beingHit.getHitPoints() > 0) {
                this.currentScore.increase(5);
            } else if (beingHit.getHitPoints() == 0) {
                this.currentScore.increase(100);
            }
        } else {
            return;
        }
    }
}
