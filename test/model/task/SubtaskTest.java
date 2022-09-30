package model.task;

import model.data_test.CorrectData;
import model.data_test.IncorrentData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test
    void getIdParentEpic() {
        Subtask temp = Subtask.fromArrayString(CorrectData.correctInitSubtask);
        assertEquals(0, temp.getIdParentEpic());
    }

    @Test
    void getFromArrayString() {
        Subtask temp = Subtask.fromArrayString(CorrectData.correctInitSubtask);
        assertNotNull(temp);
        assertEquals(2, temp.getId());
        assertEquals("subtask1", temp.getTitle());
        Assertions.assertEquals(Status.NEW, temp.getStatus());
        assertEquals("test1", temp.getDescription());
        assertEquals(0, temp.getIdParentEpic());
    }

    @Test
    void getFromNullIncorrectArrayString() {
        Subtask tempNullIncorrectArrayString = Subtask.fromArrayString(IncorrentData.incorrectArrayStringSubtask);
        assertNull(tempNullIncorrectArrayString);
    }

    @Test
    void getFromNullIndexIncorrectArrayString() {
        Subtask tempNullIndex = Subtask.fromArrayString(IncorrentData.incorrectIndexStringInitSubtask);
        assertNull(tempNullIndex);
    }

    @Test
    void getFromNullIndexParentIncorrectArrayString() {
        Subtask tempNullParentIndex = Subtask.fromArrayString(IncorrentData.incorrectIdParentStringISubtask);
        assertNull(tempNullParentIndex);
    }
}