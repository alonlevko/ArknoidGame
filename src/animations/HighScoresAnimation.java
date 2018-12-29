package animations;
import biuoop.DrawSurface;
import utils.HighScoresTable;
import java.awt.Color;

/**
 * the animation that runs the high score table.
 * @author Alon Levkovitch
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable table;

    /**
     * create a new animation based on the table it will show.
     * @param scoresTable the table that we will show in the animation.
     */
    public HighScoresAnimation(HighScoresTable scoresTable) {
        this.table = scoresTable;
    }

    /**
     * get the table that is shown by this animation.
     * @return the table of the animation.
     */
    public HighScoresTable getTable() {
        return this.table;
    }

    @Override
    public boolean shouldStop() {
        return false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.YELLOW);
        d.drawText(250, 100, "High Scores: ", 40);
        d.setColor(Color.CYAN);
        int count = this.table.size();
        // draw all of the scores that the table saves.
        if (this.table.getScores().size() > this.table.getSize()) {
            count = this.table.getSize();
        } else if (this.table.getScores().size() < this.table.getSize()) {
            count = this.table.getScores().size();
        }
        for (int i = 0; i < count; i++) {
                d.drawText(250, 150 + (350 / this.table.getSize()) * i,
                        this.table.getScores().get(i).getName() + " scored "
                                + this.table.getScores().get(i).getScore(), 30);
        }
    }
}
