package ivy.parser;

import ivy.task.Deadline;
import ivy.task.Event;
import ivy.task.Todo;
import ivy.exception.InvalidFormatException;
import ivy.exception.InvalidIndexException;
import ivy.exception.IvyException;

import java.time.format.DateTimeParseException;

/**
 * Provides utility methods for parsing user input into commands,
 * arguments, and task objects.
 */
public class Parser {
    /**
     * Extracts command word from user input.
     *
     * @param input Full user input.
     * @return Command word.
     */
    public static String getCommand(String input) {
        String[] parts = input.split(" ", 2);
        return parts[0];
    }

    /**
     * Extracts arguments from user input.
     *
     * @param input Full user input.
     * @return Argument portion of the input or an empty string if none exists.
     */
    public static String getArgs(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    /**
     * Validates that no arguments are provided for a command.
     *
     * @param arg Argument string.
     * @param command Command being validated.
     * @throws IvyException If arguments are present.
     */
    public static void validateNoArgs(String arg, String command) throws IvyException {
        if (!arg.isEmpty()) {
            throw new InvalidFormatException(command);
        }
    }

    /**
     * Parses task number from user input.
     *
     * @param arg Argument containing the task number.
     * @param command Command being executed.
     * @param max Total number of tasks.
     * @return Parsed zero-based task index.
     * @throws IvyException If the index is invalid.
     */
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

    /**
     * Parses a Todo task from user input.
     *
     * @param arg Description of todo task.
     * @return {@code Todo} task.
     * @throws IvyException If the input format is invalid.
     */
    public static Todo parseTodo(String arg) throws IvyException {
        if (arg.isEmpty()) {
            throw new InvalidFormatException("todo");
        }
        return new Todo(arg);
    }

    /**
     * Parses a Deadline task from user input.
     *
     * @param arg Input containing description and deadline.
     * @return {@code Deadline} task.
     * @throws IvyException If date-time is invalid or not in the expected format.
     */
    public static Deadline parseDeadline(String arg) throws IvyException {
        String[] parts = arg.split(" /by ", 2);
        if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new InvalidFormatException("deadline");
        }
        try {
            return new Deadline(parts[0], parts[1]);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("datetime");
        }
    }

    /**
     * Parses an Event task from user input.
     *
     * @param arg Input containing description, start time, and end time.
     * @return {@code Event} task.
     * @throws IvyException If date-time is invalid or not in the expected format.
     */
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
