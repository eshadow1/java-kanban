package controller;

import controller.history.HistoryManager;
import controller.history.InMemoryHistoryManager;
import controller.task.InMemoryTaskManager;
import controller.task.TaskManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static controller.task.FileBackedTasksManager.loadFromFile;

public class Manager {
    public enum Config{
        TYPE,
        VALUE
    }

    private static final Path RESOURCES_PATH =  Paths.get(System.getProperty("user.dir"), "resources");
    private static final Path CONFIG_FILE =  Paths.get(String.valueOf(RESOURCES_PATH), "config.csv");
    private static final Path DEFAULT_SAVE_FILE =  Paths.get(String.valueOf(RESOURCES_PATH), "default_save.csv");

    private Manager() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getFileBacked() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CONFIG_FILE.toFile()))) {
            while (bufferedReader.ready()) {
                String[] temp = bufferedReader.readLine().split(",");
                if (temp[Config.TYPE.ordinal()].equals("file_save")) {
                    return loadFromFile(new File(temp[Config.VALUE.ordinal()]));
                }
            }
        } catch (IOException error) {
            return loadFromFile(DEFAULT_SAVE_FILE.toFile());
        }
        return loadFromFile(DEFAULT_SAVE_FILE.toFile());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
