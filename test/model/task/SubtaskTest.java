package model.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void getIdParentEpic() {
        Subtask temp = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "null","-1", "0"});
        assertEquals(0, temp.getIdParentEpic());
    }

    @Test
    void getFromArrayString() {
        Subtask tempNullIncorrectArrayString = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1"});
        assertNull(tempNullIncorrectArrayString);

        Subtask tempNullIndex = Subtask.fromArrayString(new String[]{"a2", "SUBTASK", "subtask1", "NEW", "test1", "null","-1", "0"});
        assertNull(tempNullIndex);

        Subtask tempNullParentIndex = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "null","-1", "a0"});
        assertNull(tempNullParentIndex);

        Subtask temp = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "null","-1", "0"});
        assertNotNull(temp);
        assertEquals(2, temp.getId());
        assertEquals("subtask1", temp.getTitle());
        Assertions.assertEquals(Status.NEW, temp.getStatus());
        assertEquals("test1", temp.getDescription());
        assertEquals(0, temp.getIdParentEpic());
    }
}