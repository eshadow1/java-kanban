package view;

public class DisplayInfo {
    public void logNotFoundTask(int idTask) {
        System.out.println("Задача c id " + idTask + " не найдена");
    }

    public void logNotFoundEpicForSubtask(int idSubtask, int idParentEpic) {
        System.out.println("Для подзадачи c id " + idSubtask + " не найден эпик c id "
                + idParentEpic);
    }

    public void logNotEmptyEpic(int idEpic) {
        System.out.println("Эпик c id " + idEpic + " не пуст. Сначала удалите все подзадачи");
    }
}
