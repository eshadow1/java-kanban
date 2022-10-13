package http.server.manager.handler.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.task.Status;
import model.task.Task;

import java.io.IOException;
import java.time.LocalDateTime;

public class TaskAdapter extends TypeAdapter<Task> {
    @Override
    public void write(JsonWriter jsonWriter, Task task) throws IOException {
        String startTimeString = (task.getStartPeriod() == null) ? null : task.getStartPeriod().toString();
        Integer durationNumberMinutes = (task.getDurationMinutes() == null) ? null : (int) task.getDurationMinutes().toMinutes();

        jsonWriter.beginObject();
        jsonWriter.name("id").value(task.getId());
        jsonWriter.name("title").value(task.getTitle());
        jsonWriter.name("description").value(task.getDescription());
        jsonWriter.name("status").value(task.getStatus().toString());
        jsonWriter.name("type").value(task.getType().toString());
        jsonWriter.name("startTime").value(startTimeString);
        jsonWriter.name("durationMinutes").value(durationNumberMinutes);
        jsonWriter.endObject();
    }

    @Override
    public Task read(final JsonReader jsonReader) throws IOException {
        Integer id = null;
        String title = null;
        String description = null;
        Status status = null;
        LocalDateTime startTime = null;
        Integer durationMinutes = null;

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            switch (name) {
                case "id":
                    id = jsonReader.nextInt();
                    break;
                case "title":
                    title = jsonReader.nextString();
                    break;
                case "description":
                    description = jsonReader.nextString();
                    break;
                case "status":
                    status = Status.valueOf(jsonReader.nextString());
                    break;
                case "startTime":
                    startTime = LocalDateTime.parse(jsonReader.nextString());
                    break;
                case "durationMinutes":
                    durationMinutes = jsonReader.nextInt();
                    break;
                default:
                    break;
            }
        }
        jsonReader.endObject();
        Task task;
        if (status == null) {
            task = new Task(title, description);
        } else {
            task = new Task(title, description, status, startTime, durationMinutes);
        }

        if(id!= null) {
            task.setId(id);
        }
        return task;
    }
}