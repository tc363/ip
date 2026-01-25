package ivy.exception;

/**
 * Represents an exception thrown when the task list has reached its capacity
 * and cannot accept more tasks.
 */
public class TaskListFullException extends IvyException {
    /**
     * Constructs a {@code TaskListFullException} with a default error message.
     */
    public TaskListFullException() {
        super("The task list is full :o");
    }
}
