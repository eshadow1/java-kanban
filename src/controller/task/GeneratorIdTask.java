package controller.task;

public class GeneratorIdTask {
    private static Integer id = 0;

    public static int getId() {
        return id++;
    }

    public static void setStartPosition(int startPosition) {
        id = startPosition;
    }
}
