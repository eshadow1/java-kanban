package controller.task;

import model.task.Epic;
import model.task.Subtask;
import model.task.Task;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoaderTaskManagerTest {
    private static final Path RESOURCES_PATH = Paths.get(System.getProperty("user.dir"), "resources-test");
    private static final Path DEFAULT_SAVE_FILE = Paths.get(String.valueOf(RESOURCES_PATH), "default_save_to_load.csv");
    private static final Path DEFAULT_SAVE_FILE_WITH_EMPTY_HISTORY = Paths.get(String.valueOf(RESOURCES_PATH),
            "default_save_to_load_with_empty_history.csv");
    private static final Path DEFAULT_SAVE_FILE_WITH_EMPTY = Paths.get(String.valueOf(RESOURCES_PATH),
            "default_save_to_load_with_empty.csv");
    private Task task1;
    private Task task2;
    private Epic epic1;
    private Epic epic2;
    private Subtask subtask1;
    private Subtask subtask2;
    private Subtask subtask3;

    @Test
    void loadFromFile() {
        FileBackedTasksManager fileBackedTasksManager = LoaderTaskManager.loadFromFile(DEFAULT_SAVE_FILE.toFile());
        initAllTasks();
        assertEquals(List.of(task1, task2), fileBackedTasksManager.getAllTasks());
        assertEquals(List.of(subtask1, subtask2, subtask3), fileBackedTasksManager.getAllSubtasks());
        assertEquals(List.of(epic1, epic2), fileBackedTasksManager.getAllEpics());
        assertEquals(List.of(subtask2, task2, subtask1, epic1), fileBackedTasksManager.getHistory());
    }

    @Test
    void loadFromFileEmptyHistory() {
        FileBackedTasksManager fileBackedTasksManager = LoaderTaskManager.loadFromFile(DEFAULT_SAVE_FILE_WITH_EMPTY_HISTORY.toFile());
        initAllTasks();
        assertEquals(List.of(task1, task2), fileBackedTasksManager.getAllTasks());
        assertEquals(List.of(subtask1, subtask2, subtask3), fileBackedTasksManager.getAllSubtasks());
        assertEquals(List.of(epic1, epic2), fileBackedTasksManager.getAllEpics());
        assertEquals(List.of(), fileBackedTasksManager.getHistory());
    }

    @Test
    void loadFromFileEmpty() {
        FileBackedTasksManager fileBackedTasksManager = LoaderTaskManager.loadFromFile(DEFAULT_SAVE_FILE_WITH_EMPTY.toFile());
        assertEquals(List.of(), fileBackedTasksManager.getAllTasks());
        assertEquals(List.of(), fileBackedTasksManager.getAllSubtasks());
        assertEquals(List.of(), fileBackedTasksManager.getAllEpics());
        assertEquals(List.of(), fileBackedTasksManager.getHistory());
    }

    private void initAllTasks() {
        task1 = Task.fromArrayString(new String[]{"5", "TASK", "task1", "NEW", "test1", "null", "null"});
        task2 = Task.fromArrayString(new String[]{"6", "TASK", "task2", "NEW", "test2", "null", "null"});
        epic1 = Epic.fromArrayString(new String[]{"0", "EPIC", "epic1", "NEW", "test1", "null", "null"});
        epic2 = Epic.fromArrayString(new String[]{"1", "EPIC", "epic2", "NEW", "test2", "null", "null"});
        subtask1 = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "null", "null", "0"});
        subtask2 = Subtask.fromArrayString(new String[]{"3", "SUBTASK", "subtask2", "NEW", "test2", "null", "null", "0"});
        subtask3 = Subtask.fromArrayString(new String[]{"4", "SUBTASK", "subtask3", "NEW", "test3", "null", "null", "0"});
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        epic1.addSubtask(subtask3);
    }
}