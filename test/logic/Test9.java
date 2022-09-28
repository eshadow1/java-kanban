package logic;

import controller.Manager;
import controller.task.GeneratorIdTask;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.task.Epic;
import model.task.Subtask;
import model.task.Task;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test9 {
    private static final Path RESOURCES_PATH = Paths.get(System.getProperty("user.dir"), "resources");
    private static final Path DEFAULT_SAVE_FILE = Paths.get(String.valueOf(RESOURCES_PATH), "default_save.csv");

    @BeforeEach
    public void beforeEach() {
        GeneratorIdTask.setStartPosition(GeneratorIdTask.START_GENERATOR);
        DEFAULT_SAVE_FILE.toFile().delete();
    }

    @Test
    public void getPriorityWithSaveAndTime() {
        boolean isSuccessTest = true;
        System.out.println("Девятый тест: просмотр истории из записи со временем");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        TaskManager taskManager = Manager.getFileBacked();
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

        LocalDateTime localDateTimeSubtask1 =  LocalDateTime.of(2022, 1, 1, 0, 0, 0, 0);
        LocalDateTime localDateTimeSubtask2 =  LocalDateTime.of(2022, 2, 1, 0, 0, 0, 0);
        LocalDateTime localDateTimeSubtask3 =  LocalDateTime.of(2022, 3, 1, 0, 0, 0, 0);
        Subtask testSubtask1 = new Subtask("subtask1", "test1", localDateTimeSubtask1, 5, testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", localDateTimeSubtask2, 15, testEpic1.getId());
        Subtask testSubtask3 = new Subtask("subtask3", "test3", localDateTimeSubtask3, 25, testEpic1.getId());
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

        LocalDateTime localDateTimeTask1 =  LocalDateTime.of(2022, 1, 1, 1, 0, 0, 0);
        LocalDateTime localDateTimeTask2 =  LocalDateTime.of(2022, 2, 1, 3, 0, 0, 0);
        Task testTask1 = new Task("task1", "test1", localDateTimeTask1, 5);
        Task testTask2 = new Task("task2", "test2", localDateTimeTask2, 5);

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

        System.out.println(taskManager.getTask(testTask1.getId()));
        System.out.println(taskManager.getTask(testTask2.getId()));
        System.out.println(taskManager.getTask(testTask1.getId()));
        System.out.println(taskManager.getSubtask(testSubtask2.getId()));
        System.out.println(taskManager.getEpic(testEpic1.getId()));
        System.out.println(taskManager.getTask(testTask1.getId()));

        System.out.println(taskManager.getPrioritizedTasks());

        assertTrue(isSuccessTest);
    }

}
