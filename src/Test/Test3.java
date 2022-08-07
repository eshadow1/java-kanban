package Test;

import controller.Manager;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Test3 {
    public static void changeStatusTasks() {
        System.out.println("Третий тест: изменение статуса");
        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Epic testEpic1 = new Epic("epic1", "test1");
        TaskManager taskManager = Manager.getDefault();
        try {
            Task successTestTask1 = taskManager.create(testTask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        Subtask testUpdateSubtask2 = new Subtask("subtask2", "test2",
                testSubtask2.getIdParentEpic(), Status.IN_PROGRESS);
        testUpdateSubtask2.setId(testSubtask2.getId());
        Task testUpdateTask2 = new Task("task2", "test2", Status.IN_PROGRESS);
        testUpdateTask2.setId(testTask2.getId());
        Task testUpdateTask1 = new Task("task1", "test1", Status.DONE);
        testUpdateTask1.setId(testTask1.getId());
        try {
            Subtask successTestSubtask2 = taskManager.update(testUpdateSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task failTestUpdateTask2 = taskManager.update(testUpdateTask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestUpdateTask1 = taskManager.update(testUpdateTask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllTasks());
    }
}
