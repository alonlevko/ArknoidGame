package collidable;
import geometry.Ball;
import geometry.Rectangle;
import geometry.Point;
import geometry.Velocity;
import game.GameLevel;
import biuoop.DrawSurface;
import java.awt.Color;
import counters.HitListener;
import levelinformation.BlockCreator;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.awt.Image;


/**
 * the Block is out basic building block of the game. it is a sprite and a
 * collidable. it holds a rectangle, has hit points, color and a font to write
 * its hit points.
 *
 * @author Alon Levkovitch
 */


public class Block implements Collidable, Sprite, HitNotifier {
    private List<HitListener> hitListeners;
    private Rectangle rectangle;
    private Color color;
    private Color border;
    private Image img;
    // by default a block doesn't have/show hit points.
    private int hitPoints = -1;
    private boolean isAlien = false;
    private boolean isPaddle = false;

    /**
     * create a new block that has hitpoints. use a rectangle, color and
     * number of hit points we want it to have.
     * @param rect the rectangle that is the edges of the block.
     * @param r the color of the block.
     * @param hitPoints the hit points the block has to start with.
     */
    public Block(Rectangle rect, Color r, int hitPoints) {
        this.rectangle = rect;
        this.color = r;
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * create a new block that has hitpoints. use a rectangle, color and
     * number of hit points we want it to have.
     * @param rect the rectangle that is the edges of the block.
     * @param r the color of the block.
     * @param hitPoints the hit points the block has to start with.
     */
    public Block(Rectangle rect, Image r, int hitPoints) {
        this.rectangle = rect;
        this.img = r;
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * create a new block that has no hit points (edge, paddle).
     * @param rect the rectangular shape of this block.
     * @param r the color of the block.
     */
    public Block(Rectangle rect, Color r) {
        this.rectangle = rect;
        this.color = r;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * create a new block based on the block definer and the x and y starting positions
     * of the block.
     * @param definer the block definer of the block
     * @param xPos the x of the upper left point
     * @param yPos the y of the upper left point
     */
    public Block(BlockCreator definer, int xPos, int yPos) {
        // create the rectangle of the block.
        this.rectangle = new Rectangle(new Point(xPos, yPos), definer.getWidth(), definer.getHeight());
        if (definer.getStroke() != null) { // if we have a stroke save it.
            this.border = definer.getStroke();
        }
        if (definer.getImage() != null) { // if we have an image it will be the image of the block.
            try { // read the image into the block.
                InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(definer.getImage());
                this.img = ImageIO.read(in);
            } catch (IOException i) {
                System.out.println("error in reading image from block definer");
            }
        }
        if (definer.getFill() != null) { // if we have a fill for the block.
            this.color = definer.getFill();
        }
        this.hitPoints = definer.getHitPoints();
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * return the image of the block.
     * @return the image of the block.
     */
    public Image getImg() {
        return img;
    }

    /**
     * return the rectangle that his class holds.
     * @return the rectangle of this block.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * give this block a new rectangle.
     * @param d the new rectangle that will be the rectangle of the block.
     */
    public void setRectangle(Rectangle d) {
        this.rectangle = d;
    }

    /**
     * return the color of the block.
     * @return the color of the block.
     */
    public Color getColor() {
        return color;
    }

    /**
     * set the color of the block to be a color.
     * @param c the new color of the block.
     */
    public void setColor(Color c) {
        this.color = c;
    }
    /**
     * get the rectangle of the block, this time treat it as a collishion
     * rectangle and not just a regular one.
     * @return the rectangle of the block.
     */
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    /**
     *uses the current velocity of the ball and the point of collosion with the
     * block to calculate the balls new velocity after the impact.
     *
     * @param collisionPoint the point were the collision is happening.
     * @param currentVelocity the current velocity of the object hitiing this
     *                        block.
     * @param hitter the ball that hit this block.
     * @return the new velocity of the object that hits the ball.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // check if we have more than 0 hit points.
        if (this.hitPoints > 0) {
            if (hitter.isAlienShot()) {
                if (this.isAlien) {
                    this.hitPoints = this.hitPoints;
                } else {
                    this.hitPoints--;
                }
            } else {
                // if we do reduce 1 hit point for each hit.
                this.hitPoints--;
            }
        }
        // notify the listeners that a hit accured.
        this.notifyHit(hitter);
        // check if the collision is on left side of the rectangle.
        if (collisionPoint.getX() == this.rectangle.getUpperLeft().getX()) {
            // change only the horizontal direction of the ball.
            return new Velocity(-1 * currentVelocity.getDx(),
                    currentVelocity.getDy(), currentVelocity.getSpeed());
        // check if the colloshion is on the right side of the rectangle.
        } else if (collisionPoint.getX()
                == (this.rectangle.getUpperLeft().getX()
            + this.rectangle.getWidth())) {
            //change only the horizontal direction of the ball.
            return new Velocity(-1 * currentVelocity.getDx(),
                    currentVelocity.getDy(), currentVelocity.getSpeed());
        // check if the collision is on the top of the block.
        } else if (collisionPoint.getY()
                == this.rectangle.getUpperLeft().getY()) {
            // change only the veertical direction of the ball.
            return new Velocity(currentVelocity.getDx(),
                    -1 * currentVelocity.getDy(),
                    currentVelocity.getSpeed());
        // check if the collision is on the bottom of the block.
        } else if (collisionPoint.getY()
                == (this.rectangle.getUpperLeft().getY()
                + this.rectangle.getHeight())) {
            // change only the veertical direction of the ball.
            return new Velocity(currentVelocity.getDx(),
                    -1 * currentVelocity.getDy(),
                    currentVelocity.getSpeed());
        } else {
            return null; // error
        }
    }

    /**
     * use a drawsurface to draw the block to the screen.
     * @param d the drawsurface we are drawing on.
     */
    public void drawOn(DrawSurface d) {
        // if we have an image draw it as the block and don't use the color of the block.
        if (this.img != null) {
            d.drawImage((int) this.rectangle.getUpperLeft().getX(),
                    (int) this.rectangle.getUpperLeft().getY(), this.img);
        } else if (this.color != null) {
                // set the drawing color to be the color of the block.
                d.setColor(this.color);
                // draw the inside of the block.
                d.fillRectangle((int) this.rectangle.getUpperLeft().getX(),
                        (int) this.rectangle.getUpperLeft().getY(),
                        (int) this.rectangle.getWidth(),
                        (int) this.rectangle.getHeight());
        }
        if (this.border != null) {
            // we draw all outer lines in black.
            d.setColor(this.border);
            // draw the outer lines of the block.
            d.drawRectangle((int) this.rectangle.getUpperLeft().getX(),
                    (int) this.rectangle.getUpperLeft().getY(),
                    (int) this.rectangle.getWidth(),
                    (int) this.rectangle.getHeight());
        }
    }

    /**
     * the block does nothing when the time passes.
     * @param dt the time that passed since the last call.
     */
    public void timePassed(double dt) {
        return;
    }

    /**
     * we add this block to the game enviroment and the sprite collection.
     * @param g the game we are adding this block to.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
        this.addHitListener(g.getBlockKill());
    }

    /**
     * removes the block from the game when it is dystroied.
     * @param game the game we are removing the block from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    /**
     * add a hit listner to the blocks hit listener list.
     * @param hl the hit listner we are adding.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * remove a hit listner from the hit listener list.
     * @param hl the hit listner we are removing.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * notify all of the hit listners that a hit has accured.
     * @param hitter the ball that hits this block.
     */
    public void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * return the number of hit points the ball has.
     * @return return the hit points of the block.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * get the height of the block.
     * @return the height of the block.
     */
    public int getHeight() {
        return (int) this.rectangle.getHeight();
    }

    /**
     * make the block to be an alien.
     * @param b send true to make it an alien
     */
    public void beAlien(boolean b) {
        this.isAlien = b;
    }

    /**
     * check if this block is an enemy.
     * @return true - the block is an alien. false - it is not.
     */
    public boolean alienOrNot() {
        return this.isAlien;
    }

    /**
     * get the list of hit listeners of the block.
     * @return the list of hit listeneres.
     */
    public List<HitListener> getHitListeners() {
        return this.hitListeners;
    }

    /**
     * add a list of hit listners to the block.
     * @param list the list of hit listners we are adding.
     */
    public void addAllHitListeners(List<HitListener> list) {
        this.hitListeners.addAll(list);
    }

    /**
     * make the block a paddle.
     */
    public void bePaddle() {
        this.isPaddle = true;
    }

    /**
     * check if the block is a paddle or not.
     * @return true - it is a paddle. false - it is not.
     */
    public boolean isInPaddle() {
        return this.isPaddle;
    }
}
