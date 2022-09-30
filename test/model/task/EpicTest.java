package model.task;

import model.data_test.CorrectData;
import model.data_test.IncorrentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class EpicTest {
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    void beforeEach() {
        epic = Epic.fromArrayString(CorrectData.correctInitEpic);
        subtask = Subtask.fromArrayString(CorrectData.correctInitSubtask);
    }

    @Test
    void getEmptySubtasks() {
        assertTrue(epic.getSubtasks().isEmpty());
    }

    @Test
    void addAndGetSubtasks() {
        assertTrue(epic.getSubtasks().isEmpty());
        epic.addSubtask(subtask);
        assertFalse(epic.getSubtasks().isEmpty());
        assertEquals(List.of(subtask), epic.getSubtasks());
    }

    @Test
    void updateSubtask() {
        epic.addSubtask(subtask);
        assertEquals(List.of(subtask), epic.getSubtasks());
        subtask.setStatus(Status.IN_PROGRESS);
        epic.updateSubtask(subtask);
        assertEquals(List.of(subtask), epic.getSubtasks());
    }

    @Test
    void removeSubtask() {
        epic.addSubtask(subtask);
        assertFalse(epic.getSubtasks().isEmpty());
        epic.removeSubtask(subtask);
        assertTrue(epic.getSubtasks().isEmpty());
    }

    @Test
    void updateInProgressStatus() {
        epic.addSubtask(subtask);
        assertEquals(Status.NEW, epic.getStatus());

        subtask.setStatus(Status.IN_PROGRESS);
        epic.updateSubtask(subtask);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void updateDoneStatus() {
        epic.addSubtask(subtask);
        assertEquals(Status.NEW, epic.getStatus());
        subtask.setStatus(Status.DONE);
        epic.updateSubtask(subtask);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    void clearSubtasks() {
        epic.addSubtask(subtask);
        assertFalse(epic.getSubtasks().isEmpty());
        epic.clearSubtasks();
        assertTrue(epic.getSubtasks().isEmpty());
    }

    @Test
    void getFromCorrectArrayString() {
        Epic temp = Epic.fromArrayString(CorrectData.correctInitEpic);
        assertNotNull(temp);
        assertEquals(0, temp.getId());
        assertEquals("epic1", temp.getTitle());
        assertEquals(Status.NEW, temp.getStatus());
        assertEquals("test1", temp.getDescription());
    }

    @Test
    void getFromNullIncorrectArrayString() {
        Epic tempNullIncorrectArrayString = Epic.fromArrayString(IncorrentData.incorrectArrayStringInitEpic);
        assertNull(tempNullIncorrectArrayString);
    }

    @Test
    void getFromNullIndexIncorrectArrayString() {
        Epic tempNullIndex = Epic.fromArrayString(IncorrentData.incorrectIndexStringInitEpic);
        assertNull(tempNullIndex);
    }
}