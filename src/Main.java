import controller.AllTaskManager;
import controller.GeneratorIdTask;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public class Main {
    private final static GeneratorIdTask generatorIdTask = new GeneratorIdTask();

    public static void main(String[] args) {
        Task testTask1 = new Task(generatorIdTask.getId(), "task1", "test1");
        Task testTask2 = new Task(generatorIdTask.getId(), "task2", "test2");
        Epic testEpic1 = new Epic(generatorIdTask.getId(), "epic1", "test1");
        Epic testEpic2 = new Epic(generatorIdTask.getId(), "epic2", "test2");
        Subtask testSubtask1 = new Subtask(generatorIdTask.getId(), "subtask1",
                "test1", testEpic1.getId());
        Subtask testSubtask2 = new Subtask(generatorIdTask.getId(), "subtask2",
                "test2", testEpic1.getId());
        Subtask testSubtask3 = new Subtask(generatorIdTask.getId(), "subtask3",
                "test3", testEpic2.getId());
        AllTaskManager allTaskManager = new AllTaskManager();

        allTaskManager.create(testTask1);
        allTaskManager.create(testTask2);
        allTaskManager.create(testSubtask1);
        allTaskManager.create(testEpic1);
        allTaskManager.create(testSubtask1);
        allTaskManager.create(testSubtask2);
        allTaskManager.create(testEpic2);
        allTaskManager.create(testSubtask3);
        List<Task> tasks = allTaskManager.getAllTasks();
        List<Subtask> subtasks = allTaskManager.getAllSubtasks();
        List<Epic> epics = allTaskManager.getAllEpics();
        System.out.println(epics);
        System.out.println(subtasks);
        System.out.println(tasks);
        System.out.println(allTaskManager.getSubtasksForEpic(testEpic1.getId()));

        allTaskManager.deleteEpic(testEpic1.getId());
        allTaskManager.deleteSubtask(testSubtask1.getId());
        allTaskManager.deleteEpic(testEpic1.getId());
        allTaskManager.create(testEpic1);
        allTaskManager.create(testSubtask2);
        allTaskManager.create(testSubtask1);
        allTaskManager.deleteSubtask(testSubtask1.getId());
        testSubtask2 = new Subtask(testSubtask2.getId(), "subtask2", "test2",
                testSubtask2.getIdParentEpic(), Task.Status.IN_PROGRESS);
        testTask2 = new Task(testTask2.getId(), "task2", "test2",
                Task.Status.IN_PROGRESS);
        testTask1 = new Task(testTask1.getId(), "task1", "test1", Task.Status.DONE);
        allTaskManager.update(testSubtask2);
        allTaskManager.update(testTask2);
        allTaskManager.update(testTask1);
        tasks = allTaskManager.getAllTasks();
        subtasks = allTaskManager.getAllSubtasks();
        epics = allTaskManager.getAllEpics();
        System.out.println(epics);
        System.out.println(subtasks);
        System.out.println(tasks);

        System.out.println(allTaskManager.getTask(0));
        System.out.println(allTaskManager.getEpic(2));
        System.out.println(allTaskManager.getSubtask(4));
        System.out.println(allTaskManager.getSubtask(5));

        testSubtask2 = new Subtask(testSubtask2.getId(), "test21", "gag",
                testSubtask2.getIdParentEpic(), Task.Status.DONE);
        allTaskManager.update(testSubtask2);
        epics = allTaskManager.getAllEpics();
        System.out.println(epics);
        allTaskManager.clearTasks();
        allTaskManager.clearSubtasks();
        epics = allTaskManager.getAllEpics();
        System.out.println(epics);
        allTaskManager.clearEpics();
        System.out.println(allTaskManager.getAllTasks());
        System.out.println(allTaskManager.getAllSubtasks());
        System.out.println(allTaskManager.getAllEpics());
    }
}
