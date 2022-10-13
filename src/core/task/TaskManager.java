package core.task;

import model.task.Epic;
import model.task.Subtask;
import model.task.Task;

import java.util.List;

public interface TaskManager {
    void clearTasks();

    void clearSubtasks();

    void clearEpics();

    Task getTask(int idTask);

    Subtask getSubtask(int idTask);

    Epic getEpic(int idTask);

    List<Task> getAllTasks();

    List<Subtask> getAllSubtasks();

    List<Epic> getAllEpics();

    Task create(Task task);

    Subtask create(Subtask subtask);

    Epic create(Epic epic);

    Task update(Task task);

    Subtask update(Subtask subtask);

    Epic update(Epic epic);

    Task removeTask(int idTask);

    Subtask removeSubtask(int idTask);

    Epic removeEpic(int idTask);

    List<Subtask> getSubtasksForEpic(int idEpic);

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();
}
