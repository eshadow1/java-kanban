package model.task;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class EpicTest {

    @Test
    void addAndGetSubtasks() {
        Epic epic = Epic.fromArrayString(new String[]{"0", "EPIC", "epic1", "NEW", "test1"});
        assertTrue(epic.getSubtasks().isEmpty());
        Subtask subtask = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "0"});
        epic.addSubtask(subtask);
        assertFalse(epic.getSubtasks().isEmpty());
        assertEquals(List.of(subtask), epic.getSubtasks());
    }

    @Test
    void updateSubtask() {
        Epic epic = Epic.fromArrayString(new String[]{"0", "EPIC", "epic1", "NEW", "test1"});
        Subtask subtask = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "0"});
        epic.addSubtask(subtask);
        assertEquals(List.of(subtask), epic.getSubtasks());

        subtask.setStatus(Status.IN_PROGRESS);
        epic.updateSubtask(subtask);
        assertEquals(List.of(subtask), epic.getSubtasks());
    }

    @Test
    void removeSubtask() {
        Epic epic = Epic.fromArrayString(new String[]{"0", "EPIC", "epic1", "NEW", "test1"});
        Subtask subtask = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "0"});
        epic.addSubtask(subtask);
        assertFalse(epic.getSubtasks().isEmpty());
        epic.removeSubtask(subtask);
        assertTrue(epic.getSubtasks().isEmpty());
    }

    @Test
    void updateStatus() {
        Epic epic = Epic.fromArrayString(new String[]{"0", "EPIC", "epic1", "NEW", "test1"});
        Subtask subtask = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "0"});
        epic.addSubtask(subtask);
        assertEquals(Status.NEW, epic.getStatus());

        subtask.setStatus(Status.IN_PROGRESS);
        epic.updateSubtask(subtask);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subtask.setStatus(Status.DONE);
        epic.updateSubtask(subtask);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    void clearSubtasks() {
        Epic epic = Epic.fromArrayString(new String[]{"0", "EPIC", "epic1", "NEW", "test1"});
        Subtask subtask = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "0"});
        epic.addSubtask(subtask);
        assertFalse(epic.getSubtasks().isEmpty());
        epic.clearSubtasks();
        assertTrue(epic.getSubtasks().isEmpty());
    }

    @Test
    void getFromArrayString() {
        Epic tempNullIncorrectArrayString = Epic.fromArrayString(new String[]{"0", "EPIC", "epic1", "NEW"});
        assertNull(tempNullIncorrectArrayString);

        Epic tempNullIndex = Epic.fromArrayString(new String[]{"a0", "EPIC", "epic1", "NEW", "test1"});
        assertNull(tempNullIndex);

        Epic temp = Epic.fromArrayString(new String[]{"0", "EPIC", "epic1", "NEW", "test1"});
        assertNotNull(temp);
        assertEquals(0, temp.getId());
        assertEquals("epic1", temp.getTitle());
        assertEquals(Status.NEW, temp.getStatus());
        assertEquals("test1", temp.getDescription());
    }
}