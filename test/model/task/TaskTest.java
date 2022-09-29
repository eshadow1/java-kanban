package model.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private final String[] correctInitTask = new String[]{"35", "TASK", "task1", "NEW", "test1", "null", "null"};
    @Test
    void getFromArrayStringAndCheckAllParameters() {
        Task tempNullIncorrectArrayString = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW"});
        assertNull(tempNullIncorrectArrayString);

        Task tempNullIndex = Task.fromArrayString(new String[]{"a35", "TASK", "task1", "NEW", "test1", "null", "null"});
        assertNull(tempNullIndex);

        Task temp = Task.fromArrayString(correctInitTask);
        assertNotNull(temp);
        assertEquals(35, temp.getId());
        assertEquals("task1", temp.getTitle());
        Assertions.assertEquals(Status.NEW, temp.getStatus());
        assertEquals("test1", temp.getDescription());
        assertEquals(Type.TASK, temp.getType());
        assertNull(temp.getDurationMinutes());
    }
}