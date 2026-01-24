import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

public class Ivy {
    private static final String FILE_PATH = "./data/ivy.txt";
    private static ArrayList<Task> tasks;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        loadTasks();

        welcomeUser();

        while (true) {
            String input = scanner.nextLine();

            try {
                boolean modified = handleInput(input);

                if (modified) {
                    saveTasks();
                }

                if (input.equals("bye")) {
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

        switch (command) {
        case "bye":
            if (!arg.isEmpty()) {
                throw new InvalidFormatException("bye");
            }
            sayBye();
            return false;
        case "list":
            if (!arg.isEmpty()) {
                throw new InvalidFormatException("list");
            }
            printTaskList();
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
            addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
            return true;
        case "event":
            String[] eventParts = arg.split(" /from | /to ", 3);
            if (eventParts.length < 3 || eventParts[0].isEmpty() || eventParts[1].isEmpty() || eventParts[2].isEmpty()) {
                throw new InvalidFormatException("event");
            }
            addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
            return true;
        default:
            throw new UnknownCommandException();
        }
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
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidIndexException();
        }
        return index;
    }

    private static void addTask(Task task) {
        tasks.add(task);
        printAdded(task);
    }

    private static void printTaskList() {
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + tasks.get(i));
        }
    }

    private static void printAdded(Task task) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
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

    private static void deleteTask(int index) {
        Task removed = tasks.remove(index);

        System.out.println("    Noted. I've removed this task:");
        System.out.println("      " + removed);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
    }

    private static void loadTasks() {
        tasks = new ArrayList<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = Task.createTaskFromFile(line);
                tasks.add(task);
            }
        } catch (Exception e) {
            System.out.println("Error loading tasks: "  + e.getMessage());
        }
    }

    private static void saveTasks() {
        File dir = new File("./data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (FileWriter fw = new FileWriter(FILE_PATH, false)) {
            for (Task t : tasks) {
                fw.write(t.toFileString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

}
