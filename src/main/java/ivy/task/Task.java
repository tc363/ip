package ivy.task;

import java.time.LocalDateTime;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public static Task createTaskFromFile(String line) {
        try {
            String[] parts = line.split(" \\| ");

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task;

            switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                task = new Deadline(description, LocalDateTime.parse(parts[3]));
                break;
            case "E":
                task = new Event(description, LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
                break;
            default:
                return null;
            }

            if (isDone) {
                task.markAsDone();
            }

            return task;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Warning: skipped corrupted line -> " + line);
            return null;
        }
    }

    public abstract String toFileString();

    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
