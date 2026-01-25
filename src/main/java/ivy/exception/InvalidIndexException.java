package ivy.exception;

/**
 * Represents an exception thrown when a task number provided by the user
 * is out of bounds.
 */
public class InvalidIndexException extends IvyException {
    /**
     * Constructs an {@code InvalidIndexException} with a default error message.
     */
    public InvalidIndexException() {
        super("Task number out of bounds :/");
    }
}
