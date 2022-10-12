package controller.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.server.handler.adapter.TaskAdapter;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.handler.PairAnswer;
import model.task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class TaskHandler implements HttpHandler {
    private final TaskManager taskManager;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson;

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        gson = new GsonBuilder()
                .registerTypeAdapter(Task.class, new TaskAdapter())
                .create();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

        PairAnswer answerTask = processTask(method, body, query);
        String response = answerTask.getResponse();
        Integer statusCode = answerTask.getStatusCode();

        httpExchange.sendResponseHeaders(statusCode, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private PairAnswer processTask(String method, String body, String query) {
        String response = "";
        int statusCode = 200;
        switch (method) {
            case "POST":
                Task newTask = gson.fromJson(body, Task.class);
                try {
                    if (taskManager.create(newTask) != null || taskManager.create(newTask).getId() == null) {
                        statusCode = 400;
                        response = "Задача не добавлена!";
                    } else {
                        response = "Задача добавлена!";
                    }
                } catch (TaskManagerException errorCreate) {
                    try {
                        taskManager.update(newTask);
                        response = "Задача обновлена!";
                    } catch (TaskManagerException errorUpdate) {
                        statusCode = 400;
                        response = "Задача не добавлена!";
                    }
                }
                break;
            case "GET":
                if (query == null || query.isEmpty()) {
                    response = gson.toJson(taskManager.getAllTasks());
                } else {
                    var allParameters = ParserQuery.queryToMap(query);
                    if (allParameters.containsKey("id")) {
                        Task task = taskManager.getTask(Integer.parseInt(allParameters.get("id")));
                        if (task == null) {
                            statusCode = 404;
                            break;
                        }
                        response = gson.toJson(task);
                    }
                }
                break;
            case "DELETE":
                if (query == null || query.isEmpty()) {
                    taskManager.clearTasks();
                    response = "Все задачи удалены!";
                } else {
                    var allParameters = ParserQuery.queryToMap(query);
                    if (allParameters.containsKey("id")) {
                        int idTask = Integer.parseInt(allParameters.get("id"));
                        Task task = taskManager.removeTask(idTask);
                        if (task == null) {
                            statusCode = 204;
                            response = "Задача c id " + idTask + " не найдена";
                            break;
                        }
                        response = gson.toJson(task);
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
