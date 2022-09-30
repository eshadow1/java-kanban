package logic;

import controller.Manager;
import controller.task.GeneratorIdTask;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.task.Epic;
import model.task.Subtask;
import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetTaskByIdTest {
    @BeforeEach
    public void beforeEach() {
        GeneratorIdTask.setStartPosition(GeneratorIdTask.START_GENERATOR);
    }

    @Test
    public void getTaskByID() {
        boolean isSuccessTest = true;
        System.out.println("Четвертый тест: получение по ID");
        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
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
            Task successTestTask2 = taskManager.create(testTask2);
            if (successTestTask2 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);
        try {
            Task successTestEpic1 = taskManager.create(testEpic1);
            if (successTestEpic1 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);
        try {
            Task successTestEpic2 = taskManager.create(testEpic2);
            if (successTestEpic2 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
        try {
            Subtask successTestSubtask1 = taskManager.create(testSubtask1);
            if (successTestSubtask1 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
            if (successTestSubtask2 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);
        try {
            Subtask successTestSubtask3 = taskManager.create(testSubtask3);
            if (successTestSubtask3 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        Task task = taskManager.getTask(testTask1.getId());
        if (task == null) isSuccessTest = false;
        assertTrue(isSuccessTest);
        System.out.println(task);

        Epic epic = taskManager.getEpic(testEpic1.getId());
        if (epic == null) isSuccessTest = false;
        assertTrue(isSuccessTest);
        System.out.println(epic);

        Subtask subtask1 = taskManager.getSubtask(testSubtask1.getId());
        if (subtask1 == null) isSuccessTest = false;
        assertTrue(isSuccessTest);
        System.out.println(subtask1);

        Subtask subtask3 = taskManager.getSubtask(testSubtask3.getId());
        if (subtask3 == null) isSuccessTest = false;
        assertTrue(isSuccessTest);
        System.out.println(subtask3);

        assertTrue(isSuccessTest);
    }
}
