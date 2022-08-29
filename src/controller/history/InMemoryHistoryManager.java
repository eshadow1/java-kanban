package controller.history;

import model.task.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList<Task> history = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        history.linkLast(task.getId(), task);
    }

    @Override
    public void remove(int idTask) {
        history.remove(idTask);
    }

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }
}
