package http.server.manager;

import com.sun.net.httpserver.HttpServer;
import core.Manager;
import http.server.manager.handler.EpicHandler;
import http.server.manager.handler.SubtaskHandler;
import http.server.manager.handler.TaskHandler;
import http.server.manager.handler.TasksHandler;
import core.task.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer{
    private static final int PORT = 8080;
    private final HttpServer httpServer;
    private final TaskManager taskManager;

    public HttpTaskServer() throws IOException {
        this.httpServer = HttpServer.create();
        this.taskManager = Manager.getDefault();
    }

    public void start() throws IOException {
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler(taskManager));
        httpServer.createContext("/tasks/task", new TaskHandler(taskManager));
        httpServer.createContext("/tasks/subtask", new SubtaskHandler(taskManager));
        httpServer.createContext("/tasks/epic", new EpicHandler(taskManager));
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stop() {
        httpServer.stop(1);
    }
}
