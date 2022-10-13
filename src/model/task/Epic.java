package model.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static model.task.Type.EPIC;

public class Epic extends Task {
    private static final int SIZE_EPIC_CONFIG_CSV = 7;
    private final HashMap<Integer, Subtask> subtasks;

    public Epic(String title, String description) {
        super(title, description);
        this.subtasks = new HashMap<>();
        this.type = EPIC;
    }

    private Epic(Integer id, String title, String description, Status status,
                 LocalDateTime startPeriod, Integer durationMinutes) {
        super(id, title, description, status, startPeriod, durationMinutes);
        this.subtasks = new HashMap<>();
        this.type = EPIC;
        updateStatusAndDateTime();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateStatusAndDateTime();
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
        updateStatusAndDateTime();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId());
        updateStatusAndDateTime();
    }

    public void updateStatusAndDateTime() {
        updateStatus();
        updateDateTime();
    }

    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void clearSubtasks() {
        subtasks.clear();
        updateStatusAndDateTime();
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
        return super.toString();
    }

    static public Epic fromArrayString(String[] value) {
        if (value.length != SIZE_EPIC_CONFIG_CSV
                || !checkedCorrectId(value[SchemeCsv.ID.index])
                || (!"null".equals(value[SchemeCsv.DURATION.index])
                && !checkedCorrectId(value[SchemeCsv.DURATION.index]))) {
            return null;
        }
        LocalDateTime localDateTime = "null".equals(value[SchemeCsv.DATETIME.index]) ? null
                : LocalDateTime.parse(value[SchemeCsv.DATETIME.index]);
        Integer minutes = "null".equals(value[SchemeCsv.DURATION.index]) ? null
                : Integer.parseInt(value[SchemeCsv.DURATION.index]);

        return new Epic(
                Integer.parseInt(value[SchemeCsv.ID.index]),
                value[SchemeCsv.NAME.index],
                value[SchemeCsv.DESCRIPTION.index],
                Status.valueOf(value[SchemeCsv.STATUS.index]),
                localDateTime,
                minutes);
    }

    private void updateStatus() {
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

    private void updateDateTime() {
        if (subtasks.isEmpty()) {
            setStartPeriod(null);
            setDurationMinutes(null);
            return;
        }

        LocalDateTime startPeriod = null;
        LocalDateTime endTime = null;
        for (var task : subtasks.values()) {
            if (task.getStartPeriod() != null) {
                if (startPeriod == null) {
                    startPeriod = task.getStartPeriod();
                } else {
                    if (task.getStartPeriod().isBefore(startPeriod)) {
                        startPeriod = task.getStartPeriod();
                    }
                }
                if (endTime == null) {
                    endTime = task.getEndTime();
                } else {
                    if (task.getEndTime().isAfter(endTime)) {
                        endTime = task.getEndTime();
                    }
                }
            }
        }
        setStartPeriod(startPeriod);
        if (startPeriod == null || endTime == null) {
            setDurationMinutes(null);
        } else {
            setDurationMinutes(Duration.between(startPeriod, endTime));
        }
    }
}
