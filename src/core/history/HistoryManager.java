package core.history;

import model.task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    void remove(int idTask);

    List<Task> getHistory();
}
