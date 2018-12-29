package animations;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * this class is the animation runner that runs the animation loop and stopes it
 * when the animation tells it to.
 * @author Alon Levkovitch
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * create a new animation runner based on the gui, frame rate and sleeper.
     * @param gui the gui that will run the animation.
     * @param sleep the sleeper we will use.
     */
    public AnimationRunner(GUI gui, Sleeper sleep) {
        this.framesPerSecond = 60;
        this.gui = gui;
        this.sleeper = sleep;
    }

    /**
     * run the animation loop.
     * @param animation the animation loop that we will run.
     */
    public void run(Animation animation) {
        // the number of milliseconds per frame.
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        // run the animation untill we meet the stop condition
        double dt = (double) 1 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            // save the start time of the animation.
            long startTime = System.currentTimeMillis();
            // get a new draw surface.
            DrawSurface d = this.gui.getDrawSurface();
            // run one frame of the animation.
            animation.doOneFrame(d, dt);
            // show the gui.
            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            // sleep for the time left.
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
