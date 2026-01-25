package ivy.exception;

public class InvalidIndexException extends IvyException {
    public InvalidIndexException() {
        super("ivy.task.Task number out of bounds:/");
    }
}
