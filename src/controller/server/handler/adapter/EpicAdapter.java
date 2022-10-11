package controller.server.handler.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import model.task.Epic;

public class EpicAdapter extends TypeAdapter<Epic> {
    @Override
    public void write(JsonWriter jsonWriter, Epic epic) throws IOException {
        String startTimeString = (epic.getStartTime() == null) ? null : epic.getStartTime().toString();
        Integer durationNumberMinutes = (epic.getDurationMinutes() == null) ? null : (int) epic.getDurationMinutes().toMinutes();
        jsonWriter.beginObject();
        jsonWriter.name("id").value(epic.getId());
        jsonWriter.name("title").value(epic.getTitle());
        jsonWriter.name("description").value(epic.getDescription());
        jsonWriter.name("status").value(epic.getStatus().toString());
        jsonWriter.name("type").value(epic.getType().toString());
        jsonWriter.name("startTime").value(startTimeString);
        jsonWriter.name("durationMinutes").value(durationNumberMinutes);
        jsonWriter.name("subtasks").value(epic.getSubtasks().toString());
        jsonWriter.endObject();
    }

    @Override
    public Epic read(final JsonReader jsonReader) throws IOException {
        Integer id = null;
        String title = null;
        String description = null;

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("id")) {
                id = jsonReader.nextInt();
            } else if (name.equals("title")) {
                title = jsonReader.nextString();
            } else if (name.equals("description")) {
                description = jsonReader.nextString();
            }
        }
        jsonReader.endObject();

        Epic epic = new Epic(title, description);
        if(id!= null) {
            epic.setId(id);
        }
        return epic;
    }
}
