package ivy;

import ivy.exception.IvyException;
import ivy.exception.UnknownCommandException;
import ivy.parser.Parser;
import ivy.storage.Storage;
import ivy.task.Task;
import ivy.task.TaskList;
import ivy.ui.Ui;

public class Ivy {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    public Ivy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.loadTasks());
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
        String command = Parser.getCommand(input);
        String arg = Parser.getArgs(input);

        switch (command) {
        case "bye":
            Parser.validateNoArgs(arg, "bye");
            ui.showFarewell();
            return false;
        case "list":
            Parser.validateNoArgs(arg, "list");
            ui.showTaskList(tasks);
            return false;
        case "mark": {
            int index = Parser.parseTaskIndex(arg, "mark", tasks.getTaskCount());
            markTask(tasks.getTask(index));
            return true;
        }
        case "unmark": {
            int index = Parser.parseTaskIndex(arg, "unmark", tasks.getTaskCount());
            unmarkTask(tasks.getTask(index));
            return true;
        }
        case "delete": {
            int index = Parser.parseTaskIndex(arg, "delete", tasks.getTaskCount());
            deleteTask(index);
            return true;
        }
        case "todo":
            addTask(Parser.parseTodo(arg));
            return true;
        case "deadline":
            addTask(Parser.parseDeadline(arg));
            return true;
        case "event":
            addTask(Parser.parseEvent(arg));
            return true;
        default:
            throw new UnknownCommandException();
        }
    }

    private void addTask(Task task) {
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getTaskCount());
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
        Task removed = tasks.deleteTask(index);
        ui.showTaskDeleted(removed, tasks.getTaskCount());
    }
}
