package controller.history;

import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager inMemoryHistoryManager;
    private Task task;

    @BeforeEach
    public void beforeEach() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        task = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW", "test1", "null", "null"});
    }

    @Test
    void add() {
        assertEquals(List.of(), inMemoryHistoryManager.getHistory());
        assertEquals("", inMemoryHistoryManager.toString());

        inMemoryHistoryManager.add(task);
        assertEquals(List.of(task), inMemoryHistoryManager.getHistory());
        assertEquals("35", inMemoryHistoryManager.toString());
    }

    @Test
    void remove() {
        inMemoryHistoryManager.add(task);
        assertEquals(List.of(task), inMemoryHistoryManager.getHistory());

        inMemoryHistoryManager.remove(task.getId());
        assertEquals(List.of(), inMemoryHistoryManager.getHistory());
        assertEquals("", inMemoryHistoryManager.toString());
    }
}