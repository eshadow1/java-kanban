package model.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void getFromArrayStringAndCheckAllParameters() {
        Task tempNullIncorrectArrayString = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW"});
        assertNull(tempNullIncorrectArrayString);

        Task tempNullIndex = Task.fromArrayString(new String[]{"a35", "TASK", "task1", "NEW", "test1", "null","-1"});
        assertNull(tempNullIndex);

        Task temp = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW", "test1", "null","-1"});
        assertNotNull(temp);
        assertEquals(35, temp.getId());
        assertEquals("task1", temp.getTitle());
        Assertions.assertEquals(Status.NEW, temp.getStatus());
        assertEquals("test1", temp.getDescription());
        assertEquals(Type.TASK, temp.getType());
        assertEquals(Duration.ofMinutes(-1), temp.getDurationMinutes());
    }
}