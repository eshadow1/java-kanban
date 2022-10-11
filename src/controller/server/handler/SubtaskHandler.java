package controller.server.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.handler.PairAnswer;
import model.task.Subtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SubtaskHandler implements HttpHandler {
    private final TaskManager taskManager;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson;

    public SubtaskHandler(TaskManager taskManager) {

        this.taskManager = taskManager;
        gson = new Gson();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        String path = httpExchange.getRequestURI().getPath();
        String[] paths = path.split("/");
        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);


        String response = "";
        Integer statusCode = 200;

        if ("GET".equals(method) && path.endsWith("epic")) {
            if (query == null || query.isEmpty()) {
                statusCode = 404;
            } else {
                var allParameters = ParserQuery.queryToMap(query);
                if (allParameters.containsKey("id")) {
                    response = gson.toJson(taskManager.getSubtasksForEpic(Integer.parseInt(allParameters.get("id"))));
                }
            }
        } else {
            PairAnswer answerTask = processSubtask(method, body, query);
            response = answerTask.getResponse();
            statusCode = answerTask.getStatusCode();
        }

        httpExchange.sendResponseHeaders(statusCode, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private PairAnswer processSubtask(String method, String body, String query) {
        String response = "";
        int statusCode = 200;
        switch (method) {
            case "POST":
                Subtask newSubtask = gson.fromJson(body, Subtask.class);
                try {
                    taskManager.create(newSubtask);
                    response = "Подзадача добавлена!";
                } catch (TaskManagerException error) {
                    try {
                        taskManager.update(newSubtask);
                        response = "Подзадача обновлена!";
                    } catch (TaskManagerException errorUpdate) {
                        statusCode = 400;
                    }
                }
                break;
            case "GET":
                if (query == null || query.isEmpty()) {
                    response = gson.toJson(taskManager.getAllSubtasks());
                } else {
                    var allParameters = ParserQuery.queryToMap(query);
                    if (allParameters.containsKey("id")) {
                        Subtask subtask = taskManager.getSubtask(Integer.parseInt(allParameters.get("id")));
                        if (subtask == null) {
                            statusCode = 404;
                            break;
                        }
                        response = gson.toJson(subtask);
                    }
                }
                break;
            case "DELETE":
                if (query == null || query.isEmpty()) {
                    taskManager.clearSubtasks();
                    response = "Все подзадачи удалены!";
                } else {
                    var allParameters = ParserQuery.queryToMap(query);
                    if (allParameters.containsKey("id")) {
                        int idSubtask = Integer.parseInt(allParameters.get("id"));
                        Subtask subtask = taskManager.removeSubtask(idSubtask);
                        if (subtask == null) {
                            statusCode = 204;
                            response = "Подзадача c id " + idSubtask + " не найдена";
                            break;
                        }
                        response = gson.toJson(subtask);
                    }
                }
                break;
            default:
                response = "Вы использовали какой-то другой метод!";
        }
        return new PairAnswer(statusCode, response);
    }

}
