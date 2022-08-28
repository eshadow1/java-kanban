package test;

import controller.Manager;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.task.Epic;
import model.task.Status;
import model.task.Subtask;

import java.util.List;

public class Test5 {
    public static boolean changeStatusAndUpdateSubtasks() {
        boolean isSuccessTest = true;
        System.out.println("Пятый тест: изменение статуса и обновление подзадачи");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        TaskManager taskManager = Manager.getDefault();
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
            if (successTestEpic1 != null)  isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        try {
            Epic successTestEpic2 = taskManager.create(testEpic2);
            if (successTestEpic2 != null)  isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }

        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
        try {
            Subtask successTestSubtask1 = taskManager.create(testSubtask1);
            if (successTestSubtask1 != null)  isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
            if (successTestSubtask2 != null)  isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        try {
            Subtask successTestSubtask3 = taskManager.create(testSubtask3);
            if (successTestSubtask3 != null)  isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
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
            isSuccessTest = false;
        }
        try {
            taskManager.update(testUpdateSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }

        List<Epic> allEpics = taskManager.getAllEpics();
        System.out.println(allEpics);
        if(allEpics.isEmpty()) isSuccessTest = false;
        if(allEpics.get(0).getStatus() != Status.IN_PROGRESS) isSuccessTest = false;
        if(allEpics.get(1).getStatus() != Status.DONE) isSuccessTest = false;

        List<Subtask> allSubtasks = taskManager.getAllSubtasks();
        System.out.println(allSubtasks);
        if(allSubtasks.isEmpty()) isSuccessTest = false;
        if(allSubtasks.get(0).getStatus() != Status.NEW) isSuccessTest = false;
        if(allSubtasks.get(1).getStatus() != Status.IN_PROGRESS) isSuccessTest = false;
        if(allSubtasks.get(2).getStatus() != Status.DONE) isSuccessTest = false;

        System.out.println(taskManager.getSubtasksForEpic(testEpic1.getId()));
        System.out.println(taskManager.getSubtasksForEpic(testEpic2.getId()));
        return isSuccessTest;
    }
}
