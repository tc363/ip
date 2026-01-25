package ivy.ui;

import ivy.task.Task;
import ivy.task.TaskList;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("    Hello! I'm ivy.Ivy.");
        System.out.println("    What can I do for you?");
    }

    public void showFarewell() {
        System.out.println("    Bye. Hope to see you again soon!");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            System.out.println("    " + (i + 1) + ". " + tasks.getTask(i));
        }
    }

    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + totalTasks + " tasks in the list.");
    }

    public void showTaskMarked(Task task) {
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("      " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("      " + task);
    }

    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println("    Noted. I've removed this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + totalTasks + " tasks in the list.");
    }

    public void showError(String message) {
        System.out.println("    " + message);
    }

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
