package controller.task;

import controller.history.HistoryFormatter;
import model.task.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LoaderTaskManager {
    private LoaderTaskManager() {
    }

    static public FileBackedTasksManager loadFromFile(File file) {
        try {
            Path testFile = Paths.get(file.toString());
            if (!Files.exists(testFile)) {
                Files.createFile(testFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int maxIdTask = 0;

            if (bufferedReader.ready()) {
                bufferedReader.readLine();
            }
            while (bufferedReader.ready()) {
                String temp = bufferedReader.readLine();
                if (temp.isEmpty()) {
                    break;
                } else {
                    Task task = getFromStringAndAddIfNotNull(fileBackedTasksManager, temp);
                    if (task != null && maxIdTask < task.getId()) {
                        maxIdTask = task.getId();
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

    static private Task getFromStringAndAddIfNotNull(FileBackedTasksManager fileBackedTasksManager,
                                                     String value) {
        String[] temp = value.split(",");
        switch (Type.valueOf(temp[SchemeCsv.TYPE.index])) {
            case TASK:
                var task = Task.fromArrayString(temp);
                fileBackedTasksManager.add(task);
                return task;
            case SUBTASK:
                var subtask = Subtask.fromArrayString(temp);
                fileBackedTasksManager.add(subtask);
                return subtask;
            case EPIC:
                var epic = Epic.fromArrayString(temp);
                fileBackedTasksManager.add(epic);
                return epic;
            default:
                return null;
        }
    }
}
