package model.task;

import java.util.Objects;

import static model.task.Type.SUBTASK;

public class Subtask extends Task {
    private final Integer idParentEpic;

    public Subtask(String title, String description, Integer idParentEpic) {
        super(title, description);
        this.idParentEpic = idParentEpic;
    }

    public Subtask(String title, String description, Integer idParentEpic, Status status) {
        super(title, description, status);
        this.idParentEpic = idParentEpic;
    }

    private Subtask(Integer id, String title, String description, Integer idParentEpic, Status status) {
        super(id, title, description, status);
        this.idParentEpic = idParentEpic;
    }

    public Integer getIdParentEpic() {
        return idParentEpic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return idParentEpic.equals(subtask.idParentEpic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idParentEpic);
    }

    @Override
    public String toString() {
        return id + "," +
                SUBTASK + "," +
                title + "," +
                status + "," +
                description + "," +
                idParentEpic;
    }

    static public Subtask fromArrayString(String[] value) {
        return new Subtask(Integer.parseInt(value[SchemeCsv.ID.ordinal()]), value[SchemeCsv.NAME.ordinal()],
                value[SchemeCsv.DESCRIPTION.ordinal()], Integer.parseInt(value[SchemeCsv.EPIC.ordinal()]),
                Status.valueOf(value[SchemeCsv.STATUS.ordinal()]));
    }
}
