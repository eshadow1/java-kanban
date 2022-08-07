package test;

import controller.Manager;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.Epic;
import model.Subtask;
import model.Task;

public class Test4 {
    public static void getTaskByID() {
        System.out.println("Четвертый тест: получение по ID");
        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        TaskManager taskManager = Manager.getDefault();
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
            Task successTestEpic1 = taskManager.create(testEpic1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestEpic2 = taskManager.create(testEpic2);
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

        System.out.println(taskManager.getTask(testTask1.getId()));
        System.out.println(taskManager.getEpic(testEpic1.getId()));
        System.out.println(taskManager.getSubtask(testSubtask1.getId()));
        System.out.println(taskManager.getSubtask(testSubtask3.getId()));
    }
}
