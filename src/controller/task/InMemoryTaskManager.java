package controller.task;

import controller.GeneratorIdTask;
import controller.Manager;
import controller.history.HistoryManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;
import logger.DisplayInfoLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Subtask> subtasks;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.subtasks = new HashMap<>();
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.historyManager = Manager.getDefaultHistory();
    }

    @Override
    public void clearTasks() {
        tasks.clear();
    }

    @Override
    public void clearSubtasks() {
        subtasks.clear();
        for (int idEpic : epics.keySet()) {
            Epic epic = epics.get(idEpic);
            epic.clearSubtasks();
        }
    }

    @Override
    public void clearEpics() throws TaskManagerException {
        for (int idEpic : epics.keySet()) {
            Epic epic = epics.get(idEpic);
            if (!epic.getSubtasks().isEmpty()) {
                throw new TaskManagerException("Not empty list subtask for epic");
            }
        }

        epics.clear();
    }

    @Override
    public Task getTask(int idTask) {
        if (!tasks.containsKey(idTask)) return null;

        Task currentTask = tasks.get(idTask);
        historyManager.add(currentTask);
        return currentTask;
    }

    @Override
    public Subtask getSubtask(int idTask) {
        if (!subtasks.containsKey(idTask)) return null;

        Subtask currentSubtask = subtasks.get(idTask);
        historyManager.add(currentSubtask);
        return currentSubtask;
    }

    @Override
    public Epic getEpic(int idTask) {
        if (!epics.containsKey(idTask)) return null;

        Epic currentEpic = epics.get(idTask);
        historyManager.add(currentEpic);
        return currentEpic;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Task create(Task task) throws TaskManagerException {
        if (task.getId() != null || !task.getStatus().equals(Status.NEW)) {
            throw new TaskManagerException("Not new task");
        }
        task.setId(GeneratorIdTask.getId());
        return tasks.put(task.getId(), task);
    }

    @Override
    public Subtask create(Subtask subtask) throws TaskManagerException {
        if (subtask.getId() != null || !subtask.getStatus().equals(Status.NEW)) {
            throw new TaskManagerException("Not new subtask");
        }

        subtask.setId(GeneratorIdTask.getId());

        if (!epics.containsKey(subtask.getIdParentEpic())) {
            DisplayInfoLogger.logNotFoundEpicForSubtask(subtask.getId(), subtask.getIdParentEpic());
            return subtask;
        }

        Epic parent = epics.get(subtask.getIdParentEpic());
        parent.addSubtask(subtask);
        return subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public Epic create(Epic epic) throws TaskManagerException {
        if (epic.getId() != null || !epic.getStatus().equals(Status.NEW)) {
            throw new TaskManagerException("Not new epic");
        }
        epic.setId(GeneratorIdTask.getId());
        return epics.put(epic.getId(), epic);
    }

    @Override
    public Task update(Task task) throws TaskManagerException {
        if (task.getId() == null) throw new TaskManagerException("Not ID task");

        if (!tasks.containsKey(task.getId())) {
            DisplayInfoLogger.logNotFoundTask(task.getId());
            return task;
        }

        tasks.remove(task.getId());
        return tasks.put(task.getId(), task);
    }

    @Override
    public Subtask update(Subtask subtask) throws TaskManagerException {
        if (subtask.getId() == null) throw new TaskManagerException("Not ID subtask");

        if (!subtasks.containsKey(subtask.getId())) {
            DisplayInfoLogger.logNotFoundTask(subtask.getId());
            return subtask;
        }

        Epic parent = epics.get(subtask.getIdParentEpic());
        parent.updateSubtask(subtask);
        subtasks.remove(subtask.getId());
        return subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public Epic update(Epic epic) throws TaskManagerException {
        if (epic.getId() == null) throw new TaskManagerException("Not ID epic");

        if (!epics.containsKey(epic.getId())) {
            DisplayInfoLogger.logNotFoundTask(epic.getId());
            return epic;
        }
        epic.updateStatus();
        epics.remove(epic.getId());
        return epics.put(epic.getId(), epic);
    }

    @Override
    public Task removeTask(int idTask) {
        if (!tasks.containsKey(idTask)) {
            DisplayInfoLogger.logNotFoundTask(idTask);
            return null;
        }
        return tasks.remove(idTask);
    }

    @Override
    public Subtask removeSubtask(int idTask) {
        if (!subtasks.containsKey(idTask)) {
            DisplayInfoLogger.logNotFoundTask(idTask);
            return null;
        }
        Subtask subtask = subtasks.get(idTask);
        Epic epicParent = epics.get(subtask.getIdParentEpic());
        epicParent.removeSubtask(subtask);
        return subtasks.remove(idTask);
    }

    @Override
    public Epic removeEpic(int idTask) throws TaskManagerException {
        if (!epics.containsKey(idTask)) {
            DisplayInfoLogger.logNotFoundTask(idTask);
            return null;
        }
        Epic epic = epics.get(idTask);
        if (!epic.getSubtasks().isEmpty()) {
            throw new TaskManagerException("Not empty list subtask for epic");
        }
        return epics.remove(idTask);
    }

    @Override
    public List<Subtask> getSubtasksForEpic(int idEpic) {
        if (!epics.containsKey(idEpic)) {
            return null;
        }
        return epics.get(idEpic).getSubtasks();
    }
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}