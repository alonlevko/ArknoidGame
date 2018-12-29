package utils;
import animations.AnimationRunner;
import animations.KeyPressStoppableAnimation;

/**
 * this task is a task that shows the high scores screen of the game.
 * @param <T> the return value of the task.
 */
public class ShowHiScoresTask<T> implements Task {
    private AnimationRunner runner;
    private KeyPressStoppableAnimation animation;

    /**
     * create a high score task using the animation runner and the animation we need to run.
     * @param runner the animation runner.
     * @param k the animation we will run.
     */
    public ShowHiScoresTask(AnimationRunner runner, KeyPressStoppableAnimation k) {
        this.runner = runner;
        this.animation = k;
    }

    /**
     * run the task.
     * @return we return null because this animation does not return anything.
     */
    public T run() {
        this.runner.run(this.animation);
        return null;
    }
}
