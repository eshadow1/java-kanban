package model.task;

import model.data_test.CorrectData;
import model.data_test.IncorrentData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void getFromArrayStringAndCheckAllParameters() {
        Task temp = Task.fromArrayString(CorrectData.correctInitTask);
        assertNotNull(temp);
        assertEquals(35, temp.getId());
        assertEquals("task1", temp.getTitle());
        Assertions.assertEquals(Status.NEW, temp.getStatus());
        assertEquals("test1", temp.getDescription());
        assertEquals(Type.TASK, temp.getType());
        assertNull(temp.getDurationMinutes());
    }

    @Test
    void getFromNullIncorrectArrayString() {
        Task tempNullIncorrectArrayString = Task.fromArrayString(IncorrentData.incorrectArrayStringInitTask);
        assertNull(tempNullIncorrectArrayString);
    }

    @Test
    void getFromNullIndexIncorrectArrayString() {
        Task tempNullIndex = Task.fromArrayString(IncorrentData.incorrectIndexStringInitTask);
        assertNull(tempNullIndex);
    }
}