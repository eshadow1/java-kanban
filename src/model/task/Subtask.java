package model.task;

import java.time.LocalDateTime;
import java.util.Objects;

import static model.task.Type.SUBTASK;

public class Subtask extends Task {
    private static final int SIZE_SUBTASK_CONFIG_CSV = 8;
    private final Integer idParentEpic;

    public Subtask(String title, String description, LocalDateTime startPeriod, Integer durationMinutes, Integer idParentEpic) {
        super(title, description, startPeriod, durationMinutes);
        this.idParentEpic = idParentEpic;
        this.type = SUBTASK;
    }

    public Subtask(String title, String description, Integer idParentEpic) {
        super(title, description);
        this.idParentEpic = idParentEpic;
        this.type = SUBTASK;
    }

    public Subtask(String title, String description, Status status, LocalDateTime startPeriod,
                   Integer durationMinutes, Integer idParentEpic) {
        super(title, description, status, startPeriod, durationMinutes);
        this.idParentEpic = idParentEpic;
        this.type = SUBTASK;
    }

    public Subtask(String title, String description, Status status, Integer idParentEpic) {
        super(title, description, status);
        this.idParentEpic = idParentEpic;
        this.type = SUBTASK;
    }

    private Subtask(Integer id, String title, String description, Status status,
                    LocalDateTime startPeriod, Integer durationMinutes, Integer idParentEpic) {
        super(id, title, description, status, startPeriod, durationMinutes);
        this.idParentEpic = idParentEpic;
        this.type = SUBTASK;
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
        return super.toString() + "," + idParentEpic;
    }

    static public Subtask fromArrayString(String[] value) {
        if (value.length != SIZE_SUBTASK_CONFIG_CSV
                || !checkedCorrectId(value[SchemeCsv.ID.index])
                || (!"null".equals(value[SchemeCsv.DURATION.index])
                && !checkedCorrectId(value[SchemeCsv.DURATION.index]))
                || !checkedCorrectId(value[SchemeCsv.EPIC.index])) {
            return null;
        }

        LocalDateTime localDateTime = "null".equals(value[SchemeCsv.DATETIME.index]) ? null
                : LocalDateTime.parse(value[SchemeCsv.DATETIME.index]);
        Integer minutes = "null".equals(value[SchemeCsv.DURATION.index]) ? null
                : Integer.parseInt(value[SchemeCsv.DURATION.index]);

        return new Subtask(
                Integer.parseInt(value[SchemeCsv.ID.index]),
                value[SchemeCsv.NAME.index],
                value[SchemeCsv.DESCRIPTION.index],
                Status.valueOf(value[SchemeCsv.STATUS.index]),
                localDateTime,
                minutes,
                Integer.parseInt(value[SchemeCsv.EPIC.index])
        );
    }
}
