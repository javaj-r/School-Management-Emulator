package control;

/**
 * @author javid
 * Created on 12/26/2021
 */
public class Response {

    private String sessionId;
    private String body;

    public String getSessionId() {
        return sessionId;
    }

    public String getBody() {
        return body;
    }

    public Response setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public Response setBody(String body) {
        this.body = body;
        return this;
    }
}
