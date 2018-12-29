package utils;

import java.io.File;
import java.io.IOException;

/**
 * this task quits the game.
 * @param <T>
 */
public class QuitTask<T> implements Task {
    private HighScoresTable table;
    private File save;

    /**
     * create a new quit task.
     * @param t the score table we will save.
     * @param s the file we will save it to.
     */
    public QuitTask(HighScoresTable t, File s) {
        this.table = t;
        this.save = s;
    }
    @Override
    public T run() {
        try { // save the score table.
            this.table.save(this.save);
        } catch (IOException e) {
            System.out.println("unable to save table");
        }
        System.exit(1);
        return null;
    }
}
