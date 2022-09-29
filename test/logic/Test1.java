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

public class Test1 {
    @BeforeEach
    public void beforeEach() {
        GeneratorIdTask.setStartPosition(GeneratorIdTask.START_GENERATOR);
    }

    @Test
    public void createOutputAndRemoveTasks() {
        boolean isSuccessTest = true;
        System.out.println("Первый тест: создание, вывод элементов и удаление всех элементов");
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
        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        try {
            Subtask failTestSubtask1 = taskManager.create(testSubtask1);
            if (!failTestSubtask1.equals(testSubtask1)) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
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
        try {
            Epic successTestEpic2 = taskManager.create(testEpic2);
            if (successTestEpic2 != null) isSuccessTest = false;
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
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
        try {
            Subtask successTestSubtask3 = taskManager.create(testSubtask3);
            if (successTestSubtask3 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        if (taskManager.getAllEpics().isEmpty()) isSuccessTest = false;
        assertTrue(isSuccessTest);
        System.out.println(taskManager.getAllEpics());
        if (taskManager.getAllSubtasks().isEmpty()) isSuccessTest = false;
        assertTrue(isSuccessTest);
        System.out.println(taskManager.getAllSubtasks());
        if (taskManager.getAllTasks().isEmpty()) isSuccessTest = false;
        assertTrue(isSuccessTest);
        System.out.println(taskManager.getAllTasks());
        if (taskManager.getSubtasksForEpic(testEpic1.getId()).isEmpty()) isSuccessTest = false;
        assertTrue(isSuccessTest);
        System.out.println(taskManager.getSubtasksForEpic(testEpic1.getId()));

        taskManager.clearEpics();
        if (!taskManager.getAllEpics().isEmpty()) isSuccessTest = false;
        assertTrue(isSuccessTest);
        taskManager.clearTasks();
        if (!taskManager.getAllTasks().isEmpty()) isSuccessTest = false;
        assertTrue(isSuccessTest);
        taskManager.clearSubtasks();
        if (!taskManager.getAllSubtasks().isEmpty()) isSuccessTest = false;
        assertTrue(isSuccessTest);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllEpics());

        assertTrue(isSuccessTest);
    }
}
