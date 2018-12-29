package game;

import biuoop.DrawSurface;
import collidable.Sprite;
import levelinformation.BlockFactory;

import java.awt.Color;

/**
 * this is a sprite that is responsible to show the name of the level the game is
 * in to the screen.
 * @author Alon Levkovitch
 */
public class LevelIndicator implements Sprite {
    private GameLevel game;
    private int font;
    private String name;
    private Color color;
    private int round;

    /**
     * create a new level indicator using a game level object.
     * @param g we need this to know hot to place the indicator and get the counter.
     * @param round the round of battle we are in.
     */
    public LevelIndicator(GameLevel g, int round) {
        this.game = g;
        // we want the font of the text to be a bit smaller than the edge size.
        this.font = g.getEdgeSize() - 5;
        this.name = this.game.getLevel().levelName();
        this.color = BlockFactory.randomColor();
        this.round = round;
    }

    /**
     * draw the name of the level to the screen.
     * @param d the drawsurface we are drawing on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawText(this.game.getWidth() - 250, this.game.getEdgeSize() - 3,
                "Level: " + this.name + " " + this.round, this.font);
    }

    /**
     * notifiy the sprite that time has passed.
     * @param dt the time that had passed since the last call
     */
    public void timePassed(double dt) {
    }

    /**
     * add this indicator to the game.
     */
    public void addToGame() {
        this.game.addSprite(this);
    }
}
