package controller.server.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.task.TaskManager;

import java.io.IOException;
import java.io.OutputStream;

public class TasksHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public TasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        gson = new Gson();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        int statusCode = 200;
        String response = "";
        String path = httpExchange.getRequestURI().getPath();
        String[] paths = path.split("/");

        if (paths.length == 2 && "tasks".equals(paths[1]) && "GET".equals(method)) {
            response = gson.toJson(taskManager.getPrioritizedTasks());
        } else if (paths.length == 3 && "history".equals(paths[2]) && "GET".equals(method)) {
            response = taskManager.getHistory().toString();
        } else {
            statusCode = 404;
        }

        httpExchange.sendResponseHeaders(statusCode, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
