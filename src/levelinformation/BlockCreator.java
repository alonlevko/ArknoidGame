package levelinformation;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to store the definitions of the blocks that we have read
 * from the file.
 */
public class BlockCreator {
    // the parameters of the blocks we can read from the file.
    private int height = 0;
    private int width = 0;
    private String symbol = null;
    private Color fill = null;
    private int hitPoints = 0;
    private Color stroke = null;
    private String image = null;

    /**
     * set the width of the block type.
     * @param w the width we are setting.
     */
    public void setWidth(int w) {
        this.width = w;
    }

    /**
     * set the heigh of the block type.
     * @param h the height we are setting.
     */
    public void setHeight(int h) {
        this.height = h;
    }

    /**
     * get the width of the block.
     * @return return the width of the block.
     */
    public int getWidth() {
        return width;
    }

    /**
     * get the symbol of the block.
     * @return the symbol of the block.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * get the defined height of the block.
     * @return the height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * get the defined color of the block.
     * @return the defined fill color.
     */
    public Color getFill() {
        return fill;
    }

    /**
     * set the fill of the block definition.
     * @param f the new color of the fill.
     */
    public void setFill(Color f) {
        this.fill = f;
    }

    /**
     * get the defined starting hit points of the to be block.
     * @return the starting hit points of the block.
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * set the starting hit points of the definition.
     * @param hP the hit point we are setting.
     */
    public void setHitPoints(int hP) {
        this.hitPoints = hP;
    }

    /**
     * get the store color of the block.
     * @return the stroke color.
     */
    public Color getStroke() {
        return stroke;
    }

    /**
     * set the sroke color.
     * @param c the stroke color we are setting.
     */
    public void setStroke(Color c) {
        this.stroke = c;
    }

    /**
     * return the string that contains the path to the image.
     * @return the path to the image.
     */
    public String getImage() {
        return image;
    }

    /**
     * set a string to be the path to the image.
     * @param i the path to the image.
     */
    public void setImage(String i) {
        this.image = i;
    }

    /**
     * get a string the is the block definition line and create based on it the block
     * definition.
     * @param bdef the string the containes the definition.
     */
    public BlockCreator(String bdef) {
        // prase the bdef line.
        String[] spaceSplited = bdef.split(" ");
        for (int i = 0; i < spaceSplited.length; i++) {
            // prase each value seperatly.
            singleValuePraser(spaceSplited[i]);
        }
    }

    /**
     * understand the key:value part of the bdef line.
     * @param keyVal the key value : seperated string.
     */
    public void singleValuePraser(String keyVal) {
        // split it by the double dots.
        String[] dotSplit = keyVal.split(":");
        // make sure it is long enough to actually be a key value representation
        if (dotSplit.length > 1) {
            // replace unwanted characters.
            dotSplit[1] = dotSplit[1].replaceAll("\\r", "");
        }
        // check what is the key and set the value accordingly.
        switch (dotSplit[0]) {
            case "symbol":
                // set the symbol of
                this.symbol = dotSplit[1];
                break;
            case "width":
                // get the width of the block.
                this.width = Integer.parseInt(dotSplit[1]);
                break;
            case "height":
                // get the height of the block.
                this.height = Integer.parseInt(dotSplit[1]);
                break;
            case "hit_points":
                // get the starting hit points of the block.
                this.hitPoints = Integer.parseInt(dotSplit[1]);
                break;
            case "stroke":
                // split the line
                String[] line = dotSplit[1].split("\\(");
                // replace unwanted characters.
                line[1] = line[1].replaceAll("\\)", "");
                // parse the right color from the string.
                this.stroke = BlockFactory.getColorFromString(line[1], null);
                break;
            case "fill":
                line = dotSplit[1].split("\\(");
                line[1] = line[1].replaceAll("\\)", "");
                switch (line[0]) {
                    case "color": // if the start is color.
                        if (line.length > 2) {
                            this.fill = BlockFactory.getColorFromString(line[1], line[2]);
                        } else {
                            this.fill = BlockFactory.getColorFromString(line[1], null);
                        }
                        break;
                    case "image":
                        this.image = line[1];
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
    }

    /**
     * get the block definer that stores all of the default values and a list of
     * block definers. set the parameters of the default block definer to all of the block
     * definers in the list.
     * @param def the block definer with the default values.
     * @param old the list of blocks that we want to apply the defaults to.
     * @return a new lust of blocks that contains the blocks after setting the values.
     */
        public List<BlockCreator> copyDefaultsToBlocks(BlockCreator def, List<BlockCreator> old) {
        List<BlockCreator> list = new ArrayList<>(old);
        if (def.getWidth() != 0) { // if there is a default width
            for (BlockCreator b : list) {
                b.setWidth(def.getWidth());
            }
        }
        if (def.getHeight() != 0) { // if there is a default height.
            for (BlockCreator b : list) {
                b.setHeight(def.height);
            }
        }
        if (def.getFill() != null) { // if there is a default fill.
            for (BlockCreator b : list) {
                b.setFill(b.getFill());
            }
        }
        if (def.getHitPoints() != 0) { // if there is a default hit-point.
            for (BlockCreator b : list) {
                b.setHitPoints(def.getHitPoints());
            }
        }
        if (def.getStroke() != null) { // if there is a default stroke.
            for (BlockCreator b : list) {
                b.setStroke(def.getStroke());
            }
        }
        if (def.getImage() != null) { // if there is a default image.
            for (BlockCreator b : list) {
                b.setImage(def.getImage());
            }
        }
        return list;
     }
}

