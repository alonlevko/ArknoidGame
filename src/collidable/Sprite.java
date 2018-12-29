package collidable;
import biuoop.DrawSurface;

/**
 *Sprites are object that can be drawn to the screen and they also can be
 * notified that time has passed from the start of the game.
 *
 * @author Alon Levkovitch
 */
public interface Sprite {
    /**
     * uses the drawsurface to draw the sprite to the screen.
     * @param d the drawsurface we are drawing on.
     */
    void drawOn(DrawSurface d);

    /**
     * notifies the sprite that time has passed from the start of the game. each
     * call to this function is one animation/"game step".
     * @param dt - the time passed from the last time time has passed.
     */
    void timePassed(double dt);
}
