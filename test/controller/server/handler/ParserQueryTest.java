package controller.server.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserQueryTest {

    @Test
    void checkOneQueryToMap() {
        String query = "id=1";
        var result = ParserQuery.queryToMap(query);
        assertFalse(result.isEmpty());
        assertEquals("1", result.get("id"));
    }

    @Test
    void checkTwoQueryToMap() {
        String query = "id=1&check=2";
        var result = ParserQuery.queryToMap(query);
        assertFalse(result.isEmpty());
        assertEquals("1", result.get("id"));
        assertEquals("2", result.get("check"));
    }
}