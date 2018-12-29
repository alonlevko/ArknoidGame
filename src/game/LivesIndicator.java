package game;
import biuoop.DrawSurface;
import collidable.Sprite;
import counters.Counter;
import levelinformation.BlockFactory;
import java.awt.Color;
/**
 * this indicator shows us on the screen the number of lives we have left in the game.
 * @author Alon Lekvovitch
 */
public class LivesIndicator implements Sprite {
    private Counter lives;
    private GameLevel game;
    private int font;
    private Color color;

    /**
     * create a new lives indicator base on the counter of lives and the game.
     * @param g the game level the indicator is shown on.
     * @param c the counter of the number of lives.
     */
    public LivesIndicator(GameLevel g, Counter c) {
        this.lives = c;
        this.game = g;
        this.font = g.getEdgeSize() - 5;
        this.color = BlockFactory.randomColor();
    }

    /**
     * notifiy the indicator that time has passed.
     * @param dt the time that had passed since the last call
     */
    public void timePassed(double dt) {
    }

    /**
     * draw the indicator on to the screen using the drawsurface.
     * @param d the drawsurface we are drawing on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawText(30, this.game.getEdgeSize() - 3, "Lives: " + this.lives.getValue(), this.font);
    }

    /**
     * add this sptite to the game.
     */
    public void addToGame() {
        this.game.addSprite(this);
    }
}
