package view;

import control.Request;
import control.Response;
import control.Router;
import util.GenericList;
import util.Screen;

import java.util.Arrays;

import static app.Application.HELP;

/**
 * @author javid
 * Created on 12/26/2021
 */
public class Console {

    private static final String AT = "@";
    private String sessionId;
    private String appName;
    private String notAuthorisedUser;
    private String authorisedUser;
    private Router server;

    public Console() {
        appName = "University-Emulator";
        authorisedUser = notAuthorisedUser = Screen.USER_NAME + AT + Screen.PC_NAME + " - " + appName;
        server = new Router();
        loginMenu();
    }

    private void loginMenu() {
        String username = Screen.getString(authorisedUser + " username: ").trim().toLowerCase();
        if (username.isEmpty())
            loginMenu();
        if (username.equals("exit"))
            System.exit(0);

        String password = Screen.getPassword(username + AT + appName + " password: ").trim();

        Request request = getRequest()
                .setKeyWord("login")
                .setUsername(username)
                .setPassword(password);

        Response response = this.server.request(request);

        if (response == null) {
            System.out.println("No response!");
            loginMenu();
        } else if (response.getSessionId() == null) {
            System.out.println(response.getBody());
            loginMenu();
        } else {
            this.sessionId = response.getSessionId();
            authorisedUser = username + AT + this.appName + "> ";
            System.out.println(response.getBody());
            authorisedUserMenu();
        }
    }

    private void authorisedUserMenu() {
        while (true) {
            try {
                String input = Screen.getString(authorisedUser).trim();
                if (input.isEmpty())
                    continue;

                if (input.equalsIgnoreCase("help")) {
                    System.out.println(HELP);
                    continue;
                }

                String[] inputArray = input.split(" +");
                String key = inputArray[0].toLowerCase();
                Request request = getRequest();

                if (inputArray.length == 1) {
                    if (key.equals("exit")) {
                        request.setKeyWord("logout");
                        printResponseBody(server.request(request));
                        System.exit(0);
                        break;
                    } else if (key.equals("logout")) {
                        request.setKeyWord(key);
                        printResponseBody(server.request(request));
                        this.sessionId = null;
                        this.authorisedUser = this.notAuthorisedUser;
                        loginMenu();
                    }
                } else {
                    GenericList<String> commands = new GenericList<>(Arrays.copyOfRange(inputArray, 1, inputArray.length));
                    request.setKeyWord(key)
                            .setBody(commands);
                    printResponseBody(server.request(request));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printResponseBody(Response response) {
        System.out.println(response != null
                ? response.getBody()
                : "No response!");
    }

    private Request getRequest() {
        return new Request().setSessionId(this.sessionId);
    }

}
