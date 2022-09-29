package model.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static model.task.Type.TASK;

public class Task implements Comparable<Task> {
    protected Integer id;
    protected final String title;
    protected final String description;
    protected Status status;
    protected LocalDateTime startTime;
    protected Type type;
    protected Duration durationMinutes;
    private static final int SIZE_TASK_CONFIG_CSV = 7;

    protected Task(Integer id, String title, String description, Status status, LocalDateTime startTime, Integer minutes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.durationMinutes = minutes == null ? null : Duration.ofMinutes(minutes);
        this.startTime = startTime;
        this.type = TASK;
    }

    public Task(String title, String description, LocalDateTime startTime, Integer minutes) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
        this.startTime = startTime;
        this.durationMinutes = minutes == null ? null : Duration.ofMinutes(minutes);
        this.type = TASK;
    }

    public Task(String title, String description) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
        this.startTime = null;
        this.durationMinutes = null;
        this.type = TASK;
    }

    public Task(String title, String description, Status status, LocalDateTime startTime, Integer minutes) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.durationMinutes = minutes == null ? null : Duration.ofMinutes(minutes);
        this.type = TASK;
    }

    public Task(String title, String description, Status status) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = null;
        this.durationMinutes = null;
        this.type = TASK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(description, task.description)
                && Objects.equals(title, task.title) && Objects.equals(status, task.status)
                && Objects.equals(startTime, task.startTime) && Objects.equals(durationMinutes, task.durationMinutes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, status, startTime, durationMinutes);
    }

    @Override
    public String toString() {
        String startTimeString = (startTime == null) ? "null" : startTime.toString();
        Integer durationNumberMinutes = (durationMinutes == null) ? null : (int) durationMinutes.toMinutes();
        return id + "," +
                type + "," +
                title + "," +
                status + "," +
                description + "," +
                startTimeString + "," +
                durationNumberMinutes;
    }

    static public Task fromArrayString(String[] value) {
        if (value.length != SIZE_TASK_CONFIG_CSV
                || !checkedCorrectId(value[SchemeCsv.ID.index])
                || (!"null".equals(value[SchemeCsv.DURATION.index])
                && !checkedCorrectId(value[SchemeCsv.DURATION.index]))) {
            return null;
        }
        LocalDateTime localDateTime = "null".equals(value[SchemeCsv.DATETIME.index]) ? null
                : LocalDateTime.parse(value[SchemeCsv.DATETIME.index]);
        Integer minutes = "null".equals(value[SchemeCsv.DURATION.index]) ? null
                : Integer.parseInt(value[SchemeCsv.DURATION.index]);

        return new Task(
                Integer.parseInt(value[SchemeCsv.ID.index]),
                value[SchemeCsv.NAME.index],
                value[SchemeCsv.DESCRIPTION.index],
                Status.valueOf(value[SchemeCsv.STATUS.index]),
                localDateTime,
                minutes);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null)
            return null;
        if (durationMinutes == null)
            return startTime;
        return startTime.plus(durationMinutes);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDurationMinutes() {
        return durationMinutes;
    }

    public Type getType() {
        return type;
    }

    protected void setStatus(Status status) {
        this.status = status;
    }

    protected void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    protected void setDurationMinutes(Duration durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    protected static boolean checkedCorrectId(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException error) {
            return false;
        }
    }

    @Override
    public int compareTo(Task o) {
        if (o == null) {
            return -1;
        }

        if (o.getStartTime() == null) {
            return -1;
        }

        if (this.getStartTime() == null) {
            return 1;
        }

        return this.getStartTime().compareTo(o.getStartTime());
    }
}
