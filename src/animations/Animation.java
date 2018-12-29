package animations;
import biuoop.DrawSurface;

/**
 * this is an interface of all the animations we have and that we can run using
 * ths animation runner.
 *
 * @author Alon Levkovitch
 */
public interface Animation {
    /**
     * run one frame of the animation.
     * @param d the draw surface we are drawing the animation on.
     * @param dt the time that passes between calls.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * changes the stopping member of the animation.
     * @return true - stop the animation. false - continue running.
     */
    boolean shouldStop();
}
