package controller.task;

import model.task.Epic;
import model.task.Subtask;
import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest{
    private static final Path RESOURCES_PATH = Paths.get(System.getProperty("user.dir"), "resources-test");
    private static final Path DEFAULT_SAVE_FILE = Paths.get(String.valueOf(RESOURCES_PATH), "default_save.csv");

    @BeforeEach
    public void beforeEach() {
        taskManager = new FileBackedTasksManager(DEFAULT_SAVE_FILE.toFile());
        int startGenerator = 0;
        GeneratorIdTask.setStartPosition(startGenerator);
        initAllTasks();
    }

    @Test
    void excaptionNullFile() {
        FileBackedTasksManager nullFileBackedTasksManager = new FileBackedTasksManager(null);
        assertThrows(
                ManagerSaveException.class,
                () -> nullFileBackedTasksManager.create(task));
    }

    @Test
    void addTask() {
        task.setId(GeneratorIdTask.getId());
        var fileBackedTasksManager = (FileBackedTasksManager) taskManager;
        fileBackedTasksManager.add(task);
        assertEquals(task, fileBackedTasksManager.getTask(task.getId()));
        assertNull(fileBackedTasksManager.add((Task) null));
    }

    @Test
    void addSubtask() {
        epic.setId(GeneratorIdTask.getId());
        var fileBackedTasksManager = (FileBackedTasksManager) taskManager;
        fileBackedTasksManager.add(epic);
        subtask.setId(GeneratorIdTask.getId());
        fileBackedTasksManager.add(subtask);
        assertEquals(subtask, fileBackedTasksManager.getSubtask(subtask.getId()));
        assertNull(fileBackedTasksManager.add((Subtask) null));
    }

    @Test
    void addEpic() {
        epic.setId(GeneratorIdTask.getId());
        var fileBackedTasksManager = (FileBackedTasksManager) taskManager;
        fileBackedTasksManager.add(epic);
        assertEquals(epic, fileBackedTasksManager.getEpic(epic.getId()));
        assertNull(fileBackedTasksManager.add((Epic) null));
    }
}