package controller;

import controller.history.HistoryManager;
import controller.history.InMemoryHistoryManager;
import controller.task.InMemoryTaskManager;
import controller.task.TaskManager;

public class Manager {
    private Manager() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
