package ivy.exception;

/**
 * Represents an exception thrown when the user enters a command
 * that is not recognised by the application.
 */
public class UnknownCommandException extends IvyException {
    /**
     * Constructs an {@code UnknownCommandException} with a default error message.
     */
    public UnknownCommandException() {
        super("I don't recognise this command :(");
    }
}
