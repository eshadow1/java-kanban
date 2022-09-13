package model.task;

public enum SchemeCsv {
    ID(0),
    TYPE(1),
    NAME(2),
    STATUS(3),
    DESCRIPTION(4),
    EPIC(5);

    final public int index;

    SchemeCsv(int index) {
        this.index = index;
    }
}
