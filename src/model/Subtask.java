package model;

import java.util.Objects;

public class Subtask extends Task {
    private final int idParentEpic;

    public Subtask(int id, String title, String description, int idParentEpic) {
        super(id, title, description);
        this.idParentEpic = idParentEpic;
    }

    public Subtask(int id, String title, String description, int idParentEpic, Status status) {
        super(id, title, description, status);
        this.idParentEpic = idParentEpic;
    }

    public int getIdParentEpic() {
        return idParentEpic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return idParentEpic == subtask.idParentEpic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idParentEpic);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", idParentEpic=" + idParentEpic +
                '}';
    }
}
