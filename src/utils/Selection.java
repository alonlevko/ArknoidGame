package utils;

/**
 * the selection is what we can run from the manu.
 * @param <T> the return value of the the selection.
 */
public class Selection<T> {
    private String key;
    private String message;
    private T returnVal;

    /**
     * create a new selection.
     * @param key the key that is connected to this selection.
     * @param message the message we will see in the menu for the selection.
     * @param returnVal the return value of the selection.
     */
    public Selection(String key, String message, T returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

    /**
     * return the key of the selection.
     * @return the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * return the name of the selection.
     * @return a string that is the name of the selection.
     */
    public String getMessage() {
        return message;
    }

    /**
     * return the value of the task. return what the selection is.
     * @return what the selection is.
     */
    public T getReturnVal() {
        return returnVal;
    }
}
