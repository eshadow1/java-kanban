package test;

import controller.Manager;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.Epic;
import model.Status;
import model.Subtask;

public class Test5 {
    public static void changeStatusAndUpdateSubtasks() {
        System.out.println("Пятый тест: изменение статуса и обновление подзадачи");
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

        Subtask testUpdateSubtask3 = new Subtask("subtask31", "test1",
                testSubtask3.getIdParentEpic(), Status.DONE);
        testUpdateSubtask3.setId(testSubtask3.getId());
        Subtask testUpdateSubtask2 = new Subtask("subtask32", "test1",
                testSubtask2.getIdParentEpic(), Status.IN_PROGRESS);
        testUpdateSubtask2.setId(testSubtask2.getId());
        try {
            taskManager.update(testUpdateSubtask3);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            taskManager.update(testUpdateSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getSubtasksForEpic(testEpic1.getId()));
        System.out.println(taskManager.getSubtasksForEpic(testEpic2.getId()));
    }
}
