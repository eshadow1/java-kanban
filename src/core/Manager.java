package core;

import core.history.HistoryManager;
import core.history.InMemoryHistoryManager;
import core.task.InMemoryTaskManager;
import core.task.LoaderTaskManager;
import core.task.TaskManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
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
    private static final URI DEFAULT_SAVE_HTTP = URI.create("http://localhost:8078/");
    private static final String TYPE_CONFIG_SAVE_FILE = "file_save";
    private static final String DEFAULT_SAVE_KEY = "default";

    private Manager() {
    }

    public static TaskManager getDefault() {
        return LoaderTaskManager.loadFromHTTP(DEFAULT_SAVE_HTTP, DEFAULT_SAVE_KEY);
    }

    public static TaskManager getInMemoryTaskManager() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getFileBacked() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CONFIG_FILE.toFile()))) {
            while (bufferedReader.ready()) {
                String[] temp = bufferedReader.readLine().split(",");
                if (TYPE_CONFIG_SAVE_FILE.equals(temp[Config.TYPE.index])) {
                    return LoaderTaskManager.loadFromFile(Paths.get(temp[Config.VALUE.index]));
                }
            }
        } catch (IOException error) {
            return LoaderTaskManager.loadFromFile(DEFAULT_SAVE_FILE);
        }
        return LoaderTaskManager.loadFromFile(DEFAULT_SAVE_FILE);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
