package ivy.exception;

/**
 * Represents the base class for all custom exceptions used in Ivy.
 */
public abstract class IvyException extends Exception {
    /**
     * Constructs an {@code IvyException} with the specified error message.
     *
     * @param message Message describing the exception.
     */
    public IvyException(String message) {
        super(message);
    }
}
