package core.task;

import model.task.Epic;
import model.task.Status;
import model.task.Subtask;
import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager taskManager;
    private Task task;
    private Subtask subtask;
    private Epic epic;
    private Task taskWithDateTime1;
    private Task taskWithDateTime2;
    private Task taskWithDateTime3;
    private Subtask subtaskWithDateTime1;
    private Subtask subtaskWithDateTime2;
    private Subtask subtaskWithDateTime3;

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
        GeneratorIdTask.setStartPosition(GeneratorIdTask.START_GENERATOR);

        initAllTasks();
    }

    @Test
    void clearTasks() {
        taskManager.create(task);
        assertEquals(List.of(task), taskManager.getAllTasks());
        taskManager.clearTasks();
        assertEquals(List.of(), taskManager.getAllTasks());
    }

    @Test
    void clearSubtasks() {
        taskManager.create(epic);
        taskManager.create(subtask);
        assertEquals(List.of(subtask), taskManager.getAllSubtasks());
        taskManager.clearSubtasks();
        assertEquals(List.of(), taskManager.getAllSubtasks());
    }

    @Test
    void clearEpics() {
        taskManager.create(epic);
        assertEquals(List.of(epic), taskManager.getAllEpics());
        taskManager.clearEpics();
        assertEquals(List.of(), taskManager.getAllEpics());
    }

    @Test
    void createAndGetTask() {
        taskManager.create(task);
        int notAddId = -1;
        assertNotNull(taskManager.getTask(task.getId()));
        assertEquals(task, taskManager.getTask(task.getId()));
        assertNull(taskManager.getTask(notAddId));
    }

    @Test
    void notCreateAndGetTask() {
        Task noCreateTask = new Task("task1", "test1", Status.IN_PROGRESS);
        noCreateTask.setId(GeneratorIdTask.getId());

        final TaskManagerException exception = assertThrows(
                TaskManagerException.class,
                () -> taskManager.create(noCreateTask));

        assertEquals("Not new task", exception.getMessage());
    }

    @Test
    void notCreateAndGetTaskTime() {
        taskManager.create(taskWithDateTime1);
        assertNotNull(taskManager.getTask(taskWithDateTime1.getId()));
        assertEquals(taskWithDateTime1, taskManager.getTask(taskWithDateTime1.getId()));
        taskManager.create(taskWithDateTime2);

        final TaskManagerException exceptionDateTime = assertThrows(
                TaskManagerException.class,
                () -> taskManager.create(taskWithDateTime3));

        assertEquals("Incorrect time in task", exceptionDateTime.getMessage());
    }

    @Test
    void createAndGetSubtask() {
        taskManager.create(epic);
        taskManager.create(subtask);
        int notAddId = -1;
        assertNotNull(taskManager.getSubtask(subtask.getId()));
        assertEquals(subtask, taskManager.getSubtask(subtask.getId()));
        assertNull(taskManager.getSubtask(notAddId));
    }

    @Test
    void notCreateAndGetSubtask() {
        taskManager.create(epic);
        Subtask noCreateSubtask = new Subtask("subtask1", "test1", Status.IN_PROGRESS, 0);
        noCreateSubtask.setId(GeneratorIdTask.getId());

        final TaskManagerException exception = assertThrows(
                TaskManagerException.class,
                () -> taskManager.create(noCreateSubtask));

        assertEquals("Not new subtask", exception.getMessage());
    }

    @Test
    void notCreateAndGetSubtaskTime() {
        taskManager.create(epic);
        taskManager.create(subtaskWithDateTime1);
        assertNotNull(taskManager.getSubtask(subtaskWithDateTime1.getId()));
        assertEquals(subtaskWithDateTime1, taskManager.getSubtask(subtaskWithDateTime1.getId()));
        taskManager.create(subtaskWithDateTime3);

        final TaskManagerException exceptionDateTime = assertThrows(
                TaskManagerException.class,
                () -> taskManager.create(subtaskWithDateTime2));

        assertEquals("Incorrect time in subtask", exceptionDateTime.getMessage());
    }

    @Test
    void createAndGetEpic() {
        taskManager.create(epic);
        int notAddId = -1;
        assertNotNull(taskManager.getEpic(epic.getId()));
        assertEquals(epic, taskManager.getEpic(epic.getId()));
        assertNull(taskManager.getEpic(notAddId));

        Epic noCreateEpic = new Epic("epic1", "test1");
        noCreateEpic.setId(GeneratorIdTask.getId());

        final TaskManagerException exception = assertThrows(
                TaskManagerException.class,
                () -> taskManager.create(noCreateEpic));

        assertEquals("Not new epic", exception.getMessage());
    }

    @Test
    void getAllTasks() {
        assertEquals(List.of(), taskManager.getAllTasks());
        taskManager.create(task);
        assertEquals(List.of(task), taskManager.getAllTasks());
    }

    @Test
    void getAllSubtasks() {
        taskManager.create(epic);
        assertEquals(List.of(), taskManager.getAllSubtasks());
        taskManager.create(subtask);
        assertEquals(List.of(subtask), taskManager.getAllSubtasks());
    }

    @Test
    void getAllEpics() {
        assertEquals(List.of(), taskManager.getAllEpics());
        taskManager.create(epic);
        assertEquals(List.of(epic), taskManager.getAllEpics());
    }

    @Test
    void updateTask() {
        taskManager.create(task);
        assertNotNull(taskManager.getTask(task.getId()));

        Task updateTask = new Task("task1", "test1", Status.IN_PROGRESS);
        updateTask.setId(task.getId());
        taskManager.update(updateTask);
        assertEquals(updateTask, taskManager.getTask(task.getId()));

        taskManager.create(taskWithDateTime1);
        taskManager.create(taskWithDateTime2);
        assertNotNull(taskManager.getTask(taskWithDateTime1.getId()));

        Task updateTaskWithDateTime1 = new Task("task1", "test1", Status.IN_PROGRESS, taskWithDateTime1.getStartPeriod(),
                (int) Duration.between(taskWithDateTime1.getStartPeriod(), taskWithDateTime1.getEndTime()).toMinutes());
        updateTaskWithDateTime1.setId(taskWithDateTime1.getId());

        Task notUpdateTaskWithDateTime1 = new Task("task1", "test1", Status.IN_PROGRESS, taskWithDateTime2.getStartPeriod(),
                (int) Duration.between(taskWithDateTime1.getStartPeriod(), taskWithDateTime1.getEndTime()).toMinutes());
        notUpdateTaskWithDateTime1.setId(taskWithDateTime1.getId());

        taskManager.update(updateTaskWithDateTime1);
        taskManager.update(notUpdateTaskWithDateTime1);
        assertEquals(updateTaskWithDateTime1, taskManager.getTask(taskWithDateTime1.getId()));
    }

    @Test
    void notUpdateTask() {
        taskManager.create(taskWithDateTime1);
        taskManager.create(taskWithDateTime2);
        assertNotNull(taskManager.getTask(taskWithDateTime1.getId()));

        Task updateTaskWithDateTime1 = new Task("task1", "test1", Status.IN_PROGRESS, taskWithDateTime1.getStartPeriod(),
                (int) Duration.between(taskWithDateTime1.getStartPeriod(), taskWithDateTime1.getEndTime()).toMinutes());
        updateTaskWithDateTime1.setId(taskWithDateTime1.getId());

        Task notUpdateTaskWithDateTime1 = new Task("task1", "test1", Status.IN_PROGRESS, taskWithDateTime2.getStartPeriod(),
                (int) Duration.between(taskWithDateTime1.getStartPeriod(), taskWithDateTime1.getEndTime()).toMinutes());
        notUpdateTaskWithDateTime1.setId(taskWithDateTime1.getId());

        taskManager.update(updateTaskWithDateTime1);
        taskManager.update(notUpdateTaskWithDateTime1);
        assertEquals(updateTaskWithDateTime1, taskManager.getTask(taskWithDateTime1.getId()));
    }

    @Test
    void updateSubtask() {
        taskManager.create(epic);
        taskManager.create(subtask);

        Subtask updateSubtaskInProgress = new Subtask("subtask1", "test1", Status.IN_PROGRESS, 0);
        updateSubtaskInProgress.setId(subtask.getId());
        taskManager.update(updateSubtaskInProgress);
        assertEquals(updateSubtaskInProgress, taskManager.getSubtask(subtask.getId()));
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(epic.getId()).getStatus());

        Subtask updateSubtaskDone = new Subtask("subtask1", "test1", Status.DONE, 0);
        updateSubtaskDone.setId(subtask.getId());
        taskManager.update(updateSubtaskDone);
        assertEquals(updateSubtaskDone, taskManager.getSubtask(subtask.getId()));
        assertEquals(Status.DONE, taskManager.getEpic(epic.getId()).getStatus());

        Subtask newSubtask = new Subtask("subtask2", "test2", 0);
        taskManager.create(newSubtask);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(epic.getId()).getStatus());
    }

    @Test
    void notUpdateSubtask() {
        taskManager.create(epic);
        taskManager.create(subtaskWithDateTime1);
        taskManager.create(subtaskWithDateTime3);
        assertNotNull(taskManager.getSubtask(subtaskWithDateTime1.getId()));

        Subtask updateSubtaskWithDateTime1 = new Subtask("task1", "test1", Status.IN_PROGRESS,
                subtaskWithDateTime1.getStartPeriod(),
                (int) Duration.between(subtaskWithDateTime1.getStartPeriod(), subtaskWithDateTime1.getEndTime()).toMinutes(),
                subtaskWithDateTime1.getIdParentEpic());
        updateSubtaskWithDateTime1.setId(subtaskWithDateTime1.getId());

        Subtask notUpdateSubtaskWithDateTime1 = new Subtask("task1", "test1", Status.IN_PROGRESS,
                subtaskWithDateTime3.getStartPeriod(),
                (int) Duration.between(subtaskWithDateTime1.getStartPeriod(), subtaskWithDateTime1.getEndTime()).toMinutes(),
                subtaskWithDateTime1.getIdParentEpic());
        notUpdateSubtaskWithDateTime1.setId(subtaskWithDateTime1.getId());

        taskManager.update(updateSubtaskWithDateTime1);
        taskManager.update(notUpdateSubtaskWithDateTime1);
        assertEquals(updateSubtaskWithDateTime1, taskManager.getSubtask(subtaskWithDateTime1.getId()));
    }

    @Test
    void updateEpic() {
        epic = new Epic("epic1", "test1");
        taskManager.create(epic);

        assertNotNull(taskManager.getEpic(epic.getId()));
        assertEquals(epic, taskManager.getEpic(epic.getId()));

        Epic updateEpic = new Epic("epic1", "test2");
        updateEpic.setId(epic.getId());
        taskManager.update(updateEpic);
        assertEquals(updateEpic, taskManager.getEpic(epic.getId()));
    }

    @Test
    void removeTask() {
        taskManager.create(task);
        assertEquals(List.of(task), taskManager.getAllTasks());
        taskManager.removeTask(task.getId());
        assertEquals(List.of(), taskManager.getAllTasks());
    }

    @Test
    void removeSubtask() {
        taskManager.create(epic);
        taskManager.create(subtask);
        assertEquals(List.of(subtask), taskManager.getAllSubtasks());
        taskManager.removeSubtask(subtask.getId());
        assertEquals(List.of(), taskManager.getAllSubtasks());
        assertEquals(List.of(epic), taskManager.getAllEpics());
    }

    @Test
    void removeEpic() {
        taskManager.create(epic);
        taskManager.create(subtask);
        assertEquals(List.of(epic), taskManager.getAllEpics());
        taskManager.removeEpic(epic.getId());
        assertEquals(List.of(), taskManager.getAllSubtasks());
        assertEquals(List.of(), taskManager.getAllEpics());
    }

    @Test
    void getSubtasksForEpic() {
        taskManager.create(epic);
        taskManager.create(subtask);
        assertNotNull(taskManager.getSubtasksForEpic(epic.getId()));
        assertEquals(List.of(subtask), taskManager.getSubtasksForEpic(epic.getId()));

        int notAddId = -1;
        assertNull(taskManager.getSubtasksForEpic(notAddId));
    }

    @Test
    void getHistory() {
        assertEquals(List.of(), taskManager.getHistory());
        taskManager.create(epic);
        taskManager.create(task);
        assertEquals(List.of(), taskManager.getHistory());

        taskManager.getTask(task.getId());
        taskManager.getEpic(epic.getId());
        assertEquals(List.of(task, epic), taskManager.getHistory());
        taskManager.getTask(task.getId());
        assertEquals(List.of(epic, task), taskManager.getHistory());
        taskManager.removeTask(task.getId());
        assertEquals(List.of(epic), taskManager.getHistory());
        taskManager.removeEpic(epic.getId());
        assertEquals(List.of(), taskManager.getHistory());
    }

    protected void initAllTasks() {
        LocalDateTime localDateTimeTask1 = LocalDateTime.of(2022, 1, 1, 1, 0, 0, 0);
        LocalDateTime localDateTimeTask2 = LocalDateTime.of(2022, 1, 1, 1, 30, 0, 0);
        LocalDateTime localDateTimeTask3 = LocalDateTime.of(2022, 1, 1, 1, 29, 0, 0);
        LocalDateTime localDateTimeSubtask1 = LocalDateTime.of(2022, 2, 1, 12, 20, 0, 0);
        LocalDateTime localDateTimeSubtask2 = LocalDateTime.of(2022, 2, 1, 11, 0, 0, 0);
        LocalDateTime localDateTimeSubtask3 = LocalDateTime.of(2022, 3, 1, 14, 0, 0, 0);

        task = new Task("task1", "test1");
        taskWithDateTime1 = new Task("taskWithDateTime1", "test1", localDateTimeTask1, 30);
        taskWithDateTime2 = new Task("taskWithDateTime2", "test2", localDateTimeTask2, 40);
        taskWithDateTime3 = new Task("taskWithDateTime3", "test3", localDateTimeTask3, 40);

        subtask = new Subtask("subtask1", "test1", 0);
        subtaskWithDateTime1 = new Subtask("subtaskWithDateTime1", "subtask1",
                localDateTimeSubtask1, 40, 0);
        subtaskWithDateTime2 = new Subtask("subtaskWithDateTime2", "subtask2",
                localDateTimeSubtask2, 90, 0);
        subtaskWithDateTime3 = new Subtask("subtaskWithDateTime3", "subtask3",
                localDateTimeSubtask3, 40, 0);

        epic = new Epic("epic1", "test1");
    }
}