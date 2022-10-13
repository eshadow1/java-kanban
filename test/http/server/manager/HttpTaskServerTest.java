package http.server.manager;

import core.task.GeneratorIdTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import http.server.kv.KVServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpTaskServerTest {
    private HttpTaskServer httpTaskServer;
    private KVServer kvServer;
    private HttpClient client;
    private static URI urlTasks;
    private static URI urlTask;
    private static URI urlEpic;
    private static URI urlSubtask;
    private static String jsonTask;
    private static String jsonEpic;
    private static String jsonSubtask;
    private static URI urlTaskId0;
    private static URI urlTaskId1;
    private static URI urlSubtaskId1;
    private static URI urlSubtaskId2;
    private static URI urlSubtaskForEpic;
    private static URI urlEpicId0;
    private static URI urlEpicId1;

    @BeforeAll
    public static void beforeAll() {
        urlTasks = URI.create("http://localhost:8080/tasks/");
        urlTask = URI.create("http://localhost:8080/tasks/task/");
        urlEpic = URI.create("http://localhost:8080/tasks/epic/");
        urlSubtask = URI.create("http://localhost:8080/tasks/subtask/");

        jsonTask = "{\"title\":\"task1\", \"description\":\"test1\"}";
        jsonEpic = "{\"title\":\"epic\", \"description\":\"epic1\"}";
        jsonSubtask = "{\"title\":\"subtask1\", \"description\":\"subtask1\", \"idParentEpic\":0}";

        urlTaskId0 = URI.create("http://localhost:8080/tasks/task/?id=0");
        urlTaskId1 = URI.create("http://localhost:8080/tasks/task/?id=1");

        urlSubtaskId1 = URI.create("http://localhost:8080/tasks/subtask/?id=1");
        urlSubtaskId2 = URI.create("http://localhost:8080/tasks/subtask/?id=2");
        urlSubtaskForEpic = URI.create("http://localhost:8080/tasks/subtask/epic/?id=0");

        urlEpicId0 = URI.create("http://localhost:8080/tasks/epic/?id=0");
        urlEpicId1 = URI.create("http://localhost:8080/tasks/epic/?id=1");
    }

    @BeforeEach
    public void beforeEach() {
        GeneratorIdTask.setStartPosition(GeneratorIdTask.START_GENERATOR);
        try {
            kvServer = new KVServer();
        } catch (IOException error) {
            throw new RuntimeException(error.getMessage());
        }
        kvServer.start();

        try {
            httpTaskServer = new HttpTaskServer();
            httpTaskServer.start();
        } catch (IOException error) {
            throw new RuntimeException(error.getMessage());
        }

        client = HttpClient.newHttpClient();
    }

    @Test
    void getAllTasks() {
        try {
            HttpRequest requestGet = HttpRequest.newBuilder().uri(urlTasks).GET().build();
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseGet.statusCode());
            assertEquals("[]", responseGet.body());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void getAllTask() {
        try {
            HttpRequest requestGet = HttpRequest.newBuilder().uri(urlTask).GET().build();
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseGet.statusCode());
            assertEquals("[]", responseGet.body());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void getAllSubtask() {
        try {
            HttpRequest requestGet = HttpRequest.newBuilder().uri(urlSubtask).GET().build();
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseGet.statusCode());
            assertEquals("[]", responseGet.body());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void getAllEpic() {
        try {
            HttpRequest requestGet = HttpRequest.newBuilder().uri(urlEpic).GET().build();
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseGet.statusCode());
            assertEquals("[]", responseGet.body());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void createAndGetTask() {
        try {
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonTask);
            HttpRequest requestPost = HttpRequest.newBuilder().uri(urlTask).POST(body).build();
            HttpResponse<String> responsePost = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePost.statusCode());

            HttpRequest requestGet = HttpRequest.newBuilder().uri(urlTaskId0).GET().build();
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseGet.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void getTaskNull() {
        try {
            HttpRequest requestGet = HttpRequest.newBuilder().uri(urlTaskId1).GET().build();
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(404, responseGet.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void deleteTask() {
        try {
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonTask);
            HttpRequest requestPost = HttpRequest.newBuilder().uri(urlTask).POST(body).build();
            HttpResponse<String> responsePost = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePost.statusCode());

            HttpRequest requestGet = HttpRequest.newBuilder().uri(urlTaskId0).GET().build();
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseGet.statusCode());

            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlTaskId0).DELETE().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void deleteTaskNull() {
        try {
            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlTaskId0).DELETE().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(204, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void otherRequestTask() {
        try {
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonTask);
            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlTaskId0).PUT(body).build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(400, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void deleteAllTask() {
        try {
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonTask);
            HttpRequest requestPost = HttpRequest.newBuilder().uri(urlTask).POST(body).build();
            HttpResponse<String> responsePost = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePost.statusCode());

            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlTask).DELETE().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void createAndGetSubtask() {
        try {
            final HttpRequest.BodyPublisher bodyEpic = HttpRequest.BodyPublishers.ofString(jsonEpic);
            HttpRequest requestPostEpic = HttpRequest.newBuilder().uri(urlEpic).POST(bodyEpic).build();
            HttpResponse<String> responsePostEpic = client.send(requestPostEpic, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePostEpic.statusCode());

            final HttpRequest.BodyPublisher bodySubtask = HttpRequest.BodyPublishers.ofString(jsonSubtask);
            HttpRequest requestPostSubtask = HttpRequest.newBuilder().uri(urlSubtask).POST(bodySubtask).build();
            HttpResponse<String> responsePostSubtask = client.send(requestPostSubtask, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePostSubtask.statusCode());

            HttpRequest requestGetFirst = HttpRequest.newBuilder().uri(urlSubtaskId1).GET().build();
            HttpResponse<String> responseGetFirst = client.send(requestGetFirst, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, responseGetFirst.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void GetSubtaskForEpic() {
        try {
            final HttpRequest.BodyPublisher bodyEpic = HttpRequest.BodyPublishers.ofString(jsonEpic);
            HttpRequest requestPostEpic = HttpRequest.newBuilder().uri(urlEpic).POST(bodyEpic).build();
            HttpResponse<String> responsePostEpic = client.send(requestPostEpic, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePostEpic.statusCode());

            final HttpRequest.BodyPublisher bodySubtask = HttpRequest.BodyPublishers.ofString(jsonSubtask);
            HttpRequest requestPostSubtask = HttpRequest.newBuilder().uri(urlSubtask).POST(bodySubtask).build();
            HttpResponse<String> responsePostSubtask = client.send(requestPostSubtask, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePostSubtask.statusCode());

            HttpRequest requestGetSubtaskForEpic = HttpRequest.newBuilder().uri(urlSubtaskForEpic).GET().build();
            HttpResponse<String> responseGetSubtaskForEpic = client.send(requestGetSubtaskForEpic, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, responseGetSubtaskForEpic.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void getSubtaskNull() {
        try {
            HttpRequest requestGetSecond = HttpRequest.newBuilder().uri(urlSubtaskId2).GET().build();
            HttpResponse<String> responseGetSecond = client.send(requestGetSecond, HttpResponse.BodyHandlers.ofString());
            assertEquals(404, responseGetSecond.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void deleteSubtask() {
        try {
            final HttpRequest.BodyPublisher bodyEpic = HttpRequest.BodyPublishers.ofString(jsonEpic);
            HttpRequest requestPostEpic = HttpRequest.newBuilder().uri(urlEpic).POST(bodyEpic).build();
            HttpResponse<String> responsePostEpic = client.send(requestPostEpic, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePostEpic.statusCode());

            final HttpRequest.BodyPublisher bodySubtask = HttpRequest.BodyPublishers.ofString(jsonSubtask);
            HttpRequest requestPostSubtask = HttpRequest.newBuilder().uri(urlSubtask).POST(bodySubtask).build();
            HttpResponse<String> responsePostSubtask = client.send(requestPostSubtask, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePostSubtask.statusCode());

            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlSubtaskId1).DELETE().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void deleteSubtaskNull() {
        try {
            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlSubtaskId2).DELETE().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(204, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void otherRequestSubtask() {
        try {
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonSubtask);
            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlSubtaskId1).PUT(body).build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(400, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void deleteAllSubtask() {
        try {
            final HttpRequest.BodyPublisher bodyEpic = HttpRequest.BodyPublishers.ofString(jsonEpic);
            HttpRequest requestPostEpic = HttpRequest.newBuilder().uri(urlEpic).POST(bodyEpic).build();
            HttpResponse<String> responsePostEpic = client.send(requestPostEpic, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePostEpic.statusCode());

            final HttpRequest.BodyPublisher bodySubtask = HttpRequest.BodyPublishers.ofString(jsonSubtask);
            HttpRequest requestPostSubtask = HttpRequest.newBuilder().uri(urlSubtask).POST(bodySubtask).build();
            HttpResponse<String> responsePostSubtask = client.send(requestPostSubtask, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePostSubtask.statusCode());

            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlSubtask).DELETE().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void createAndGetEpic() {
        try {
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonEpic);
            HttpRequest requestPost = HttpRequest.newBuilder().uri(urlEpic).POST(body).build();
            HttpResponse<String> responsePost = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePost.statusCode());

            HttpRequest requestGet = HttpRequest.newBuilder().uri(urlEpicId0).GET().build();
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseGet.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void getEpicNull() {
        try {
            HttpRequest requestGet = HttpRequest.newBuilder().uri(urlEpicId1).GET().build();
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(404, responseGet.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void deleteEpic() {
        try {
            final HttpRequest.BodyPublisher bodyEpic = HttpRequest.BodyPublishers.ofString(jsonEpic);
            HttpRequest requestPostEpic = HttpRequest.newBuilder().uri(urlEpic).POST(bodyEpic).build();
            HttpResponse<String> responsePostEpic = client.send(requestPostEpic, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePostEpic.statusCode());

            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlEpicId0).DELETE().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void deleteEpicNull() {
        try {
            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlEpicId1).DELETE().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(204, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void otherRequestEpic() {
        try {
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonEpic);
            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlEpicId0).PUT(body).build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(400, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void deleteAllEpic() {
        try {
            final HttpRequest.BodyPublisher bodyEpic = HttpRequest.BodyPublishers.ofString(jsonEpic);
            HttpRequest requestPostEpic = HttpRequest.newBuilder().uri(urlEpic).POST(bodyEpic).build();
            HttpResponse<String> responsePostEpic = client.send(requestPostEpic, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePostEpic.statusCode());

            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlEpic).DELETE().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void otherRequest() {
        try {
            URI urlOtherRequest = URI.create("http://localhost:8080/tasks/test");
            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlOtherRequest).GET().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(404, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @Test
    void getHistory() {
        try {
            URI urlHistory = URI.create("http://localhost:8080/tasks/history");
            HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlHistory).GET().build();
            HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseDelete.statusCode());
        } catch (IOException | InterruptedException error) {
            throw new RuntimeException(error);
        }
    }

    @AfterEach
    public void afterEach() {
        httpTaskServer.stop();
        kvServer.stop();
    }

}