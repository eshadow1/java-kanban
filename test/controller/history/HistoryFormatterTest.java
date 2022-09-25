package controller.history;

import controller.Manager;
import model.task.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryFormatterTest {

    @Test
    void getHistoryToString() {
        final HistoryManager historyManager = Manager.getDefaultHistory();
        Task temp = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW", "test1"});
        historyManager.add(temp);
        assertEquals("35", HistoryFormatter.historyToString(historyManager));
    }

    @Test
    void getHistoryFromString() {
        final String test = "3,0,9,6,11,15";
        List<Integer> rightHistoryTasks = List.of(3, 0, 9, 6, 11, 15);
        assertEquals(rightHistoryTasks, HistoryFormatter.historyFromString(test));

        List<Integer> tempEmptyList = new ArrayList<>();
        assertEquals(tempEmptyList, HistoryFormatter.historyFromString(null));
    }
}