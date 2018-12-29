package geometry;
/**
 * the Point is consisted of 2 values, x value and y value. It has methods the
 * calculate simple things in the cartesien plain that the point is on.
 *
 * @author Alon levkovitch
 */
public class Point {
    private double x;
    private double y;

    /**
     * creates a point based on the x value and y value supplied.
     *
     * @param x the x value of the point.
     * @param y the y value of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * uses math to return the distance between two points.
     *
     * @param other the point to calculate the distance to.
     * @return the distance.
     */
    public double distance(Point other) {
        // the equation for calculating distance between two points.
        return Math.sqrt((this.x - other.getX()) * (this.x - other.getX()) + ((
                this.y - other.getY()) * (this.y - other.getY())));
    }

    /**
     * checks if the value (x or y) of a point is in between two other values.
     *
     * @param check the value we want to check if it is in between.
     * @param pA    one of the limits.
     * @param pB    ont of the limits.
     * @return true - the point is between the two others, false - it is not.
     */
    public boolean isBetween(double check, double pA, double pB) {
        // if the right value is bigger
        if (pA > pB) {
            // we check if the check value is in between them.
            return (check <= pA) && (check >= pB);
        } else if (pB > pA) { // if the right value is bigger.
            // we check if the check value is in between them.
            return ((check <= pB) && (check >= pA));
        } else {
            return ((pA == pB) && (check == pA));
        }
    }

    /**
     * checks if two points are equal, it means the x and y are the same.
     *
     * @param other the point we are comparing with.
     * @return true - the points are equal. false - they are not.
     */
    public boolean equals(Point other) {
        // if there is no other points to compare to.
        if (other == null) {
            return false;
        }
        // if the x values are equal and the y values are equal.
        return ((this.x == other.getX()) && (this.y == other.getY()));
    }

    /**
     * return the x value of the point.
     *
     * @return the x value
     */
    public double getX() {
        return this.x;
    }

    /**
     * return the y value of the point.
     *
     * @return the y value.
     */
    public double getY() {
        return this.y;
    }
}

