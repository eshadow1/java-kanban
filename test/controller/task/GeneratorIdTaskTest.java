package controller.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorIdTaskTest {
    @Test
    void setStartPosition() {
        int start = 1;
        GeneratorIdTask.setStartPosition(start);
        assertEquals(start, GeneratorIdTask.getId());
    }
}