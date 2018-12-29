package game;

import biuoop.DrawSurface;
import collidable.Sprite;
import counters.Counter;
import levelinformation.BlockFactory;

import java.awt.Color;

/**
 * this class is and indicator that shows the score of out player onto the game screen.
 * @author Alon Levkovitch
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    private GameLevel game;
    private int font;
    private Color color;

    /**
     * create a new score indicator based on our game level and the counter of the score.
     * @param g the game level we are on.
     * @param c the counter of the score.
     */
    public ScoreIndicator(GameLevel g, Counter c) {
        this.score = c;
        this.game = g;
        this.font = g.getEdgeSize() - 5;
        this.color = BlockFactory.randomColor();
    }

    /**
     * notify the indicator that time has passed.
     * @param dt the time that had passed since the last call
     */
    public void timePassed(double dt) {
    }

    /**
     * draw the indicator to the screen.
     * @param d the drawsurface we are drawing on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawText(this.game.getWidth() / 2, this.game.getEdgeSize() - 3, "Score: " + this.score.getValue(), this.font);
    }

    /**
     * add this score indicator to the screen.
     */
    public void addToGame() {
        this.game.addSprite(this);
    }
}
