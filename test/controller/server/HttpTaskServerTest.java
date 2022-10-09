package controller.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class HttpTaskServerTest {

    @Test
    void start() {
        try {
            HttpTaskServer httpTaskServer = new HttpTaskServer();
            httpTaskServer.start();
        } catch (IOException error) {

        }

    }
}