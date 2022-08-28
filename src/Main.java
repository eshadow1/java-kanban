import test.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(Test1.createOutputAndRemoveTasks());
        System.out.println();

        System.out.println(Test2.removeTasksById());
        System.out.println();

        System.out.println(Test3.changeStatusTasks());
        System.out.println();

        System.out.println(Test4.getTaskByID());
        System.out.println();

        System.out.println(Test5.changeStatusAndUpdateSubtasks());
        System.out.println();

        System.out.println(Test6.getHistory());
        System.out.println();

        System.out.println(Test7.getHistoryWithRemoveTask());
        System.out.println();
    }
}
