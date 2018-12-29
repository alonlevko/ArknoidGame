package animations;

import biuoop.DrawSurface;

/**
 * the animation that shows the end screen of our game.
 * @author Alon Levkovitch
 */
public class EndScreen implements Animation {
    private boolean win;
    private int score;

    /**
     * create a new end screen. we need a keyboard sensor, the know if we won or
     * lost, and the score the played has got to in the game.
     * @param win true - we win. false - we lost.
     * @param score the score the player got to.
     */
    public EndScreen(boolean win, int score) {
        this.win = win;
        this.score = score;
    }

    /**
     * draw to the screen on frame of the animation.
     * @param d the draw surface we are drawing the animation on.
     * @param dt the time that passes between calls.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // if we won.
        if (this.win) {
            d.drawText(10, d.getHeight() / 2, "You Win! Your score is " + this.score, 32);
        } else { // if we lost.
            d.drawText(10, d.getHeight() / 2, "Game Over. Your score is " + this.score, 32);
        }
    }

    /**
     * check if we need to stop or not.
     * @return true - we need to stop. false - we don't need to stop.
     */
    public boolean shouldStop() { return false;  }
}
