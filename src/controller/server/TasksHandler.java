package controller.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.Manager;
import controller.task.TaskManager;
import controller.task.TaskManagerException;
import model.task.Epic;
import model.task.Subtask;
import model.task.Task;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TasksHandler implements HttpHandler {
    private class PairAnswer {
        private final Integer statusCode;
        private final String response;

        public PairAnswer(Integer statusCode, String response) {
            this.statusCode = statusCode;
            this.response = response;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public String getResponse() {
            return response;
        }
    }

    private final TaskManager taskManager;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson;

    public TasksHandler() {
        this.taskManager = Manager.getFileBacked();
        gson = new Gson();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        Integer statusCode = 200;
        String response = "";
        String path = httpExchange.getRequestURI().getPath();
        String[] paths = path.split("/");
        String query = httpExchange.getRequestURI().getQuery();

        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

        if (paths.length == 2 && "tasks".equals(paths[1]) && "GET".equals(method)) {
            response = gson.toJson(taskManager.getPrioritizedTasks());
        } else if (paths.length == 3) {
            switch (paths[2]) {
                case "task":
                    var answerTask = processTask(method, body, query);
                    response = answerTask.getResponse();
                    statusCode = answerTask.getStatusCode();
                    break;
                case "subtask":
                    var answerSubtask = processSubtask(method, body, query);
                    response = answerSubtask.getResponse();
                    statusCode = answerSubtask.getStatusCode();
                    break;
                case "epic":
                    var answerEpic = processEpic(method, body, query);
                    response = answerEpic.getResponse();
                    statusCode = answerEpic.getStatusCode();
                    break;
                case "history":
                    response = taskManager.getHistory().toString();
                    break;
                default:
                    break;
            }
        } else if (paths.length > 3) {
            if ("subtask".equals(paths[2]) && "GET".equals(method)) {
                if (query == null || query.isEmpty()) {
                    statusCode = 404;
                } else {
                    var allParameters = queryToMap(query);
                    if (allParameters.containsKey("id")) {
                        response = gson.toJson(taskManager.getSubtasksForEpic(Integer.parseInt(allParameters.get("id"))));
                    }
                }
            }
        }

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
                    taskManager.create(newEpic);
                    response = "Эпик добавлен!";
                } catch (TaskManagerException error) {
                    try {
                        taskManager.update(newEpic);
                        response = "Эпик обновлен!";
                    } catch (TaskManagerException errorUpdate) {
                        statusCode = 400;
                    }
                }
                break;
            case "GET":
                if (query == null || query.isEmpty()) {
                    response = gson.toJson(taskManager.getAllEpics());
                } else {
                    var allParameters = queryToMap(query);
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
                    var allParameters = queryToMap(query);
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
                response = "Вы использовали какой-то другой метод!";
        }
        return new PairAnswer(statusCode, response);
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
                    var allParameters = queryToMap(query);
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
                    var allParameters = queryToMap(query);
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

    private PairAnswer processTask(String method, String body, String query) {
        String response = "";
        int statusCode = 200;
        switch (method) {
            case "POST":
                Task newTask = gson.fromJson(body, Task.class);
                try {
                    taskManager.create(newTask);
                    response = "Задача добавлена!";
                } catch (TaskManagerException errorCreate) {
                    try {
                        taskManager.update(newTask);
                        response = "Задача обновлена!";
                    } catch (TaskManagerException errorUpdate) {
                        statusCode = 400;
                    }
                }
                break;
            case "GET":
                if (query == null || query.isEmpty()) {
                    response = gson.toJson(taskManager.getAllTasks());
                } else {
                    var allParameters = queryToMap(query);
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
                    var allParameters = queryToMap(query);
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
                response = "Вы использовали какой-то другой метод!";
        }
        return new PairAnswer(statusCode, response);
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}
