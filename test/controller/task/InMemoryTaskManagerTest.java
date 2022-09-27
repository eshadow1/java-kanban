package controller.task;

import model.task.Epic;
import model.task.Status;
import model.task.Subtask;
import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager inMemoryTaskManager;
    Task task;
    Subtask subtask;
    Epic epic;

    @BeforeEach
    public void beforeEach() {
        inMemoryTaskManager = new InMemoryTaskManager();
        int startGenerator = 0;
        GeneratorIdTask.setStartPosition(startGenerator);
        task = new Task("task1", "test1");
        subtask = new Subtask("subtask1", "test1", 0);
        epic = new Epic("epic1", "test1");
    }

    @Test
    void clearTasks() {
        inMemoryTaskManager.create(task);
        assertEquals(List.of(task), inMemoryTaskManager.getAllTasks());
        inMemoryTaskManager.clearTasks();
        assertEquals(List.of(), inMemoryTaskManager.getAllTasks());
    }

    @Test
    void clearSubtasks() {
        inMemoryTaskManager.create(epic);
        inMemoryTaskManager.create(subtask);
        assertEquals(List.of(subtask), inMemoryTaskManager.getAllSubtasks());
        inMemoryTaskManager.clearSubtasks();
        assertEquals(List.of(), inMemoryTaskManager.getAllSubtasks());
    }

    @Test
    void clearEpics() {
        inMemoryTaskManager.create(epic);
        assertEquals(List.of(epic), inMemoryTaskManager.getAllEpics());
        inMemoryTaskManager.clearEpics();
        assertEquals(List.of(), inMemoryTaskManager.getAllEpics());
    }

    @Test
    void createAndGetTask() {
        inMemoryTaskManager.create(task);
        int notAddId = -1;
        assertNotNull(inMemoryTaskManager.getTask(task.getId()));
        assertEquals(task, inMemoryTaskManager.getTask(task.getId()));
        assertNull(inMemoryTaskManager.getTask(notAddId));

        Task noCreateTask = new Task("task1", "test1", Status.IN_PROGRESS);
        noCreateTask.setId(GeneratorIdTask.getId());

        final TaskManagerException exception = assertThrows(
                TaskManagerException.class,
                () -> inMemoryTaskManager.create(noCreateTask));

        assertEquals("Not new task", exception.getMessage());
    }

    @Test
    void createAndGetSubtask() {
        inMemoryTaskManager.create(epic);
        inMemoryTaskManager.create(subtask);
        int notAddId = -1;
        assertNotNull(inMemoryTaskManager.getSubtask(subtask.getId()));
        assertEquals(subtask, inMemoryTaskManager.getSubtask(subtask.getId()));
        assertNull(inMemoryTaskManager.getSubtask(notAddId));

        Subtask noCreateSubtask = new Subtask("subtask1", "test1", Status.IN_PROGRESS, 0);
        noCreateSubtask.setId(GeneratorIdTask.getId());

        final TaskManagerException exception = assertThrows(
                TaskManagerException.class,
                () -> inMemoryTaskManager.create(noCreateSubtask));

        assertEquals("Not new subtask", exception.getMessage());
    }

    @Test
    void createAndGetEpic() {
        inMemoryTaskManager.create(epic);
        int notAddId = -1;
        assertNotNull(inMemoryTaskManager.getEpic(epic.getId()));
        assertEquals(epic, inMemoryTaskManager.getEpic(epic.getId()));
        assertNull(inMemoryTaskManager.getEpic(notAddId));

        Epic noCreateEpic = new Epic("epic1", "test1");
        noCreateEpic.setId(GeneratorIdTask.getId());

        final TaskManagerException exception = assertThrows(
                TaskManagerException.class,
                () -> inMemoryTaskManager.create(noCreateEpic));

        assertEquals("Not new epic", exception.getMessage());
    }

    @Test
    void getAllTasks() {
        assertEquals(List.of(), inMemoryTaskManager.getAllTasks());
        inMemoryTaskManager.create(task);
        assertEquals(List.of(task), inMemoryTaskManager.getAllTasks());
    }

    @Test
    void getAllSubtasks() {
        inMemoryTaskManager.create(epic);
        assertEquals(List.of(), inMemoryTaskManager.getAllSubtasks());
        inMemoryTaskManager.create(subtask);
        assertEquals(List.of(subtask), inMemoryTaskManager.getAllSubtasks());
    }

    @Test
    void getAllEpics() {
        assertEquals(List.of(), inMemoryTaskManager.getAllEpics());
        inMemoryTaskManager.create(epic);
        assertEquals(List.of(epic), inMemoryTaskManager.getAllEpics());
    }


    @Test
    void updateTask() {
        inMemoryTaskManager.create(task);
        assertNotNull(inMemoryTaskManager.getTask(task.getId()));

        Task updateTask = new Task("task1", "test1", Status.IN_PROGRESS);
        updateTask.setId(task.getId());
        inMemoryTaskManager.update(updateTask);
        assertEquals(updateTask, inMemoryTaskManager.getTask(task.getId()));
    }

    @Test
    void updateSubtask() {
        inMemoryTaskManager.create(epic);
        inMemoryTaskManager.create(subtask);

        Subtask updateSubtaskInProgress = new Subtask("subtask1", "test1", Status.IN_PROGRESS, 0);
        updateSubtaskInProgress.setId(subtask.getId());
        inMemoryTaskManager.update(updateSubtaskInProgress);
        assertEquals(updateSubtaskInProgress, inMemoryTaskManager.getSubtask(subtask.getId()));
        assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.getEpic(epic.getId()).getStatus());

        Subtask updateSubtaskDone = new Subtask("subtask1", "test1", Status.DONE, 0);
        updateSubtaskDone.setId(subtask.getId());
        inMemoryTaskManager.update(updateSubtaskDone);
        assertEquals(updateSubtaskDone, inMemoryTaskManager.getSubtask(subtask.getId()));
        assertEquals(Status.DONE, inMemoryTaskManager.getEpic(epic.getId()).getStatus());

        Subtask newSubtask = new Subtask("subtask2", "test2", 0);
        inMemoryTaskManager.create(newSubtask);
        assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.getEpic(epic.getId()).getStatus());
    }

    @Test
    void updateEpic() {
        epic = new Epic("epic1", "test1");
        inMemoryTaskManager.create(epic);

        assertNotNull(inMemoryTaskManager.getEpic(epic.getId()));
        assertEquals(epic, inMemoryTaskManager.getEpic(epic.getId()));

        Epic updateEpic = new Epic("epic1", "test2");
        updateEpic.setId(epic.getId());
        inMemoryTaskManager.update(updateEpic);
        assertEquals(updateEpic, inMemoryTaskManager.getEpic(epic.getId()));
    }

    @Test
    void removeTask() {
        inMemoryTaskManager.create(task);
        assertEquals(List.of(task), inMemoryTaskManager.getAllTasks());
        inMemoryTaskManager.removeTask(task.getId());
        assertEquals(List.of(), inMemoryTaskManager.getAllTasks());
    }

    @Test
    void removeSubtask() {
        inMemoryTaskManager.create(epic);
        inMemoryTaskManager.create(subtask);
        assertEquals(List.of(subtask), inMemoryTaskManager.getAllSubtasks());
        inMemoryTaskManager.removeSubtask(subtask.getId());
        assertEquals(List.of(), inMemoryTaskManager.getAllSubtasks());
        assertEquals(List.of(epic), inMemoryTaskManager.getAllEpics());
    }

    @Test
    void removeEpic() {
        inMemoryTaskManager.create(epic);
        inMemoryTaskManager.create(subtask);
        assertEquals(List.of(epic), inMemoryTaskManager.getAllEpics());
        inMemoryTaskManager.removeEpic(epic.getId());
        assertEquals(List.of(), inMemoryTaskManager.getAllSubtasks());
        assertEquals(List.of(), inMemoryTaskManager.getAllEpics());
    }

    @Test
    void getSubtasksForEpic() {
        inMemoryTaskManager.create(epic);
        inMemoryTaskManager.create(subtask);
        assertNotNull(inMemoryTaskManager.getSubtasksForEpic(epic.getId()));
        assertEquals(List.of(subtask), inMemoryTaskManager.getSubtasksForEpic(epic.getId()));

        int notAddId = -1;
        assertNull(inMemoryTaskManager.getSubtasksForEpic(notAddId));
    }

    @Test
    void getHistory() {
        assertEquals(List.of(), inMemoryTaskManager.getHistory());
        inMemoryTaskManager.create(epic);
        inMemoryTaskManager.create(task);
        assertEquals(List.of(), inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getTask(task.getId());
        inMemoryTaskManager.getEpic(epic.getId());
        assertEquals(List.of(task, epic), inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getTask(task.getId());
        assertEquals(List.of(epic, task), inMemoryTaskManager.getHistory());
        inMemoryTaskManager.removeTask(task.getId());
        assertEquals(List.of(epic), inMemoryTaskManager.getHistory());
        inMemoryTaskManager.removeEpic(epic.getId());
        assertEquals(List.of(), inMemoryTaskManager.getHistory());
    }
}