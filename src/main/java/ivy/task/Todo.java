package ivy.task;

/**
 * Represents a Todo task with only a description.
 */
public class Todo extends Task {

    /**
     * Constructs a {@code Todo} task with the given description.
     *
     * @param description Description of task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of task suitable for saving to a file.
     *
     * @return Formatted string representing task.
     */
    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of task for display to the user.
     *
     * @return Formatted string representing task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
