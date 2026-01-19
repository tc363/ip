import java.util.Scanner;

public class Ivy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("    Hello! I'm Ivy.");
        System.out.println("    What can I do for you?");

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);

            if (input.equals("bye")) {
                System.out.println("    Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                System.out.println("    Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("    " + (i + 1) + ". " + tasks[i]);
                }
            } else if (parts[0].equals("mark")) {
                int index = Integer.parseInt(parts[1]) - 1;
                if (index < taskCount && index >= 0) {
                    tasks[index].markAsDone();
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println("      " + tasks[index].toString());
                }
            } else if (parts[0].equals("unmark")) {
                int index = Integer.parseInt(parts[1]) - 1;
                if (index < taskCount && index >= 0) {
                    tasks[index].markAsNotDone();
                    System.out.println("    OK, I've marked this task as not done yet:");
                    System.out.println("      " + tasks[index].toString());
                }
            } else {
                if (taskCount < tasks.length) {
                    tasks[taskCount] = new Task(input);
                    taskCount++;
                    System.out.println("    added: " + input);
                }
            }
        }

        scanner.close();
    }
}
