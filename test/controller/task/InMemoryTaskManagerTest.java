package controller.task;

import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest {
    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
        GeneratorIdTask.setStartPosition(GeneratorIdTask.START_GENERATOR);

        initAllTasks();
    }
}