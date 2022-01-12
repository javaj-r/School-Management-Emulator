package control;

import util.GenericList;

/**
 * @author javid
 * Created on 12/26/2021
 */
public class Request {

    private String sessionId;
    private String username;
    private String password;
    private String keyWord;
    private GenericList<String> body;

    public String getSessionId() {
        return sessionId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public GenericList<String> getBody() {
        return body;
    }

    public Request setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public Request setUsername(String username) {
        this.username = username;
        return this;
    }

    public Request setPassword(String password) {
        this.password = password;
        return this;
    }

    public Request setKeyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }

    public Request setBody(GenericList<String> body) {
        this.body = body;
        return this;
    }
}
