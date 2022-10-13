package logic;

import core.Manager;
import core.task.GeneratorIdTask;
import core.task.TaskManager;
import core.task.TaskManagerException;
import model.task.Epic;
import model.task.Subtask;
import model.task.Task;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetHistoryWithDeleteTasksTest {
    @BeforeEach
    public void beforeEach() {
        GeneratorIdTask.setStartPosition(GeneratorIdTask.START_GENERATOR);
    }

    @Test
    public void getHistoryWithRemoveTask() {
        boolean isSuccessTest = true;
        System.out.println("Седьмой тест: просмотр истории запросов задач с удалением задач");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        TaskManager taskManager = Manager.getInMemoryTaskManager();
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic2 = taskManager.create(testEpic2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic1.getId());
        try {
            Subtask successTestSubtask1 = taskManager.create(testSubtask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask3 = taskManager.create(testSubtask3);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");

        try {
            Task successTestTask1 = taskManager.create(testTask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestTask2 = taskManager.create(testTask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Empty history");
        List<Task> history = taskManager.getHistory();
        System.out.println(history);
        if (history.size() != 0) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        System.out.println(taskManager.getTask(testTask1.getId()));
        history = taskManager.getHistory();
        System.out.println(history);
        if (history.size() != 1) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getTask(testTask2.getId()));
        history = taskManager.getHistory();
        if (history.size() != 2) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getTask(testTask1.getId()));
        history = taskManager.getHistory();
        if (history.size() != 2) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask2.getId()));
        history = taskManager.getHistory();
        if (history.size() != 3) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getEpic(testEpic1.getId()));
        history = taskManager.getHistory();
        if (history.size() != 4) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getTask(testTask1.getId()));
        history = taskManager.getHistory();
        if (history.size() != 4) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println("Remove task " + testTask2.getId());
        Task testTaskRemove = taskManager.removeTask(testTask2.getId());
        history = taskManager.getHistory();
        if (history.size() != 3) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println("Remove epic " + testEpic1.getId());
        taskManager.removeEpic(testEpic1.getId());
        history = taskManager.getHistory();
        if (history.size() != 1) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        assertTrue(isSuccessTest);
    }
}
