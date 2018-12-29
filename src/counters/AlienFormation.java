package counters;
import biuoop.DrawSurface;
import collidable.Alien;
import collidable.Edges;
import collidable.Sprite;
import game.GameLevel;
import geometry.Ball;
import geometry.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * this is a sprite that represents all of the aliens in our game. the alien formation
 * sends commands to the aliens in the formation. the commands are to shoot and to
 * change direction and speed when hiting the side of the screen.
 * @author Alon Levkovitch
 */
public class AlienFormation implements Sprite {
    // for each alien row the bottom alien is in the last place in the list.
    private List<List<Alien>> alienRows;
    private GameLevel game;
    private Edges edges;
    private Counter lives;
    private Point startPos;
    private long lastShot = 0;

    /**
     * create a new alien formation with the things we need to send commands.
     * @param list the list of aliens in the formtation.
     * @param g the game the formation is in.
     * @param e the edges of the moving area.
     * @param l the counter of lives of the game.
     */
    public AlienFormation(List<List<Alien>> list, GameLevel g, Edges e, Counter l) {
        this.alienRows = list;
        this.game = g;
        this.edges = e;
        this.lives = l;
        // we want to save the aliens start position. we will need it later.
        this.startPos = list.get(0).get(0).getBlock().getRectangle().getUpperLeft();
    }

    /**
     * make on row of aliens fire randomly.
     */
    private void fireRow() {
        // save the time we last shot.
        this.lastShot = System.currentTimeMillis();
        Random rand = new Random();
        // save a random int.
        int rowToFire = rand.nextInt(this.alienRows.size());
        int place = this.alienRows.get(rowToFire).size();
        // make the shot of the last alien in the column.
        Ball shot = this.alienRows.get(rowToFire).get(place - 1).fire();
        // add the shot to the game.
        shot.addToGame(this.game);
    }

    /**
     * checkes if the formation hits the edges. if it does tells the aliens that
     * a hit with one of the edges and lets the aliens change their direction / lose a life
     * and restart.
     */
    private void hitEdges() {
        // if we hit the right edge.
        if (this.getRightestX() >= this.edges.getRight().start().getX()) {
            for (List<Alien> l : alienRows) { // go over all of the aliens.
                for (Alien a : l) {
                    a.sideHit(); // tell them about the hit with the edge.
                }
            }
        // if we hit the left edge.
        } else if (this.getLeftestX() <= this.edges.getLeft().start().getX()) {
            for (List<Alien> l : alienRows) { // go over all of the aliens.
                for (Alien a : l) {
                    a.sideHit(); // tell them about the hit with the edge.
                }
            }
        // if we hit the botom edge, witch is the shields.
        } else if (this.getBotomY() >= this.edges.getBottom().start().getY()) {
            // remove a life and restart the aliens to the start position.
            this.lives.decrease(1);
            this.resetToStratPos();
        }
    }

    /**
     * get the rightest x in the alien formation.
     * @return the x value of the rightest point in the formation.
     */
    private double getRightestX() {
        // save the rows and height we need.
        int rows = alienRows.size();
        int rHeight = alienRows.get(rows - 1).size();
        if (rHeight == 0) { // if the row is actually empty.
            rows--; // go to the next row.
            rHeight = alienRows.get(rows - 1).size(); // save it s height.
        }
        Alien rightes = alienRows.get(rows - 1).get(rHeight - 1); // save the rightest alien.
        // return the x value of the right part of the alien.
        return (rightes.getBlock().getRectangle().getUpperLeft().getX()
                + rightes.getBlock().getRectangle().getWidth());
    }
    /**
     * get the leftest x in the alien formation.
     * @return the value of the leftest x in the alien formation.
     */
    private double getLeftestX() {
        // the leftest colums in the first in the list.
        int col = 0;
        int height = alienRows.get(col).size();
        if (height == 0) { // if the first columns is empty.
            col++; // go to the next colum
            height = alienRows.get(col).size();
        }
        // save the leftest alien.
        Alien leftest = alienRows.get(col).get(height - 1);
        // return the x value of its left side.
        return leftest.getBlock().getRectangle().getUpperLeft().getX();
    }

    /**
     * get the most botom y value in the formation.
     * @return the y value of the bottom of the formation.
     */
    private double getBotomY() {
        // we first need to find the longest column in the formation.
        List<Alien> longestRow = this.findLongesRow();
        // get the last alien in the longest column.
        Alien lowst = longestRow.get(longestRow.size() - 1);
        // return that aliens bottom y value.
        return (lowst.getBlock().getRectangle().getUpperLeft().getY()
                + lowst.getBlock().getHeight());
    }

    /**
     * move all of the aliens to the starting position of the formation.
     */
    public void resetToStratPos() {
        // go over all of the aliens in the formation.
        for (int i = 0; i < this.alienRows.size(); i++) {
            for (int j = 0; j < this.alienRows.get(i).size(); j++) {
                // tell the aliens to move to the point we defain, that is their starting points.
                Alien alien = this.alienRows.get(i).get(j);
                this.alienRows.get(i).get(j).move(new Point(this.startPos.getX()
                        + i * alien.getBlock().getRectangle().getWidth(),
                        this.startPos.getY() + j * alien.getBlock().getHeight()));
                // reset the speed of the aliens to their starting speed.
                this.alienRows.get(i).get(j).resetSpeed();
            }
        }
    }

    @Override
    public void timePassed(double dt) {
        // clear the dead aliens from the formation.
        this.clearDead();
        // check if the aliens hit the edges.
        this.hitEdges();
        // make sure that 0.5 second have passed since that last shot.
        if (System.currentTimeMillis() - this.lastShot > 500) {
            this.fireRow(); // make a row fire.
        } else if (this.lastShot == 0) { // if it is the first shot in the game.
            this.fireRow();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        return;
    }

    /**
     * add the formation to the game.
     */
    public void addToGame() {
        this.game.addSprite(this);
    }

    /**
     * find the longest column of aliens.
     * @return the longest column of aliens.
     */
    private List<Alien> findLongesRow() {
        // we first want to find the maximum size of the rows.
        int max = this.alienRows.get(0).size();
        int index = 0;
        List<Alien> l;
        // go over all of the aliens.
        for (int i = 0; i < this.alienRows.size(); i++) {
            l = this.alienRows.get(i); // save the alien row
            if (l.size() > max) { // check if it longer than the defined max.
                max = l.size(); // if it is, make it the max.
                index = i; // save its index.
            }
        }
        return this.alienRows.get(index);
    }

    /**
     * clear all of the dead aliens from the formation.
     */
    private void clearDead() {
        // copy the formation so there will be no problems.
        List<List<Alien>> aliens = new ArrayList<>(this.alienRows);
        for (int i = 0; i < aliens.size(); i++) { // loop over the columns.
            List<Alien> col = aliens.get(i);
            // loop over the aliens in each column.
            for (int j = 0; j < aliens.get(i).size(); j++) {
                if (!aliens.get(i).get(j).isAlive()) { // if the alien is dead.
                    // save the alien.
                    Alien alien = aliens.get(i).get(j);
                    // remove it from the list.
                    aliens.get(i).remove(alien);
                }
            }
            if (col.size() == 0) { // if the column has 0 aliens we want to remove it.
                aliens.remove(col);
            }
        }
        this.alienRows = aliens;
    }
}
