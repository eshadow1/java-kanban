package controller.task;

import controller.history.HistoryFormatter;
import logger.DisplayInfoLogger;
import model.task.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private static final String CONFIG_CSV = "id,type,name,status,description,datetime,duration,epic\n";
    private final File saveFile;

    public FileBackedTasksManager(File fileName) {
        saveFile = fileName;
    }

    public void clearTasks() {
        super.clearTasks();
        this.save();
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        this.save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        this.save();
    }

    @Override
    public Task getTask(int idTask) {
        var task = super.getTask(idTask);
        this.save();
        return task;
    }

    @Override
    public Subtask getSubtask(int idTask) {
        var subtask = super.getSubtask(idTask);
        this.save();
        return subtask;
    }

    @Override
    public Epic getEpic(int idTask) {
        var epic = super.getEpic(idTask);
        this.save();
        return epic;
    }

    @Override
    public Task create(Task task) {
        var result = super.create(task);
        this.save();
        return result;
    }

    @Override
    public Subtask create(Subtask subtask) {
        var result = super.create(subtask);
        this.save();
        return result;
    }

    @Override
    public Epic create(Epic epic) {
        var result = super.create(epic);
        this.save();
        return result;
    }

    @Override
    public Task update(Task task) {
        var result = super.update(task);
        this.save();
        return result;
    }

    @Override
    public Subtask update(Subtask subtask) {
        var result = super.update(subtask);
        this.save();
        return result;
    }

    @Override
    public Epic update(Epic epic) {
        var result = super.update(epic);
        this.save();
        return result;
    }

    @Override
    public Task removeTask(int idTask) {
        var result = super.removeTask(idTask);
        this.save();
        return result;
    }

    @Override
    public Subtask removeSubtask(int idTask) {
        var result = super.removeSubtask(idTask);
        this.save();
        return result;
    }

    @Override
    public Epic removeEpic(int idTask) {
        var result = super.removeEpic(idTask);
        this.save();
        return result;
    }

    public Task add(Task task) {
        if (task == null) {
            return null;
        }
        prioritizedTasks.add(task);
        return tasks.put(task.getId(), task);
    }

    public Subtask add(Subtask subtask) {
        if (subtask == null) {
            return null;
        }
        if (!epics.containsKey(subtask.getIdParentEpic())) {
            DisplayInfoLogger.logNotFoundEpicForSubtask(subtask.getId(), subtask.getIdParentEpic());
            return subtask;
        }

        Epic parent = epics.get(subtask.getIdParentEpic());
        parent.addSubtask(subtask);
        prioritizedTasks.add(subtask);
        return subtasks.put(subtask.getId(), subtask);
    }

    public Epic add(Epic epic) {
        if (epic == null) {
            return null;
        }
        return epics.put(epic.getId(), epic);
    }

    private void save() {
        if (saveFile == null) {
            throw new ManagerSaveException("NullPointerException");
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(saveFile))) {
            bufferedWriter.write(CONFIG_CSV);
            for (var task : getAllTasks()) {
                bufferedWriter.write(task.toString() + "\n");
            }
            for (var epic : getAllEpics()) {
                bufferedWriter.write(epic.toString() + "\n");
            }
            for (var subtask : getAllSubtasks()) {
                bufferedWriter.write(subtask.toString() + "\n");
            }
            bufferedWriter.write("\n");
            bufferedWriter.write(HistoryFormatter.historyToString(historyManager));
        } catch (IOException error) {
            throw new ManagerSaveException(error.getMessage());
        }
    }
}
