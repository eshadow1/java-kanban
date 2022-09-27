package model.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static model.task.Type.EPIC;

public class Epic extends Task {
    private static final int SIZE_EPIC_CONFIG_CSV = 7;
    private final TreeMap<Integer, Subtask> subtasks;

    public Epic(String title, String description) {
        super(title, description);
        this.subtasks = new TreeMap<>();
        this.type = EPIC;
    }

    public Epic(String title, String description, Map<Integer, Subtask> subtasks) {
        super(title, description);
        this.subtasks = new TreeMap<>(subtasks);
        this.type = EPIC;
        updateStatusAndDateTime();
    }

    private Epic(Integer id, String title, String description, Status status, LocalDateTime startTime, int durationMinutes) {
        super(id, title, description, status, startTime, durationMinutes);
        this.subtasks = new TreeMap<>();
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
        if (value.length != SIZE_EPIC_CONFIG_CSV ||
                !checkedCorrectId(value[SchemeCsv.ID.index]) ||
                !checkedCorrectId(value[SchemeCsv.DURATION.index])) {
            return null;
        }
        LocalDateTime localDateTime = "null".equals(value[SchemeCsv.DATETIME.index]) ? null :  LocalDateTime.parse(value[SchemeCsv.DATETIME.index]);
        return new Epic(
                Integer.parseInt(value[SchemeCsv.ID.index]),
                value[SchemeCsv.NAME.index],
                value[SchemeCsv.DESCRIPTION.index],
                Status.valueOf(value[SchemeCsv.STATUS.index]),
                localDateTime,
                Integer.parseInt(value[SchemeCsv.DURATION.index])
        );
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
            setStartTime(DEFAULT_START_TIME);
            setDurationMinutes(DEFAULT_DURATION);
            return;
        }

        LocalDateTime startTime = subtasks.firstEntry().getValue().getStartTime();
        LocalDateTime endTime = subtasks.lastEntry().getValue().getEndTime();
        if (startTime != null && endTime == null) {
            for (var task : subtasks.values()) {
                if (task.getStartTime() == null) {
                    break;
                }
                endTime = task.getEndTime();
            }
        }
        setStartTime(startTime);
        if(startTime == null || endTime == null) {
            setDurationMinutes(DEFAULT_DURATION);
        } else {
            setDurationMinutes(Duration.between(startTime, endTime));
        }
    }
}
