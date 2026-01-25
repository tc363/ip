package ivy.exception;

/**
 * Represents an exception thrown when a user command does not follow
 * the expected format.
 */
public class InvalidFormatException extends IvyException {
    /**
     * Constructs an {@code InvalidFormatException} with an error message
     * specific to the given command.
     *
     * @param command Command entered by user that has an invalid format.
     */
    public InvalidFormatException(String command) {
        super(generateMessage(command));
    }

    private static String generateMessage(String command) {
        return switch (command) {
            case "mark" -> "mark requires a single task number: mark <taskNumber>";
            case "unmark" -> "unmark requires a single task number: unmark <taskNumber>";
            case "list" -> "list takes no arguments: list";
            case "todo" -> "todo requires a description: todo <description>";
            case "deadline" -> "deadline requires a description and /by <time>: deadline <desc> /by <time>";
            case "event" -> "event requires a description, /from <start> and /to <end>: event <desc> /from <start> /to <end>";
            case "bye" -> "bye takes no arguments: bye";
            case "delete" -> "delete requires a single task number: delete <taskNumber>";
            case "datetime" -> "Date must be d/M/yyyy and time must be HHmm";
            default -> "Invalid command format!";
        };
    }
}
