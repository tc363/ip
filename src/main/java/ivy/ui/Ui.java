package ivy.ui;

import ivy.task.Task;
import ivy.task.TaskList;

import java.util.Scanner;

/**
 * Handles all user interactions including reading input and displaying messages.
 */
public class Ui {
    private final Scanner scanner;
    private String lastMessage = "";

    /**
     * Constructs a {@code Ui} object and initializes the input scanner.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Shows welcome message when program starts.
     */
    public void showWelcome() {
        lastMessage = "Hello! I'm Ivy.\nWhat can I do for you?";
        System.out.println("    " + lastMessage);
    }

    /**
     * Shows farewell message when program ends.
     */
    public void showFarewell() {
        lastMessage = "Bye. Hope to see you again soon!";
        System.out.println("    "  + lastMessage);
    }

    /**
     * Reads a line of user input.
     *
     * @return User's input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays full list of tasks.
     *
     * @param tasks {@code TaskList} to display.
     */
    public void showTaskList(TaskList tasks) {
        if (handleEmptyTaskList(tasks, "Your task list is currently empty.")) {
            return;
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            sb.append(formatTaskWithIndex(i + 1, tasks.getTask(i))).append("\n");
        }

        lastMessage = sb.toString(); // store for GUI

        // Print for CLI
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            System.out.println("    " + (i + 1) + ". " + tasks.getTask(i));
        }
    }

    /**
     * Displays a message when a task has been added.
     *
     * @param task Task added.
     * @param totalTasks Total number of tasks in list.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        lastMessage = "Got it. I've added this task:\n"
                + task + "\n"
                + "Now you have " + totalTasks + " tasks in the list.";
        System.out.println("    " + lastMessage.replace("\n", "\n    "));
    }

    /**
     * Displays a message when a task has been marked as done.
     *
     * @param task Task marked as done.
     */
    public void showTaskMarked(Task task) {
        lastMessage = "Nice! I've marked this task as done:\n" + task;
        System.out.println("    " + lastMessage.replace("\n", "\n    "));
    }

    /**
     * Displays a message when a task has been unmarked.
     *
     * @param task Task unmarked.
     */
    public void showTaskUnmarked(Task task) {
        lastMessage = "OK, I've marked this task as not done yet:\n" + task;
        System.out.println("    " + lastMessage.replace("\n", "\n    "));
    }

    /**
     * Displays a message when a task has been deleted.
     *
     * @param task Task deleted.
     * @param totalTasks Total number of tasks in list.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        lastMessage = "Noted. I've removed this task:\n" + task
                + "\nNow you have " + totalTasks + " tasks in the list.";
        System.out.println("    " + lastMessage.replace("\n", "\n    "));
    }

    /**
     * Displays an error message to the user.
     *
     * @param message Error message.
     */
    public void showError(String message) {
        lastMessage = message;
        System.out.println("    " + lastMessage.replace("\n", "\n    "));
    }

    /**
     * Closes the input scanner.
     * <p>
     * This method should be called when the program exits to release resources.
     */
    public void exit() {
        scanner.close();
    }

    public void showMatchingTasks(TaskList tasks) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            sb.append(formatTaskWithIndex(i + 1, tasks.getTask(i))).append("\n");
        }

        lastMessage = sb.toString().trim(); // store for GUI

        System.out.println("    " + lastMessage.replace("\n", "\n    "));
    }

    public void showUpcomingTasks(TaskList tasks) {
        StringBuilder sb = new StringBuilder("Here are the upcoming tasks in your list:\n");
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            sb.append(formatTaskWithIndex(i + 1, tasks.getTask(i))).append("\n");
        }

        lastMessage = sb.toString().trim();

        System.out.println("    " + lastMessage.replace("\n", "\n    "));
    }

    // AI-assisted refactor:
    // Extracted task formatting into a helper method to avoid duplication
    // across multiple display methods with the help of ChatGPT.

    /**
     * Formats a task with proper indentation so wrapped lines align nicely.
     *
     * @param index Task index.
     * @param task Task.
     */
    private String formatTaskWithIndex(int index, Task task) {
        String indexStr = index + ". ";
        return indexStr + task;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
