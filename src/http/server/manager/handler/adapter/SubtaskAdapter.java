package http.server.manager.handler.adapter;

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
        String startTimeString = (subtask.getStartPeriod() == null) ? null : subtask.getStartPeriod().toString();
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
                case "idParentEpic":
                    idParentEpic = jsonReader.nextInt();
                    break;
                default:
                    break;
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
