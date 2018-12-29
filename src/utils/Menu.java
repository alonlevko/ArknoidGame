package utils;
import animations.Animation;

/**
 * this is the interface of all in game menus. they can add a sub menu and a selection.
 * @author Alon Levkovitch
 * @param <T> the object we are creating a menu of.
 */
public interface Menu<T> extends Animation {
    /**
     * add a selection to the menu.
     * @param key the key that invokes the selection.
     * @param message the name of the selection,
     * @param returnVal what the selection returns.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * return the last selection that was made.
     * @return the lase selection of the menu.
     */
    T getStatus();

    /**
     * add a sub menu to the menu.
     * @param key the key that invokes the sub menu.
     * @param message the name of the sub menu.
     * @param subMenu the sub menu itself.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
