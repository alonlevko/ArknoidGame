package geometry;
import java.util.ArrayList;
/**
 * the rectangle is used in blocks to define the form of the block and check the
 * intersection with other geometric forms.
 *
 * @author Alon Levkovitch
 */
public class Rectangle {
    private Point upperLeftP;
    private double width;
    private double height;
    private Line top;
    private Line bottom;
    private Line left;
    private Line right;

    /**
     * create a new rectangle based on our upper left point of it, its height
     * and its width.
     *
     * @param upperLeft the upper left point of the rectangle.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        // save the vlaues.
        this.upperLeftP = upperLeft;
        this.width = width;
        this.height = height;
        // create a new line based on the top left point and the top right point
        this.top = new Line(this.upperLeftP.getX(), this.upperLeftP.getY(),
                width + this.upperLeftP.getX(),
                this.upperLeftP.getY());
        /* create a new line based on the bottom left point and the bottom right
         point */
        this.bottom = new Line(this.upperLeftP.getX(), height
                + this.upperLeftP.getY(), width + this.upperLeftP.getX(),
                height + this.upperLeftP.getY());
        /*create a new line based on the top left point and the bottom left
        point. */
        this.left = new Line(this.upperLeftP.getX(), this.upperLeftP.getY(),
                this.upperLeftP.getX(), height + this.upperLeftP.getY());
        /* create a new line based on the top right point and the bottom right
        point.
         */
        this.right = new Line(width + this.upperLeftP.getX(),
                this.upperLeftP.getY(), width + this.upperLeftP.getX(),
                height + this.upperLeftP.getY());
    }

    /**
     * checks if the line inputed intersects with one of the lines in our
     * rectangle. returns a list of all the intersection points. uses the
     * methods in Line to do this.
     *
     * @param line the line we are checking intersections with.
     * @return a list of all the intersection points with the rectangle.
     */
    public java.util.List intersectionPoints(Line line) {
        // create a new array for saving the intersections.
        java.util.List list = new ArrayList<Point>();
        // save the intersection point with the line.
        Point intersection = line.intersectionWith(this.top);
        if (intersection != null) { // if there is an intersection point.
            list.add(intersection); // add to the list.
        }
        // save the intersection point with the line.
        intersection = line.intersectionWith(this.bottom);
        if (intersection != null) { // if there is an intersection point.
            list.add(intersection); // add to the list.
        }
        // save the intersection point with the line.
        intersection = line.intersectionWith(this.right);
        if (intersection != null) { // if there is an intersection point.
            list.add(intersection); // add to the list.
        }
        // save the intersection point with the line.
        intersection = line.intersectionWith(this.left);
        if (intersection != null) { // if there is an intersection point.
            list.add(intersection); // add to the list.
        }
        return list;
    }

    /**
     * return the upper left point of this rectangle.
     * @return the upper left point.
     */
    public Point getUpperLeft() {
        return upperLeftP;
    }

    /**
     * return the height of the rectangle.
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * return the width of the rectangle.
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return width;
    }
}
