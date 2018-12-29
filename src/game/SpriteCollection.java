package game;
import collidable.Sprite;
import biuoop.DrawSurface;
import java.util.List;
import java.util.ArrayList;


/**
 * used to save the collection of sprites we have in this game. notifies the
 * sprites of different events that happen in the game.
 *
 * @author Alon Levkovitch
 */
public class SpriteCollection {
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    /**
     * adds a sprite to the game.
     * @param s the sprite we are adding to the game.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * notifies all the sprites in out collection that time passed.
     * @param dt the time that had passed since the last call
     */
    public void notifyAllTimePassed(double dt) {
        Sprite spr;
        List<Sprite> copy = new ArrayList<Sprite>(sprites);
        for (int i = 0; i < copy.size(); i++) {
            spr = copy.get(i);
            spr.timePassed(dt);
        }
    }

    /**
     * draws all of the sprites on to the screen.
     * @param d the draw surface we are drawing the sprites on.
     */
    public void drawAllOn(DrawSurface d) {
        Sprite spr;
        List<Sprite> copy = new ArrayList<>(sprites);
        for (int i = 0; i < copy.size(); i++) {
            spr = copy.get(i);
            spr.drawOn(d);
        }
    }

    /**
     * remove a sprite from this collection.
     * @param s the sprite we are removing
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * add a list of sprites to our sprite list.
     * @param list the list of sprites we are adding.
     */
    public void addAllSprites(List<Sprite> list) {
        for (Sprite s : list) {
            this.addSprite(s);
        }
    }
}
