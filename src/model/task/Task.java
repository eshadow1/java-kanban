package model.task;

import java.util.Objects;

import static model.task.Type.TASK;

public class Task {

    protected Integer id;
    protected final String title;
    protected final String description;
    protected Status status;

    protected Task(Integer id, String title, String description, Status status) {
        this.id =  id;
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
        return new Task(Integer.parseInt(value[SchemeCsv.ID.ordinal()]), value[SchemeCsv.NAME.ordinal()],
                value[SchemeCsv.DESCRIPTION.ordinal()], Status.valueOf(value[SchemeCsv.STATUS.ordinal()]));
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
}
