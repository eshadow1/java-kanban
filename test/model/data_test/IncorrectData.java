package model.data_test;

public class IncorrectData {
    public static final String[] incorrectArrayStringInitTask = new String[]{"35", "TASK", "task1", "NEW"};
    public static final String[] incorrectIndexStringInitTask = new String[]{"a35", "TASK", "task1", "NEW", "test1", "null", "null"};
    public static final String[] incorrectArrayStringInitEpic = new String[]{"0", "EPIC", "epic1", "NEW"};
    public static final String[] incorrectIndexStringInitEpic = new String[]{"a0", "EPIC", "epic1", "NEW", "test1", "null", "null"};
    public static final String[] incorrectArrayStringSubtask = new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1"};
    public static final String[] incorrectIndexStringInitSubtask = new String[]{"a2", "SUBTASK", "subtask1", "NEW", "test1", "null", "null", "0"};
    public static final String[] incorrectIdParentStringISubtask = new String[]{"2", "SUBTASK", "subtask1", "NEW", "test1", "null", "null", "a0"};

    private IncorrectData() {
    }
}
