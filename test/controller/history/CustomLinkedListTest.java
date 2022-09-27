package controller.history;

import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomLinkedListTest {
    private CustomLinkedList<Task> history;
    private Task task;

    @BeforeEach
    public void beforeEach() {
        history = new CustomLinkedList<>();
        task = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW", "test1", "null","-1"});
    }

    @Test
    void addTaskAndCheckIt() {
        history.linkLast(task.getId(), task);
        assertEquals(List.of(task), history.getTasks());
    }

    @Test
    void getEmptyHistoryTasks() {
        assertEquals(new ArrayList<Task>(), history.getTasks());
    }

    @Test
    void removeTaskInHistory() {
        history.linkLast(task.getId(), task);
        history.remove(task.getId());
        assertEquals(new ArrayList<Task>(), history.getTasks());
    }
}