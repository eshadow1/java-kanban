package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

public class Epic extends Task {
    private final Set<Subtask> subtasks;

    public Epic(int id, String title, String description) {
        super(id, title, description);
        this.subtasks = new HashSet<>();
    }

    public Epic(int id, String title, String description, Set<Subtask> subtasks) {
        super(id, title, description);
        this.subtasks = subtasks;
        updateStatus();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateStatus();
    }

    public void updateSubtask(Subtask subtask) {
        if (!subtasks.add(subtask)) {
            subtasks.remove(subtask);
            subtasks.add(subtask);
        }
        updateStatus();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        updateStatus();
    }

    public void updateStatus() {
        if (subtasks.isEmpty()) setStatus(Status.NEW);

        boolean isDone = true;
        boolean isNew = true;

        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != Status.NEW) isNew = false;
            if (subtask.getStatus() != Status.DONE) isDone = false;
            if (!isDone && !isNew) break;
        }
        if (isNew) setStatus(Status.NEW);
        else if (isDone) setStatus(Status.DONE);
        else setStatus(Status.IN_PROGRESS);
    }

    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks);
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
                ", subtasks=" + subtasks +
                '}';
    }
}
