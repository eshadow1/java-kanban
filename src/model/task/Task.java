package model.task;

import java.util.Objects;

import static model.task.Type.TASK;

public class Task {
    protected Integer id;
    protected final String title;
    protected final String description;
    protected Status status;
    private static final int SIZE_TASK_CONFIG_CSV = 5;

    protected Task(Integer id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(String title, String description) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
    }

    public Task(String title, String description, Status status) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(description, task.description)
                && Objects.equals(title, task.title) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, status);
    }

    @Override
    public String toString() {
        return id + "," +
                TASK + "," +
                title + "," +
                status + "," +
                description + ",";
    }

    static public Task fromArrayString(String[] value) {
        if (value.length != SIZE_TASK_CONFIG_CSV ||
                !checkedCorrectId(value[SchemeCsv.ID.index])) {
            return null;
        }

        return new Task(
                Integer.parseInt(value[SchemeCsv.ID.index]),
                value[SchemeCsv.NAME.index],
                value[SchemeCsv.DESCRIPTION.index],
                Status.valueOf(value[SchemeCsv.STATUS.index])
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

    public void setId(Integer id) {
        this.id = id;
    }

    protected void setStatus(Status status) {
        this.status = status;
    }

    protected static boolean checkedCorrectId(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException error) {
            return false;
        }
    }
}
