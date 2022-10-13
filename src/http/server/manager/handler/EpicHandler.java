package http.server.manager.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http.server.manager.handler.adapter.EpicAdapter;
import core.task.TaskManager;
import core.task.TaskManagerException;
import model.handler.PairAnswer;
import model.task.Epic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class EpicHandler implements HttpHandler {
    private final TaskManager taskManager;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson;

    public EpicHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        gson = new GsonBuilder()
                .registerTypeAdapter(Epic.class, new EpicAdapter())
                .create();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

        PairAnswer answerTask = processEpic(method, body, query);
        String response = answerTask.getResponse();
        Integer statusCode = answerTask.getStatusCode();

        httpExchange.sendResponseHeaders(statusCode, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
    private PairAnswer processEpic(String method, String body, String query) {
        String response = "";
        int statusCode = 200;
        switch (method) {
            case "POST":
                Epic newEpic = gson.fromJson(body, Epic.class);
                try {
                    if (taskManager.create(newEpic) != null || taskManager.create(newEpic).getId() == null) {
                        statusCode = 400;
                        response = "Эпик не добавлен!";
                    } else {
                        response = "Эпик добавлен!";
                    }
                } catch (TaskManagerException error) {
                    try {
                        taskManager.update(newEpic);
                        response = "Эпик обновлен!";
                    } catch (TaskManagerException errorUpdate) {
                        statusCode = 400;
                        response = "Эпик не добавлен!";
                    }
                }
                break;
            case "GET":
                if (query == null || query.isEmpty()) {
                    response = gson.toJson(taskManager.getAllEpics());
                } else {
                    var allParameters = ParserQuery.queryToMap(query);
                    if (allParameters.containsKey("id")) {
                        Epic epic = taskManager.getEpic(Integer.parseInt(allParameters.get("id")));
                        if (epic == null) {
                            statusCode = 404;
                            break;
                        }
                        response = gson.toJson(epic);
                    }
                }
                break;
            case "DELETE":
                if (query == null || query.isEmpty()) {
                    taskManager.clearEpics();
                    response = "Все эпики удалены!";
                } else {
                    var allParameters = ParserQuery.queryToMap(query);
                    if (allParameters.containsKey("id")) {
                        int idTask = Integer.parseInt(allParameters.get("id"));
                        Epic epic = taskManager.removeEpic(idTask);
                        if (epic == null) {
                            statusCode = 204;
                            response = "Эпик c id " + idTask + " не найден";
                            break;
                        }
                        response = gson.toJson(epic);
                    }
                }
                break;
            default:
                statusCode = 400;
                response = "Вы использовали какой-то другой метод!";
        }
        return new PairAnswer(statusCode, response);
    }
}
