
import animations.AnimationRunner;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import java.io.IOException;
import java.io.File;

import game.GameFlow;
import levelinformation.SpaceLevel;
import utils.MenuAnimation;
import utils.HighScoresTable;
import utils.Task;
import utils.QuitTask;
import utils.ShowHiScoresTask;
import utils.MenuBackground;


/**
 * create a game and run it.
 * @author Alon Levkovitch
 */
public class Ass7Game {
    /**
     * initializes a new game and runs it.
     *
     * @param args the command line arguments we don't use.
     */
    public static void main(String[] args) {
        // create the general thing we need to run the game.
        GUI gui = new GUI("gui", 800, 600);
        AnimationRunner runner = new AnimationRunner(gui, new Sleeper());
        // create a new high scores table and animation.
        HighScoresAnimation highScores = new HighScoresAnimation(new HighScoresTable(4));
        // wrap it in a key as it is a key press stopable animation.
        KeyPressStoppableAnimation key = new KeyPressStoppableAnimation(gui.getKeyboardSensor(), "space", highScores);
        // the path to the file that keeps the scores.
        File high = new File("highscores");
        try { // try to create an new file
            if (!high.createNewFile()) { // the file has been created.
                highScores.getTable().load(high); // load the file.
            }
        } catch (IOException e) { // error loading the file.
            System.out.println("error creating table file");
        }
        KeyboardSensor sensor = gui.getKeyboardSensor();
        // create the main menu of the game.
        MenuAnimation<Task<Void>> mainMenu = new MenuAnimation<Task<Void>>(
                "Main Menu", sensor, new MenuBackground(), runner);
        mainMenu.addSelection("h", "high scores", new ShowHiScoresTask<Void>(runner, key));
        mainMenu.addSelection("q", "quit", new QuitTask<Void>(highScores.getTable(), high));
        mainMenu.addSelection("s", "play game", new GameFlow<Void>(gui, new SpaceLevel(), highScores.getTable()));
        Task<Void> task;
        // run the game.
        while (true) {
            // first run the main manu.
            task = mainMenu.run();
            if (task != null) { // make sure everything was ok.
                task.run();
                mainMenu.zeroStatus(); //we want to run each task once.
            }
        }

    }
}
