package controller.task;

import controller.history.HistoryFormatter;
import model.task.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LoaderTaskManager {
    private LoaderTaskManager() {
    }
    static public HTTPTaskManager loadFromHTTP(URI uri, String key) {
        HTTPTaskManager httpTaskManager = new HTTPTaskManager(uri);
        httpTaskManager.load(key);
        return httpTaskManager;
    }

    static public FileBackedTasksManager loadFromFile(Path file) {
        try {
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file.toFile()))) {
            int maxIdTask = 0;

            if (bufferedReader.ready()) {
                bufferedReader.readLine();
            }
            while (bufferedReader.ready()) {
                String temp = bufferedReader.readLine();
                if (temp.isEmpty()) {
                    break;
                } else {
                    Task task = fileBackedTasksManager.getFromStringAndAddIfNotNull(temp);
                    if (task != null && maxIdTask < task.getId()) {
                        maxIdTask = task.getId()  + 1;
                    }
                }
            }

            GeneratorIdTask.setStartPosition(maxIdTask);

            List<Integer> tempHistory = HistoryFormatter.historyFromString(bufferedReader.readLine());
            for (var id : tempHistory) {
                if (fileBackedTasksManager.getEpic(id) != null) {
                    continue;
                }
                if (fileBackedTasksManager.getSubtask(id) != null) {
                    continue;
                }
                fileBackedTasksManager.getTask(id);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileBackedTasksManager;
    }
}
