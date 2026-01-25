package ivy.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoTest {

    @Test
    public void testDescriptionStoredCorrectly() {
        Todo todo = new Todo("Buy groceries");
        assertEquals("Buy groceries", todo.getDescription());
    }

    @Test
    public void testMarkAsDoneAndStatusIcon() {
        Todo todo = new Todo("Buy groceries");
        // Initially not done
        assertFalse(todo.isDone());
        assertEquals(" ", todo.getStatusIcon());

        // After marking done
        todo.markAsDone();
        assertTrue(todo.isDone());
        assertEquals("X", todo.getStatusIcon());

        // After marking not done
        todo.markAsNotDone();
        assertFalse(todo.isDone());
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void testToFileString() {
        Todo todo = new Todo("Buy groceries");
        // Not done yet
        String expected = "T | 0 | Buy groceries";
        assertEquals(expected, todo.toFileString());

        // Mark done
        todo.markAsDone();
        expected = "T | 1 | Buy groceries";
        assertEquals(expected, todo.toFileString());
    }

    @Test
    public void testToStringFormat() {
        Todo todo = new Todo("Buy groceries");
        String expected = "[T][ ] Buy groceries";
        assertEquals(expected, todo.toString());

        todo.markAsDone();
        expected = "[T][X] Buy groceries";
        assertEquals(expected, todo.toString());
    }
}
