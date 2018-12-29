package utils;

import biuoop.DrawSurface;
import collidable.Sprite;
import java.awt.Color;

/**
 * this class is a sprite that is the background of the main menue.
 */
public class MenuBackground implements Sprite {
    /**
     * create a new menu background.
     */
    public MenuBackground() {
    }
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.CYAN);
        d.fillCircle(750, 500, 50);
        d.setColor(Color.RED);
        d.fillCircle(50, 50, 50);
        d.setColor(Color.GREEN);
        d.fillCircle(50, 550, 50);
        d.setColor(Color.MAGENTA);
        d.fillCircle(750, 50, 50);
    }

    @Override
    public void timePassed(double dt) { }
}
