package utils;

/**
 * the interface is a task. a task can only run.
 * @param <T> the return parameter of the task.
 */
public interface Task<T> {
    /**
     * run the task. return T.
     * @return the return value of the task.
     */
    T run();
}
