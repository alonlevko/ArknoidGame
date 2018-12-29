package levelinformation;

import collidable.Block;
import geometry.Point;
import geometry.Rectangle;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * this class containes statice functions that we can use to build the blocks in the
 * game.
 */
public class BlockFactory {
    /**
     * create the 4 block that are the ends of the screen. the first block in the list is the bottom block.
     * @param downRight the downright point of the screen.
     * @param edgeSize the size of the edges of the screen.
     * @return a list that containes the ends of the screen. the bottome edge is in place 0.
     */
    public static List<Block> createEnds(Point downRight, int edgeSize) {
        List<Block> ends = new ArrayList<>();
        // the bottom end of the screen.
        ends.add(new Block(new Rectangle(new Point(edgeSize,
                downRight.getY() - edgeSize), downRight.getX() - 2 * edgeSize,
                edgeSize), Color.gray));
        // the left edge of the screen.
        ends.add(new Block(new Rectangle(new Point(0, 0),
                edgeSize, downRight.getY()), Color.gray));
        // the top edge of the screen.
        ends.add(new Block(new Rectangle(new Point(edgeSize, 0),
                downRight.getX() - 2 * edgeSize, edgeSize), Color.gray));
        // the right edge of the screen.
        ends.add(new Block(new Rectangle(new Point(
                downRight.getX() - edgeSize, 0), edgeSize, downRight.getY()),
                Color.gray)); // right
        // add the 4 blocks to the game.
        return ends;
    }

    /**
     * generate a random color.
     * @return the random color we had generated.
     */
    public static Color randomColor() {
        Random rand = new Random();
        int bound = 5;
        int number = rand.nextInt(bound);
        switch (number) {
            case 1:
                return Color.red;
            case 2:
                return Color.cyan;
            case 3:
                return Color.yellow;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.orange;
            default:
                return Color.white;
        }
    }

    /**
     * create a row of blocks but each block has a random color.
     * @param leftXstart the x vaule of the left top point of the leftest block.
     * @param leftYstart the y value of the left yop point of the leftest block.
     * @param amount the amount of blocks we want to create.
     * @param hitPoints the hit points of the blocks we are creating.
     * @param width the width of each block in the row.
     * @param height the height of each block in the row.
     * @return the list of blocks we are returning.
     */
    public static List<Block> createBlockRowRandomColor(int leftXstart, int leftYstart, int amount,
                                      int hitPoints, int width, int height) {
        // use an array to store the blocks.
        List<Block> row = new ArrayList<Block>();
        /* create a loop the creates each block based on the parameters and
        block number in the row */
        for (int i = 0; i < amount; i++) {
            row.add(new Block(new Rectangle(new Point((double)
                    ((i * width) + leftXstart), (double) leftYstart),
                    width, height), randomColor(), hitPoints));
        }
        return row;
    }

    /**
     * get a string and turn it into a regular or rgb color based on the second
     * parameter.
     * @param s the string we are getting the color from.
     * @param rgb the string we are getting the rgb color from.
     * @return the color we had parsed.
     */
    public static Color getColorFromString(String s, String rgb) {
        if (s.contains("RGB")) { // the color is an rgb color.
            // replace unwanted characters.
            rgb = rgb.replaceAll("\\)", "");
            // split it to the three numbers.
            String[] toInts = rgb.split(",");
            List<Integer> number = new ArrayList<>();
            for (int i = 0; i < toInts.length; i++) {
                number.add(Integer.parseInt(toInts[i]));
            }
            // if we dont have 3 number we can't run the rgb.
            if (toInts.length != 3) {
                return null;
            } else {
                return new Color(number.get(0), number.get(1), number.get(2));
            }
        } else { // the color is of name form.
            switch (s) { // use the name to get the color.
                case "black":
                    return Color.black;
                case "blue":
                    return Color.blue;
                case "cyan":
                    return Color.cyan;
                case "gray":
                    return Color.gray;
                case "lightGray":
                    return Color.lightGray;
                case "green":
                    return Color.green;
                case "orange":
                    return Color.orange;
                case "pink":
                    return Color.pink;
                case "red":
                    return Color.red;
                case "white":
                    return Color.white;
                case "yellow":
                    return Color.yellow;
                default:
                    return null;
            }
        }
    }
}
