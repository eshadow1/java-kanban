package model.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    private final Map<Integer, Subtask> subtasks;

    public Epic(String title, String description) {
        super(title, description);
        this.subtasks = new HashMap<>();
    }

    public Epic(String title, String description, Map<Integer, Subtask> subtasks) {
        super(title, description);
        this.subtasks = subtasks;
        updateStatus();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateStatus();
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
        updateStatus();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId());
        updateStatus();
    }

    public void updateStatus() {
        if (subtasks.isEmpty()) {
            setStatus(Status.NEW);
            return;
        }

        boolean isDone = true;
        boolean isNew = true;

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getStatus() != Status.NEW) isNew = false;
            if (subtask.getStatus() != Status.DONE) isDone = false;
            if (!isDone && !isNew) break;
        }
        if (isNew) setStatus(Status.NEW);
        else if (isDone) setStatus(Status.DONE);
        else setStatus(Status.IN_PROGRESS);
    }

    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void clearSubtasks() {
        subtasks.clear();
        updateStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasks, epic.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasks);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtasks=" + subtasks.values() +
                '}';
    }
}
