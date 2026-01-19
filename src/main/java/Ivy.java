import java.util.Scanner;

public class Ivy {
    private static final int MAX_TASKS = 100;
    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("    Hello! I'm Ivy.");
        System.out.println("    What can I do for you?");

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String arg = parts.length > 1 ? parts[1] : "";

            if (input.equals("bye")) {
                System.out.println("    Bye. Hope to see you again soon!");
                break;
            } else if (command.equals("list")) {
                System.out.println("    Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("    " + (i + 1) + ". " + tasks[i]);
                }
            } else if (command.equals("mark")) {
                int index = Integer.parseInt(arg) - 1;
                if (index < taskCount && index >= 0) {
                    tasks[index].markAsDone();
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println("      " + tasks[index].toString());
                }
            } else if (command.equals("unmark")) {
                int index = Integer.parseInt(arg) - 1;
                if (index < taskCount && index >= 0) {
                    tasks[index].markAsNotDone();
                    System.out.println("    OK, I've marked this task as not done yet:");
                    System.out.println("      " + tasks[index].toString());
                }
            } else if (command.equals("todo")) {
                if (taskCount < tasks.length) {
                    tasks[taskCount] = new Todo(arg);
                    printAdded(tasks[taskCount]);
                    taskCount++;
                }
            } else if (command.equals("deadline")) {
                if (taskCount < tasks.length) {
                    String[] deadlineParts = input.split(" /by ", 2);
                    tasks[taskCount] = new Deadline(deadlineParts[0], deadlineParts[1]);
                    printAdded(tasks[taskCount]);
                    taskCount++;
                }
            } else if (command.equals("event")) {
                if (taskCount < tasks.length) {
                    String[] eventParts = input.split(" /from | /to ", 3);
                    tasks[taskCount] = new Event(eventParts[0], eventParts[1], eventParts[2]);
                    printAdded(tasks[taskCount]);
                    taskCount++;
                }
            }
        }

        scanner.close();
    }

    public static void printAdded(Task task) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + (taskCount + 1) + " tasks in the list.");
    }
}
