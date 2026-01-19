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

            try {
                boolean continueLoop = handleInput(input);
                if (!continueLoop) {
                    break;
                }
            } catch (IvyException e) {
                System.out.println("    " + e.getMessage());
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

    private static boolean handleInput(String input) throws IvyException {
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String arg = parts.length > 1 ? parts[1] : "";

        if (command.equals("bye")) {
            if (!arg.isEmpty()) {
                throw new InvalidFormatException("bye");
            }
            sayBye();
            return false;
        } else if (command.equals("list")) {
            if (!arg.isEmpty()) {
                throw new InvalidFormatException("list");
            }
            printTaskList();
        } else if (command.equals("mark")) {
            int index = parseTaskIndex(arg, "mark");
            markTask(tasks[index]);
        } else if (command.equals("unmark")) {
            int index = parseTaskIndex(arg, "unmark");
            unmarkTask(tasks[index]);
        } else if (command.equals("todo")) {
            if (arg.isEmpty()) {
                throw new InvalidFormatException("todo");
            }
            addTask(new Todo(arg));
        } else if (command.equals("deadline")) {
            String[] deadlineParts = arg.split(" /by ", 2);
            if (deadlineParts.length < 2 || deadlineParts[0].isEmpty() || deadlineParts[1].isEmpty()) {
                throw new InvalidFormatException("deadline");
            }
            addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
        } else if (command.equals("event")) {
            String[] eventParts = arg.split(" /from | /to ", 3);
            if (eventParts.length < 3 || eventParts[0].isEmpty() || eventParts[1].isEmpty() || eventParts[2].isEmpty()) {
                throw new InvalidFormatException("event");
            }
            addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
        } else {
            throw new UnknownCommandException();
        }

        return true;
    }

    private static int parseTaskIndex(String arg, String command) throws IvyException {
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
        if (index < 0 || index >= taskCount) {
            throw new InvalidIndexException();
        }
        return index;
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
