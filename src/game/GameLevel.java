package game;
import animations.PauseScreen;
import animations.KeyPressStoppableAnimation;
import animations.CountdownAnimation;
import animations.Animation;
import animations.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.GUI;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import geometry.Ball;
import collidable.Alien;
import counters.AlienFormation;
import counters.BallRemover;
import counters.BlockRemover;
import counters.Counter;
import counters.HitListener;
import collidable.Paddle;
import collidable.Collidable;
import collidable.Sprite;
import counters.ScoreTrackingListener;
import collidable.Block;
import collidable.Edges;
import biuoop.KeyboardSensor;
import geometry.Rectangle;
import geometry.Point;
import levelinformation.SpaceLevel;

/**
 * this class is the general game class. it initializes all the game object and
 * then runs the animation loop. contains all the constants we need to run the
 * game.
 *
 * @author Alon Levkovitch
 */

public class GameLevel implements Animation {
    private SpaceLevel level;
    private HitListener blockKill;
    private SpriteCollection sprites;
    private GameEnviroment environment;
    private GUI gui;
    private Counter aliens;
    private Counter score;
    private Counter lives;
    private AnimationRunner runner;
    private Paddle paddle;
    private KeyboardSensor keyboard;
    private boolean running;
    private Counter paddleNum;
    private int height;
    private int width;
    private int edgeSize;
    private BallRemover remover;
    private int paddleSize;
    private AlienFormation formation;
    private List<Ball> balls;

    /**
     * create a new game level based on the level information and the object needed
     * to run the level.
     * @param l the information about the level we are creating.
     * @param k the keyboard sensor we will use.
     * @param r the animation runner that will run the level.
     * @param g the gui we wll show the level on.
     * @param live the counter of the lives.
     * @param score the counter of the score.
     */
    public GameLevel(SpaceLevel l, KeyboardSensor k, AnimationRunner r, GUI g, Counter live, Counter score) {
        this.level = l;
        this.keyboard = k;
        this.gui = g;
        this.lives = live;
        this.score = score;
        this.runner = r;
        this.balls = new ArrayList<>();
    }

    /**
     * get the number of lives remaining after/during the run of this level.
     * @return the number of lives.
     */
    public int getLives() {
        return this.lives.getValue();
    }

    /**
     * return the sprite collection of this game.
     * @return the sprite collection.
     */
    public SpriteCollection getSprites() {
        return this.sprites;
    }

    /**
     * return the game enviroment of this game.
     * @return the game enviroments (collidables) off this game.
     */
    public GameEnviroment gameEnviroment() {
        return this.environment;
    }

    /**
     * get the size of the edges we had set for this game.
     * @return the edge size of this game.
     */
    public int getEdgeSize() {
        return this.edgeSize;
    }

    /**
     * return the gui we are using in this game.
     * @return the gui of this game.
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * get the height of our game screen.
     * @return the height of the game screen.
     */
    public int getHeight() {
        return height;
    }

    /**
     * get the width of out game screen.
     * @return the width of our game screen.
     */
    public int getWidth() {
        return width;
    }

    /**
     * add a collidable to this game using the game enviroment.
     * @param c the collidable we are adding.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add a prite to this game using the sprite collection.
     * @param s the sprite we are adding to this game.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * initialize a new game. set all the game parameters that we need. then
     * create the general members we need for the game (gui, sprites,
     * collidables). then create the edges, blocks, paddle and ball. add them
     * to the game.
     * @param speedIncrease the increase in speed we want from the default speed
     *                      set in the level information.
     * @param round - the number of round we are playing.
     */
    public void initialize(double speedIncrease, int round) {
        // set the size of the screen and edges.
        this.width = this.level.width();
        this.height = this.level.height();
        this.edgeSize = this.level.edgeSize();
        this.paddleSize = this.level.paddleWidth();
        this.paddleNum = new Counter();
        // create the utilites we need for initialization of the game.
        this.sprites = new SpriteCollection();
        this.environment = new GameEnviroment();
        this.aliens = new Counter();
        LivesIndicator livesIndi = new LivesIndicator(this, this.lives);
        ScoreIndicator indicator = new ScoreIndicator(this, this.score);
        LevelIndicator levelIndicator = new LevelIndicator(this, round);
        HitListener scoreTracker = new ScoreTrackingListener(this.score);
        this.blockKill = new BlockRemover(this, this.aliens);
        // add the backround to the sprite list.
        this.sprites.addSprite(level.getBackground());
        // get the list of block for the game.
        List<Block> list = new ArrayList<Block>(this.level.blocks());
        List<List<Alien>> aliensList;
        aliensList = this.level.getAliens();
        Edges edges = new Edges(this.edgeSize, this.edgeSize, this.width - (2 * this.edgeSize),
                list.get(4).getRectangle().getUpperLeft().getY());
        this.formation = new AlienFormation(aliensList, this, edges, this.lives);
        formation.addToGame();
        // count the blocks.
        this.aliens.increase(this.level.numberOfAliensToRemove());
        // add the ball remover to the bottom end of the screen.
        this.remover = new BallRemover(this);
        for (int i = 0; i < aliensList.size(); i++) {
            for (int j = 0; j < aliensList.get(0).size(); j++) {
                aliensList.get(i).get(j).addHitListener(this.remover);
                aliensList.get(i).get(j).addHitListener(this.blockKill);
                aliensList.get(i).get(j).getBlock().beAlien(true);
                aliensList.get(i).get(j).addToGame(this);
                aliensList.get(i).get(j).addHitListener(scoreTracker);
                aliensList.get(i).get(j).addGame(this);
                aliensList.get(i).get(j).setMoveSpeed(0.75 * speedIncrease);
            }
        }
        // add all the block to the game.
        for (Block b: list) {
            b.addToGame(this);
            b.addHitListener(scoreTracker);
            b.addHitListener(remover);
            b.addHitListener(this.blockKill);
        }
        levelIndicator.addToGame();
        indicator.addToGame();
        livesIndi.addToGame();
    }

    /**
     * return the keyboard sensor we are using in this level.
     * @return the keyboard sensor we are using in this level.
     */
    public KeyboardSensor getKeyboard() {
        return this.keyboard;
    }

    /**
     * create the balls and paddle and add them to the game.
     */
    private void createBallsPaddle() {
        // create our paddle in the lower end of the screen.
        this.paddle = new Paddle(new Block(new Rectangle(new Point(this.width / 2,
                this.height - this.edgeSize * 2), this.level.paddleWidth(), this.edgeSize), Color.orange), this);
        this.paddleNum.increase(1);
        // add the paddle to the game.
        paddle.addToGame(this);
        paddle.addHitListener(this.remover);
    }

    /**
     * remove the ball and paddle from the game to make sure they do not go from
     * level to or live to live.
     */
    private void removeBallsAndPaddle() {
        this.paddle.removeFromGame();
    }

    /**
     * start the animation loop and run the game.
     **/
    public void playOneTurn() {
        // create the balls and paddle.
        this.createBallsPaddle();
        // run the countdown animation.
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        // run this animation.
        this.runner.run(this);
        //remove the ball and paddle at the end of the turn.
        this.removeBallsAndPaddle();
    }

    /**
     * the logic used to run one frame of this animation.
     * @param d the draw surface we are drawing the animation on.
     * @param dt the time that had passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // the pause screen we are using.
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
        }
        // run the time passed function on all of the sprites.
        this.sprites.notifyAllTimePassed(dt);
        // draw all the sprites to the screen.
        this.sprites.drawAllOn(d);
        this.running = false;
    }
    /**
     *
     * @return true - we should stop running. false - go on.
     */
    public boolean shouldStop() {
        if (this.aliens.getValue() == 0) {
            this.score.increase(100);
            return true;
        }
        if (this.paddleNum.getValue() == 0) {
            this.lives.decrease(1);
            return true;
        }
        return false;
    }

    /**
     * return the game enviroment of this game.
     * @return the enviroment of this game.
     */
    public GameEnviroment getEnvironment() {
        return this.environment;
    }

    /**
     * remove a collidable from the game.
     * @param c the collidable we are removing.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * remove a sprite from the game.
     * @param s the sprite we are removing from the game.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * return the block killer of this game.
     * @return the hit listner we are returning.
     */
    public HitListener getBlockKill() {
        return this.blockKill;
    }

    /**
     * return the level information of this level.
     * @return the level information of this level.
     */
    public SpaceLevel getLevel() {
        return this.level;
    }

    /**
     * return the amount of block remaining.
     * @return the number of block remaining.
     */
    public int blocksRemaining() {
        return this.aliens.getValue();
    }

    /**
     * when the player looses a life there is a specific logic that has to be done.
     */
    public void removeLife() {
        this.formation.resetToStratPos();
        this.paddleNum.decrease(1);
        this.removeBalls();
    }

    /**
     * remove all of the balls from the game.
     */
    private void removeBalls() {
        for (Ball b : balls) {
            b.removeFromGame(this);
        }
    }

    /**
     * add a ball to the game.
     * @param b the ball we are adding to the game.
     */
    public void addBall(Ball b) {
        this.balls.add(b);
    }
}
