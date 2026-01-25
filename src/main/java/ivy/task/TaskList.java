package ivy.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 * <p>
 * Provides methods to add, remove, and access tasks in the list.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs a {@code TaskList} with the given list of tasks.
     *
     * @param tasks List of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task {@code Task} to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list at specified index.
     *
     * @param index Index of task to delete.
     * @return {@code Task} removed.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns list of tasks.
     *
     * @return {@code ArrayList} containing all tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns task at specified index.
     *
     * @param index Index of task to retrieve.
     * @return {@code Task} at the given index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns total number of tasks in list.
     *
     * @return Number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }
}
