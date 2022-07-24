package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import view.DisplayInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllTaskManager {
    private final DisplayInfo displayInfo;
    private final Map<Integer, Subtask> subtasks;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;

    public AllTaskManager() {
        this.displayInfo = new DisplayInfo();
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

    public void clearEpics() {
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

    public void create(Task task) {
        tasks.remove(task.getId());
        tasks.put(task.getId(), task);
    }

    public void create(Subtask subtask) {
        if (!epics.containsKey(subtask.getIdParentEpic())) {
            displayInfo.logNotFoundEpicForSubtask(subtask.getId(), subtask.getIdParentEpic());
            return;
        }

        Epic parent = epics.get(subtask.getIdParentEpic());
        parent.addSubtask(subtask);
        subtasks.remove(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
    }

    public void create(Epic epic) {
        epics.remove(epic.getId());
        epics.put(epic.getId(), epic);
    }

    public void update(Task task) {
        if (!tasks.containsKey(task.getId())) {
            displayInfo.logNotFoundTask(task.getId());
            return;
        }

        tasks.remove(task.getId());
        tasks.put(task.getId(), task);
    }

    public void update(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId())) {
            displayInfo.logNotFoundTask(subtask.getId());
            return;
        }

        subtasks.remove(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
        Epic parent = epics.get(subtask.getIdParentEpic());
        parent.updateSubtask(subtask);
    }

    public void update(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            displayInfo.logNotFoundTask(epic.getId());
            return;
        }
        epic.updateStatus();
        epics.remove(epic.getId());
        epics.put(epic.getId(), epic);
    }

    public void deleteTask(int idTask) {
        if (!tasks.containsKey(idTask)) {
            displayInfo.logNotFoundTask(idTask);
            return;
        }
        tasks.remove(idTask);
    }

    public void deleteSubtask(int idTask) {
        if (!subtasks.containsKey(idTask)) {
            displayInfo.logNotFoundTask(idTask);
            return;
        }
        Subtask subtask = subtasks.get(idTask);
        Epic epicParent = epics.get(subtask.getIdParentEpic());
        epicParent.removeSubtask(subtask);
        subtasks.remove(idTask);
    }

    public void deleteEpic(int idTask) {
        if (!epics.containsKey(idTask)) {
            displayInfo.logNotFoundTask(idTask);
            return;
        }
        Epic epic = epics.get(idTask);
        if (!epic.getSubtasks().isEmpty()) {
            displayInfo.logNotEmptyEpic(epic.getId());
            return;
        }
        epics.remove(idTask);
    }

    public List<Subtask> getSubtasksForEpic(int idEpic) {
        if (!epics.containsKey(idEpic)) {
            return null;
        }
        return epics.get(idEpic).getSubtasks();
    }
}
