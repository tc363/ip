import java.util.Scanner;

public class Ivy {
    private static final int MAX_TASKS = 100;
    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        welcomeUser();

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String arg = parts.length > 1 ? parts[1] : "";

            if (input.equals("bye")) {
                sayBye();
                break;
            } else if (command.equals("list")) {
                printTaskList();
            } else if (command.equals("mark")) {
                int index = Integer.parseInt(arg) - 1;
                if (index < taskCount && index >= 0) {
                    markTask(tasks[index]);
                }
            } else if (command.equals("unmark")) {
                int index = Integer.parseInt(arg) - 1;
                if (index < taskCount && index >= 0) {
                    unmarkTask(tasks[index]);
                }
            } else if (command.equals("todo")) {
                    addTask(new Todo(arg));
            } else if (command.equals("deadline")) {
                    String[] deadlineParts = arg.split(" /by ", 2);
                    addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
            } else if (command.equals("event")) {
                    String[] eventParts = arg.split(" /from | /to ", 3);
                    addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
            }
        }

        scanner.close();
    }

    private static void welcomeUser() {
        System.out.println("    Hello! I'm Ivy.");
        System.out.println("    What can I do for you?");
    }

    private static void sayBye() {
        System.out.println("    Bye. Hope to see you again soon!");
    }

    private static void addTask(Task task) {
        if (taskCount < tasks.length) {
            tasks[taskCount] = task;
            taskCount++;
            printAdded(task);
        }
    }

    private static void printTaskList() {
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("    " + (i + 1) + ". " + tasks[i]);
        }
    }

    private static void printAdded(Task task) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + taskCount + " tasks in the list.");
    }

    private static void markTask(Task task) {
        task.markAsDone();
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("      " + task);
    }

    private static void unmarkTask(Task task) {
        task.markAsNotDone();
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("      " + task);
    }
}
