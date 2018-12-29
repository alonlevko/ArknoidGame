package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * this class is the father class for the animation that can be stopped by the
 * press of a key.
 * @author Alon Levkovitch
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;
    private boolean pressed;

    /**
     * create a new key press stopabble animtaion.
     * @param sensor the sensor we will use to stop the animation.
     * @param key the key that will be used to stop the animation.
     * @param animation the actual animation we will run.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = true;
        this.pressed = false;
    }

    @Override
    public boolean shouldStop() {
        return this.pressed;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.sensor.isPressed(this.key)) {
            if (!isAlreadyPressed) {
                this.pressed = true;
            }
        }
        this.isAlreadyPressed = false;
        this.animation.doOneFrame(d, dt);
    }
}
