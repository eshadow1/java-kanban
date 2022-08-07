package controller.task;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public interface TaskManager {
    void clearTasks();

    void clearSubtasks();

    void clearEpics() throws TaskManagerException;

    Task getTask(int idTask);

    Subtask getSubtask(int idTask);

    Epic getEpic(int idTask);

    List<Task> getAllTasks();

    List<Subtask> getAllSubtasks();

    List<Epic> getAllEpics();

    Task create(Task task) throws TaskManagerException;

    Subtask create(Subtask subtask) throws TaskManagerException;

    Epic create(Epic epic) throws TaskManagerException;

    Task update(Task task) throws TaskManagerException;

    Subtask update(Subtask subtask) throws TaskManagerException;

    Epic update(Epic epic) throws TaskManagerException;

    Task removeTask(int idTask);

    Subtask removeSubtask(int idTask);

    Epic removeEpic(int idTask) throws TaskManagerException;

    List<Subtask> getSubtasksForEpic(int idEpic);

    List<Task> getHistory();
}
