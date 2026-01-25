import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, FORMATTER);
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }
}
