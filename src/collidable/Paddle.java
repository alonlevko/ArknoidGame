package collidable;
import counters.HitListener;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Ball;
import java.awt.Color;

/**
 * the paddle is the player in the game. that ball bounces of it and it can
 * move to the right or the left based on what the used presses.
 *
 * @author Alon Levkovitch
 */

public class Paddle implements Collidable, Sprite, HitNotifier {
    private biuoop.KeyboardSensor keyboard;
    private Block block;
    private GameLevel game;
    // the distance the paddle moves in each step.
    private int step;
    private long lastShot;

    /**
     * create a new paddle based on a block and the game we want the paddle to
     * be in.
     *
     * @param b the block the paddle is based on.
     * @param g the game the paddle will be in.
     */
    public Paddle(Block b, GameLevel g) {
        this.block = b;
        b.bePaddle();
        this.keyboard = g.getKeyboard();
        this.game = g;
        this.step = g.getLevel().paddleSpeed();
    }

    /**
     * move the paddle one step to the left.
     * @param dt the change in time.
     */
    private void moveLeft(double dt) {
        this.block.setRectangle(new Rectangle(
                new Point(this.block.getRectangle().getUpperLeft().getX()
                        - this.step * dt,
                        this.block.getRectangle().getUpperLeft().getY()),
                        this.block.getRectangle().getWidth(),
                        this.block.getRectangle().getHeight()));
    }

    /**
     * move the paddle one step to the right.
     * @param dt the change in time
     */
    private void moveRight(double dt) {
        this.block.setRectangle(new Rectangle(
                new Point(this.block.getRectangle().getUpperLeft().getX()
                        + this.step * dt,
                        this.block.getRectangle().getUpperLeft().getY()),
                        this.block.getRectangle().getWidth(),
                        this.block.getRectangle().getHeight()));
    }

    /**
     * notify the paddle that time passed. check for keyboard click and move the
     * paddle based on which key was clicked.
     * @param dt the time that padded from the last call.
     */
    public void timePassed(double dt) {
        // check if the left key on the keyboard has been pressed.
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            // make sure we do not go out of the edges.
            if (this.block.getRectangle().getUpperLeft().getX()
                    > (this.game.getEdgeSize())) {
                moveLeft(dt);
            }
        // check if the right key on the keyboard has been pressed.
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            // make sure we do no go out of the edges.
            if ((this.block.getRectangle().getUpperLeft().getX()
                    + this.block.getRectangle().getWidth())
                    < (this.game.getWidth() - game.getEdgeSize())) {
                moveRight(dt);
            }
        }
        if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - this.lastShot <= 350) {
                return;
            } else {
                this.lastShot = System.currentTimeMillis();
                Ball b = new Ball(new Point(this.block.getRectangle().getUpperLeft().getX()
                        + this.block.getRectangle().getWidth() / 2,
                        this.block.getRectangle().getUpperLeft().getY() - 4), 3, Color.white);
                b.setVelocity(Velocity.fromAngleAndSpeed(0, 600));
                b.addToGame(this.game);
            }
        }
    }

    /**
     * draw the paddle on the draw surface inputed.
     * @param d the draw surface we are drawing on.
     */
    public void drawOn(DrawSurface d) {
        this.block.drawOn(d);
    }

    /**
     * return the rectangle of the paddle.
     * @return the rectangle of the block of this paddle.
     */
    public Rectangle getCollisionRectangle() {
        return this.block.getCollisionRectangle();
    }

    /**
     * notifies the paddle that it has been hit in a certain collision point
     * and speed and returns the new speed of the colliding object.
     * @param collisionPoint the point were the collision is happening.
     * @param currentVelocity the current velocity of the colliding object.
     * @param hitter that ball that hits the paddle.
     * @return the new velocity of the colliding object.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // each region end is width/i {1<i<5}
        Velocity newVel;
        this.notifyHit(hitter);
        this.game.removeLife();
        // if the ball hits the top of the paddle
        if (collisionPoint.getX() == this.block.getRectangle().getUpperLeft().getX()) {
            newVel = new Velocity(-1 * currentVelocity.getDx(),
                    currentVelocity.getDy(), currentVelocity.getSpeed());
        } else if (collisionPoint.getX()
                == (this.block.getRectangle().getUpperLeft().getX()
                + this.block.getRectangle().getWidth())) {
            newVel = new Velocity(-1 * currentVelocity.getDx(),
                    currentVelocity.getDy(), currentVelocity.getSpeed());
        } else if (collisionPoint.getY()
                == this.block.getRectangle().getUpperLeft().getY()) {
            double rectanglePart = this.block.getRectangle().getWidth() / 5;
            // hits the first part of the paddle
            if (collisionPoint.getX() <= rectanglePart + this.block.getRectangle().getUpperLeft().getX()) {
                newVel = Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
            } else if (collisionPoint.getX() <= 2 * rectanglePart + this.block.getRectangle().getUpperLeft().getX()) {
                newVel = Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
            } else if (collisionPoint.getX() <= 3 * rectanglePart + this.block.getRectangle().getUpperLeft().getX()) {
                newVel = new Velocity(currentVelocity.getDx(),
                        -1 * currentVelocity.getDy(), currentVelocity.getSpeed());
            } else if (collisionPoint.getX() <= 4 * rectanglePart + this.block.getRectangle().getUpperLeft().getX()) {
                newVel = Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
            } else if (collisionPoint.getX() <= 5 * rectanglePart + this.block.getRectangle().getUpperLeft().getX()) {
                newVel = Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
            } else {
                newVel = currentVelocity;
            }
        } else {
            newVel = currentVelocity; // error
        }
        return newVel;
    }

    /**
     * add the paddle to the game. add it to the collidable list and sprite
     * list.
     * @param g the game we are adding the paddle to.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * removes the paddle from the game if needed.
     */
    public void removeFromGame() {
        this.game.removeSprite(this);
        this.game.removeCollidable(this);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.block.removeHitListener(hl);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.block.addHitListener(hl);
    }

    /**
     * notify all of the hit listners that a hit has accured.
     * @param hitter the ball that hits this block.
     */
    private void notifyHit(Ball hitter) {
        this.block.notifyHit(hitter);
    }
}
