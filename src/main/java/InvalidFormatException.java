public class InvalidFormatException extends IvyException {
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
            default -> "Invalid command format!";
        };
    }
}
