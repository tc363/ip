package ivy.storage;

import ivy.task.Task;
import ivy.task.TaskList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles saving and loading tasks to and from a file.
 * <p>
 * Responsible for initializing the storage file and directory if they do not exist.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a {@code Storage} object that manages a file at the given path.
     *
     * @param filePath Path of file used to store tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        initFile();
    }

    private void initFile() {
        File dir = new File("./data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error initializing storage file: " + e.getMessage());
            }
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return {@code ArrayList} of {@code Task} objects read from the file.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = Task.createTaskFromFile(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves the given task list to the storage file.
     *
     * @param tasks {@code TaskList} containing tasks to save.
     */
    public void saveTasks(TaskList tasks) {
        assert tasks != null : "TaskList should not be null";

        try (FileWriter fw = new FileWriter(filePath, false)) {
            for (Task t : tasks.getTasks()) {
                fw.write(t.toFileString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
