package ivy.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific start and end time.
 * <p>
 * The start and end times are stored as {@link LocalDateTime} objects.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Constructs an {@code Event} task from a description and date-time strings.
     *
     * @param description Description of task.
     * @param from Start date-time as a string in the format d/M/yyyy HHmm.
     * @param to End date-time as a string in the format d/M/yyyy HHmm.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDateTime.parse(from, FORMATTER);
        this.to = LocalDateTime.parse(to, FORMATTER);
    }

    /**
     * Constructs an {@code Event} task from a description and {@link LocalDateTime} objects.
     *
     * @param description Description of task.
     * @param from Start date-time.
     * @param to End date-time.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of task suitable for saving to a file.
     *
     * @return Formatted string representing the task.
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }

    /**
     * Returns a string representation of task for display to the user.
     *
     * @return Formatted string representing the task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }
}
