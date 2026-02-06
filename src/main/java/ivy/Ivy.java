package ivy;

import ivy.exception.IvyException;
import ivy.exception.UnknownCommandException;
import ivy.parser.CommandType;
import ivy.parser.Parser;
import ivy.storage.Storage;
import ivy.task.Task;
import ivy.task.TaskList;
import ivy.ui.Ui;

/**
 * Main class of the Ivy application.
 * <p>
 * Handles initialization of UI, storage, and task list, and runs the main program loop.
 */
public class Ivy {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;
    private String FILE_PATH = "data/ivy.txt";

    /**
     * Constructs an {@code Ivy} object with given storage file path.
     */
    public Ivy() {
        ui = new Ui();
        storage = new Storage(FILE_PATH);
        tasks = new TaskList(storage.loadTasks());
    }

    /**
     * Starts the main program loop, handling user input until the user exits.
     */
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
        new Ivy().run();
    }

    /**
     * Handles a single line of user input.
     *
     * @param input User input.
     * @return {@code true} If task list modified, {@code false} otherwise.
     * @throws IvyException If command is invalid or arguments are incorrect.
     */
    private boolean handleInput(String input) throws IvyException {
        CommandType command = Parser.getCommand(input);
        String arg = Parser.getArgs(input);

        switch (command) {
        case BYE:
            Parser.validateNoArgs(arg, command);
            ui.showFarewell();
            return false;
        case LIST:
            Parser.validateNoArgs(arg, command);
            ui.showTaskList(tasks);
            return false;
        case MARK: {
            int index = Parser.parseTaskIndex(arg, command, tasks.getTaskCount());
            markTask(tasks.getTask(index));
            return true;
        }
        case UNMARK: {
            int index = Parser.parseTaskIndex(arg, command, tasks.getTaskCount());
            unmarkTask(tasks.getTask(index));
            return true;
        }
        case DELETE: {
            int index = Parser.parseTaskIndex(arg, command, tasks.getTaskCount());
            deleteTask(index);
            return true;
        }
        case TODO:
            addTask(Parser.parseTodo(arg));
            return true;
        case DEADLINE:
            addTask(Parser.parseDeadline(arg));
            return true;
        case EVENT:
            addTask(Parser.parseEvent(arg));
            return true;
        case FIND:
            findTask(Parser.parseKeyword(arg));
            return false;
        default:
            throw new UnknownCommandException();
        }
    }

    /**
     * Adds a task to the task list and displays a message.
     *
     * @param task {@code Task} to add.
     */
    private void addTask(Task task) {
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getTaskCount());
    }

    /**
     * Marks a task as done and displays a message.
     *
     * @param task {@code Task} to mark as done.
     */
    private void markTask(Task task) {
        task.markAsDone();
        ui.showTaskMarked(task);
    }

    /**
     * Marks a task as not done and displays a message.
     *
     * @param task {@code Task} to mark as not done.
     */
    private void unmarkTask(Task task) {
        task.markAsNotDone();
        ui.showTaskUnmarked(task);
    }

    /**
     * Deletes a task at the specified index and displays a message.
     *
     * @param index Index of task to delete.
     */
    private void deleteTask(int index) {
        Task removed = tasks.deleteTask(index);
        ui.showTaskDeleted(removed, tasks.getTaskCount());
    }

    private void findTask(String keyword) {
        TaskList matches = tasks.findTasks(keyword);
        ui.showMatchingTasks(matches);
    }

    public String getResponse(String input) {
        try {
            boolean modified = handleInput(input);

            if (modified) {
                storage.saveTasks(tasks);
            }

            if (input.equals("bye")) {
                ui.showFarewell();
            }

            return ui.getLastMessage();
        } catch (IvyException e) {
            return e.getMessage();
        }
    }

    public String getWelcomeMessage() {
        ui.showWelcome();
        return ui.getLastMessage();
    }
}
