package controller.history;

import controller.Manager;
import model.data_test.CorrectData;
import model.task.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryFormatterTest {
    @Test
    void getHistoryToString() {
        final HistoryManager historyManager = Manager.getDefaultHistory();
        Task temp = Task.fromArrayString(CorrectData.correctInitTask);
        historyManager.add(temp);
        assertEquals("35", HistoryFormatter.historyToString(historyManager));
    }

    @Test
    void getHistoryFromString() {
        final String test = "3,0,9,6,11,15";
        List<Integer> rightHistoryTasks = List.of(3, 0, 9, 6, 11, 15);
        assertEquals(rightHistoryTasks, HistoryFormatter.historyFromString(test));
    }

    @Test
    void getHistoryFromNullString() {
        List<Integer> tempEmptyList = new ArrayList<>();
        assertEquals(tempEmptyList, HistoryFormatter.historyFromString(null));
    }
}