package controller.server.handler.adapter;

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
        String startTimeString = (task.getStartTime() == null) ? null : task.getStartTime().toString();
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
            if (name.equals("id")) {
                id = jsonReader.nextInt();
            } else if (name.equals("title")) {
                title = jsonReader.nextString();
            } else if (name.equals("description")) {
                description = jsonReader.nextString();
            } else if (name.equals("status")) {
                status = Status.valueOf(jsonReader.nextString());
            } else if (name.equals("startTime")) {
                startTime = LocalDateTime.parse(jsonReader.nextString());
            } else if (name.equals("durationMinutes")) {
                durationMinutes = jsonReader.nextInt();
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