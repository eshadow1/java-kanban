package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import logger.DisplayInfoLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllTaskManager {
    private final Map<Integer, Subtask> subtasks;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;

    public AllTaskManager() {
        this.subtasks = new HashMap<>();
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    public void clearTasks() {
        tasks.clear();
    }

    public void clearSubtasks() {
        subtasks.clear();
        for (int idEpic : epics.keySet()) {
            Epic epic = epics.get(idEpic);
            epic.clearSubtasks();
        }
    }

    public void clearEpics() throws AllTaskException {
        for (int idEpic : epics.keySet()) {
            Epic epic = epics.get(idEpic);
            if (!epic.getSubtasks().isEmpty()) {
                DisplayInfoLogger.logNotClearEpics();
                throw new AllTaskException("Not empty list subtask for epic");
            }
        }

        epics.clear();
    }

    public Task getTask(int idTask) {
        if (!tasks.containsKey(idTask)) return null;
        return tasks.get(idTask);
    }

    public Subtask getSubtask(int idTask) {
        if (!subtasks.containsKey(idTask)) return null;
        return subtasks.get(idTask);
    }

    public Epic getEpic(int idTask) {
        if (!epics.containsKey(idTask)) return null;
        return epics.get(idTask);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public Task create(Task task) throws AllTaskException {
        if (task.getId() != null || !task.getStatus().equals(Task.Status.NEW)) {
            DisplayInfoLogger.logNotNewTask();
            throw new AllTaskException("Not new task");
        }
        task.setId(GeneratorIdTask.getId());
        return tasks.put(task.getId(), task);
    }

    public Subtask create(Subtask subtask) throws AllTaskException {
        if (subtask.getId() != null || !subtask.getStatus().equals(Task.Status.NEW)) {
            DisplayInfoLogger.logNotNewTask();
            throw new AllTaskException("Not new subtask");
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

    public Epic create(Epic epic) throws AllTaskException {
        if (epic.getId() != null || !epic.getStatus().equals(Task.Status.NEW)) {
            DisplayInfoLogger.logNotNewTask();
            throw new AllTaskException("Not new epic");
        }
        epic.setId(GeneratorIdTask.getId());
        return epics.put(epic.getId(), epic);
    }

    public Task update(Task task) throws AllTaskException {
        if (task.getId() == null) {
            DisplayInfoLogger.logNotIdTask();
            throw new AllTaskException("Not ID task");
        }

        if (!tasks.containsKey(task.getId())) {
            DisplayInfoLogger.logNotFoundTask(task.getId());
            return task;
        }

        tasks.remove(task.getId());
        return tasks.put(task.getId(), task);
    }

    public Subtask update(Subtask subtask) throws AllTaskException {
        if (subtask.getId() == null) {
            DisplayInfoLogger.logNotIdTask();
            throw new AllTaskException("Not ID subtask");
        }

        if (!subtasks.containsKey(subtask.getId())) {
            DisplayInfoLogger.logNotFoundTask(subtask.getId());
            return subtask;
        }

        Epic parent = epics.get(subtask.getIdParentEpic());
        parent.updateSubtask(subtask);
        subtasks.remove(subtask.getId());
        return subtasks.put(subtask.getId(), subtask);
    }

    public Epic update(Epic epic) throws AllTaskException {
        if (epic.getId() == null) {
            DisplayInfoLogger.logNotIdTask();
            throw new AllTaskException("Not ID epic");
        }

        if (!epics.containsKey(epic.getId())) {
            DisplayInfoLogger.logNotFoundTask(epic.getId());
            return epic;
        }
        epic.updateStatus();
        epics.remove(epic.getId());
        return epics.put(epic.getId(), epic);
    }

    public Task removeTask(int idTask) {
        if (!tasks.containsKey(idTask)) {
            DisplayInfoLogger.logNotFoundTask(idTask);
            return null;
        }
        return tasks.remove(idTask);
    }

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

    public Epic removeEpic(int idTask) throws AllTaskException {
        if (!epics.containsKey(idTask)) {
            DisplayInfoLogger.logNotFoundTask(idTask);
            return null;
        }
        Epic epic = epics.get(idTask);
        if (!epic.getSubtasks().isEmpty()) {
            DisplayInfoLogger.logNotEmptyEpic(epic.getId());
            throw new AllTaskException("Not empty list subtask for epic");
        }
        return epics.remove(idTask);
    }

    public List<Subtask> getSubtasksForEpic(int idEpic) {
        if (!epics.containsKey(idEpic)) {
            return null;
        }
        return epics.get(idEpic).getSubtasks();
    }
}