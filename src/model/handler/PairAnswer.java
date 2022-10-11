package model.handler;

public class PairAnswer {
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
