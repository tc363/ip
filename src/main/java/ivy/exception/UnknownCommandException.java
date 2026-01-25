package ivy.exception;

public class UnknownCommandException extends IvyException {
    public UnknownCommandException() {
        super("I don't recognise this command :(");
    }
}
