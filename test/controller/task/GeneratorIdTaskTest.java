package controller.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorIdTaskTest {
    @Test
    void setStartPosition() {
        int newStartPosition = 1;
        GeneratorIdTask.setStartPosition(newStartPosition);
        assertEquals(newStartPosition, GeneratorIdTask.getId());
    }
}
