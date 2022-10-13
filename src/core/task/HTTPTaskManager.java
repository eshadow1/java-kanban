package core.task;

import http.client.kv.KVTaskClient;
import core.history.HistoryFormatter;
import model.task.*;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;

public class HTTPTaskManager extends FileBackedTasksManager {
    KVTaskClient kvTaskClient;
    private final String url;
    private String key;
    public HTTPTaskManager(URI uriServer) {
        super(Path.of(""));
        url = uriServer.toString();
        kvTaskClient = new KVTaskClient(url);
    }

    @Override
    protected void save() {
        kvTaskClient.put(key, createDataSave());
    }

    public void load(String key) {
        this.key = key;
        String data = kvTaskClient.load(this.key);

        String[] values = data.split("\n");
        int i =0;
        int maxIdTask = 0;
        for(; i<values.length; ++i) {
            if(values[i].isEmpty()) {
                break;
            }

            Task task = getFromStringAndAddIfNotNull(values[i]);
            if (task != null && maxIdTask < task.getId()) {
                maxIdTask = task.getId() + 1;
            }
        }

        GeneratorIdTask.setStartPosition(maxIdTask);

        if(i<values.length) {
            List<Integer> tempHistory = HistoryFormatter.historyFromString(values[i]);
            for (var id : tempHistory) {
                if (getEpic(id) != null) {
                    continue;
                }
                if (getSubtask(id) != null) {
                    continue;
                }
                getTask(id);
            }
        }
    }
}
