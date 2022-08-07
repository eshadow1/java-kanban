import controller.Manager;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;

import java.util.List;

public class Main {


    private static void fisrtTest() {
        System.out.println("Первый тест: создание, вывод элементов и удаление всех элементов");
        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        TaskManager taskManager = Manager.getDefault();

        try {
            Task successTestTask1 = taskManager.create(testTask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestTask2 = taskManager.create(testTask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        try {
            Subtask failTestSubtask1 = taskManager.create(testSubtask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic2 = taskManager.create(testEpic2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
        try {
            Subtask successTestSubtask3 = taskManager.create(testSubtask3);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getSubtasksForEpic(testEpic1.getId()));
        try {
            taskManager.clearEpics();
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(taskManager.getAllEpics());
        taskManager.clearTasks();
        taskManager.clearSubtasks();
        try {
            taskManager.clearEpics();
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllEpics());
    }

    private static void secondTest() {
        System.out.println("Второй тест: удаление по ID");
        Epic testEpic1 = new Epic("epic1", "test1");
        TaskManager taskManager = Manager.getDefault();
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        try {
            Subtask successTestSubtask1 = taskManager.create(testSubtask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic failTestEpicInt = taskManager.removeEpic(1000);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic failTestEpic1 = taskManager.removeEpic(testEpic1.getId());
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        Subtask successTestSubtask1 = taskManager.removeSubtask(testSubtask1.getId());
        Subtask successTestSubtask2 = taskManager.removeSubtask(testSubtask2.getId());
        try {
            Epic successTestEpic1 = taskManager.removeEpic(testEpic1.getId());
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        Subtask failTestSubtask1 = taskManager.removeSubtask(testSubtask1.getId());
    }

    private static void thirdTest() {
        System.out.println("Третий тест: изменение статуса");
        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Epic testEpic1 = new Epic("epic1", "test1");
        TaskManager taskManager = Manager.getDefault();
        try {
            Task successTestTask1 = taskManager.create(testTask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        Subtask testUpdateSubtask2 = new Subtask("subtask2", "test2",
                testSubtask2.getIdParentEpic(), Status.IN_PROGRESS);
        testUpdateSubtask2.setId(testSubtask2.getId());
        Task testUpdateTask2 = new Task("task2", "test2", Status.IN_PROGRESS);
        testUpdateTask2.setId(testTask2.getId());
        Task testUpdateTask1 = new Task("task1", "test1", Status.DONE);
        testUpdateTask1.setId(testTask1.getId());
        try {
            Subtask successTestSubtask2 = taskManager.update(testUpdateSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task failTestUpdateTask2 = taskManager.update(testUpdateTask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestUpdateTask1 = taskManager.update(testUpdateTask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllTasks());
    }

    private static void fourthTest() {
        System.out.println("Четвертый тест: получение по ID");
        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        TaskManager taskManager = Manager.getDefault();
        try {
            Task successTestTask1 = taskManager.create(testTask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestTask2 = taskManager.create(testTask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestEpic1 = taskManager.create(testEpic1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestEpic2 = taskManager.create(testEpic2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
        try {
            Subtask successTestSubtask1 = taskManager.create(testSubtask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask3 = taskManager.create(testSubtask3);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(taskManager.getTask(testTask1.getId()));
        System.out.println(taskManager.getEpic(testEpic1.getId()));
        System.out.println(taskManager.getSubtask(testSubtask1.getId()));
        System.out.println(taskManager.getSubtask(testSubtask3.getId()));
    }

    private static void fifthTest() {
        System.out.println("Пятый тест: изменение статуса и обновление подзадачи");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        TaskManager taskManager = Manager.getDefault();
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic2 = taskManager.create(testEpic2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
        try {
            Subtask successTestSubtask1 = taskManager.create(testSubtask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask3 = taskManager.create(testSubtask3);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        Subtask testUpdateSubtask3 = new Subtask("subtask31", "test1",
                testSubtask3.getIdParentEpic(), Status.DONE);
        testUpdateSubtask3.setId(testSubtask3.getId());
        Subtask testUpdateSubtask2 = new Subtask("subtask32", "test1",
                testSubtask2.getIdParentEpic(), Status.IN_PROGRESS);
        testUpdateSubtask2.setId(testSubtask2.getId());
        try {
            taskManager.update(testUpdateSubtask3);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            taskManager.update(testUpdateSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getSubtasksForEpic(testEpic1.getId()));
        System.out.println(taskManager.getSubtasksForEpic(testEpic2.getId()));
    }

    private static void sixthTest() {
        System.out.println("Шестой тест: просмотр истории запросов задач");
        Epic testEpic1 = new Epic("epic1", "test1");
        Epic testEpic2 = new Epic("epic2", "test2");
        TaskManager taskManager = Manager.getDefault();
        try {
            Epic successTestEpic1 = taskManager.create(testEpic1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Epic successTestEpic2 = taskManager.create(testEpic2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        Subtask testSubtask1 = new Subtask("subtask1", "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask("subtask2", "test2", testEpic1.getId());
        Subtask testSubtask3 = new Subtask("subtask3", "test3", testEpic2.getId());
        try {
            Subtask successTestSubtask1 = taskManager.create(testSubtask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask2 = taskManager.create(testSubtask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Subtask successTestSubtask3 = taskManager.create(testSubtask3);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        Task testTask1 = new Task("task1", "test1");
        Task testTask2 = new Task("task2", "test2");
        Task testTask3 = new Task("task3", "test3");

        try {
            Task successTestTask1 = taskManager.create(testTask1);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestTask2 = taskManager.create(testTask2);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Task successTestTask3 = taskManager.create(testTask3);
        } catch (TaskManagerException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(taskManager.getTask(testTask1.getId()));
        List<Task> history = taskManager.getHistory();
        System.out.println(history);
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getTask(testTask2.getId()));
        history = taskManager.getHistory();
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getTask(testTask1.getId()));
        history = taskManager.getHistory();
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getTask(testTask3.getId()));
        history = taskManager.getHistory();
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getEpic(testEpic2.getId()));
        history = taskManager.getHistory();
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getEpic(testEpic1.getId()));
        history = taskManager.getHistory();
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask2.getId()));
        history = taskManager.getHistory();
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask1.getId()));
        history = taskManager.getHistory();
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask2.getId()));
        history = taskManager.getHistory();
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask3.getId()));
        history = taskManager.getHistory();
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask1.getId()));
        history = taskManager.getHistory();
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();

        System.out.println(taskManager.getSubtask(testSubtask1.getId()));
        history = taskManager.getHistory();
        System.out.println(history);
        for(Task task: history) System.out.print(task.getId() + " ");
        System.out.println();
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
        System.out.println();

        sixthTest();
        System.out.println();
    }
}
