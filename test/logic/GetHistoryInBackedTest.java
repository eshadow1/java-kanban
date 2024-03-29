package logic;

import core.Manager;
import core.task.GeneratorIdTask;
import core.task.TaskManager;
import core.task.TaskManagerException;
import model.task.Epic;
import model.task.Subtask;
import model.task.Task;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetHistoryInBackedTest {
    private static final Path RESOURCES_PATH = Paths.get(System.getProperty("user.dir"), "resources");
    private static final Path DEFAULT_SAVE_FILE = Paths.get(String.valueOf(RESOURCES_PATH), "default_save.csv");

    @BeforeEach
    public void beforeEach() {
        GeneratorIdTask.setStartPosition(GeneratorIdTask.START_GENERATOR);
        DEFAULT_SAVE_FILE.toFile().delete();
    }

    @Test
    public void getHistoryWithSave() {
        boolean isSuccessTest = true;
        System.out.println("Восьмой тест: просмотр истории из записи");
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

        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic1.getId());
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

        TaskManager taskManager2 = Manager.getFileBacked();
        List<Task> history = taskManager.getHistory();
        List<Task> history2 = taskManager2.getHistory();
        System.out.println("Первоначальная: " + history);
        System.out.println("После загрузки: " + history2);
        if (history.size() != history2.size()) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);

        if (!taskManager2.getTask(testTask1.getId()).equals(taskManager.getTask(testTask1.getId()))) {
            isSuccessTest = false;
        }
        assertTrue(isSuccessTest);
    }

}
