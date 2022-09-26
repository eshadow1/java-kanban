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

    @Test
    void loadFromFile() {
        FileBackedTasksManager fileBackedTasksManager = LoaderTaskManager.loadFromFile(DEFAULT_SAVE_FILE.toFile());
        Task task1 = Task.fromArrayString(new String[]{"5", "TASK", "task1", "NEW", "test1"});
        Task task2 = Task.fromArrayString(new String[]{"6", "TASK", "task2", "NEW", "test2"});
        Epic epic1 = Epic.fromArrayString(new String[]{"0", "EPIC", "epic1", "NEW", "test1"});
        Epic epic2 = Epic.fromArrayString(new String[]{"1", "EPIC", "epic2", "NEW", "test2"});
        Subtask subtask1 = Subtask.fromArrayString(new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "0"});
        Subtask subtask2 = Subtask.fromArrayString(new String[]{"3", "SUBTASK", "subtask2", "NEW", "test2", "0"});
        Subtask subtask3 = Subtask.fromArrayString(new String[]{"4", "SUBTASK", "subtask3", "NEW", "test3", "0"});
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        epic1.addSubtask(subtask3);
        assertEquals(List.of(task1, task2), fileBackedTasksManager.getAllTasks());
        assertEquals(List.of(subtask1, subtask2, subtask3), fileBackedTasksManager.getAllSubtasks());
        assertEquals(List.of(epic1, epic2), fileBackedTasksManager.getAllEpics());
        assertEquals(List.of(subtask2, epic1, task2, subtask1), fileBackedTasksManager.getHistory());
    }
}