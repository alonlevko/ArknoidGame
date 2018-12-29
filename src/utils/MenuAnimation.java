package utils;

import animations.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collidable.Sprite;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * this is an animation that shows a menu and lets the user choose the task he
 * want to run from the menu.
 * @param <T> the type of task the menu runs.
 */
public class MenuAnimation<T> implements Menu<T>, Task<T> {
    private String title;
    private AnimationRunner runner;
    private boolean stop;
    private KeyboardSensor sensor;
    private ArrayList<Selection> selections;
    private Sprite backGround;
    private T status;
    private List<Menu<T>> subMenues;
    private boolean enterSubMenu;
    private Menu<T> currentSub;
    private boolean isAlreadyPressed = true;

    /**
     * create a new menu based on its simple charactaristics.
     * @param title the title of the menu.
     * @param sensor the keyboard sensor that will read the choice.
     * @param b the background of the menu.
     * @param r the animation runner that will run the menu.
     */
    public MenuAnimation(String title, KeyboardSensor sensor, Sprite b, AnimationRunner r) {
        this.stop = false;
        this.title = title;
        this.sensor = sensor;
        this.selections = new ArrayList<Selection>();
        this.subMenues = new ArrayList<Menu<T>>();
        this.backGround = b;
        this.runner = r;
        this.enterSubMenu = false;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new Selection<T>(key, message, returnVal));
    }

    @Override
    public boolean shouldStop() {
        if (enterSubMenu) {
            return currentSub.shouldStop();
        } else {
            return this.stop;
        }
    }
    @Override
    public T run() {
        this.runner.run(this);
        return getStatus();
    }
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.enterSubMenu) { // check if we have a sub menu on.
            this.currentSub.doOneFrame(d, dt);
        } else { // we are in this menu.
                // check if the used pressed on of the keys of the selections.
                for (int i = 0; i < this.selections.size(); i++) {
                    if (this.sensor.isPressed(this.selections.get(i).getKey())) {
                        if (!isAlreadyPressed) { // make sure we dont run anything on a bad key
                            // update the statuse.
                            this.status = (T) selections.get(i).getReturnVal();
                            this.stop = true; // we want to stop after a selection was made.
                            this.isAlreadyPressed = false;
                            // check if the press was also fit for a sum menu.
                            for (Menu<T> s : this.subMenues) {
                                if (this.status == s) {
                                    // make the sub menu a member and let all the functions know.
                                    this.currentSub = s;
                                    this.enterSubMenu = true;
                                }
                            }
                        } else {
                            this.isAlreadyPressed = false;
                        }
                    }
                }
                // draw the menu on to the screen.
                this.backGround.drawOn(d);
                d.setColor(Color.RED);
                d.drawText(250, 100, this.title, 40);
                for (int i = 0; i < this.selections.size(); i++) {
                    d.drawText(250, 150 + (200 / this.selections.size()) * i,
                            "for " + this.selections.get(i).getMessage() + " press "
                                    + this.selections.get(i).getKey(), 20);
                }
            }

    }

    @Override
    public T getStatus() {
        if (this.enterSubMenu) {
            this.stop = currentSub.shouldStop();
            return this.currentSub.getStatus();
        } else {
            this.stop = false;
            return this.status;
        }
    }

    /**
     * add a sub menu to the menu.
     * @param key the key that chooses the submenu.
     * @param message the name of the sub menu.
     * @param subMenu the sub menu itself.
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        // add it to our lists.
        this.subMenues.add(subMenu);
        this.selections.add(new Selection(key, message, subMenu));
    }

    /**
     * add many selections togheter if we need.
     * @param list the selection we are adding.
     */
    public void addAll(List<Selection<T>> list) {
        this.selections.addAll(list);
    }

    /**
     * reset the menu for another run.
     */
    public void zeroStatus() {
        this.currentSub = null;
        this.enterSubMenu = false;
        this.status = null;
    }
}
