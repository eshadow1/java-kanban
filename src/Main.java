import controller.AllTaskException;
import controller.AllTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

/*
* Спасибо за замечания! Постарался их все исправить
* */

public class Main {
    private static void fisrtTest() {
        System.out.println("Первый тест: создание, вывод элементов и удаление всех элементов");
        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        AllTaskManager allTaskManager = new AllTaskManager();

        try {
            Task successTestTask1 = allTaskManager.create(testTask1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestTask2 = allTaskManager.create(testTask2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        try {
            Subtask failTestSubtask1 = allTaskManager.create(testSubtask1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic1 = allTaskManager.create(testEpic1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic2 = allTaskManager.create(testEpic2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }

        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        try {
            Subtask successTestSubtask2 = allTaskManager.create(testSubtask2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
        try {
            Subtask successTestSubtask3 = allTaskManager.create(testSubtask3);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(allTaskManager.getAllEpics());
        System.out.println(allTaskManager.getAllSubtasks());
        System.out.println(allTaskManager.getAllTasks());
        System.out.println(allTaskManager.getSubtasksForEpic(testEpic1.getId()));
        try {
            allTaskManager.clearEpics();
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(allTaskManager.getAllEpics());
        allTaskManager.clearTasks();
        allTaskManager.clearSubtasks();
        try {
            allTaskManager.clearEpics();
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(allTaskManager.getAllTasks());
        System.out.println(allTaskManager.getAllSubtasks());
        System.out.println(allTaskManager.getAllEpics());
    }

    private static void secondTest() {
        System.out.println("Второй тест: удаление по ID");
        Epic testEpic1 = new Epic("epic1", "test1");
        AllTaskManager allTaskManager = new AllTaskManager();
        try {
            Epic successTestEpic1 = allTaskManager.create(testEpic1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        try {
            Subtask successTestSubtask1 = allTaskManager.create(testSubtask1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask2 = allTaskManager.create(testSubtask2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic failTestEpicInt = allTaskManager.removeEpic(1000);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic failTestEpic1 = allTaskManager.removeEpic(testEpic1.getId());
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        Subtask successTestSubtask1 = allTaskManager.removeSubtask(testSubtask1.getId());
        Subtask successTestSubtask2 = allTaskManager.removeSubtask(testSubtask2.getId());
        try {
            Epic successTestEpic1 = allTaskManager.removeEpic(testEpic1.getId());
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        Subtask failTestSubtask1 = allTaskManager.removeSubtask(testSubtask1.getId());
    }

    private static void thirdTest() {
        System.out.println("Третий тест: изменение статуса");
        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Epic testEpic1 = new Epic("epic1", "test1");
        AllTaskManager allTaskManager = new AllTaskManager();
        try {
            Task successTestTask1 = allTaskManager.create(testTask1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic1 = allTaskManager.create(testEpic1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        try {
            Subtask successTestSubtask2 = allTaskManager.create(testSubtask2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }

        Subtask testUpdateSubtask2 = new Subtask("subtask2", "test2",
                testSubtask2.getIdParentEpic(), Task.Status.IN_PROGRESS);
        testUpdateSubtask2.setId(testSubtask2.getId());
        Task testUpdateTask2 = new Task("task2", "test2", Task.Status.IN_PROGRESS);
        testUpdateTask2.setId(testTask2.getId());
        Task testUpdateTask1 = new Task("task1", "test1", Task.Status.DONE);
        testUpdateTask1.setId(testTask1.getId());
        try {
            Subtask successTestSubtask2 = allTaskManager.update(testUpdateSubtask2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task failTestUpdateTask2 = allTaskManager.update(testUpdateTask2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestUpdateTask1 = allTaskManager.update(testUpdateTask1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(allTaskManager.getAllEpics());
        System.out.println(allTaskManager.getAllSubtasks());
        System.out.println(allTaskManager.getAllTasks());
    }

    private static void fourthTest() {
        System.out.println("Четвертый тест: получение по ID");
        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        AllTaskManager allTaskManager = new AllTaskManager();
        try {
            Task successTestTask1 = allTaskManager.create(testTask1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestTask2 = allTaskManager.create(testTask2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestEpic1 = allTaskManager.create(testEpic1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestEpic2 = allTaskManager.create(testEpic2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }

        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
        try {
            Subtask successTestSubtask1 = allTaskManager.create(testSubtask1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask2 = allTaskManager.create(testSubtask2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask3 = allTaskManager.create(testSubtask3);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(allTaskManager.getTask(testTask1.getId()));
        System.out.println(allTaskManager.getEpic(testEpic1.getId()));
        System.out.println(allTaskManager.getSubtask(testSubtask1.getId()));
        System.out.println(allTaskManager.getSubtask(testSubtask3.getId()));
    }

    private static void fifthTest() {
        System.out.println("Пятый тест: изменение статуса и обновление подзадачи");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        AllTaskManager allTaskManager = new AllTaskManager();
        try {
            Epic successTestEpic1 = allTaskManager.create(testEpic1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic2 = allTaskManager.create(testEpic2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }

        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
        try {
            Subtask successTestSubtask1 = allTaskManager.create(testSubtask1);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask2 = allTaskManager.create(testSubtask2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask3 = allTaskManager.create(testSubtask3);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }

        Subtask testUpdateSubtask3 = new Subtask("subtask31", "test1",
                testSubtask3.getIdParentEpic(), Task.Status.DONE);
        testUpdateSubtask3.setId(testSubtask3.getId());
        Subtask testUpdateSubtask2 = new Subtask("subtask32", "test1",
                testSubtask2.getIdParentEpic(), Task.Status.IN_PROGRESS);
        testUpdateSubtask2.setId(testSubtask2.getId());
        try {
            allTaskManager.update(testUpdateSubtask3);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }
        try {
            allTaskManager.update(testUpdateSubtask2);
        } catch (AllTaskException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(allTaskManager.getAllEpics());
        System.out.println(allTaskManager.getAllSubtasks());
        System.out.println(allTaskManager.getSubtasksForEpic(testEpic1.getId()));
        System.out.println(allTaskManager.getSubtasksForEpic(testEpic2.getId()));
    }

    public static void main(String[] args) {
        fisrtTest();
        System.out.println();
        secondTest();
        System.out.println();
        thirdTest();
        System.out.println();
        fourthTest();
        System.out.println();
        fifthTest();
    }
}
