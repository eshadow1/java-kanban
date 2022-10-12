package model.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairAnswerTest {

    @Test
    void checkGetStatusCodeAndGetResponse() {
        int code = 200;
        String response = "ok";
        PairAnswer pairAnswer = new PairAnswer(code, response);
        assertEquals(code, pairAnswer.getStatusCode());
        assertEquals(response, pairAnswer.getResponse());

    }
}