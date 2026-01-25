package ivy.storage;

import ivy.task.Task;
import ivy.task.TaskList;
import ivy.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    private final String testFilePath = "test_storage.txt";

    @Test
    public void testSaveAndLoadTasks() throws Exception {
        // Create some tasks
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Buy groceries"));
        tasks.add(new Todo("Feed pet"));

        TaskList taskList = new TaskList(tasks);

        // Save tasks
        Storage storage = new Storage(testFilePath);
        storage.saveTasks(taskList);

        // Load tasks
        ArrayList<Task> loadedTasks = storage.loadTasks();

        assertEquals(2, loadedTasks.size());
        assertEquals("[T][ ] Buy groceries", loadedTasks.get(0).toString());
        assertEquals("[T][ ] Feed pet", loadedTasks.get(1).toString());

        // Clean up
        new File(testFilePath).delete();
    }

    @Test
    public void testLoadTasksWithCorruptLine() throws Exception {
        // Write a corrupt line to the file
        try (FileWriter fw = new FileWriter(testFilePath)) {
            fw.write("T | 0 | Valid task\n");
            fw.write("CORRUPT LINE\n");
        }

        Storage storage = new Storage(testFilePath);
        ArrayList<Task> loadedTasks = storage.loadTasks();

        // Only the valid line should be loaded
        assertEquals(1, loadedTasks.size());
        assertEquals("[T][ ] Valid task", loadedTasks.get(0).toString());

        // Clean up
        new File(testFilePath).delete();
    }
}
