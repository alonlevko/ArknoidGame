package animations;
import biuoop.DrawSurface;
import game.SpriteCollection;
import java.awt.Color;
/**
 * an animation that shows a count down to 0 on the screen before the start of
 * each level.
 * @author Alon Levkovitch
 */
public class CountdownAnimation implements Animation {
    private Integer countFrom;
    private SpriteCollection gameScreen;
    private double appearFor;
    private double apperences;

    /**
     * create a new countdown animation based on the number of seconds we want
     * it to show, the number we are starting the count at and the object we will
     * draw to the screen.
     * @param numOfSeconds the number of second the animation will show.
     * @param countFrom the first number of the countdown.
     * @param gameScreen the thing we will draw to the screen.
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        // calculate the number of times we need the loop to happen.
        this.appearFor = 1000 * (numOfSeconds / countFrom);
        this.apperences = this.appearFor;
    }

    /**
     * run one frame of the animation.
     * @param d the draw surface we are drawing the animation on.
     * @param dt the time that passed between calls.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //draw the sprites to the draw surface.
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.MAGENTA);
        // draw the numbers.
        d.drawText(350, 300, this.countFrom.toString(), 100);
        // count the frame.
        this.apperences = this.apperences - (1000 / 60);
        // reset the loop.
        if (this.apperences <= 0) {
            this.countFrom = this.countFrom - 1;
            this.apperences = this.appearFor;
        }
    }

    /**
     * check if we ended our animation loop.
     * @return true - we ended the loop, false - continue.
     */
    public boolean shouldStop() {
        return (this.countFrom == 0);
    }
}
