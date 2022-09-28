package controller.task;

public class GeneratorIdTask {
    public static final int START_GENERATOR = 0;
    private static Integer id = 0;

    public static int getId() {
        return id++;
    }

    public static void setStartPosition(int startPosition) {
        id = startPosition;
    }
}
