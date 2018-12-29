package game;
import animations.AnimationRunner;
import animations.EndScreen;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import counters.Counter;
import biuoop.Sleeper;

import levelinformation.SpaceLevel;
import utils.HighScoresTable;
import utils.ScoreInfo;
import utils.Task;

/**
 * the game flow class is responsible for running the diffrenet levels of the game
 * and managing the game in a general way.
 * @author Alon Levkovitch
 * @param <T> the return type of the game flow.
 */
public class GameFlow<T> implements Task {
    private HighScoresTable table;
    private Counter lives;
    private Counter score;
    private AnimationRunner runner;
    private KeyboardSensor keyboardSensor;
    private GUI gui;
    private SpaceLevel levels;

    /**
     * create a new game flow based on a gui that gives us all we need to run the game.
     * @param gui the gui that will run the game.
     * @param levels the list of level we will run.
     * @param t the high score table we will update with the game score.
     */
    public GameFlow(GUI gui, SpaceLevel levels, HighScoresTable t) {
        this.levels = levels;
        this.table = t;
        this.lives = new Counter();
        // we want to have 7 lives in this game.
        lives.increase(7);
        this.score = new Counter();
        this.gui = gui;
        // create an animation runner to run our animations.
        this.runner = new AnimationRunner(this.gui, new Sleeper());
        this.keyboardSensor = gui.getKeyboardSensor();
    }

    /**
     * this method is responsible for running the levels supplied in the list one
     * after the other.
     * @return we always return void.
     */
    public T run() {
        boolean win = true;
        // loop over all of the levels in our list.{
        // create a new game level with the parameters we need from the level inforamtion and the game flow.
        GameLevel level = new GameLevel(this.levels, this.keyboardSensor,
                this.runner, this.gui, this.lives, this.score);
        // initialize the new level.
        double i = 1;
        while (win) { // while the player hasn't lost we continue to run the game.
            // initialize the game with the round of play and the aliens speed.
            level.initialize(i, (int) i);
            // if we still have blocks an lives we run the animation of the level.
            while (level.getLives() > 0 && level.blocksRemaining() > 0) {
                level.playOneTurn();
            }
            // if the player looses.
            if (level.getLives() == 0) {
                // the player lost.
                win = false;
            }
            if (win) {
                i++;
            }

        }
        // run the end screen with taking notice if the player won or lost.
        EndScreen end = new EndScreen(win, this.score.getValue());
        this.runner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space", end));
        DialogManager dialog = gui.getDialogManager();
        String player = dialog.showQuestionDialog("Name", "What is your name?", "");
        this.table.add(new ScoreInfo(player, this.score.getValue()));
        this.runner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space",
                new HighScoresAnimation(this.table)));
        return null;
    }

    /**
     * get the table that is associated with this game.
     * @return the high score table of the game.
     */
    public HighScoresTable getHighscores() {
        return this.table;
    }
}
