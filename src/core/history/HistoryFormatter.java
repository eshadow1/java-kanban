package core.history;

import java.util.ArrayList;
import java.util.List;

public class HistoryFormatter {
    private HistoryFormatter() {
    }

    public static String historyToString(HistoryManager manager) {
        return manager.toString();
    }

    public static List<Integer> historyFromString(String value) {
        if (value == null || value.isEmpty()) return new ArrayList<>();

        String[] history = value.split(",");
        List<Integer> historyTasks = new ArrayList<>();
        for (String s : history) {
            historyTasks.add(Integer.parseInt(s));
        }
        return historyTasks;
    }
}
