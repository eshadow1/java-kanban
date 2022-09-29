package logic;

import controller.Manager;
import controller.task.GeneratorIdTask;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.task.Epic;
import model.task.Status;
import model.task.Subtask;
import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test3 {
    @BeforeEach
    public void beforeEach() {
        GeneratorIdTask.setStartPosition(GeneratorIdTask.START_GENERATOR);
    }

    @Test
    public void changeStatusTasks() {
        boolean isSuccessTest = true;
        System.out.println("Третий тест: изменение статуса");
        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Epic testEpic1 = new Epic("epic1", "test1");
        TaskManager taskManager = Manager.getDefault();
        try {
            Task successTestTask1 = taskManager.create(testTask1);
            if (successTestTask1 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
            if (successTestEpic1 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
            if (successTestSubtask2 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        Subtask testUpdateSubtask2 = new Subtask("subtask2", "test2",
                Status.IN_PROGRESS, testSubtask2.getIdParentEpic());
        testUpdateSubtask2.setId(testSubtask2.getId());
        Task testUpdateTask2 = new Task("task2", "test2", Status.IN_PROGRESS);
        testUpdateTask2.setId(testTask2.getId());
        Task testUpdateTask1 = new Task("task1", "test1", Status.DONE);
        testUpdateTask1.setId(testTask1.getId());
        try {
            Subtask successTestSubtask2 = taskManager.update(testUpdateSubtask2);
            if (successTestSubtask2 != null && successTestSubtask2.equals(testUpdateSubtask2)) {
                isSuccessTest = false;
            }
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);
        try {
            Task failTestUpdateTask2 = taskManager.update(testUpdateTask2);
            isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(isSuccessTest);
        try {
            Task successTestUpdateTask1 = taskManager.update(testUpdateTask1);
            if (successTestUpdateTask1 != null && successTestUpdateTask1.equals(testUpdateTask1)) {
                isSuccessTest = false;
            }
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllTasks());
        assertTrue(isSuccessTest);
    }
}
