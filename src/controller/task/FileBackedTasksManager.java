package controller.task;

import controller.history.HistoryManager;
import logger.DisplayInfoLogger;
import model.task.*;
import test.Test8;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File saveFile;
    private static final String CONFIG_CSV = "id,type,name,status,description,epic\n";
    private static final int SIZE_EPIC_CONFIG_CSV = 5;
    private static final int SIZE_SUBTASK_CONFIG_CSV = 6;
    private static final int SIZE_TASK_CONFIG_CSV = 5;

    static public void main(String[] argv) {
        System.out.println(Test8.getHistoryWithSave());
        System.out.println();
    }

    static private Task fromString(String value) {
        String[] temp = value.split(",");

        switch (temp[SchemeCsv.TYPE.ordinal()]) {
            case "TASK":
                if (temp.length != SIZE_TASK_CONFIG_CSV) {
                    throw new TaskManagerException("Ошибка при создании таска: некорректные данные");
                }
                return Task.fromArrayString(temp);
            case "SUBTASK":
                if (temp.length != SIZE_SUBTASK_CONFIG_CSV) {
                    throw new TaskManagerException("Ошибка при создании таска: некорректные данные");
                }
                return Subtask.fromArrayString(temp);
            case "EPIC":
                if (temp.length != SIZE_EPIC_CONFIG_CSV) {
                    throw new TaskManagerException("Ошибка при создании таска: некорректные данные");
                }
                return Epic.fromArrayString(temp);
            default:
                throw new TaskManagerException("Ошибка при создании таска: некорректный тип");
        }
    }

    static public FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            if (bufferedReader.ready()) {
                bufferedReader.readLine();
            }

            while (bufferedReader.ready()) {
                String temp = bufferedReader.readLine();
                if (temp.isEmpty()) {
                    break;
                } else {
                    Task taskInHistory = fromString(temp);
                    if (taskInHistory.getClass() == Task.class) {
                        fileBackedTasksManager.add(taskInHistory);
                    } else if (taskInHistory.getClass() == Subtask.class) {
                        fileBackedTasksManager.add((Subtask) taskInHistory);
                    } else if (taskInHistory.getClass() == Epic.class) {
                        fileBackedTasksManager.add((Epic) taskInHistory);
                    }
                }
            }

            List<Integer> tempHistory = historyFromString(bufferedReader.readLine());
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

    private FileBackedTasksManager(File fileName) {
        saveFile = fileName;
    }

    static String historyToString(HistoryManager manager) {
        StringBuilder historyString = new StringBuilder();
        for (var task : manager.getHistory()) {
            historyString.append(task.getId()).append(",");
        }
        historyString.setLength(historyString.length() != 0 ? historyString.length() - 1 : 0);
        return historyString.toString();
    }

    static List<Integer> historyFromString(String value) {
        if (value == null || value.isEmpty()) return new ArrayList<>();

        String[] history = value.split(",");
        List<Integer> historyTasks = new ArrayList<>();
        for (String s : history) {
            historyTasks.add(Integer.parseInt(s));
        }
        return historyTasks;
    }

    void save() {
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
            bufferedWriter.write(historyToString(historyManager));
        } catch (IOException error) {
            throw new ManagerSaveException(error.getMessage());
        }
    }

    @Override
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
        return tasks.put(task.getId(), task);
    }

    public Subtask add(Subtask subtask) {
        if (!epics.containsKey(subtask.getIdParentEpic())) {
            DisplayInfoLogger.logNotFoundEpicForSubtask(subtask.getId(), subtask.getIdParentEpic());
            return subtask;
        }

        Epic parent = epics.get(subtask.getIdParentEpic());
        parent.addSubtask(subtask);
        return subtasks.put(subtask.getId(), subtask);
    }

    public Epic add(Epic epic) {
        return epics.put(epic.getId(), epic);
    }
}
