package collidable;
import geometry.Line;
import geometry.Point;
/**
 * this class is used to save and accsess 4 lines that represent the edges of the
 * game screen that an object can move in.
 * @author alon levkovitch
 */
public class Edges {
    private Line top;
    private Line bottom;
    private Line left;
    private Line right;

    /**
     * create a new edges instance usinf the upper left x,y and the height and width.
     * @param leftX the upped left x.
     * @param leftY the upper left y.
     * @param width the width.
     * @param height the height.
     */
    public Edges(double leftX, double leftY, double width, double height) {
        // create the lines of the edges based on the points and parameters.
        this.top = new Line(new Point(leftX, leftY), new Point(leftX + width, leftY));
        this.bottom = new Line(new Point(leftX, height), new Point(leftX + width, height));
        this.left = new Line(new Point(leftX, leftY), new Point(leftX, leftY + height));
        this.right = new Line(new Point(leftX + width, leftY), new Point(leftX + width, leftY + height));
    }

    /**
     * get the top line of the edge.
     * @return the top line of the edge.
     */
    public Line getTop() {
        return top;
    }

    /**
     * set the top line of the edge.
     * @param t the line that will be the top.
     */
    public void setTop(Line t) {
        this.top = t;
    }

    /**
     * get the bottom line of the edge.
     * @return the bottom line of the screen.
     */
    public Line getBottom() {
        return bottom;
    }

    /**
     * set the bottom of the edge.
     * @param b the line that will be the bottom edge.
     */
    public void setBottom(Line b) {
        this.bottom = b;
    }

    /**
     * get the left line of the screen.
     * @return the left line of the edge.
     */
    public Line getLeft() {
        return left;
    }

    /**
     * set a new line to be the left line of the screen.
     * @param l the left line of the screen.
     */
    public void setLeft(Line l) {
        this.left = l;
    }

    /**
     * return the right line of the screen.
     * @return the right line of the screen.
     */
    public Line getRight() {
        return right;
    }

    /**
     * set a new line to be the right line of the screen.
     * @param r the new right line of the screen.
     */
    public void setRight(Line r) {
        this.right = r;
    }
}
