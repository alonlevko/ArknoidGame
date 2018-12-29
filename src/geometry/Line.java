package geometry;
import java.util.ListIterator;

/**
 * This class is a line segment on a cartesian plain. it has a start and end
 * point, a slope, and an equation. The class containes methods to find special
 * points on the line, intersection with other lines and general properties of
 * the line.
 *
 * @author Alon Levkovitch
 */
public class Line {
    // the starting point of the segment.
    private Point start;
    // the end point of the segment.
    private Point end;
    // the slope of the segment.
    private double slope;
    // the free coefficient in the equation of the line.
    private double freeCoefficient;

    /**
     * constructs a line based on its starting point and ending point.
     *
     * @param start the starting point of the line.
     * @param end the end poing of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        // sets the slope of the line and its equation.
        this.setSlopeEquation();
    }

    /**
     * constructs a line based on the x and y values of the start and end
     * points.
     *
     * @param x1 the x of the starting point.
     * @param y1 the y of the sarting point.
     * @param x2 the x of the end point.
     * @param y2 the y of the end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        // sets the slope of the line and its equation.
        this.setSlopeEquation();
    }

    /**
     * finds the slope of the line segments and the equation of the line. saves
     * them as properties of the line.
     */
    public void setSlopeEquation() {
        if ((this.start.getX() - this.end.getX()) == 0) { // it is a line of form x = c
            this.slope = Double.POSITIVE_INFINITY;
            this.freeCoefficient = this.start().getX();
        } else if (this.start.getY() - this.end().getY() == 0) {
            this.slope = 0;
            this.freeCoefficient = this.start().getY();
        } else {
            this.slope = (this.start.getY() - this.end.getY()) / (this.start.getX()
                    - this.end.getX());
            this.freeCoefficient = (-1 * this.start.getX() * this.slope)
                    + this.start.getY();
        }

    }

    /**
     * gets the length of the line.
     *
     * @return returns the length of the line.
     */
    public double length() {
        // uses the distance method of the point class.
        return this.start.distance(this.end);
    }

    /**
     * uses math to find the middle point of the line segment.
     *
     * @return the middle point of the line.
     */
    public Point middle() {
        // based on the equation to calculate the middle of a line segment.
        Point middle = new Point(((this.start.getX() + this.end.getX()) / 2),
                ((this.start.getY() + this.end.getY()) / 2));
        return middle;
    }

    /**
     * get the slope of the line.
     *
     * @return the slope of the line.
     */
    public double getSlope() {
        return this.slope;
    }

    /**
     * get the free coefficient of the equation.
     *
     * @return the free coefficient of the line.
     */
    public double getFreeCoefficient() {
        return this.freeCoefficient;
    }

    /**
     * returns the starting point of the line.
     *
     * @return returns the starting point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * returns the ending point of the line.
     *
     * @return returns the end point of the line/
     */
    public Point end() {
        return this.end;
    }

    /**
     * checks if the two lines intersect by getting the intersection point and
     * then checking if the intersection point is on both line segments.
     *
     * @param other the line we are checking intersection with.
     * @return true - the lines intersect, false - they do not.
     */
    public boolean isIntersecting(Line other) {
        // if the lines have the same slop they are not intersecting.
        if (this.slope == other.getSlope()) {
            return false;
        }
        // get the intersection point.
        Point intersection = this.intersectionPoint(other);
        // used to check if the intersection point is on the two line segments.
        boolean first = false, second = false;
        //if the point is on the first line segment.
        if ((intersection.isBetween(intersection.getX(), this.start.getX(),
                this.end.getX()))
                && (intersection.isBetween(intersection.getY(),
                this.start.getY(), this.end.getY()))) {
            first = true;
        } // if the point is on the second line segment.
        if ((intersection.isBetween(intersection.getX(), other.start().getX(),
                other.end().getX()))
                && (intersection.isBetween(intersection.getY(),
                        other.start().getY(), other.end().getY()))) {
            second = true;
        }
        return (first && second);
    }

    /**
     * returns the intersection point of the two line by using and solving
     * the equation system of the equations of the two lines.
     * @param other the other line we are getting the intersection point with.
     * @return the intersection point of the two lines.
     */
    public Point intersectionPoint(Line other) {
        double valueX;
        double valueY;
        // the x value of the point is calculated using the equations.
        if (Double.isInfinite(this.slope) && (other.getSlope() == 0)) {
            valueX = this.freeCoefficient;
            valueY = other.freeCoefficient;
        } else if (Double.isInfinite(other.getSlope())
                && (this.getSlope() == 0)) {
            valueY = this.freeCoefficient;
            valueX = other.freeCoefficient;
        } else if (Double.isInfinite(other.getSlope())) {
            valueX = other.getFreeCoefficient();
            valueY = (this.slope * valueX) + this.freeCoefficient;
        } else if (other.slope == 0) {
            valueX = ((other.getFreeCoefficient() - this.getFreeCoefficient())
                    / this.slope);
            valueY = other.getFreeCoefficient();
        } else {
            valueX = (other.getFreeCoefficient() - this.freeCoefficient)
                    / (this.slope - other.getSlope());
            // the y value is calculated using the x value.
            valueY = (this.slope * valueX) + this.freeCoefficient;
        }
        // create the intersection point.
        return new Point(valueX, valueY);
    }

    /**
     * checks if the lines intersect. if they do intersect it returns the
     * intersection point.
     * @param other the line we are checking intersection with.
     * @return the intersection point if there is one and null if there is no
     * intersection point.
     */
    public Point intersectionWith(Line other) {
        // if the lines are not intersecting.
        if (!this.isIntersecting(other)) {
            return null;
        } else {
            // if the lines are intersection return the intersection point.
            return this.intersectionPoint(other);
        }
    }

    /**
     * checks if the lines are equal. equal lines are lines that the starting
     * point and end point are the same.
     * @param other the line we are checking if it is equal.
     * @return true - the lines are equal. false - they are not.
     */
    public boolean equals(Line other) {
    return (this.start.equals(other.start) && this.end.equals(other.end));
    }

    /**
     * find the closest intersection of the line and the ractangle that is
     * the closest one to the start of the line.
     * @param rect the rectangle we are checking intersection with.
     * @return the closest intersection
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List list = rect.intersectionPoints(this);
        if (list.size() == 0) {
            return null;
        }
        ListIterator<Point> itr = list.listIterator();
        Point closest = (Point) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            Point next = (Point) list.get(i);
            if (closest.distance(this.start) > next.distance(this.start)) {
                closest = (Point) list.get(i);
            }
        }
        return closest;
    }
}
