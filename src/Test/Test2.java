package Test;

import controller.Manager;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.Epic;
import model.Subtask;

public class Test2 {
    public static void removeTasksById() {
        System.out.println("Второй тест: удаление по ID");
        Epic testEpic1 = new Epic("epic1", "test1");
        TaskManager taskManager = Manager.getDefault();
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
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
            Epic failTestEpicInt = taskManager.removeEpic(1000);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic failTestEpic1 = taskManager.removeEpic(testEpic1.getId());
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        Subtask successTestSubtask1 = taskManager.removeSubtask(testSubtask1.getId());
        Subtask successTestSubtask2 = taskManager.removeSubtask(testSubtask2.getId());
        try {
            Epic successTestEpic1 = taskManager.removeEpic(testEpic1.getId());
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        Subtask failTestSubtask1 = taskManager.removeSubtask(testSubtask1.getId());
    }
}
