package levelinformation;

import collidable.Alien;
import collidable.Block;
import collidable.Sprite;
import geometry.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * this is an empty level information class. it is used to save an reuse the information
 * about the level that was in the files.
 * @author Alon Levkovitch
 */
public class SpaceLevel {
    private String levelName = "Battle No.";
    private int paddleSpeed = 150;
    private int paddleWidth = 50;
    private int alienNum = 50;

    /**
     * create a new parsed level.
     */
    public SpaceLevel() {
    }

    /**
     * return the height of the level. set to default 600.
     * @return 600
     */
    public int height() {
        return 600;
    }

    /**
     * returh the width of the level. set to default 800.
     * @return 800
     */
    public int width() {
        return 800;
    }

    /**
     * return the size of the edges of the level. set to default 20.
     * @return 20
     */
    public int edgeSize() {
        return 25;
    }

    /**
     * the distance the paddle goes with each step it makes.
     * @return the speed of the paddle.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * set the speed of the paddle.
     * @param speed the speed of the paddle.
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;
    }

    /**
     * the width of the paddle.
     * @return the width of the paddle.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * set the width of the paddle.
     * @param width the width of the paddle.
     */
    public void setPaddleWidth(int width) {
        this.paddleWidth = width;
    }
    /**
     * the level name that will be displayed at the top of the screen.
     * @return a string of the level name.
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * choose what will be the level name.
     * @param name the name that will be the level name.
     */
    public void setLevelName(String name) {
        this.levelName = name;
    }
    /**
     * Returns a sprite with the background of the level.
     * @return the background of the level.
     */
    public Sprite getBackground() {
        return new BackGround("color(black)");
    }

    /**
     * The Blocks that make up this level. containes also the edges.
     * @return a list of all the blocks in the game. the 0 place is the bottom block.
     */
    public List<Block> blocks() {
        // create the ends of the screen.
        List<Block> blocks = new ArrayList<>(BlockFactory.createEnds(new Point(800,
                600), 25));
        // create a shield block creator.
        BlockCreator shild = new BlockCreator("symbol:b width:5"
                + " height:5 hit_points:1 fill:color(cyan)");
        // loop over the 3 shields.
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < 30; i++) { // loop over each row.
                for (int j = 0; j < 4; j++) { // loop over each colum.
                    // create a block shield based on the block creator and itereation.
                    blocks.add(new Block(shild, 70 + 250 * k + 5 * i, 450 + j * 5));
                }
            }
        }
        return blocks;
    }

    /**
     * create the list of aliens that will be the enemys in this level.
     * @return the list of aliens.
     */
    public List<List<Alien>> getAliens() {
        List<List<Alien>> list = new ArrayList<>();
        // the block definition of the block inside the aliens.
        BlockCreator ablock = new BlockCreator("symbol:b width:40 height:30"
                + " hit_points:1 fill:image(resources/images/enemy.jpg)");
        for (int i = 0; i < 10; i++) { // loop over the columns of aliens.
            List<Alien> alienRow = new LinkedList<>();
            for (int j = 0; j < 5; j++) { // loop over the rows of aliens.
                // add n alien to the row, use the starting position and iteration.
                alienRow.add(new Alien(new Block(ablock, 100 + i * 50, 50 + j * 40)));
            }
            // add the row to the total list.
            list.add(alienRow);
        }
        return list;
    }
    /**
     * the number of blocks in this level.
     * @return the number of block to remove in the level.
     */
    public int numberOfAliensToRemove() {
        return this.alienNum;
    }

    /**
     * decide what will be the number of blocks to remove.
     * @param num the number of blocks.
     */
    public void setBlockNum(int num) {
        this.alienNum = num;
    }
    /**
     * get what will be the color of the ball.
     * @return the color of the ball.
     */
    public Color getBallColor() {
        return Color.white;
    }

    /**
     * the center points of all of the balls we are going to create.
     * @return a list containing the center points.
     */
    public List<Point> initialBallCenters() {
        List<Point> list = new ArrayList<Point>();
        list.add(new Point((this.width() - 2 * this.edgeSize()) / 2, this.height() - 50));
        return list;
    }

}

