package controller;

import controller.history.HistoryManager;
import controller.history.InMemoryHistoryManager;
import controller.task.InMemoryTaskManager;
import controller.task.LoaderTaskManager;
import controller.task.TaskManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Manager {
    public enum Config {
        TYPE(0),
        VALUE(1);
        final int index;

        Config(int index) {
            this.index = index;
        }
    }

    private static final Path RESOURCES_PATH = Paths.get(System.getProperty("user.dir"), "resources");
    private static final Path CONFIG_FILE = Paths.get(String.valueOf(RESOURCES_PATH), "config.csv");
    private static final Path DEFAULT_SAVE_FILE = Paths.get(String.valueOf(RESOURCES_PATH), "default_save.csv");
    private static final String TYPE_CONFIG_SAVE_FILE = "file_save";

    private Manager() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getFileBacked() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CONFIG_FILE.toFile()))) {
            while (bufferedReader.ready()) {
                String[] temp = bufferedReader.readLine().split(",");
                if (TYPE_CONFIG_SAVE_FILE.equals(temp[Config.TYPE.index])) {
                    return LoaderTaskManager.loadFromFile(new File(temp[Config.VALUE.index]));
                }
            }
        } catch (IOException error) {
            return LoaderTaskManager.loadFromFile(DEFAULT_SAVE_FILE.toFile());
        }
        return LoaderTaskManager.loadFromFile(DEFAULT_SAVE_FILE.toFile());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
