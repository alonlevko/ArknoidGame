package game;
import geometry.Line;
import geometry.Point;
import collidable.Collidable;
import collidable.CollisionInfo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class holds all of the collidables in out game and helps manage the
 * movement of the ball in our game. we also use the game enviorment to define
 * the edges of out screen.
 *
 * @author Alon Levkovitch
 */
public class GameEnviroment {
    private List<Collidable> objects = new LinkedList<Collidable>();


    /**
     * add a colidable to out list of collaidiables in the game enviorment.
     * @param c the collidable we are adding.
     */
    public void addCollidable(Collidable c) {
        this.objects.add(c);
    }

    /**
     * get rid of a collidable in the index inputted in out list of collidables.
     * @param index the index of the collidable we want to get rid of.
     */
    public void removeCollidable(int index) {
        this.objects.remove(index);
    }

    /**
     * get the information about the next closest collision in the way of the
     * ball. use the line between the current and next position of the ball
     * and checks if the line is intersecting with on of out collidables.
     * if so, we return the info about the closest collision.
     * @param trajectory the line beteen the balls current position and its
     *                   next position.
     * @return the info about the collision.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // create a list to hold all of our intersections.
        List<Point> intersections = new ArrayList<Point>();
        // create a list to hold all of our collidiables that have intersection
        List<Collidable> colids = new ArrayList<Collidable>();
        List<Collidable> copy = new LinkedList<Collidable>(this.objects);
        // used to iterate over the three lists.
        Point intersection;
        Collidable colid;
        // go over the objects we have saved in the game enviroment.
        for (int i = 0; i < copy.size(); i++) {
            // save the next collidiable on our list.
            colid = (Collidable) copy.get(i);
            // get the intersection of the trajectory with the colidable.
            intersection = trajectory.
                    closestIntersectionToStartOfLine(colid.
                            getCollisionRectangle());
            /*if we have an intersection and if the intersection isnt the old
            center of the ball.*/
            if (intersection != null && !intersection.equals(trajectory.
                    start())) {
                // add the intersection to our list.
                intersections.add(intersection);
                // add the colidiable to our list.
                colids.add(colid);
            }
        }
        // we have no intersection.
        if (intersections.size() == 0) {
            // no intersections.
            return null;
        // we have only 1 intersection so we don't need to find the closest.
        } else if (intersections.size() == 1) {
            // return the info of the collision.
            return new CollisionInfo(intersections.get(0), colids.get(0));
        } else { // we have multiple intersections.
            // randomly set the first point to be the closest.
            Point closest = (Point) intersections.get(0);
            // set the place of the closest point in the two arrays.
            int closestPlace = 0;
            // save the next point we will compare.
            Point next;
            // used to save the closest place.
            int i = 0;
            // go over all the intersections we had saved.
            for (i = 0; i < intersections.size(); i++) {
                next = (Point) intersections.get(i); // save the next element.
                /* if the next point is closer to the center than the last
                closest point we found. */
                if (closest.distance(trajectory.start())
                        > next.distance(trajectory.start())) {
                    closest = next; // the next is closer than the closest.
                    closestPlace = i; // save the position of the closest place.
                }
            }
            return new CollisionInfo(closest, colids.get(closestPlace));
        }
    }

    /**
     * used to remove a collidable object from the list of collidables.
     * @param c the collidable we are removing.
     */
    public void removeCollidable(Collidable c) {
        this.objects.remove(c);
    }

    /**
     * add a list of collidables to the collidables we already have.
     * @param list the new list of collidables we are adding.
     */
    public void addAllCollidables(List<Collidable> list) {
        this.objects.addAll(list);
    }
}
