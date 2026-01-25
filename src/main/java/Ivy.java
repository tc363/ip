import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ivy {
    private ArrayList<Task> tasks;
    private Storage storage;
    private Ui ui;

    public Ivy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = storage.loadTasks();
    }

    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                boolean modified = handleInput(input);

                if (modified) {
                    storage.saveTasks(tasks);
                }

                if (input.equals("bye")) {
                    break;
                }
            } catch (IvyException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.exit();
    }

    public static void main(String[] args) {
        new Ivy("data/ivy.txt").run();
    }

    private boolean handleInput(String input) throws IvyException {
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String arg = parts.length > 1 ? parts[1] : "";

        switch (command) {
        case "bye":
            if (!arg.isEmpty()) {
                throw new InvalidFormatException("bye");
            }
            ui.showFarewell();
            return false;
        case "list":
            if (!arg.isEmpty()) {
                throw new InvalidFormatException("list");
            }
            ui.showTaskList(tasks);
            return false;
        case "mark": {
            int index = parseTaskIndex(arg, "mark");
            markTask(tasks.get(index));
            return true;
        }
        case "unmark": {
            int index = parseTaskIndex(arg, "unmark");
            unmarkTask(tasks.get(index));
            return true;
        }
        case "delete": {
            int index = parseTaskIndex(arg, "delete");
            deleteTask(index);
            return true;
        }
        case "todo":
            if (arg.isEmpty()) {
                throw new InvalidFormatException("todo");
            }
            addTask(new Todo(arg));
            return true;
        case "deadline":
            String[] deadlineParts = arg.split(" /by ", 2);
            if (deadlineParts.length < 2 || deadlineParts[0].isEmpty() || deadlineParts[1].isEmpty()) {
                throw new InvalidFormatException("deadline");
            }
            try {
                addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
            } catch (DateTimeParseException e) {
                throw new InvalidFormatException("datetime");
            }
            return true;
        case "event":
            String[] eventParts = arg.split(" /from | /to ", 3);
            if (eventParts.length < 3 || eventParts[0].isEmpty() || eventParts[1].isEmpty() || eventParts[2].isEmpty()) {
                throw new InvalidFormatException("event");
            }
            try {
                addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
            } catch (DateTimeParseException e) {
                throw new InvalidFormatException("datetime");
            }
            return true;
        default:
            throw new UnknownCommandException();
        }
    }

    private int parseTaskIndex(String arg, String command) throws IvyException {
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
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidIndexException();
        }
        return index;
    }

    private void addTask(Task task) {
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    private void markTask(Task task) {
        task.markAsDone();
        ui.showTaskMarked(task);
    }

    private void unmarkTask(Task task) {
        task.markAsNotDone();
        ui.showTaskUnmarked(task);
    }

    private void deleteTask(int index) {
        Task removed = tasks.remove(index);
        ui.showTaskDeleted(removed, tasks.size());
    }
}
