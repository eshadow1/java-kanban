import http.server.manager.HttpTaskServer;
import http.server.kv.KVServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new KVServer().start();
            HttpTaskServer httpTaskServer = new HttpTaskServer();
            httpTaskServer.start();
        } catch (IOException error) {
            throw new RuntimeException(error.getMessage());
        }
    }
}