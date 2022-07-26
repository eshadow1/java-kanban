package logger;

public class DisplayInfoLogger {
    public static void logNotFoundTask(Integer idTask) {
        System.out.println("Задача c id " + idTask + " не найдена");
    }

    public static void logNotFoundEpicForSubtask(Integer idSubtask, Integer idParentEpic) {
        System.out.println("Для подзадачи c id " + idSubtask + " не найден эпик c id "
                + idParentEpic);
    }

    public static void logNotEmptyEpic(Integer idEpic) {
        System.out.println("Эпик c id " + idEpic + " не пуст. Сначала удалите все подзадачи");
    }

    public static void logNotClearEpics() {
        System.out.println("Эпики содержат подзадачи. Удалите все подзадачи перед очисткой эпиков");
    }

    public static void logNotNewTask() {
        System.out.println("На вход подана не новая задача");
    }

    public static void logNotIdTask() {
        System.out.println("У полученной задачи нет ID");
    }
}
