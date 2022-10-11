package controller.server.handler.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.task.Status;
import model.task.Subtask;

import java.io.IOException;
import java.time.LocalDateTime;

public class SubtaskAdapter extends TypeAdapter<Subtask> {
    @Override
    public void write(JsonWriter jsonWriter, Subtask subtask) throws IOException {
        String startTimeString = (subtask.getStartTime() == null) ? null : subtask.getStartTime().toString();
        Integer durationNumberMinutes = (subtask.getDurationMinutes() == null) ? null : (int) subtask.getDurationMinutes().toMinutes();

        jsonWriter.beginObject();
        jsonWriter.name("id").value(subtask.getId());
        jsonWriter.name("title").value(subtask.getTitle());
        jsonWriter.name("description").value(subtask.getDescription());
        jsonWriter.name("status").value(subtask.getStatus().toString());
        jsonWriter.name("type").value(subtask.getType().toString());
        jsonWriter.name("startTime").value(startTimeString);
        jsonWriter.name("durationMinutes").value(durationNumberMinutes);
        jsonWriter.name("idParentEpic").value(subtask.getIdParentEpic());
        jsonWriter.endObject();
    }

    @Override
    public Subtask read(final JsonReader jsonReader) throws IOException {
        Integer id = null;
        String title = null;
        String description = null;
        Status status = null;
        LocalDateTime startTime = null;
        Integer durationMinutes = null;
        Integer idParentEpic = null;

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
            } else if (name.equals("idParentEpic")) {
                idParentEpic = jsonReader.nextInt();
            }
        }
        jsonReader.endObject();

        Subtask subtask;
        if (status == null) {
            subtask = new Subtask(title, description, idParentEpic);
        } else {
            subtask = new Subtask(title, description, status, startTime, durationMinutes, idParentEpic);
        }

        if (id != null) {
            subtask.setId(id);
        }
        return subtask;
    }
}
