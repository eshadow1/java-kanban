package logger;

public class DisplayInfoLogger {
    public static void logNotFoundTask(Integer idTask) {
        System.out.println("Задача c id " + idTask + " не найдена");
    }

    public static void logNotFoundEpicForSubtask(Integer idSubtask, Integer idParentEpic) {
        System.out.println("Для подзадачи c id " + idSubtask + " не найден эпик c id "
                + idParentEpic);
    }
}
