package controller.history;

import java.util.ArrayList;
import java.util.List;

public class HistoryFormatter {
    private HistoryFormatter(){
    }

    static public String historyToString(HistoryManager manager) {
        StringBuilder historyString = new StringBuilder();
        for (var task : manager.getHistory()) {
            historyString.append(task.getId()).append(",");
        }
        historyString.setLength(historyString.length() != 0 ? historyString.length() - 1 : 0);
        return historyString.toString();
    }

    static public List<Integer> historyFromString(String value) {
        if (value == null || value.isEmpty()) return new ArrayList<>();

        String[] history = value.split(",");
        List<Integer> historyTasks = new ArrayList<>();
        for (String s : history) {
            historyTasks.add(Integer.parseInt(s));
        }
        return historyTasks;
    }
}
