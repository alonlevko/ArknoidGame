package animations;
import biuoop.DrawSurface;

/**
 * the pause screen of out game. the screen the user sees when he stops the game.
 * @author Alon Levkovitch
 */
public class PauseScreen implements Animation {
    private boolean stop;

    /**
     * create a new pause screen based on the keyboard sensor.
     */
    public PauseScreen() {
        this.stop = false;
    }

    /**
     * draw one frame of the pause screen on the draw surface.
     * @param d the draw surface we are drawing the animation on.
     * @param dt the time that passes between calls.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    /**
     * check if the animation needs to stop of not.
     * @return true - we need to stop, false - we don't need to stop.
     */
    public boolean shouldStop() { return false; }
}
