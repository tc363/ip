import java.time.format.DateTimeParseException;

public class Parser {
    public static String getCommand(String input) {
        String[] parts = input.split(" ", 2);
        return parts[0];
    }

    public static String getArgs(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    public static void validateNoArgs(String arg, String command) throws IvyException {
        if (!arg.isEmpty()) {
            throw new InvalidFormatException(command);
        }
    }

    public static int parseTaskIndex(String arg, String command, int max) throws IvyException {
        if (arg.isEmpty()) {
            throw new InvalidFormatException(command);
        }

        int index;
        try {
            index = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException(command);
        }

        index--;
        if (index < 0 || index >= max) {
            throw new InvalidIndexException();
        }
        return index;
    }

    public static Todo parseTodo(String arg) throws IvyException {
        if (arg.isEmpty()) {
            throw new InvalidFormatException("todo");
        }
        return new Todo(arg);
    }

    public static Deadline parseDeadline(String arg) throws IvyException {
        String[] parts = arg.split(" /by ", 2);
        if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new InvalidFormatException("deadline");
        }
        try {
            return new Deadline(parts[0], parts[1]);
        } catch (
                DateTimeParseException e) {
            throw new InvalidFormatException("datetime");
        }
    }

    public static Event parseEvent(String arg) throws IvyException {
        String[] parts = arg.split(" /from | /to ", 3);
        if (parts.length < 3 || parts[0].isEmpty() || parts[1].isEmpty() || parts[2].isEmpty()) {
            throw new InvalidFormatException("event");
        }
        try {
            return new Event(parts[0], parts[1], parts[2]);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("datetime");
        }
    }
}
