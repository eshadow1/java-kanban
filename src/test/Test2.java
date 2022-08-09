package test;

import controller.Manager;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.Epic;
import model.Subtask;

public class Test2 {
    public static boolean removeTasksById() {
        boolean isSuccessTest = true;
        System.out.println("Второй тест: удаление по ID");
        Epic testEpic1 = new Epic("epic1", "test1");
        TaskManager taskManager = Manager.getDefault();
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
            if (successTestEpic1 != null)  isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
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

        Epic failTestEpicInt = taskManager.removeEpic(1000);
        if(failTestEpicInt != null)  isSuccessTest = false;
        Epic successTestEpic1 = taskManager.removeEpic(testEpic1.getId());
        if(successTestEpic1 == null)  isSuccessTest = false;
        Subtask failTestSubtask1 = taskManager.removeSubtask(testSubtask1.getId());
        if(failTestSubtask1 != null)  isSuccessTest = false;
        Subtask failTestSubtask2 = taskManager.removeSubtask(testSubtask2.getId());
        if(failTestSubtask2 != null)  isSuccessTest = false;

        return isSuccessTest;
    }
}
