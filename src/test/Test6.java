package test;

import controller.Manager;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.task.Epic;
import model.task.Subtask;
import model.task.Task;

import java.util.List;

public class Test6 {
    public static boolean getHistory() {
        boolean isSuccessTest = true;
        System.out.println("Шестой тест: просмотр истории запросов задач");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        TaskManager taskManager = Manager.getDefault();
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
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
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
        Task testTask3 = new Task("task3", "test3");

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
        try {
            Task successTestTask3 = taskManager.create(testTask3);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(taskManager.getTask(testTask1.getId()));
        List<Task> history = taskManager.getHistory();
        System.out.println(history);
        if (history.size() != 1) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getTask(testTask2.getId()));
        history = taskManager.getHistory();
        if (history.size() != 2) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getTask(testTask1.getId()));
        history = taskManager.getHistory();
        if (history.size() != 2) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getTask(testTask3.getId()));
        history = taskManager.getHistory();
        if (history.size() != 3) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getEpic(testEpic2.getId()));
        history = taskManager.getHistory();
        if (history.size() != 4) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getEpic(testEpic1.getId()));
        history = taskManager.getHistory();
        if (history.size() != 5) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask2.getId()));
        history = taskManager.getHistory();
        if (history.size() != 6) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask1.getId()));
        history = taskManager.getHistory();
        if (history.size() != 7) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask2.getId()));
        history = taskManager.getHistory();
        if (history.size() != 7) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask3.getId()));
        history = taskManager.getHistory();
        if (history.size() != 8) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask1.getId()));
        history = taskManager.getHistory();
        if (history.size() != 8) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask1.getId()));
        history = taskManager.getHistory();
        System.out.println(history);
        if (history.size() != 8) isSuccessTest = false;
        for (Task task : history) System.out.print(task.getId() + " ");
        System.out.println();
        return isSuccessTest;
    }
}
