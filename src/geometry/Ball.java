package geometry;
import collidable.Sprite;
import game.GameLevel;
import game.GameEnviroment;
import biuoop.DrawSurface;
import collidable.CollisionInfo;
import java.awt.Color;


/**
 * a ball that has size, color, and a center point.
 * @author Alon Levkovitch
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnviroment enviroment;
    private boolean isAlienShot;

    /**
     * creates a ball based on its center point, its radios and color.
     *
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = Velocity.fromAngleAndSpeed(145, 6);
    }

    /**
     * choose what will be the game enviroment of this game based on the game
     * we input.
     * @param g the game this ball is in.
     */
    public void setEnviroment(GameLevel g) {
        this.enviroment = g.getEnvironment();
    }
    /**
     * return the rounded value of the ball's center x.
     *
     * @return the x value of the center of the ball.
     */
    public int getX() {
        return (int) Math.round(this.center.getX());
    }

    /**
     * return the rounded value of the ball's center y.
     *
     * @return the y value of the center of the ball.
     */
    public int getY() {
        return (int) Math.round(this.center.getY());
    }

    /**
     * used to accesses the radius of the ball.
     *
     * @return the radius of the ball.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * used to accesses the color of the ball.
     *
     * @return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * uses the balls parameters to draw it on the surface.
     *
     * @param surface the surface the ball will be drawn on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) Math.round(this.center.getX()),
                (int) Math.round(this.center.getY()), this.radius);
        surface.setColor(this.color);
        surface.fillCircle((int) Math.round(this.center.getX()),
                (int) Math.round(this.center.getY()), this.radius);
    }

    /**
     * returns the game enviorment of this ball.
     * @return the game enviorment of the ball.
     */
    public GameEnviroment getGame() {
        return this.enviroment;
    }

    /**
     * set the game enviorment to be a game enviorment created.
     * @param g the game enviorment we are setting.
     */
    public void setGame(GameEnviroment g) {
        this.enviroment = g;
    }

    /**
     * get a velocity and put it as the velocity of the ball.
     * @param v the new velocity of the ball
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * sets a new velocity for the ball based on the change in x and y recieved.
     * @param dx the change in x.
     * @param dy the change in y.
     * @param speed the speed of the ball we need for later use.
     */
    public void setVelocity(double dx, double dy, double speed) {
        this.velocity = new Velocity(dx, dy, speed);
    }

    /**
     * return the velocity of the ball.
     *
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * move the ball one step forward in the animation. check if the ball had a
     * collision with some object in out game enviroment. we check for collision
     * using a 3 trajectory calculations in 3 diffrent directions. we do this so
     * we can treat the corners in a proper manner and not lose the ball.
     * @param dt the time that had passed since the last call
     */
    public void moveOneStep(double dt) {
        // the original tragectory of the ball.
        Point nextStepCenter = new Point(this.center.getX()
                + this.velocity.getDx() * dt,
                this.center.getY() + this.velocity.getDy() * dt);
        // the trajectory of the ball if we change the x direction
        Point nextStepAltX = new Point(this.center.getX()
                + (-1 * this.velocity.getDx() * dt),
                this.center.getY() + this.velocity.getDy() * dt);
        // the trajectory of the ball if we change the y direction.
        Point nextStepAltY = new Point(this.center.getX()
                + this.velocity.getDx() * dt,
                this.center.getY() + (-1 * this.velocity.getDy() * dt));
        // the original trajectory of the ball.
        Line traj = new Line(this.center, nextStepCenter);
        // the trajectory of the ball with the diffrent x direction
        Line trajAltX = new Line(this.center, nextStepAltX);
        // the trajectory of the ball with the diffrent y direction
        Line trajAltY = new Line(this.center, nextStepAltY);
        // the collishion of the ball with the original trajectory
        CollisionInfo colish = this.enviroment.getClosestCollision(traj);
        // the collishion of the ball with the diffrent x trajectory
        CollisionInfo colishAltY = this.enviroment.
                getClosestCollision(trajAltX);
        // the collishion of the ball with the diffrrent y trajectory
        CollisionInfo colishAltX = this.enviroment.
                getClosestCollision(trajAltY);
        /* check for collision withe the original trajectory and if we have
        collision with the oter trajectories.
         */
        if ((colish != null) && ((colishAltX == null)
                || (colishAltY == null))) {
            // if the second collision is with the diffrent x
            if (colishAltX != null) {
                /* we are colliding with the same object as the original
                trajectory*/
                if (colish.collisionObject() == colishAltX.collisionObject()) {
                    // notify the object we hit it and get new Velocity.
                    this.velocity = colish.collisionObject().
                            hit(this, colish.collisionPoint(), this.velocity);
                /*the collisions are with diffrent objects. we are cooliding
                 * with the touching edge of two blocks */
                } else {
                    // notify the object we hit it and get new Velocity.
                    this.velocity = colish.collisionObject().
                            hit(this, colish.collisionPoint(), this.velocity);
                    // make sure we have a second object.
                    if (colishAltX.collisionObject() != null) {
                        // reduce hit points also on the second object.
                        Velocity vel = colishAltX.collisionObject()
                                .hit(this, colishAltX.collisionPoint(),
                                        this.velocity);
                    }
                }
            } else if (colishAltY != null) {
                if (colish.collisionObject() == colishAltY.collisionObject()) {
                    // notify the object we hit it and get new Velocity.
                    this.velocity = colish.collisionObject().
                            hit(this, colish.collisionPoint(), this.velocity);
                /*the collisions are with diffrent objects. we are cooliding
                     * with the touching edge of two blocks */
                } else {
                    // notify the object we hit it and get new Velocity.
                    this.velocity = colish.collisionObject().
                            hit(this, colish.collisionPoint(), this.velocity);
                    // make sure we have a second object.
                    if (colishAltX != null) {
                        // reduce hit points also on the second object.
                        Velocity vel = colishAltX.collisionObject().
                                hit(this, colishAltX.collisionPoint(), this.velocity);
                    }
                }
            } else { // two of them are null
                    this.velocity = colish.collisionObject().
                            hit(this, colish.collisionPoint(), this.velocity);
            }
            // move the ball in its new direction.
            this.center = new Point(this.center.getX() + this.velocity.getDx()
                    * dt, this.center.getY() + this.velocity.getDy() * dt);
        // we hit somthing in all 3 directions, we are in a corner.
        } else if ((colish != null) && (colishAltX != null)
                && (colishAltY != null)) {
            // reduce hit points also on the object.
            Velocity vel = colish.collisionObject().
                    hit(this, colish.collisionPoint(), this.velocity);
            Velocity altVel;
            // the diffrent x object is diffrent than the original.
            if (colishAltX.collisionObject() != colish.collisionObject()) {
                // reduce hit points also on the object.
                altVel = colishAltX.collisionObject().
                        hit(this, colishAltX.collisionPoint(), this.velocity);
            // the diffrent y object is diffrent than the original.
            } else if (colishAltY.collisionObject()
                    != colish.collisionObject()) {
                // reduce hit points also on the object.
                altVel = colishAltY.collisionObject().
                        hit(this, colishAltY.collisionPoint(), this.velocity);
            }
            // the only way out of the corner is straight back.
            this.velocity = new Velocity(this.velocity.getDx() * -1,
                    this.velocity.getDy() * -1, this.velocity.getSpeed());
            // move the ball in its new direction.
            this.center = new Point(this.center.getX()
                    + this.velocity.getDx() * dt,
                    this.center.getY() + this.velocity.getDy() * dt);
        } else {
            // move the ball in its new direction.
            this.center = nextStepCenter;
        }
    }

    /**
     * checkes if this ball touches the line reacived in the function.
     * does this by using the mathematical equation for the distance between
     * a point (the center of the ball) and a line.
     *
     * @param line the line we are checking touch with.
     * @return true - the ball is touching the line. false - it is not.
     */
    public boolean touchLine(Line line) {
        double distance;
        if (line.getSlope() == 0) { // it is a horizontal line.
            // use the equation.
            distance = Math.abs(this.center.getX() - line.getFreeCoefficient());
        // it is a vertical line.
        } else if (line.getSlope() == Double.POSITIVE_INFINITY) {
            // use the equation.
            distance = Math.abs(this.center.getY() - line.getFreeCoefficient());
        } else { // it is just a regular line.
            // use the equation.
            distance = Math.abs((line.end().getY() - line.start().getY())
                    * this.center.getX() - (line.end().getX()
                    - line.start().getX()) * this.center.getY()
                    + line.end().getX() * line.start().getY()
                    - line.end().getY()  * line.start().getX())
                    / Math.sqrt(Math.pow(line.end().getY()
                    - line.start().getY(), 2) + Math.pow(line.end().getX()
                    - line.start().getY(), 2));
        }
        // if the line is closer to the center of the ball than the outside.
        return (distance <= this.radius);
    }

    /**
     * notify the ball that one step has passed. calls the move on step method.
     * @param dt - the time that passed since the last call.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * add the ball to the game.
     * @param g the game the ball will be in.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        this.setEnviroment(g);
        g.addBall(this);
    }

    /**
     * remove this ball from the game.
     * @param g the game we are removing the ball from.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        this.enviroment = new GameEnviroment();
    }

    /**
     * tell the ball it is an alien shot.
     * @param alienShot true - it will be a shot. false - it will not.
     */
    public void setAlienShot(boolean alienShot) {
        this.isAlienShot = alienShot;
    }

    /**
     * check if the ball is an alien shot.
     * @return true - it is a shot. false - it is not.
     */
    public boolean isAlienShot() {
        return isAlienShot;
    }
}
