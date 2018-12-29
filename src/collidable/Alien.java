package collidable;

import biuoop.DrawSurface;
import counters.HitListener;
import game.GameLevel;
import geometry.Ball;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import java.awt.Color;

/**
 * an alien is the enemy in the space invaders game. it wraps a block and adds to
 * the block some special methods.
 */
public class Alien implements Collidable, Sprite, HitNotifier {
    private Block block;
    private double moveSpeed;
    private double startSpeed;
    private GameLevel game;
    private boolean alive = true;

    /**
     * we create an alien with a block.
     * @param b the block that is the base to the alien.
     */
    public Alien(Block b) {
        this.block = b;
    }

    /**
     * add a game to the alien.
     * @param g the game the alien is in.
     */
    public void addGame(GameLevel g) {
        this.game = g;
    }

    /**
     * set the move speed of the alien to a new move speed.
     * @param s the new move speed of the alien.
     */
    public void setMoveSpeed(double s) {
        this.startSpeed = s;
        this.moveSpeed = s;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currenVelocity) {
        return this.block.hit(hitter, collisionPoint, currenVelocity);
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.block.drawOn(d);
    }

    @Override
    public void timePassed(double dt) {
        if (this.block.getHitPoints() == 0) { // check if the block of the alien is dead.
            // remove the block from the game.
            this.game.removeSprite(this);
            this.game.removeCollidable(this);
            // kill the alien.
            this.alive = false;
        }
        // move the alien to a new position based on its set speed.
        Point point = this.block.getRectangle().getUpperLeft();
        this.move(new Point(point.getX() + this.moveSpeed, point.getY()));
        this.block.timePassed(dt);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.block.getCollisionRectangle();
    }

    /**
     * return the block of the alien.
     * @return the block of the alien.
     */
    public Block getBlock() {
        return block;
    }

    /**
     * fire a shot from the alien and return the shot that has been fired.
     * @return a ball that is a shot of the alien.
     */
    public Ball fire() {
        // the new ball that is created on the center of the alien.
        Ball b = new Ball(new Point((this.block.getRectangle().getWidth() / 2)
                + this.block.getRectangle().getUpperLeft().getX(),
                this.getBlock().getRectangle().getHeight()
                        + this.block.getRectangle().getUpperLeft().getY() + 3),
                5, Color.red);
        // set the velocity and speed of the ball to be straight down.
        b.setVelocity(Velocity.fromAngleAndSpeed(-180, 400));
        // make that ball know that it is an alien shot.
        b.setAlienShot(true);
        return b;
    }

    /**
     * let the alien know it hit the edge of the screen.
     */
    public void sideHit() {
        // increase speed and change direction.
        this.moveSpeed = this.moveSpeed * -1.1;
        // get the old position of the alien.
        Point old = this.getBlock().getRectangle().getUpperLeft();
        // move the alien down by 10 pixels.
        this.move(new Point(old.getX(), old.getY() + 10));
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.block.addHitListener(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.block.addHitListener(hl);
    }

    /**
     * add the alien to the game.
     * @param g the game we are adding the alien to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * move the alien to a new point.
     * @param point the upper left point we are moving the alien to.
     */
    public void move(Point point) {
        // create a new block in the new position.
        Block pos = new Block(new Rectangle(point, this.block.getRectangle().getWidth(),
                this.block.getHeight()), this.block.getImg(), this.block.getHitPoints());
        // tell it it is an alien.
        pos.beAlien(true);
        // add all of the hit listners we have.
        pos.addAllHitListeners(this.block.getHitListeners());
        // set it to be our new block.
        this.block = pos;
    }

    /**
     * check if the alien is alive.
     * @return true - it is alive. false - it is not.
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * reset the speed of the alien to its starting speed.
     */
    public void resetSpeed() {
        this.moveSpeed = this.startSpeed;
    }
}
