package ivy.ui;

import ivy.task.Task;
import ivy.task.TaskList;

import java.util.Scanner;

/**
 * Handles all user interactions including reading input and displaying messages.
 */
public class Ui {
    private final Scanner scanner;

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
        System.out.println("    Hello! I'm ivy.Ivy.");
        System.out.println("    What can I do for you?");
    }

    /**
     * Shows farewell message when program ends.
     */
    public void showFarewell() {
        System.out.println("    Bye. Hope to see you again soon!");
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
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays a message when a task has been marked as done.
     *
     * @param task Task marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("      " + task);
    }

    /**
     * Displays a message when a task has been unmarked.
     *
     * @param task Task unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("      " + task);
    }

    /**
     * Displays a message when a task has been deleted.
     *
     * @param task Task deleted.
     * @param totalTasks Total number of tasks in list.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println("    Noted. I've removed this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message Error message.
     */
    public void showError(String message) {
        System.out.println("    " + message);
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
        System.out.println("    Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            System.out.println("    " + (i + 1) + ". " + tasks.getTask(i));
        }
    }
}
