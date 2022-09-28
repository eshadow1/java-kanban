package controller.task;

import controller.Manager;
import controller.history.HistoryManager;
import model.task.Epic;
import model.task.Subtask;
import model.task.Task;
import model.task.Status;
import logger.DisplayInfoLogger;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Subtask> subtasks;
    protected final Map<Integer, Task> tasks;
    protected final Map<Integer, Epic> epics;

    protected final TreeSet<Task> prioritizedTasks;
    protected final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.subtasks = new HashMap<>();
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.prioritizedTasks = new TreeSet<>();
        this.historyManager = Manager.getDefaultHistory();
    }

    @Override
    public void clearTasks() {
        clearHistoryTasks(tasks);
        clearPrioritizedTasks(tasks);
        tasks.clear();
    }

    @Override
    public void clearSubtasks() {
        clearHistoryTasks(subtasks);
        clearPrioritizedTasks(subtasks);
        subtasks.clear();
        for (int idEpic : epics.keySet()) {
            Epic epic = epics.get(idEpic);
            epic.clearSubtasks();
        }
    }

    @Override
    public void clearEpics() {
        clearHistoryTasks(subtasks);
        clearPrioritizedTasks(subtasks);
        subtasks.clear();
        clearHistoryTasks(epics);
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
    public Task create(Task task) {
        if (task.getId() != null || !task.getStatus().equals(Status.NEW)) {
            throw new TaskManagerException("Not new task");
        }
        if (!isCorrectTimeTask(task)) {
            throw new TaskManagerException("Incorrect time in task");
        }

        task.setId(GeneratorIdTask.getId());
        prioritizedTasks.add(task);
        return tasks.put(task.getId(), task);
    }


    @Override
    public Subtask create(Subtask subtask) {
        if (subtask.getId() != null || !subtask.getStatus().equals(Status.NEW)) {
            throw new TaskManagerException("Not new subtask");
        }

        if (!epics.containsKey(subtask.getIdParentEpic())) {
            DisplayInfoLogger.logNotFoundEpicForSubtask(subtask.getId(), subtask.getIdParentEpic());
            return subtask;
        }

        if (!isCorrectTimeTask(subtask)) {
            throw new TaskManagerException("Incorrect time in subtask");
        }

        subtask.setId(GeneratorIdTask.getId());

        Epic parent = epics.get(subtask.getIdParentEpic());
        parent.addSubtask(subtask);
        prioritizedTasks.add(subtask);
        return subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public Epic create(Epic epic) {
        if (epic.getId() != null || !epic.getStatus().equals(Status.NEW)) {
            throw new TaskManagerException("Not new epic");
        }
        epic.setId(GeneratorIdTask.getId());
        return epics.put(epic.getId(), epic);
    }

    @Override
    public Task update(Task task) {
        if (task.getId() == null) {
            throw new TaskManagerException("Not ID task");
        }

        if (!tasks.containsKey(task.getId())) {
            DisplayInfoLogger.logNotFoundTask(task.getId());
            return task;
        }

        Task oldTask = tasks.get(task.getId());
        removeInPrioritizedTasks(oldTask);
        if (!isCorrectTimeTask(task)) {
            prioritizedTasks.add(oldTask);
            DisplayInfoLogger.logNotUpdateTaskForDateTime(task.getId());
            return task;
        }

        prioritizedTasks.add(task);
        return tasks.put(task.getId(), task);
    }

    @Override
    public Subtask update(Subtask subtask) {
        if (subtask.getId() == null) {
            throw new TaskManagerException("Not ID subtask");
        }

        if (!subtasks.containsKey(subtask.getId())) {
            DisplayInfoLogger.logNotFoundTask(subtask.getId());
            return subtask;
        }

        Subtask oldSubtask = subtasks.get(subtask.getId());

        removeInPrioritizedTasks(oldSubtask);
        if (!isCorrectTimeTask(subtask)) {
            prioritizedTasks.add(oldSubtask);
            DisplayInfoLogger.logNotUpdateTaskForDateTime(subtask.getId());
            return subtask;
        }

        Epic parent = epics.get(subtask.getIdParentEpic());
        parent.updateSubtask(subtask);
        prioritizedTasks.add(subtask);
        return subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public Epic update(Epic epic) {
        if (epic.getId() == null) throw new TaskManagerException("Not ID epic");

        if (!epics.containsKey(epic.getId())) {
            DisplayInfoLogger.logNotFoundTask(epic.getId());
            return epic;
        }
        epic.updateStatusAndDateTime();
        return epics.put(epic.getId(), epic);
    }

    @Override
    public Task removeTask(int idTask) {
        if (!tasks.containsKey(idTask)) {
            DisplayInfoLogger.logNotFoundTask(idTask);
            return null;
        }
        historyManager.remove(idTask);
        removeInPrioritizedTasks(tasks.get(idTask));
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
        historyManager.remove(idTask);
        removeInPrioritizedTasks(subtask);
        return subtasks.remove(idTask);
    }

    @Override
    public Epic removeEpic(int idTask) {
        if (!epics.containsKey(idTask)) {
            DisplayInfoLogger.logNotFoundTask(idTask);
            return null;
        }

        List<Subtask> subtasksEpic = epics.get(idTask).getSubtasks();
        if (!subtasksEpic.isEmpty()) {
            for (Subtask subtask : subtasksEpic) {
                int idSubtask = subtask.getId();
                historyManager.remove(idSubtask);
                removeInPrioritizedTasks(subtask);
                subtasks.remove(idSubtask);
            }
        }
        historyManager.remove(idTask);
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

    @Override
    public List<Task> getPrioritizedTasks() {
        List<Task> prioritizedListTasks = new ArrayList<>(prioritizedTasks);

        for (Integer idTask : epics.keySet()) {
            var epic = epics.get(idTask);
            if (epic.getStartTime() == null) {
                prioritizedListTasks.add(epic);
            }
        }
        return prioritizedListTasks;
    }

    private <T extends Task> void clearHistoryTasks(Map<Integer, T> mapTasks) {
        for (Integer idTask : mapTasks.keySet()) {
            historyManager.remove(idTask);
        }
    }

    private <T extends Task> void clearPrioritizedTasks(Map<Integer, T> mapTasks) {
        for (Integer idTask : mapTasks.keySet()) {
            removeInPrioritizedTasks(mapTasks.get(idTask));
        }
    }

    private boolean isCorrectTimeTask(Task task) {
        Task lowerTask = prioritizedTasks.lower(task);
        if (lowerTask != null && lowerTask.getEndTime() != null
                && lowerTask.getEndTime().isAfter(task.getStartTime())) {
            return false;
        }

        Task ceilingTask = prioritizedTasks.ceiling(task);
        final boolean correctEndTask = ceilingTask == null
                || ceilingTask.getEndTime() == null
                || !ceilingTask.getStartTime().isBefore(task.getEndTime());
        return correctEndTask;
    }

    private void removeInPrioritizedTasks(Task task) {
        Iterator<Task> iter = prioritizedTasks.iterator();
        while (iter.hasNext()) {
            Task next = iter.next();
            if (next.equals(task)) {
                iter.remove();
                break;
            }
        }
    }
}

