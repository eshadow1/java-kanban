package controller.task;

public class GeneratorIdTask {
    private static Integer id = 0;

    public static int getId() {
        return id++;
    }
}
