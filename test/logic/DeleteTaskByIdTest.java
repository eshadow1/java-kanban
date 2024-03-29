package logic;

import core.Manager;
import core.task.GeneratorIdTask;
import core.task.TaskManager;
import core.task.TaskManagerException;
import model.task.Epic;
import model.task.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteTaskByIdTest {
    @BeforeEach
    public void beforeEach() {
        GeneratorIdTask.setStartPosition(GeneratorIdTask.START_GENERATOR);
    }

    @Test
    public void removeTasksById() {
        boolean isSuccessTest = true;
        System.out.println("Второй тест: удаление по ID");
        Epic testEpic1 = new Epic("epic1", "test1");
        TaskManager taskManager = Manager.getInMemoryTaskManager();
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
            if (successTestEpic1 != null) isSuccessTest = false;
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);
        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
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

        Epic failTestEpicInt = taskManager.removeEpic(1000);
        if (failTestEpicInt != null) isSuccessTest = false;
        assertTrue(isSuccessTest);
        Epic successTestEpic1 = taskManager.removeEpic(testEpic1.getId());
        if (successTestEpic1 == null) isSuccessTest = false;
        assertTrue(isSuccessTest);
        Subtask failTestSubtask1 = taskManager.removeSubtask(testSubtask1.getId());
        if (failTestSubtask1 != null) isSuccessTest = false;
        assertTrue(isSuccessTest);
        Subtask failTestSubtask2 = taskManager.removeSubtask(testSubtask2.getId());
        if (failTestSubtask2 != null) isSuccessTest = false;

        assertTrue(isSuccessTest);
    }
}
