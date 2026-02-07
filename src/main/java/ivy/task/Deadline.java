package ivy.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 * <p>
 * The deadline is stored as a {@link LocalDateTime} object.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final int UPCOMING_DAYS = 7;

    /**
     * Constructs a {@code Deadline} task from a description and a date-time string.
     *
     * @param description Description of task.
     * @param by Deadline date-time as a string in the format d/M/yyyy HHmm.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, FORMATTER);
    }

    /**
     * Constructs a {@code Deadline} task from a description and a {@link LocalDateTime} object.
     *
     * @param description Description of task.
     * @param by Deadline date-time.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public boolean isUpcoming() {
        LocalDateTime now = LocalDateTime.now();
        return !isDone() && by.isAfter(now) && by.isBefore(now.plusDays(UPCOMING_DAYS));
    }

    /**
     * Returns a string representation of task suitable for saving to a file.
     *
     * @return Formatted string representing task.
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    /**
     * Returns a string representation of task for display to the user.
     *
     * @return Formatted string representing task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }
}
