package model.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static model.task.Type.TASK;

public class Task implements Comparable<Task>{
    public static final int DEFAULT_DURATION_MINUTES = -1;
    public static final Duration DEFAULT_DURATION = Duration.ofMinutes(DEFAULT_DURATION_MINUTES);
    public static final LocalDateTime DEFAULT_START_TIME = null;

    protected Integer id;
    protected final String title;
    protected final String description;
    protected Status status;
    protected LocalDateTime startTime;
    protected Type type;
    protected Duration durationMinutes;
    private static final int SIZE_TASK_CONFIG_CSV = 7;

    protected Task(Integer id, String title, String description, Status status, LocalDateTime startTime, int minutes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.durationMinutes = Duration.ofMinutes(minutes);
        this.startTime = startTime;
        this.type = TASK;
    }

    public Task(String title, String description, LocalDateTime startTime, int minutes) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
        this.startTime = startTime;
        this.durationMinutes = Duration.ofMinutes(minutes);
        this.type = TASK;
    }

    public Task(String title, String description) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
        this.startTime = DEFAULT_START_TIME;
        this.durationMinutes = DEFAULT_DURATION;
        this.type = TASK;
    }

    public Task(String title, String description, Status status, LocalDateTime startTime, int minutes) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.durationMinutes = Duration.ofMinutes(minutes);
        this.type = TASK;
    }

    public Task(String title, String description, Status status) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = DEFAULT_START_TIME;
        this.durationMinutes = DEFAULT_DURATION;
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
        String startTimeString = (startTime == DEFAULT_START_TIME) ? "null" : startTime.toString();
        return id + "," +
                type + "," +
                title + "," +
                status + "," +
                description + "," +
                startTimeString + "," +
                durationMinutes.toMinutes();
    }

    static public Task fromArrayString(String[] value) {
        if (value.length != SIZE_TASK_CONFIG_CSV ||
                !checkedCorrectId(value[SchemeCsv.ID.index]) ||
                !checkedCorrectId(value[SchemeCsv.DURATION.index])) {
            return null;
        }
        LocalDateTime localDateTime = "null".equals(value[SchemeCsv.DATETIME.index]) ? null :  LocalDateTime.parse(value[SchemeCsv.DATETIME.index]);
        return new Task(
                Integer.parseInt(value[SchemeCsv.ID.index]),
                value[SchemeCsv.NAME.index],
                value[SchemeCsv.DESCRIPTION.index],
                Status.valueOf(value[SchemeCsv.STATUS.index]),
                localDateTime,
                Integer.parseInt(value[SchemeCsv.DURATION.index])
        );
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
        if(startTime == null)
            return null;
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
